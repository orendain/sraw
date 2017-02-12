package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.model.extract._

trait MultiSubreddit extends RedditObject

object MultiSubreddit extends HasExtractor[MultiSubreddit] {

  // TODO: obviously wrong, need to redo
  val extractor = new ObjectExtractor[MultiSubreddit] {
    def canExtract(json: JObject) = (json \ "kind") == JString("t5")
    def extract(json: JObject) =
      json match {
        case x: JObject if (x \ "data") == JNothing => new MultiSubredditName(x)
        case x => new MultiSubredditStub(x)
      }
  }
}
