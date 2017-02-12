package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.model.extract._

object FlairTemplate extends HasExtractor[FlairTemplate] {

  val extractor = new ObjectExtractor[FlairTemplate] {
    def canExtract(json: JObject) = (json \ "flair_template_id") != JNothing
    def extract(json: JObject) = new FlairTemplate(json)
  }
}

class FlairTemplate(val json: JObject) extends RedditObject with Flair {
  val flair_template_id = valString("flair_template_id")
  val flair_position = valString("flair_position")
  val flair_text_editable = valBoolean("flair_text_editable")

  // Flair
  val flairClass = valOp[String]("flair_css_class")
  val flairText = valOp[String]("flair_text")
}


// tmp
// to set:
// type, editable, text, class


//Flair
//  FlairStub
//  FlairInitialized

// package base
// package base.stub
//	 FlairTemplate
//   Subbreddit
//   etc.
// package base.extractor/output
//   FlairTemplate, etc.


// Instead of SubredditStub, just Subreddit(name) -> API call
// Same wither User, Comment, etc?
// Similar with other objects.  Some objects like FlairTemlate
// just optionize unnecessary fields (template_id)
