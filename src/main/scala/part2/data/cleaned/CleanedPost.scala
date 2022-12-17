package part2.data.cleaned

import part2.data.post.all_awardings

trait RequiredPostFields {
  def comments: List[ICleanedComment]
  def id: String
  def title: String
}

sealed trait ICleanedPost extends RequiredPostFields {
  def discriminator: String = this.getClass.getSimpleName



}

//object Cleaner {
//  def clean(rawData: TotalRedditPost): ICleanedPost = {
////    var post: PostData = null
////
////    rawData.data.map((postOrComment: TotalPostData) => {
////      postOrComment match {
////        case PostData(data) =>
////        case CommentData(data) =>
////      }
////    })
//  }
//}


object ICleanedPost {

}


object CleanedPost {

}

sealed trait ICleanedComment {}

/*
  This represents data cleaned from parsing the json from web
 */
case class WebCleanedPost(
                         comments: List[ICleanedComment],
                         // todo this is duplicate of raw data, should make a trait and extend it here
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
                         all_awardings: List[all_awardings],
                         subreddit_id: String,
                         id: String,
                         author: String,
                         num_comments: Long,
                         // timestamp
                         created_utc: Long,
                         is_video: Boolean
                         ) extends ICleanedPost {

}

object WebCleanedPost {


}




