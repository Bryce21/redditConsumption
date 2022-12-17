package part2.data.dirty.web.post

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import part2.data.web.{AllAwardings, IWebPostDataProperties}

case class RawWebPostJson(
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
                           is_video: Boolean
                         ) extends IWebPostDataProperties

object RawWebPostJson {
  implicit val decoder: Decoder[RawWebPostJson] = deriveDecoder[RawWebPostJson]
  implicit val encoder: Encoder[RawWebPostJson] = deriveEncoder[RawWebPostJson]
}
