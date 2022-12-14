package part2.data.dirty.web.comment

import io.circe.{Decoder, Encoder, Json}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax.EncoderOps
import cats.syntax.functor._
import part2.data.cleaned.web.comment.WebCleanedComment
import part2.data.web.IWebCommentProperties

sealed trait IRawWebCommentJson {}
object IRawWebCommentJson {
  implicit val decoder: Decoder[IRawWebCommentJson] = List[Decoder[IRawWebCommentJson]](
    Decoder[CommentWithData].widen,
    Decoder[MoreCommentDataJson].widen
  ).reduceLeft(_ or _)


  implicit val encoder: Encoder[IRawWebCommentJson] = new Encoder[IRawWebCommentJson] {
    override def apply(a: IRawWebCommentJson): Json = {
      a match {
        case v: CommentWithData => v.asJson
        case v: MoreCommentDataJson =>v.asJson
      }
    }
  }
}


case class CommentWithData(
                  subreddit_id: String,
                  total_awards_received: Int,
                  subreddit: String,
                  id: String,
                  gilded: Int,
                  author: String,
                  // timestamp
                  created_utc: Long,
                  parent_id: String,
                  score: Long,
                  author_fullname: String,
                  body: String,
                  edited: Boolean,
                  // timestamp
                  created: Long,
                  depth: Int,
                  controversiality: Int,
                  replies: ICommentReply
                ) extends IRawWebCommentJson with IWebCommentProperties {
  def toCleanedComment(parentId: Option[String] = None): WebCleanedComment = {
    WebCleanedComment(
      subreddit_id = subreddit_id,
      total_awards_received = total_awards_received,
      subreddit = subreddit,
      id = id,
      gilded = gilded,
      author = author,
      created_utc = created_utc,
      parent_id = parent_id,
      score = score,
      author_fullname = author_fullname,
      body = body,
      edited = edited,
      created = created,
      depth = depth,
      controversiality = controversiality,
      parentCommentId = parentId
    )
  }
}

object CommentWithData {
  implicit val decoder: Decoder[CommentWithData] = deriveDecoder[CommentWithData]
  implicit val encoder: Encoder[CommentWithData] = deriveEncoder[CommentWithData]
}



// A load more comments placeholder since reddit posts do not load all comments in a tree at once
case class MoreCommentDataJson(
                                children: List[String]
                              ) extends IRawWebCommentJson

object MoreCommentDataJson {
  implicit val decoder: Decoder[MoreCommentDataJson] = deriveDecoder[MoreCommentDataJson]
  implicit val encoder: Encoder[MoreCommentDataJson] = deriveEncoder[MoreCommentDataJson]
}
