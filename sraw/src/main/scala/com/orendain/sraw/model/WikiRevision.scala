package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.model.extract._

object WikiRevision extends HasExtractor[WikiRevision] {

  val extractor = new ObjectExtractor[WikiRevision] {
    def canExtract(json: JObject) = (json \ "timestamp") != JNothing && (json \ "page") != JNothing
    def extract(json: JObject) = new WikiRevision(json)
  }
}

class WikiRevision(val json: JObject) extends RedditObject {

  val timestamp = valDouble("timestamp")
  val reason = valOp[String]("reason")
  val page = valString("page")
  val id = valString("id")

  // TODO
  // either user or loggedin user
  //val author = (json \ "author" \ "data")
}
