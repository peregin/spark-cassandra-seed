package spark.seed

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


object WordCountApp extends App {

  val conf = new SparkConf()
    .setAppName("Word Count")
    .setMaster("local[*]")
    .set("spark.driver.bindAddress", "localhost")
    .set("spark.driver.host", "localhost")
  val sc = new SparkContext(conf)

  val rdd: RDD[String] = sc.textFile("spark-processor/src/test/resources/kipling.txt")
  println(s"lines: ${rdd.count()}")

  val input = rdd.map(line => line.toLowerCase)

  input.cache()

  val words = input
    .flatMap(line => line.split( """\W+"""))
    .map(word => (word, 1))
    .reduceByKey(_ + _)
  val counter = input.count()
  println(s"words: $words")

  val top10 = words.takeOrdered(10)(Ordering[Int].reverse.on(_._2)).toSeq
  println(s"top10: $top10")

  sc.stop()
}
