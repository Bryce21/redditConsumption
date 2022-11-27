
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col
import spark.Entry
import spark.helpers.helper.printDF
import traits.Logging


case class Main()
object Main extends Logging[Main] {
  def main(args: Array[String]): Unit = {
    implicit val spark: SparkSession = Entry.spark
    val df = Entry.process(
      List("localhost:29092")
    )
    df.printSchema()
    val t = df.select(col("commentJSON"))
    printDF(t, false)
  }
}
