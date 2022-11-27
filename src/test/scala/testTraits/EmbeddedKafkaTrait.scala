package testTraits

import io.github.embeddedkafka.EmbeddedKafka
import org.apache.spark.sql.streaming.{OutputMode, StreamingQuery}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, TestSuite}

trait EmbeddedKafkaTrait extends TestSuite with BeforeAndAfterAll with BeforeAndAfterEach {
  override def afterEach(): Unit = {
    super.afterEach()
    EmbeddedKafka.stop()
  }

  override def beforeEach(): Unit = {
    super.beforeEach()
    EmbeddedKafka.start()
  }

  def saveTable(df: DataFrame, queryName: String = "queryName"): StreamingQuery = {
    df
      .writeStream
      .format("memory")
      .queryName(queryName)
      .outputMode(OutputMode.Append())
      .start()
  }

  def getDF(queryName: String = "queryName")(implicit spark: SparkSession): DataFrame = {
    spark.sql(s"select * from ${queryName}").toDF()
  }

}
