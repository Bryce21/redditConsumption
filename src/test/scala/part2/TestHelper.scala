package part2


import part2.data.post._
import part2.data.{PostData, TotalPostData, TotalRedditPost}
import io.circe.literal._

object TestHelper {

  def createTotalPostFromJson: TotalRedditPost = {
    json"""
         {
            "data": [
                  {
                          "data":{
                            "children":[
                              {
                                "data":{
                                  "subreddit":"oddlysatisfying",
                                  "title":"Freshy Fallen Snow",
                                  "upvote_ratio":".97",
                                  "total_awards_received":7,
                                  "author_fullname":"t2_7m66z",
                                  "score":28339,
                                  "created": 1669139605,
                                  "over_18":false,
                                  "all_awardings":[
                                    {
                                      "name":"Silver",
                                      "coin_price":100,
                                      "count":1
                                    }
                                  ],
                                  "subreddit_id":"t5_2x93b",
                                  "id":"z201r0",
                                  "author":"dittidot",
                                  "num_comments":401,
                                  "created_utc":1669139605,
                                  "is_video":false
                               }
                              }
                            ]
                           }
                  },
                  {
              "kind": "Listing",
              "data": [
                {
                  "children": [
                    {
                      "kind": "t1",
                      "data": {
                        "subreddit_id": "t5_2x93b",
                        "total_awards_received": 0,
                        "subreddit": "oddlysatisfying",
                        "replies": {
                          "kind": "Listing",
                          "data": {
                            "children": [
                              {
                                "kind": "t1",
                                "data": {
                                  "parent_id": "t1_iynr1o9",
                                  "subreddit_id": "t5_2x93b",
                                  "total_awards_received": 0,
                                  "subreddit": "oddlysatisfying",
                                  "id": "ixegr9j",
                                  "gilded": 0,
                                  "author": "justmelmb",
                                  "body": "When I lived up north pictures like that were my favorite things before any bird any sun melting caused any changes, those are the best pictures",
                                  "created_utc": 1669150021,
                                  "created": 1669150021,
                                  "controversiality": 0,
                                  "depth": 2,
                                  "edited": false,
                                  "score": 5,
                                  "author_fullname": "t2_r1waz",
                                  "replies": ""
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
                        "controversiality": 0
                      }
                    }
                  ]
                }
              ]
            }

            ]
         }
          """.as[TotalRedditPost].toOption.get
  }


  def createPostData(
                      subreddit: String = "subreddit",
                      title: String = "title",
                      upvote_ratio: String = "upvote_ratio",
                      total_awards_received: Int = 0,
                      author_fullname: String = "author_fullname",
                      score: Long = 1L,
                      created: Long = 10000L,
                      over_18: Boolean = false,
                      all_awardings: List[all_awardings] = List(
                        new all_awardings(
                          "name",
                          1,
                          1
                        )
                      ),
                      subreddit_id: String = "subreddit_id",
                      id: String = "id",
                      author: String = "author",
                      num_comments: Int = 1,
                      created_utc: Long = 20000L,
                      is_video: Boolean = false
                    ): PostData = {
    PostData(
      data = childWrapper(
        children = List[children](
          children(
            data = new data(
              subreddit = subreddit,
              title = title,
              upvote_ratio = upvote_ratio,
              total_awards_received = total_awards_received,
              author_fullname = author_fullname,
              score = score,
              created = created,
              over_18 = over_18,
              all_awardings = all_awardings,
              subreddit_id = subreddit_id,
              id = id,
              author = author,
              num_comments = num_comments,
              created_utc = created_utc,
              is_video = is_video

            )
          )
        )
      )
    )
  }

  def createTotalPost(
                       subreddit: String = "subreddit",
                       title: String = "title",
                       upvote_ratio: String = "upvote_ratio",
                       total_awards_received: Int = 0,
                       author_fullname: String = "author_fullname",
                       score: Long = 1L,
                       created: Long = 10000L,
                       over_18: Boolean = false,
                       all_awardings: List[all_awardings] = List(
                         new all_awardings(
                           name = "name",
                           coin_price = 1,
                           count = 1
                         )
                       ),
                       subreddit_id: String = "subreddit_id",
                       id: String = "id",
                       author: String = "author",
                       num_comments: Int = 1,
                       created_utc: Long = 20000L,
                       is_video: Boolean = false
                     ): TotalRedditPost = {
    TotalRedditPost(
      data = List[TotalPostData](
        createPostData(
          subreddit,
          title
        ),
//        TotalPostData(
//          CommentData()
//        )
      )
    )
  }
}
