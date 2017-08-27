package spark.seed

import com.datastax.spark.connector._
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.cassandra._

//
// CREATE KEYSPACE test WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
// CREATE TABLE test.kv(key text PRIMARY KEY, value int);
//
object CassandraApp extends App {

  val conf = new SparkConf()
    .setAppName("Word Count")
    .setMaster("local[*]")
    .set("spark.driver.bindAddress", "localhost")
    .set("spark.driver.host", "localhost")
    .set("spark.cassandra.connection.port", "32769")
  val sc = new SparkContext(conf)

  val rdd = sc.cassandraTable("test", "kv")
  println(s"count on kv table is ${rdd.count}")

  val collection = sc.parallelize(Seq(("key3", 3), ("key4", 4)))
  collection.saveToCassandra("test", "kv", SomeColumns("key", "value"))

  sc.stop()
}
