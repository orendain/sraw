package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.model.extract._
import com.orendain.sraw.util.RedditJson

object Comment extends HasExtractor[Comment] {

  val extractor = new ObjectExtractor[Comment] {
    def canExtract(json: JObject) =
      (RedditJson.unwrapFirst(json) \ "kind") == JString("t1")

    def extract(json: JObject) = RedditJson.unwrapFirst(json) match {
      case obj: JObject if canExtract(obj) => new Comment(obj)
    }
  }
}

class Comment(override val json: JObject) extends PublicPost(json) {

  val controversiality = valInt("controversiality")
  val link_id = valString("link_id")
  val score_hidden = valBoolean("score_hidden")
  val body = valString("body")
  val body_html = valString("body_html")
  val parent_id = valString("parent_id")

  val replies = valString("replies")

  //def submission = Submission(parent_id)

  def edit(text: String) = {
    //LinksAndComments.edit(, text)
  }
}
