package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.model.attribute.Created
import com.orendain.sraw.model.extract._

object LiveUpdate extends HasExtractor[LiveUpdate] {

  val extractor = new ObjectExtractor[LiveUpdate] {
    def canExtract(json: JObject) = (json \ "kind") == JString("LiveUpdate")
    def extract(json: JObject) = new LiveUpdate(json)
  }
}

class LiveUpdate(val json: JObject) extends RedditThing with Created {

  val kind = "LiveUpdate"

  val body = valString("body")
  val body_html = valString("body_html")
  val name = valString("name")
  val author = valString("author")
  val stricken = valBoolean("stricken")
  val id = valString("id")

  // not sure about this one
  val embeds = valOp[List[String]]("embeds")


  // Created
  val created = valDouble("created")
  val createdUTC = valDouble("created_utc")
}
