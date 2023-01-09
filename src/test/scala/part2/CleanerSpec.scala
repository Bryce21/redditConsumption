package part2

import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}
import org.apache.kafka.streams.TopologyTestDriver
import org.apache.kafka.streams.test.TestRecord
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers.convertToAnyMustWrapper
import part2.AProcessor.RedditURL
import part2.data.cleaned.ICleanedData
import part2.data.dirty.IRawData

import scala.collection.JavaConverters._



case class CleanerSpec() extends AnyFunSpec {

  describe("web data") {
    it("consumes a reddit post json and produces cleaned version") {
      val testTopicName = "subreddit.integrationTest"
      val processor = TopicNameProcessor(testTopicName)
      processor.process()

      val driver: TopologyTestDriver = new TopologyTestDriver(processor.buildTopology)

      val inputTopic = driver.createInputTopic[RedditURL, IRawData](
        testTopicName,
        new StringSerializer,
        IRawData.serializer.serializer()
      )
      inputTopic.pipeInput(
        new TestRecord[RedditURL, IRawData](
          "http://reddit.com/test",
          TestHelper.createRawWebData
        )
      )

      val outputTopic = driver.createOutputTopic(testTopicName + "_cleaned", new StringDeserializer, ICleanedData.serializer.deserializer())

      val output: List[TestRecord[RedditURL, ICleanedData]] = outputTopic.readRecordsToList().asScala.toList
      println(output)
      output.length mustBe 1

    }
  }


}
