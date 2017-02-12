package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.model.attribute.Created
import com.orendain.sraw.model.extract._

object LabeledMulti extends HasExtractor[LabeledMulti] {

  val extractor = new ObjectExtractor[LabeledMulti] {
    def canExtract(json: JObject) = (json \ "kind") == JString("LabeledMulti")
    def extract(json: JObject) = new LabeledMulti(json)
  }
}

class LabeledMulti(val json: JObject) extends RedditThing with Created {

  val kind = "LabeledMulti"

  val can_edit = valBoolean("can_edit")
  val display_name = valString("display_name")
  val name = valString("name")
  val description_html = valString("description_html")
  val copied_from = valString("copied_from")
  val icon_url = valOp[String]("icon_url")
  val visibility = valString("visibility")
  val icon_name = valString("icon_name")
  val weighting_scheme = valString("weighting_scheme")
  val path = valString("path")
  val key_color = valString("key_color")
  val description_md = valString("description_md")

  // TODO: parse:
//  "subreddits": [{
//            "name": "SRAWTesting"
//        }, {
//            "name": "SRAW"
//        }],
  //
//  val subreddits = (json \ "data" \ "subreddits") match {
//    case JArray(lst) => lst map { MultiSubreddit.extractor.extract(_) }
//    case _ => throw new ExtractionException("Bad Extraction")
//  }

  // Created
  val created = valDouble("created")
  val createdUTC = valDouble("created_utc")
}
