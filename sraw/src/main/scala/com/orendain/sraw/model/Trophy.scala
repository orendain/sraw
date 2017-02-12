package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.model.extract._

object Trophy extends HasExtractor[Trophy] {

  val extractor = new ObjectExtractor[Trophy] {
    def canExtract(json: JObject) = (json \ "kind") == JString("t6")
    def extract(json: JObject) = new Trophy(json)
  }
}

class Trophy(val json: JObject) extends RedditThing {

  val kind = "t6"

  val icon70 = valString("icon_70")
  val icon40 = valString("icon_40")
  val awardID = valString("award_id")
  val id = valString("id")
  val description = valOp[String]("description")
  val url = valOp[String]("url")

  // NOT fullname, this is the awardname
  // rename thing's "name" to "fullname"?
  val name = valString("name")
}


object TrophyList extends HasExtractor[TrophyList] {

  val extractor = new ObjectExtractor[TrophyList] {
    def canExtract(json: JObject) = (json \ "kind") == JString("TrophyList")
    def extract(json: JObject) = new TrophyList(json)
  }
}

class TrophyList(val json: JObject) extends RedditThing {

  val kind = "TrophyList"
  val children = (json \ "data" \ "trophies") match {
    case JArray(lst) => lst map { Trophy.extractor.extract }
    case _ => throw new ExtractionException("Bad Match")
  }
}
