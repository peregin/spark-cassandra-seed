package spark.seed

import org.apache.spark.rdd.RDD

object WordCount {

  def wordCount(rdd: RDD[String]) {
    println(s"lines: ${rdd.count()}")

    val input = rdd.map(line => line.toLowerCase)
    input.cache()

    val words = input
      .flatMap(line => line.split( """\W+"""))
      .map(word => (word, 1))
      .reduceByKey(_ + _)
    val counter = input.count()
    println(s"WORDS: $counter")

    val top10 = words.takeOrdered(10)(Ordering[Int].reverse.on(_._2)).toSeq
    println(s"TOP10: $top10")
  }
}
