package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.model.extract._

object LabeledMultiDescription extends HasExtractor[LabeledMultiDescription] {

  val extractor = new ObjectExtractor[LabeledMultiDescription] {
    def canExtract(json: JObject) = (json \ "kind") == JString("LabeledMultiDescription")
    def extract(json: JObject) = new LabeledMultiDescription(json)
  }
}

class LabeledMultiDescription(val json: JObject) extends RedditThing {

  val kind = "LabeledMultiDescription"

  val body_html = valString("body_html")
  val body_md = valString("body_md")
}

