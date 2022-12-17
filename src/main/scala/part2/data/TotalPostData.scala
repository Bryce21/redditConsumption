package part2.data

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import part2.data.comment.dataInComment
import part2.data.post.childWrapper
import cats.syntax.functor._
import io.circe.syntax.EncoderOps
import org.apache.kafka.common.serialization.Serde
import part2.implicits._

//import io.circe.{Decoder, Encoder}
//import io.circe.generic.codec.DerivedAsObjectCodec.deriveCodec
//import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
//import io.circe.syntax._
//import org.apache.kafka.common.serialization.Serde
//import part2.data.comment.data
//import part2.data.post.childWrapper
//import part2.implicits._



/*
Simplified json from reddit post
First item in array is Post data
Second item in array is Comment data

This trait represents the two formats.
The two case classes representing both indexes needs to extend this trait
[
  {
    kind: "Listing",
    data: {
      children: [
        {
          kind: "ts",
          data: {
            "subreddit": "oddlysatisfying",
            "title": "Freshy Fallen Snow",
            "upvote_ratio": .97,
            "total_awards_received": 7,
            "author_fullname": "t2_7m66z",
            "score": 28339,
            "created": 1669139605,
            "over_18": false,
            "all_awardings": [
              {
                "name": "Silver",
                "coin_price": 100,
                "count": 1
              },

            ],
            "subreddit_id": "t5_2x93b",
            "id": "z201r0",
            "author": "dittidot",
            "num_comments": 401,
            "created_utc": 1669139605,
            "is_video": false
          }
        }
      ]
    }
  },
  {
    "kind": "Listing",
    "data": [
      {
        // these are all comments
        "children": [
          {
            "kind": "t1",
            "data": {
              "subreddit_id": "t5_2x93b",
              "total_awards_received": 0,
              "subreddit": "oddlysatisfying",
              "replies": { // this can be empty string??
                "kind": "Listing",
                "data": {
                  "children": [
                    {
                      "kind": "t1",
                      "data": {
                        // same comment format as parent
                        "subreddit_id": "t5_2x93b",
                        "total_awards_received": 0,
                        "subreddit": "oddlysatisfying",
                        "replies": "",
                        "id": "ixegr9j",
                        "gilded": 0,
                        "author": "justmelmb",
                        "body": "When I lived up north pictures like that were my favorite things before any bird any sun melting caused any changes, those are the best pictures",
                        "created_utc": 1669150021,
                        "created": 1669150021,
                        "controversiality": 0,
                        "depth": 2,
                        "edited": false,

                      }
                    }
                  ]
                }
              },
              "id": "ixe4g95",
              "gilded": 0,
              "author": "antillian",
              "created_utc": 1669145149,
              "parent_id": "t1_ixdt3yi",
              "score": 121,
              "author_fullname": "t2_495cl",
              "body": "I was thinking the same. I really want to run my hands through it. At the same time, I don’t because it’ll ruin it.",
              "edited": false,
              "created": 1669145149,
              "depth": 1,
              "controversiality": 0,
            }

          }
        ]
      }

    ]

  }
]

 */

sealed trait TotalPostData {}



case class PostData(
                     data: childWrapper
                   ) extends TotalPostData

object PostData {
  implicit val PostDataDecoder: Decoder[PostData] = deriveDecoder[PostData]
  implicit val PostDataEncoder: Encoder[PostData] = deriveEncoder[PostData]
}

case class CommentData(
                        data: List[dataInComment]
                      ) extends TotalPostData

object CommentData {
  implicit val decoder: Decoder[CommentData] = deriveDecoder[CommentData]
  implicit val encoder: Encoder[CommentData] = deriveEncoder[CommentData]
}

object TotalPostData {
  implicit val decoder: Decoder[TotalPostData] = List[Decoder[TotalPostData]](
    Decoder[PostData].widen,
    Decoder[CommentData].widen
  ).reduceLeft(_ or _)

  implicit val encoder: Encoder[TotalPostData] = Encoder.instance {
    case v : PostData => v.asJson
    case v : CommentData => v.asJson
  }

  implicit val implicitSerde: Serde[TotalPostData] = serde[TotalPostData]
}
