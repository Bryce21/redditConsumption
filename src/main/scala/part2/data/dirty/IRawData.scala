package part2.data.dirty

import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder, Json}
import part2.data.dirty.web.RawWebData
import cats.syntax.functor._
import org.apache.kafka.common.serialization.Serde
import part2.data.cleaned.ICleanedData
import part2.implicits._


trait IRawData {
  def clean(): ICleanedData
}

object IRawData {
  implicit val encoder: Encoder[IRawData] = new Encoder[IRawData] {
    override def apply(a: IRawData): Json = a match {
      case v: RawWebData => v.asJson
    }
  }

  implicit val decoder: Decoder[IRawData] = List[Decoder[IRawData]](
    Decoder[RawWebData].widen,
  ).reduceLeft(_ or _)


  implicit val serializer: Serde[IRawData] = serde[IRawData]
}
