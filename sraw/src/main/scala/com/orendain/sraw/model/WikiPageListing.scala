package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.model.extract._

object WikiPageListing extends HasExtractor[WikiPageListing] {

  val extractor = new ObjectExtractor[WikiPageListing] {
    def canExtract(json: JObject) = (json \ "kind") == JString("wikipagelisting")
    def extract(json: JObject) = new WikiPageListing(json)
  }
}

class WikiPageListing(val json: JObject) extends RedditThing {

  val kind = "wikipagelisting"

  val data = (json \ "data") match {
    case JArray(lst) => lst map {
      case JString(s) => s;
      case _ => throw new ExtractionException("Bad Match")
    }
    case _ => throw new ExtractionException("Bad Match")
  }
}
