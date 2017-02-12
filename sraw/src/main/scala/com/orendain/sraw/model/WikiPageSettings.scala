package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.model.extract._

object WikiPageSettings extends HasExtractor[WikiPageSettings] {

  val extractor = new ObjectExtractor[WikiPageSettings] {
    def canExtract(json: JObject) = (json \ "kind") == JString("wikipagesettings")
    def extract(json: JObject) = new WikiPageSettings(json)
  }
}

class WikiPageSettings(val json: JObject) extends RedditThing {

  val kind = "wikipagesettings"

  val permlevel = valInt("permlevel")
  val listed = valBoolean("listed")

  val page = valOp[String]("page")
  val id = valOp[String]("id")

  // todo
  // either user or loggedin user
  //val editors = (json \ "editors" \ "data")
}
