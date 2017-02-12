package com.orendain.sraw.model

import org.json4s.JsonAST.JObject
import com.orendain.sraw.model.attribute.Created
import com.orendain.sraw.model.extract._

object Post extends HasExtractor[Post] {

  val extractor = new ObjectExtractor[Post] {
    def canExtract(json: JObject) = true
    def extract(json: JObject) = {
      if (PublicPost.extractor.canExtract(json)) PublicPost.extractor.extract(json)
      else if (Message.extractor.canExtract(json)) Message.extractor.extract(json)
      else throw new ExtractionException("Extraction failed.  Input does not conform to a PublicPost.")
    }
  }
}

abstract class Post(override val json: JObject) extends RedditThing with Created {

  // Shared by all
  val kind = json.values("kind").toString

  // Used by Message, Comment, Submission
  val author = valString("author")
  val distinguished = valOp[String]("distinguished")
  val id = valString("id")
  val name = valString("name")
  val subreddit = valOp[String]("subreddit")

  // Created
  val created = valDouble("created")
  val createdUTC = valDouble("created_utc")
}
