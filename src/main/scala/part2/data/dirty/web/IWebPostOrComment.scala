package part2.data.dirty.web

import cats.syntax.functor._
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder, Json}
import part2.data.cleaned.web.WebCleanedData
import part2.data.cleaned.web.comment.WebCleanedComment
import part2.data.dirty.web.comment.{CommentWithData, IRawWebCommentJson, MoreCommentDataJson, ReplyAsString, ReplyWithData}
import part2.data.dirty.web.post.RawWebPostJson
import part2.data.dirty.web.shared.{IChildrenArrayWrapper, IDataWrapper}

sealed trait IWebPostOrComment {}

case class RawWebPostData(
                           data: IChildrenArrayWrapper[IDataWrapper[RawWebPostJson]]
                         ) extends IWebPostOrComment {
  def toCleanedNoComments: WebCleanedData = {
    data.children.headOption match {
      case Some(v) =>
        WebCleanedData(
          subreddit = v.data.subreddit,
          title = v.data.title,
          upvote_ratio = v.data.upvote_ratio,
          total_awards_received = v.data.total_awards_received,
          author_fullname = v.data.author_fullname,
          score = v.data.score,
          created = v.data.created,
          over_18 = v.data.over_18,
          all_awardings = v.data.all_awardings,
          subreddit_id = v.data.subreddit_id,
          id = v.data.id,
          author = v.data.author,
          num_comments = v.data.num_comments,
          created_utc = v.data.created_utc,
          is_video = v.data.is_video,
          comments = List.empty
        )
      // the weird reddit json format again, excepting a list with only one element
      case None => throw new Exception("Unexpected json format")
    }
  }
}

object RawWebPostData {
  implicit val encoder: Encoder[RawWebPostData] = deriveEncoder[RawWebPostData]
  implicit val decoder: Decoder[RawWebPostData] = deriveDecoder[RawWebPostData]
}

case class RawWebCommentData(
                            data: List[IChildrenArrayWrapper[IDataWrapper[IRawWebCommentJson]]]
                            ) extends IWebPostOrComment {

  def getCleanedComments: List[WebCleanedComment] = {
    def getDataFromWrappedComment(commentJson: IRawWebCommentJson, parentCommentId: Option[String] = None): List[WebCleanedComment] = {
      commentJson match {
        case v: CommentWithData => {
          v.replies match {
            case ReplyAsString(_) => List.empty
            case r: ReplyWithData => v.toCleanedComment(parentCommentId) :: r.data.children.flatMap(d => getDataFromWrappedComment(d.data, Some(v.id)))
          }
        }
        case _: MoreCommentDataJson => List.empty
      }
    }

    data.flatMap(
      _.children.flatMap(wrappedComment => {
        getDataFromWrappedComment(wrappedComment.data)
      })
    )
  }
}
object RawWebCommentData {
  implicit val encoder: Encoder[RawWebCommentData] = deriveEncoder[RawWebCommentData]
  implicit val decoder: Decoder[RawWebCommentData] = deriveDecoder[RawWebCommentData]
}

object IWebPostOrComment {
  implicit val encoder: Encoder[IWebPostOrComment] = new Encoder[IWebPostOrComment] {
    override def apply(a: IWebPostOrComment): Json = a match {
      case v: RawWebPostData => v.asJson
      case v: RawWebCommentData => v.asJson
    }
  }

  implicit val decoder: Decoder[IWebPostOrComment] = List[Decoder[IWebPostOrComment]](
    Decoder[RawWebPostData].widen,
    Decoder[RawWebCommentData].widen,
  ).reduceLeft(_ or _)
}
