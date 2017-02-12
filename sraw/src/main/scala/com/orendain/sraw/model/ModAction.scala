package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.model.extract._

object ModAction extends HasExtractor[ModAction] {

  val extractor = new ObjectExtractor[ModAction] {
    def canExtract(json: JObject) = (json \ "kind") == JString("modaction")
    def extract(json: JObject) = new ModAction(json)
  }
}

class ModAction(val json: JObject) extends RedditThing {

  val kind = "modaction"
  val description = valOp[String]("description")
  val id = valString("id")
  val mod_id36 = valString("mod_id36")
  val createdUTC = valDouble("created_utc")
  val subreddit = valOp[String]("subreddit")
  val target_permalink = valOp[String]("target_permalink")
  val details = valString("details")
  val action = valString("action")
  val target_author = valString("target_author")
  val mod = valString("mod")
  val sr_id36 = valString("sr_id36")
  val target_fullname = valOp[String]("target_fullname")
}
