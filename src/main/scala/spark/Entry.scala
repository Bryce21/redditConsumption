package spark

import org.apache.spark.sql.functions.{col, udf}
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.{DataFrame, SparkSession}
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json._
import traits.Logging

import scala.util.{Failure, Success, Try}

case class Entry()
object Entry extends Logging[Entry] {

  lazy val spark: SparkSession = SparkSession.builder()
    .master("local[2]")
    .appName("spark-streaming")
    .getOrCreate()


  def process(bootstrap: List[String])(implicit spark: SparkSession): DataFrame = {
    logger.info(s"Using bootstrap servers: ${bootstrap}")
    val rawDF = getRawDF(bootstrap)
    val parsedDF = getParsedDF(rawDF)
    parsedDF
  }



  def getRawDF(bootstrap: List[String]): DataFrame = {
    /*
    Example format:
      +----+--------------------+--------------------+---------+------+--------------------+-------------+
      | key|               value|               topic|partition|offset|           timestamp|timestampType|
      +----+--------------------+--------------------+---------+------+--------------------+-------------+
      |null|[5B 7B 22 6B 69 6...|subreddit.localDe...|        0|     3|2022-11-23 09:34:...|            0|
      +----+--------------------+--------------------+---------+------+--------------------+-------------+
     */
    spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", bootstrap.mkString(","))
      //.option("subscribePattern", "subreddit.*")
      .option("subscribe", "subreddit.test")
      // todo put this back to latest, using earliest for development
      .option("startingOffsets","earliest")
      .load()
  }


  val getPostJSON = (allJson: String) => {
    Try {
      Json.parse(allJson)(0).toString()
    }.toOption.orNull
  }


  def getCommentData(js: JsValue, replyingTo: Option[String] = None): String = {
    val lookups: Map[String, JsValue] = Map(
      "id" -> js \ "data" \ "id",
      "gilded" -> js \ "data" \ "gilded",
      "body" -> js \ "data" \ "body",
      "replyingTo" -> {
        val t: JsLookupResult = replyingTo match {
          case Some(value) => JsDefined(JsString(value))
          case None => JsDefined(JsString("NA"))
        }
        t
       }
    ).filter {
      case (_, JsDefined(value)) => value match {
        case JsNull => false
        case _ => true
      }
      case (_, JsUndefined()) => false
    }.mapValues(_.get)

    new JsObject(lookups).toString()

  }

  // todo should probably bite bullet and use circe with case classes here
  def getCommentJSON(allJson: String, isFirst: Boolean = false, replyingTo: Option[String] = None): List[String] = {
    println("getCommentJSON entered!!")
    Try {
      (if (isFirst) Json.parse(allJson)(1) \ "data" else jsValueToJsLookup(Json.parse(allJson)).result) match {
        case JsDefined(value) => value \ "children" match {
          case JsDefined(value) => {
            value match {
              case JsArray(value) => {
                value.flatMap((js: JsValue) => {
                  val data: String = getCommentData(js, replyingTo)
                  println(s"data: ${data}")
                  val idOption = (js \ "data" \ "id") match {
                    case JsDefined(value) => Some(value.toString)
                    case undefined: JsUndefined => None
                  }
                  js \ "data" \ "replies" \ "data" match {
                    case JsDefined(value) => value match {
                      case JsObject(value2) => List(data) ++ getCommentJSON(
                        value.toString(),
                        isFirst = false,
                        replyingTo = idOption)
                      case _ => List(data)
                    }
                    case _ => List(data)
                  }
                }).toList
              }
              case _ =>
                println("Here 1")
                throw new Exception("Unexpected json format")
            }
          }
          case _ =>
            println("Here 2")
            throw new Exception("Undefined json format")
        }
        case _ =>
          println("Here 3", allJson)
          println("res", jsValueToJsLookup(Json.parse(allJson)))
          println("isFirst", isFirst)
          throw new Exception("Undefined json format")
      }
    } match {
      case Failure(exception) =>
        println(s"Exception: $exception")
        null
      case Success(value) => value
    }
  }


  val postJSON = udf(getPostJSON)
  val commentJSON = udf((js: String) => {
    val res = getCommentJSON(js, true)
    if(res != null){
      println(s"There are ${res.length} comments")
    }
    // println(s"Found ${res.length} comments")
    res
  })

  /*
    This converts
   */
  def getParsedDF(raw: DataFrame): DataFrame = {
    raw
      .select("*")
      .withColumn("key", col("key").cast(StringType))
      .withColumn("value", col("value").cast(StringType))
      .withColumn("postJSON", postJSON(col("value")))
      .withColumn("commentJSON", commentJSON(col("value")))
//      .withColumn("postJSON2", from_json(
//        col("postJSON"), redditPostStruct, Map("mode" -> "FAILFAST"))
//      )
//      .withColumn("commentJSON2", from_json(
//        col("commentJSON"), commentStruct, Map("mode" -> "FAILFAST"))
//      )
      // this can work if can get the array type generation working better
      // .select(col("*"), posexplode(col("value")))
  }
}
