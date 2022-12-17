package part2.data.comment

import cats.syntax.functor._
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder, HCursor, Json}

case class replyData(
                      children: List[children]
                    )

object replyData {
  implicit val decoder: Decoder[replyData] = deriveDecoder[replyData]
  implicit val encoder: Encoder[replyData] = deriveEncoder[replyData]
}


case class ReplyWithData(
                    data: replyData
                  ) extends Reply

case class ReplyAsString(value: String = "") extends Reply
object ReplyAsString {
  implicit val decoder: Decoder[ReplyAsString] = (c: HCursor) => {
    for {
      reply <- c.as[String]
    } yield new ReplyAsString(reply)
  }
  implicit val encoder: Encoder[ReplyAsString] = (a: ReplyAsString) => Json.fromString(a.value)
}

object ReplyWithData {
  implicit val decoder: Decoder[ReplyWithData] = deriveDecoder[ReplyWithData]
  implicit val encoder: Encoder[ReplyWithData] = deriveEncoder[ReplyWithData]
}


sealed trait Reply {}
object Reply {
//  implicit val decoder: Decoder[ReplyWithData] = deriveDecoder[ReplyWithData]
//  implicit val encoder: Encoder[ReplyWithData] = deriveEncoder[ReplyWithData]

  implicit val decoder: Decoder[Reply] = List[Decoder[Reply]](
    Decoder[ReplyWithData].widen,
    Decoder[ReplyAsString].widen
  ).reduceLeft(_ or _)

//  implicit val encoder: Encoder[Reply] = {
//    case ReplyAsString(value) => value.asJson
//    case ReplyWithData(data) => data.asJson
//  }

  implicit val encoder: Encoder[Reply] = new Encoder[Reply] {
    override def apply(a: Reply): Json = {
      a match {
        case v: ReplyWithData => v.asJson
        case v: ReplyAsString =>v.asJson
      }
    }
  }

}
sealed trait CommentJson {}

object CommentJson {
  implicit val encoder: Encoder[CommentJson] = {
    case v: cData =>
      v.asJson
    case v: MoreCommentDataJson =>
      v.asJson
  }

  implicit val decoder: Decoder[CommentJson] = List[Decoder[CommentJson]](
    Decoder[cData].widen,
    Decoder[MoreCommentDataJson].widen
  ).reduceLeft(_ or _)


}

// Full comment
case class cData(
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
                        replies: Reply
                      ) extends CommentJson

object cData {
  implicit val decoder: Decoder[cData] = deriveDecoder[cData]
  implicit val encoder: Encoder[cData] = deriveEncoder[cData]
}



// A load more comments placeholder since reddit posts do not load all comments in a tree at once
case class MoreCommentDataJson(
                              children: List[String]
                              ) extends CommentJson
object MoreCommentDataJson {
  implicit val decoder: Decoder[MoreCommentDataJson] = deriveDecoder[MoreCommentDataJson]
  implicit val encoder: Encoder[MoreCommentDataJson] = deriveEncoder[MoreCommentDataJson]
}


case class children(
                     data: CommentJson
                   )
object children {
  implicit val decoder: Decoder[children] = deriveDecoder[children]
  implicit val encoder: Encoder[children] = deriveEncoder[children]
}

case class dataInComment(
                 children: List[children]
               )

object dataInComment {
  implicit val decoder: Decoder[dataInComment] = deriveDecoder[dataInComment]
  implicit val encoder: Encoder[dataInComment] = deriveEncoder[dataInComment]
}

// this is like what you'd see if add .json after a post
//case class CommentData(
//                             data: List[data]
//                           ) extends TotalPostData
//
//object CommentData {
//  implicit val decoder: Decoder[CommentData] = deriveDecoder[CommentData]
//  implicit val encoder: Encoder[CommentData] = deriveEncoder[CommentData]
//}
