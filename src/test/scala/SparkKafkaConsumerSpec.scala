import io.github.embeddedkafka.EmbeddedKafka.publishStringMessageToKafka
import org.apache.spark.sql.streaming.StreamingQuery
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers
import spark.Entry.{getRawDF, spark}
import testTraits.EmbeddedKafkaTrait

class SparkKafkaConsumerSpec
  extends AnyFunSpec
  with EmbeddedKafkaTrait
  with Matchers {

  implicit val sp = spark

  describe("Consuming from kafka") {

    describe("smoke") {
      it("consumes produced message") {
        val message = "Hello"
        val df = getRawDF(List("localhost:6001"))
        val query: StreamingQuery = saveTable(df)
        publishStringMessageToKafka("subreddit.test", message)
        // can there a max timeout to this?
        query.processAllAvailable()
        query.stop()
        val finalDF = getDF()
        finalDF.show()
        finalDF.count() mustEqual 1
      }

      it("consumes multiple messages") {
        val df = getRawDF(List("localhost:6001"))
        val query: StreamingQuery = saveTable(df)
        (1 to 10).foreach( (i) =>
          publishStringMessageToKafka("subreddit.test", i.toString)
        )
        // can there a max timeout to this?
        query.processAllAvailable()
        query.stop()
        val finalDF = getDF()
        finalDF.show()
        finalDF.count() mustEqual 10
      }
    }

    describe("raw df") {

    }


    describe("parsed df") {

    }
  }
}
