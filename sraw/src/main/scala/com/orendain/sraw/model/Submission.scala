package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.model.extract._
import com.orendain.sraw.util.RedditJson

object Submission extends HasExtractor[Submission] {

  val extractor = new ObjectExtractor[Submission] {
    def canExtract(json: JObject) =
      (RedditJson.unwrapFirst(json) \ "kind") match {
        case JString(k) if k == "t3" => true
        case _ => false
      }

    def extract(json: JObject) = RedditJson.unwrapFirst(json) match {
      case obj: JObject if canExtract(obj) => new Submission(obj)
    }
  }
}

class Submission(override val json: JObject) extends PublicPost(json) {

  val domain = valString("domain")
  val selftext = valString("selftext")
  val clicked = valBoolean("clicked")
  val num_comments = valInt("num_comments")
  val over_18 = valBoolean("over_18")
  val hidden = valBoolean("hidden")
  val thumbnail = valString("thumbnail")
  val stickied = valBoolean("stickied")
  val is_self = valBoolean("is_self")
  val permalink = valString("permalink")
  val url = valString("url")
  val title = valString("title")
  val visited = valBoolean("visited")

  //???
//  val media_embed, JObje
//  val secure_media_embed, JObje
//  val media = valOp[String]("???")
//  val secure_media = valOp[String]("???")

  // Option
  val selftext_html = valOp[String]("selftext_html")
  val link_flair_text = valOp[String]("link_flair_text")
  val link_flair_css_class = valOp[String]("link_flair_css_class")
}




object SubmissionReceipt extends HasExtractor[SubmissionReceipt] {

  val extractor = new ObjectExtractor[SubmissionReceipt] {
    def canExtract(json: JObject) = (json \ "json" \ "data" \ "url") != JNothing
    def extract(json: JObject) = (json \ "json" \ "data") match { case obj: JObject => new SubmissionReceipt(obj) }
  }
}

class SubmissionReceipt(override val json: JObject) extends RedditObject {

  val url = valString("url")
  val id = valString("id")
  val name = valString("name")
}
