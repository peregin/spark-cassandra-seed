package spark.seed

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import WordCount._

object FromTextApp extends App {

  val conf = new SparkConf()
    .setAppName("Word Count")
    .setMaster("local[*]")
    .set("spark.driver.bindAddress", "localhost")
    .set("spark.driver.host", "localhost")
  val sc = new SparkContext(conf)

  val text =
    """
      |The data is here.
      |The bicycle is green.
      |Green is cool.
    """.stripMargin
  val rdd: RDD[String] = sc.parallelize(List(text))
  wordCount(rdd)
  sc.stop()
}
