package spark.helpers

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.streaming.OutputMode
object helper {

  def printDF(df: DataFrame, truncate: Boolean = true): Unit = {
    df
      .writeStream
      .format("console")
      .outputMode(OutputMode.Append())
      .option("truncate", truncate.toString)
      .start()
  }
}
