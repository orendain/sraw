package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.model.extract._
import com.orendain.sraw.util.RedditJson

object Message extends HasExtractor[Message] {

  val extractor = new ObjectExtractor[Message] {
    def canExtract(json: JObject) =
      (RedditJson.unwrapFirst(json) \ "kind") match {
        case JString(k) if k == "t4" => true
        case _ => false
      }

    def extract(json: JObject) = RedditJson.unwrapFirst(json) match {
      case obj: JObject if canExtract(obj) => new Message(obj)
    }
  }
}

class Message(json: JObject) extends Post(json) {

  // Message
  val body = valString("body")
  val body_html = valString("body_html")
  val parent_id = valOp[String]("parent_id")
  val replies = valOp[String]("replies")

  val was_comment = valBoolean("was_comment")
  val dest = valString("dest")
  val context = valString("context")
  val isNew = valBoolean("new")
  val subject = valString("subject")
  val first_message = valOp[String]("first_message")
  val first_message_name = valOp[String]("first_message_name")
}
