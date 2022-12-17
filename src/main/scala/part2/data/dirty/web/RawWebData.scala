package part2.data.dirty.web

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import org.apache.kafka.common.serialization.Serde
import part2.data.dirty.IRawData
import part2.implicits._

/*
  This represents the total json of what is consumed from the dirty kafka topic
  The json looks something like this:

  {
    "data": [
      RawWebPostData()
      RawWebCommentData()
    ]
  }

  The default reddit format is an array where first index is the post data and second is the comment data... all wrapped under a ton of data: / children properties
  Makes consuming it really annoying

  Represent the different data with a trait that could be either post or comment
 */
case class RawWebData(
                       data: List[IWebPostOrComment]
                     ) extends IRawData

object RawWebData {
  implicit val decoder: Decoder[RawWebData] = deriveDecoder[RawWebData]
  implicit val encoder: Encoder[RawWebData] = deriveEncoder[RawWebData]

  implicit val serializer: Serde[RawWebData] = serde[RawWebData]
}
