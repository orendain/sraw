package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.model.extract._

object CommentMessage extends HasExtractor[CommentMessage] {

  // Sometimes kind="t1" but the object is a Message
  // so far only observed when mailbox notifies user re:
  // a new posted comment.
  val extractor = new ObjectExtractor[CommentMessage] {
    def canExtract(json: JObject) =
      (json \ "kind") == JString("t1") || (json \ "data" \ "was_comment") == JBool(true)
    def extract(json: JObject) = new CommentMessage(json)
  }
}

class CommentMessage(json: JObject) extends Message(json) {

  val linkTitle = valString("link_title")
  val isLiked = valOp[Boolean]("likes")
}
