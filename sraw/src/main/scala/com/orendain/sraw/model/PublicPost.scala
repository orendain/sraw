package com.orendain.sraw.model

import org.json4s.JsonAST.{JBool, JDouble, JObject}
import com.orendain.sraw.Connection
import com.orendain.sraw.api.ListingAPI
import com.orendain.sraw.model.attribute.{Commentable, Votable}
import com.orendain.sraw.model.extract._

object PublicPost extends HasExtractor[PublicPost] {

  val extractor = new ObjectExtractor[PublicPost] {
    def canExtract(json: JObject) = true
    def extract(json: JObject) = {
      if (Submission.extractor.canExtract(json)) Submission.extractor.extract(json)
      else if (Comment.extractor.canExtract(json)) Comment.extractor.extract(json)
      else throw new ExtractionException("Extraction failed.  Input does not conform to a PublicPost.")
    }
  }
}

abstract class PublicPost(override val json: JObject) extends Post(json) with Votable with Commentable {

  // Shared by Comment and Submission
  val approved_by = valOp[String]("approved_by")
  val banned_by = valOp[String]("banned_by")
  val archived = valBoolean("archived")
  val author_flair_text = valOp[String]("author_flair_text")
  val author_flair_css_class = valOp[String]("author_flair_css_class")
  val gilded = valInt("gilded")

  val edited = values("edited") match {
    case JBool(status) => status
    case JDouble(value) => value
//    case true => true
//    case false => false
//    case x: Double => x
  }

  val saved = valBoolean("saved")
  val score = valInt("score")
  val mod_reports = valOp[Array[Any]]("mod_reports")
  val user_reports = valOp[Array[Any]]("user_reports")
  val subreddit_id = valString("subreddit_id")
  val report_reasons = valOp[String]("report_reasons")
  val num_reports = valOp[Int]("num_reports")

  // Commentable
  def comments(sort: String = "confidence")(implicit con: Connection) =
    ListingAPI.comments(id, 0, sort).process()

  // Votable
  val upvotes = valInt("ups")
  val downvotes = valInt("downs")
  val isLiked = valOp[Boolean]("likes")
}
