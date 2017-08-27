package spark.seed

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object CsvStationsApp extends App {

  val conf = new SparkConf()
    .setAppName("Bay Area Bicycle Stations")
    .setMaster("local[*]")
    .set("spark.driver.bindAddress", "localhost")
    .set("spark.driver.host", "localhost")

  val ss = SparkSession.builder().config(conf).getOrCreate()
  val sql = ss.sqlContext

  val df = sql.read.format("csv").option("header", "true").csv("spark-processor/src/test/resources/bike_station_data.csv")
  df.createOrReplaceTempView("station")
  df.printSchema()

  val name = sql.sql("select name from station")
  name.show()

  ss.close()
}
