package part2

import io.circe.literal.JsonStringContext
import io.circe.syntax.EncoderOps
import org.scalatest.funspec.AnyFunSpec
import part2.data.dirty.web.{IWebPostOrComment, RawWebCommentData, RawWebData, RawWebPostData}

class DecodingSpec() extends AnyFunSpec {

  describe("RawWebCommentData") {
    it("decodes with reply as string") {
      json"""
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
            }""".as[RawWebCommentData] match {
        case Left(failure) => {
          println("Failed to parse")
          fail(s"Failed to decode: ${failure}")
        }
        case Right(_) => succeed
      }
    }

    it("decodes with terminated more comments") {
      json"""
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
                              },
                              {
                                 "kind": "more",
                                 "data": {
                                   "count": 2,
                                   "name": "t1_iyvt3vs",
                                   "id": "iyvt3vs",
                                   "parent_id": "t1_iyvpnlu",
                                   "depth": 5,
                                   "children": [
                                     "iyvt3vs",
                                     "iyvs1kk"
                                   ]
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
            }""".as[RawWebCommentData] match {
        case Left(failure) => fail(s"Failed to decode: ${failure}")
        case Right(_) => succeed
      }
    }
  }

  describe("RawWebPostData") {
    it("decodes") {
      json"""
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
                         }
                         """.as[RawWebPostData] match {
        case Left(failure) => fail(s"Failed to decode: ${failure}")
        case Right(v) => println(v.asJson)
      }
    }
  }

  describe("IWebPostOrComment class") {

    it("decodes RawWebPostData from IWebPostOrComment") {
      json"""
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
                         }
                         """.as[IWebPostOrComment] match {
        case Left(failure) => fail(s"Failed to decode: ${failure}")
        case Right(v) => v match {
          case RawWebPostData(data) => succeed
          case RawWebCommentData(data) => fail("Except post data decoded got comment data")
        }
      }
    }

    it("decodes RawWebCommentData from IWebPostOrComment") {

    }
  }


  describe("RawWebData class") {
    it("decodes") {
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
          """.as[RawWebData] match {
        case Left(err) => {
          println(err)
          fail(err)
        }
        case Right(value) => succeed
      }
    }
  }





}
