package part2.data.web

trait IWebCommentProperties {
  def subreddit_id: String
  def total_awards_received: Int
  def subreddit: String
  def id: String
  def gilded: Int
  def author: String
  // timestamp
  def created_utc: Long
  def parent_id: String
  def score: Long
  def author_fullname: String
  def body: String
  def edited: Boolean
  // timestamp
  def created: Long
  def depth: Int
  def controversiality: Int
}
