package part2

import io.circe.syntax.EncoderOps
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.kafka.streams.TopologyTestDriver
import org.apache.kafka.streams.test.TestRecord
import org.scalatest.funspec.AnyFunSpec
import part2.AProcessor.RedditURL
import part2.data.TotalRedditPost


case class CleanerSpec() extends AnyFunSpec {

  it("consumes a reddit post json") {
    val testTopicName = "subreddit.integrationTest"
    val processor = TopicNameProcessor(testTopicName)
    processor.startProcessing()

    val driver: TopologyTestDriver = new TopologyTestDriver(processor.buildTopology)

    val inputTopic = driver.createInputTopic[RedditURL, TotalRedditPost](
      testTopicName,
      new StringSerializer,
      TotalRedditPost.totalPostSerde.serializer()
    )

    println(TestHelper.createTotalPostFromJson.asJson)

    inputTopic.pipeInput(
      new TestRecord[RedditURL, TotalRedditPost](
        "http://reddit.com/test",
        TestHelper.createTotalPostFromJson
      )
    )
  }
}
