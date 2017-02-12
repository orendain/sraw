package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.model.extract._

object WikiPage extends HasExtractor[WikiPage] {

  val extractor = new ObjectExtractor[WikiPage] {
    def canExtract(json: JObject) = (json \ "kind") == JString("wikipage")
    def extract(json: JObject) = new WikiPage(json)
  }
}

class WikiPage(val json: JObject) extends RedditThing {

  val kind = "wikipage"

  val may_revise = valBoolean("may_revise")
  val revision_date = valDouble("revision_date")
  val content_html = valString("content_html")
  val content_md = valString("content_md")

  // @todo
  // either user or loggedin user
  //val revision_by = (json \ "revision_by" \ "data")
}
