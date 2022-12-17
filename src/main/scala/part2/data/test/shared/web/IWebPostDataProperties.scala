package part2.data.test.shared.web

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

case class AllAwardings(
                         name: String,
                         coin_price: Int,
                         count: Int
                        )

object AllAwardings {
  implicit val decoder: Decoder[AllAwardings] = deriveDecoder[AllAwardings]
  implicit val encoder: Encoder[AllAwardings] = deriveEncoder[AllAwardings]
}

trait IWebPostDataProperties {
  def subreddit: String
  def title: String
  // todo this is a percentage, can it be parsed as a non string?
  def upvote_ratio: String
  def total_awards_received: Int
  def author_fullname: String
  def score: Long
  // timestamp
  def created: Long
  def over_18: Boolean
  def all_awardings: List[AllAwardings]
  def subreddit_id: String
  def id: String
  def author: String
  def num_comments: Long
  // timestamp
  def created_utc: Long
  def is_video: Boolean
}
