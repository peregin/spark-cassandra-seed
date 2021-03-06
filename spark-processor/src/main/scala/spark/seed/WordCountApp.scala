package spark.seed

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import WordCount._


object WordCountApp extends App {

  val conf = new SparkConf()
    .setAppName("Word Count")
    .setMaster("local[*]")
    .set("spark.driver.bindAddress", "localhost")
    .set("spark.driver.host", "localhost")
  val sc = new SparkContext(conf)

  val rdd: RDD[String] = sc.textFile("spark-processor/src/test/resources/kipling.txt")
  wordCount(rdd)
  sc.stop()
}
