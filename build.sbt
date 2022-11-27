name := "redditConsumption"

version := "0.1"

scalaVersion := "2.12.10"
// A lot of the setup was taken from udemy-spark-stream repo from Daniel from rockthejvm


val sparkVersion = "3.0.2"
val postgresVersion = "42.2.2"
val cassandraConnectorVersion = "3.0.0" // preview version at the moment of writing (July 7, 2020)
val akkaVersion = "2.5.24"
val akkaHttpVersion = "10.1.7"
val twitter4jVersion = "4.0.7"
val kafkaVersion = "2.4.0"
val log4jVersion = "2.4.1"
val nlpLibVersion = "3.5.1"


resolvers ++= Seq(
  "bintray-spark-packages" at "https://dl.bintray.com/spark-packages/maven",
  "Typesafe Simple Repository" at "https://repo.typesafe.com/typesafe/simple/maven-releases",
  "MavenRepository" at "https://mvnrepository.com"
)

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,

  // streaming
  "org.apache.spark" %% "spark-streaming" % sparkVersion,

  // streaming-kafka
  "org.apache.spark" % "spark-sql-kafka-0-10_2.12" % sparkVersion,

  // low-level integrations
  "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion,
  "org.apache.spark" %% "spark-streaming-kinesis-asl" % sparkVersion,


  // logging
  "org.apache.logging.log4j" % "log4j-api" % log4jVersion,
  "org.apache.logging.log4j" % "log4j-core" % log4jVersion,

  // kafka
  "org.apache.kafka" %% "kafka" % kafkaVersion,
  "org.apache.kafka" % "kafka-streams" % kafkaVersion,

  "com.typesafe.play" %% "play-json" % "2.7.3",
  "io.github.embeddedkafka" %% "embedded-kafka" % "3.3.1" % Test,
  "org.scalatest"          %% "scalatest"                     % "3.1.0"          % Test,

)
