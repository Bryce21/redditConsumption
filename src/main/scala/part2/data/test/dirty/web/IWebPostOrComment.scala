package part2.data.test.dirty.web

import cats.syntax.functor._
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder, Json}
import part2.data.test.dirty.web.comment.IRawWebCommentJson
import part2.data.test.dirty.web.post.RawWebPostJson
import part2.data.test.dirty.web.shared.{IChildrenArrayWrapper, IDataWrapper}

sealed trait IWebPostOrComment {}

case class RawWebPostData(
                           data: IChildrenArrayWrapper[IDataWrapper[RawWebPostJson]]
                         ) extends IWebPostOrComment

object RawWebPostData {
  implicit val encoder: Encoder[RawWebPostData] = deriveEncoder[RawWebPostData]
  implicit val decoder: Decoder[RawWebPostData] = deriveDecoder[RawWebPostData]
}

case class RawWebCommentData(
                            data: List[IChildrenArrayWrapper[IDataWrapper[IRawWebCommentJson]]]
                            ) extends IWebPostOrComment
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
