package part2

import org.apache.kafka.streams.kstream.Printed
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala._
import org.apache.kafka.streams.scala.kstream.KStream
import org.apache.kafka.streams.scala.serialization.Serdes
import org.apache.kafka.streams.scala.serialization.Serdes._
import org.apache.kafka.streams.{KafkaStreams, StreamsConfig, Topology}
import part2.AProcessor.RedditURL
import part2.data.cleaned.ICleanedData
import part2.data.dirty.IRawData
import part2.implicits.serde

import java.util.Properties
import java.util.regex.Pattern
import scala.jdk.CollectionConverters.mapAsJavaMapConverter


object AProcessor {
    type RedditURL = String

    def getProperties(config: java.util.Map[_,_] = Map.empty.asJava): Properties = {
      val props = new Properties()
      props.put(StreamsConfig.APPLICATION_ID_CONFIG, "reddit-post")
      props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092")
      props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.stringSerde.getClass)
      props.putAll(config)
      props
    }
}

abstract class AProcessor(
                         props: Properties
                         ) {

  protected val builder = new StreamsBuilder()

  /*
    Part 3 get stream
    This is where logic to process would
   */
  def process(): KStream[AProcessor.RedditURL, ICleanedData]

  /*
    Part 4 get topology
   */
  def buildTopology: Topology = builder.build()
  def buildTopology(topologyProps: Properties): Topology = builder.build(topologyProps)


  /*
    Part 5 get kafka stream from topology and props
   */
  def createKafkaStream(
                      topology: Topology
                    ) = new KafkaStreams(topology, props)


}

case class PatternProcessor(pattern: Pattern, props: Properties = AProcessor.getProperties()) extends AProcessor(props) {
//  override def getStream(builder: StreamsBuilder): KStream[RedditURL, RawWebData] = builder.stream[RedditURL, RawWebData](pattern)
  override def process(): KStream[AProcessor.RedditURL, ICleanedData] =
    builder.stream[AProcessor.RedditURL, IRawData](pattern).mapValues(_.clean())
}

case class TopicNameProcessor(topic: String, props: Properties = AProcessor.getProperties()) extends AProcessor(props) {
  override def process(): KStream[AProcessor.RedditURL, ICleanedData] = {
    val stream = builder.stream[AProcessor.RedditURL, IRawData](topic)
      .mapValues(_.clean())


    stream.to(topic + "_cleaned")


    println("Printing from startProcessing")
    stream.print(Printed.toSysOut[RedditURL, ICleanedData])

    stream
  }
}
