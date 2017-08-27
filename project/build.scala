import sbt._
import Keys._


object dependencies {

  val sparkVersion = "2.2.0"
  val logbackVersion = "1.2.3"
  val specsVersion = "3.7"
  val json4sVersion = "3.2.11"

  val logback = "ch.qos.logback" % "logback-classic" % logbackVersion

  val sparkCore = "org.apache.spark" %% "spark-core" % sparkVersion
  val sparkStreaming = "org.apache.spark" %% "spark-streaming" % sparkVersion
  val sparkSQL = "org.apache.spark" %% "spark-sql" % sparkVersion
  val cassandraSpark = "com.datastax.spark" %% "spark-cassandra-connector" % "2.0.4"

  val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.11.3" % "test"
  val scalaSpec = "org.specs2" %% "specs2" % specsVersion % "test"

  def spark = Seq(sparkCore, sparkStreaming, sparkSQL, cassandraSpark)
  def logging = Seq(logback)
  def json = Seq(
    "org.json4s" %% "json4s-core" % json4sVersion,
    "org.json4s" %% "json4s-jackson" % json4sVersion
  )
  def testing = Seq(scalaCheck, scalaSpec)
}

object sbuild extends Build {

  lazy val buildSettings = Defaults.coreDefaultSettings ++ Seq (
    version <<= version in ThisBuild,
    scalaVersion := "2.11.11",
    organization := "spark.seed",
    description := "Spark Cassandra Seed",
    javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),
    scalacOptions := Seq("-target:jvm-1.8", "-deprecation", "-feature", "-unchecked", "-encoding", "utf8"),
    resolvers ++= Seq(
      "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
    )
  )

  lazy val commonData = Project(
    id = "common-data",
    base = file("common-data"),
    settings = buildSettings ++ Seq(
      libraryDependencies ++= dependencies.json ++ dependencies.testing

    )
  )

  lazy val sparkProcessor = Project(
    id = "spark-processor",
    base = file("spark-processor"),
    dependencies = Seq(commonData),
    settings = buildSettings ++ Seq(
      libraryDependencies ++= dependencies.spark ++ dependencies.testing

    )
  )

  // top level aggregate
  lazy val root = Project(
    id = "spark-cassandra-seed",
    base = file("."),
    settings = buildSettings,
    aggregate = Seq(commonData, sparkProcessor)
  )
}
