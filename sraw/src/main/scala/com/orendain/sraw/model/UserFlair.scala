package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.model.extract._

object UserFlair extends HasExtractor[UserFlair] {

  val extractor = new ObjectExtractor[UserFlair] {
    def canExtract(json: JObject) = (json \ "flair_position") != JNothing
    def extract(json: JObject) = new UserFlair(json)
  }
}

class UserFlair(val json: JObject) extends RedditObject with Flair {

  val flair_template_id = valOp[String]("flair_template_id")
  val flair_position = valString("flair_position")

  // Flair
  val flairClass = valOp[String]("flair_css_class")
  val flairText = valOp[String]("flair_text")
}
