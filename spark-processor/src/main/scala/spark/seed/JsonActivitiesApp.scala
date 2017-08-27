package spark.seed

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession


object JsonActivitiesApp extends App {

  val conf = new SparkConf()
    .setAppName("Cycling Activities")
    .setMaster("local[*]")
    .set("spark.driver.bindAddress", "localhost")
    .set("spark.driver.host", "localhost")

  val ss = SparkSession.builder().config(conf).getOrCreate()
  val sql = ss.sqlContext

  val df = sql.read.json("spark-processor/src/test/resources/last30activities.json").toDF()
  df.createOrReplaceTempView("activity")
  df.printSchema()

  val activities = sql.sql("select name from activity")
  activities.show()

  ss.close()
}
