package com.orendain.sraw.model

import org.json4s.JsonAST.JObject
import com.orendain.sraw.model.extract._

object GenericObject extends HasExtractor[GenericObject] {

  val extractor = new ObjectExtractor[GenericObject] {
    def canExtract(json: JObject) = true
    def extract(json: JObject) = new GenericObject(json)
  }
}

class GenericObject(val json: JObject) extends RedditObject
