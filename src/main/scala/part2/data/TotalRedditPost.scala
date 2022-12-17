package part2.data

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import org.apache.kafka.common.serialization.Serde
import part2.implicits._

case class TotalRedditPost(data: List[TotalPostData])

object TotalRedditPost {
  implicit val decoder: Decoder[TotalRedditPost] = deriveDecoder[TotalRedditPost]
  implicit val encoder: Encoder[TotalRedditPost] = deriveEncoder[TotalRedditPost]
  implicit def totalPostSerde: Serde[TotalRedditPost] = serde[TotalRedditPost]
}
