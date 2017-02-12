package com.orendain.sraw.util

import org.json4s.JsonAST.JValue
import org.json4s.JsonAST.JArray
import com.orendain.sraw.model._
import com.orendain.sraw.model.extract._

object RedditExtractor extends HasExtractor[RedditEntity] {

  val extractor = new Extractor[RedditEntity] {
    def canExtract(json: JValue) = true
    def extract(json: JValue) =
      extractors.map(_.extractor).filter(_.canExtract(json)).head.extract(json)
  }

  // TODO: fix
  val extractors = Seq(
    Comment,
    LoggedInUser,
    User,
    Submission,
    Message,
    Subreddit,
    Trophy,
    GenericObject,
    GenericEntity
  )
}

object RedditExtractors {

  object CommentTree extends HasExtractor[Listing[Comment]] {

    val extractor = new Extractor[Listing[Comment]] {
      def canExtract(json: JValue) = RedditJson.unwrap(json) match {
        case JArray(arr) => Listing.extractor(Comment).extractor.canExtract(arr.tail.head)
      }
      def extract(json: JValue) = RedditJson.unwrap(json) match {
        case JArray(arr) => Listing.extractor(Comment).extractor.extract(arr.tail.head)
      }
    }
  }

}
