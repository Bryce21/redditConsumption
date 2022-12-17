package part2.data.test.dirty.web.comment

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder, HCursor, Json}
import cats.syntax.functor._
import io.circe.syntax.EncoderOps
import part2.data.test.dirty.web.shared.{IChildrenArrayWrapper, IDataWrapper}

sealed trait ICommentReply {}

object ICommentReply {
  implicit val decoder: Decoder[ICommentReply] = List[Decoder[ICommentReply]](
    Decoder[ReplyWithData].widen,
    Decoder[ReplyAsString].widen
  ).reduceLeft(_ or _)


  implicit val encoder: Encoder[ICommentReply] = new Encoder[ICommentReply] {
    override def apply(a: ICommentReply): Json = {
      a match {
        case v: ReplyWithData => v.asJson
        case v: ReplyAsString =>v.asJson
      }
    }
  }

}



case class ReplyAsString(value: String = "") extends ICommentReply
object ReplyAsString {
  implicit val decoder: Decoder[ReplyAsString] = (c: HCursor) => {
    for {
      reply <- c.as[String]
    } yield new ReplyAsString(reply)
  }
  implicit val encoder: Encoder[ReplyAsString] = (a: ReplyAsString) => Json.fromString(a.value)
}


case class ReplyWithData(
                          data: IChildrenArrayWrapper[IDataWrapper[IRawWebCommentJson]]
                        ) extends ICommentReply


object ReplyWithData {
  implicit val decoder: Decoder[ReplyWithData] = deriveDecoder[ReplyWithData]
  implicit val encoder: Encoder[ReplyWithData] = deriveEncoder[ReplyWithData]
}


