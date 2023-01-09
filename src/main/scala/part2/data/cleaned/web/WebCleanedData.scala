package part2.data.cleaned.web

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import org.apache.kafka.common.serialization.Serde
import part2.data.cleaned.web.comment.WebCleanedComment
import part2.data.web.AllAwardings
import part2.implicits.serde

case class WebCleanedData(
                           subreddit: String,
                           title: String,
                           // todo this is a percentage, can it be parsed as a non string?
                           upvote_ratio: String,
                           total_awards_received: Int,
                           author_fullname: String,
                           score: Long,
                           // timestamp
                           created: Long,
                           over_18: Boolean,
                           all_awardings: List[AllAwardings],
                           subreddit_id: String,
                           id: String,
                           author: String,
                           num_comments: Long,
                           // timestamp
                           created_utc: Long,
                           is_video: Boolean,
                           override val comments: List[WebCleanedComment] = List.empty
                         ) extends IWebCleanedDataProperties


object WebCleanedData {
  implicit val decoder: Decoder[WebCleanedData] = deriveDecoder[WebCleanedData]
  implicit val encoder: Encoder[WebCleanedData] = deriveEncoder[WebCleanedData]

  implicit val serializer: Serde[WebCleanedData] = serde[WebCleanedData]
}
