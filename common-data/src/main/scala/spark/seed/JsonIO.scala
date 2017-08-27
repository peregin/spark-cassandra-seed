package spark.seed

import org.json4s.NoTypeHints
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization

import scala.reflect._

object JsonIO {

  implicit val formats = Serialization.formats(NoTypeHints)

  def write[A <: AnyRef](a: A): String = Serialization.write(a)

  def read[T:Manifest](json: String): T = parse(json).extract[T]
}
