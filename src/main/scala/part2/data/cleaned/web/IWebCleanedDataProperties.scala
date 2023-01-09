package part2.data.cleaned.web

import part2.data.cleaned.ICleanedData
import part2.data.cleaned.web.comment.WebCleanedComment
import part2.data.web.AllAwardings

trait IWebCleanedDataProperties extends ICleanedData {
  def subreddit: String
  def title: String
  // todo this is a percentage can it be parsed as a non string?
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
  override def comments: List[WebCleanedComment] = List.empty
}
