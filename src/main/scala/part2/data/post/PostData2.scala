package part2.data.post

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}


case class all_awardings(
                          name: String,
                          coin_price: Int,
                          count: Int
                        )

object all_awardings {
  implicit val awardDecoder: Decoder[all_awardings] = deriveDecoder[all_awardings]
  implicit val awardEncoder: Encoder[all_awardings] = deriveEncoder[all_awardings]
}

case class data(
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
               )
object data {
  implicit val dataDecoder: Decoder[data] = deriveDecoder[data]
  implicit val dataEncoder: Encoder[data] = deriveEncoder[data]
}

case class children(
                     data: data
                   )
object children {
  implicit val childrenDecoder: Decoder[children] = deriveDecoder[children]
  implicit val childrenEncoder: Encoder[children] = deriveEncoder[children]
}

case class childWrapper(
                       children: List[children]
                       )
object childWrapper {
  implicit val decoder: Decoder[childWrapper] = deriveDecoder[childWrapper]
  implicit val encoder: Encoder[childWrapper] = deriveEncoder[childWrapper]
}

// this represents going to a reddit post and adding .json at end
//case class PostData(
//                      data: childWrapper
//                    ) extends TotalPostData
//
//object PostData {
//  implicit val PostDataDecoder: Decoder[PostData] = deriveDecoder[PostData]
//  implicit val PostDataEncoder: Encoder[PostData] = deriveEncoder[PostData]
//}



