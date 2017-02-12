package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.model.extract._

object FlairList extends HasExtractor[FlairList] {

  val extractor = new ObjectExtractor[FlairList] {
    def canExtract(json: JObject) = (json \ "current") != JNothing
    def extract(json: JObject) = new FlairList(json)
  }
}

class FlairList(val json: JObject) extends RedditObject {

  val currentFlair = UserFlair.extractor.extract((json \ "current"))

  val choices = (json \ "choices") match {
    case JArray(flairs) => flairs map { FlairTemplate.extractor.extract(_) }
    case _ => throw new ExtractionException("Bad Extraction")
  }
}

object UserFlair2 extends HasExtractor[UserFlair2] {

  val extractor = new ObjectExtractor[UserFlair2] {
    def canExtract(json: JObject) = (json \ "user") != JNothing && (json \ "flair_css_class") != JNothing
    def extract(json: JObject) = new UserFlair2(json)
  }
}

class UserFlair2(val json: JObject) extends RedditObject with Flair {

  val username = valString("user")

  // Flair
  val flairClass = valOp[String]("flair_css_class")
  val flairText = valOp[String]("flair_text")
}

object UserFlairList extends HasExtractor[UserFlairList] {

  val extractor = new ObjectExtractor[UserFlairList] {
    def canExtract(json: JObject) = (json \ "users") match { case JArray(_) => true; case _ => false }
    def extract(json: JObject) = new UserFlairList(json)
  }
}

class UserFlairList(val json: JObject) extends RedditObject {

  val users = (json \ "users") match {
    case JArray(users) => users map { UserFlair2.extractor.extract(_) }
    case _ => throw new ExtractionException("Bad Extraction")
  }
}
