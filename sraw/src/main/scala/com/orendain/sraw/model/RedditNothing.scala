package com.orendain.sraw.model

import org.json4s.JsonAST.JValue
import com.orendain.sraw.model.extract._

object RedditNothing extends HasExtractor[RedditNothing] {

  val extractor = new Extractor[RedditNothing] {
    def canExtract(json: JValue) = true
    def extract(json: JValue) = new RedditNothing
  }
}

// TODO: singletonize
class RedditNothing
