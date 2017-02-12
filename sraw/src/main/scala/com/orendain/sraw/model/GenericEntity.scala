package com.orendain.sraw.model

import org.json4s.JsonAST.JValue
import com.orendain.sraw.model.extract._

object GenericEntity extends HasExtractor[GenericEntity] {

  val extractor = new Extractor[GenericEntity] {
    def canExtract(json: JValue) = true
    def extract(json: JValue) = new GenericEntity(json)
  }
}

class GenericEntity(val json: JValue) extends RedditEntity
