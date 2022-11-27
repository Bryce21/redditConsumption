package spark

import org.apache.spark.sql.types._

package object structs {
  lazy val postStruct: StructType = {
    StructType(
      Array(
        StructField(
          "data",
          StructType(
            Array(
              StructField(
                "children",

                ArrayType(
                  StructType(
                    Array(
                      StructField(
                        "data",
                        StructType(
                          Array(
                            StructField(
                              "subreddit",
                              StringType
                            ),
                            StructField(
                              "title",
                              StringType
                            ),
                            // this is actually a number, how to read it here?
                            StructField(
                              "upvote_ratio",
                              StringType
                            ),
                            StructField(
                              "total_awards_received",
                              LongType
                            ),
                            StructField(
                              "author_fullname",
                              StringType
                            ),
                            StructField(
                              "score",
                              LongType
                            ),
                            // utc string timestamp
                            // need to clean it to date with to_date
                            StructField(
                              "created",
                              TimestampType
                            ),
                            StructField(
                              "over_18",
                              BooleanType
                            ),
                            StructField(
                              "all_awardings",
                              ArrayType(
                                StructType(
                                  Array(
                                    StructField(
                                      "name",
                                      StringType
                                    ),
                                    StructField(
                                      "coin_price",
                                      DoubleType
                                    ),
                                    StructField(
                                      "count",
                                      LongType
                                    ),
                                  )
                                )
                              )
                            ),
                            StructField(
                              "subreddit_id",
                              StringType
                            ),
                            StructField(
                              "id",
                              StringType
                            ),
                            StructField(
                              "author",
                              StringType
                            ),
                            StructField(
                              "num_comments",
                              LongType
                            ),
                            // todo clean this with to_date. Utc timestamp
                            StructField(
                              "created_utc",
                              TimestampType
                            ),
                            StructField(
                              "is_video",
                              BooleanType
                            ),
                          )
                        )
                      )
                    )

                  )
                )
              )
            )
          )
        )
      )

    )
  }

//  lazy val flattenedCommentStruct: StructType = {
//    StructType(
//      Array(
//        StructField(
//          "subreddit_id",
//          StringType
//        ),
//        StructField(
//          "total_awards_received",
//          LongType
//        ),
//        StructField(
//          "subreddit",
//          StringType
//        ),
//        // replies
//        StructField(
//          "replies",
//          // recursive
//          StructType(
//            Array(
//              StructField(
//                "data",
//                StructType(
//                  Array(
//                    StructField(
//                      "children",
//                      ArrayType(
//                        flattenedCommentStruct
//                      )
//                    )
//                  )
//                )
//              )
//            )
//          )
//        ),
//
//        StructField(
//          "id",
//          StringType
//        ),
//        StructField(
//          "gilded",
//          LongType
//        ),
//        StructField(
//          "author",
//          StringType
//        ),
//        StructField(
//          "created_utc",
//          TimestampType
//        ),
//        StructField(
//          "parent_id",
//          StringType
//        ),
//        StructField(
//          "score",
//          LongType
//        ),
//        StructField(
//          "author_fullname",
//          StringType
//        ),
//        StructField(
//          "body",
//          StringType
//        ),
//        StructField(
//          "edited",
//          BooleanType
//        ),
//        StructField(
//          "created",
//          TimestampType
//        ),
//        StructField(
//          "depth",
//          LongType
//        ),
//        StructField(
//          "controversiality",
//          LongType
//        ),
//      )
//    )
//  }
//
//  lazy val commentStruct: StructType = {
//    StructType(
//      Array(
//        StructField("data",
//          ArrayType(
//            StructType(
//              Array(
//                StructField("children",
//                  ArrayType(
//                    StructType(
//                      Array(
//                        StructField(
//                          "data",
//                          flattenedCommentStruct
//                        )
//                      )
//                    )
//                  )
//                )
//              )
//            )
//          )
//        )
//      )
//    )
//
//  }

  lazy val redditPostStruct: ArrayType = {
    ArrayType(
      postStruct
    )
  }
}
