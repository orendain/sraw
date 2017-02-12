package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.model.extract._

object More extends HasExtractor[More] {

  val extractor = new ObjectExtractor[More] {
    def canExtract(json: JObject) = (json \ "kind") == JString("more")
    def extract(json: JObject) = new More(json)
  }
}

class More(val json: JObject) extends RedditThing {

  val kind = "more"

  val count = valInt("count")
  val parentID = valString("parent_id")
  val name = valString("name")
  val id = valString("id")

  val children = (json \ "data" \ "children") match {
    case JArray(lst) => lst map {
      case JString(s) => s
      case _ => throw new ExtractionException("Bad Match")
    }
    case _ => throw new ExtractionException("Bad Match")
  }
}
