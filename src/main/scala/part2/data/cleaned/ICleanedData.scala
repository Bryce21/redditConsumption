package part2.data.cleaned

import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder, Json}
import org.apache.kafka.common.serialization.Serde
import part2.data.cleaned.web.WebCleanedData
import part2.implicits.serde
import cats.syntax.functor._

trait ICleanedData {
  def comments: List[ICleanedComment] = List.empty
}

object ICleanedData {

  implicit val encoder: Encoder[ICleanedData] = new Encoder[ICleanedData] {
    override def apply(a: ICleanedData): Json = a match {
      case v: WebCleanedData => v.asJson
    }
  }

  implicit val decoder: Decoder[ICleanedData] = List[Decoder[ICleanedData]](
    Decoder[WebCleanedData].widen,
  ).reduceLeft(_ or _)

  implicit val serializer: Serde[ICleanedData] = serde[ICleanedData]
}
