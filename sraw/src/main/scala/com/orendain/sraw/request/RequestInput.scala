package com.orendain.sraw.request

import org.json4s.JsonAST._
import org.json4s.native.JsonMethods._

trait RequestInput {
  def encoded: String
}

object RequestInput {
  def apply(json: JValue) = JSONInput(json: JValue)
  def apply(pairs: Tuple2[String, String]*) = new MapInput(pairs.toMap)
  def apply(map: Map[String, String]) = new MapInput(map)

  def nonKey(json: JValue) = new PostJSONInput(json)
}

case object NoInput extends RequestInput { val encoded = "" }

class MapInput(pairs: Map[String, String]) extends RequestInput {
  val encoded = {
    pairs map { case (s1, s2) => s1 + "=" + s2 } mkString("&")
  }
}

case class JSONInput(json: JValue) extends RequestInput {

  // TODO: URLEncode
  val encoded = {
    val pairs = for {
      JObject(js) <- json
      JField(k,v) <- js
    } yield (k, v)

    pairs map { case (s1, s2) => s1 + "=" + s2 } mkString("&")
  }
}

case class PostJSONInput(json: JValue) extends RequestInput {

  val encoded = compact(render(json))
}
