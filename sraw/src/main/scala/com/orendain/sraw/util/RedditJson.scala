package com.orendain.sraw.util

import org.json4s.JsonAST._

object RedditJson {

  def unwrap(json: JValue) = json match {
    case obj: JObject => (obj \ "json" \ "data" \ "things") match {
      case arr: JArray => arr
      case _ => obj
    }
    case _ => json
  }

  def unwrapFirst(json: JValue) = unwrap(json) match {
    case arr: JArray => arr(0)
    case other => other
  }
}
