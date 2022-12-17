package part2

import org.apache.kafka.streams.kstream.Printed

import part2.AProcessor.RedditURL
import part2.data.TotalRedditPost

object Main {
  def main(args: Array[String]): Unit = {


    val processor = TopicNameProcessor("localTest")





    val stream = processor.startProcessing()


    stream.print(Printed.toSysOut[RedditURL, TotalRedditPost])

    val topology = processor.buildTopology

    val kStream = processor.createKafkaStream(topology)


    println("Starting kstream:")
    kStream.start()

    println(topology.describe())
    println(kStream.metrics())

    sys.addShutdownHook {
      kStream.close()
      kStream.cleanUp()
    }
  }
}
