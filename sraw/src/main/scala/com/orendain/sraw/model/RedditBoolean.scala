package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.model.extract._

object RedditBoolean extends HasExtractor[RedditBoolean] {

  val extractor = new Extractor[RedditBoolean] {
    def canExtract(json: JValue) = json match {
      case JString(s) if s == "true" || s == "false" => true
      case obj: JObject => (obj \ "status") match { case JBool(s) => true }
      case _ => false
    }
    def extract(json: JValue) = json match {
      case JString(s) => new RedditBoolean(s.toBoolean)
      case JBool(s) => new RedditBoolean(s)
      case obj: JObject => extract(obj \ "status")
    }
  }
}

class RedditBoolean(val value: Boolean) extends RedditEntity

/*
object RedditNames extends HasExtractor[RedditString] {

  val extractor = new ObjectExtractor[RedditString] {
    def canExtract(json: JObject) = (json \ "names") match {
      case arr: JArray => true
      case _ => false
    }
    def extract(json: JObject) = (json \ "names") match {
      case JArray(arr) => arr map { case JString(s) => new RedditString(s) }
    }
  }
}

class RedditString(val value: String) extends RedditEntity
*/
