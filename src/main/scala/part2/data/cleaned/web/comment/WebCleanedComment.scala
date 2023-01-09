package part2.data.cleaned.web.comment

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import part2.data.cleaned.ICleanedComment
import part2.data.web.IWebCommentProperties

case class WebCleanedComment(
                              subreddit_id: String,
                              total_awards_received: Int,
                              subreddit: String,
                              id: String,
                              gilded: Int,
                              author: String,
                              // timestamp
                              created_utc: Long,
                              parent_id: String,
                              score: Long,
                              author_fullname: String,
                              body: String,
                              edited: Boolean,
                              // timestamp
                              created: Long,
                              depth: Int,
                              controversiality: Int,
                              override val parentCommentId: Option[String] = None
                            ) extends ICleanedComment with IWebCommentProperties


object WebCleanedComment {
  implicit val decoder: Decoder[WebCleanedComment] = deriveDecoder[WebCleanedComment]
  implicit val encoder: Encoder[WebCleanedComment] = deriveEncoder[WebCleanedComment]
}
