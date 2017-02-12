package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.model.extract._

object SubredditSettings extends HasExtractor[SubredditSettings] {

  val extractor = new ObjectExtractor[SubredditSettings] {
    def canExtract(json: JObject) = (json \ "kind") == JString("subreddit_settings")
    def extract(json: JObject) = new SubredditSettings(json)
  }
}

class SubredditSettings(val json: JObject) extends RedditThing {

  val kind = "subreddit_settings"

  val default_set = valBoolean("default_set")
  val subreddit_id = valString("subreddit_id")
  val domain = valOp[String]("domain")
  val show_media = valBoolean("show_media")
  val wiki_edit_age = valInt("wiki_edit_age")
  val submit_text = valString("submit_text")
  val spam_links = valString("spam_links")
  val title = valString("title")
  val collapse_deleted_comments = valBoolean("collapse_deleted_comments")



  val wikimode = valString("wikimode")
  val over_18 = valBoolean("over_18")
  val description = valString("description")
  val submit_link_label = valOp[String]("submit_link_label")


  val domain_css = valBoolean("domain_css")
  val domain_sidebar = valBoolean("domain_sidebar")
  val spam_comments = valString("spam_comments")
  val header_hover_text = valOp[String]("header_hover_text")
  val submit_text_label = valOp[String]("submit_text_label")
  val spam_selfposts = valString("spam_selfposts")
  val language = valString("language")
  val wiki_edit_karma = valInt("wiki_edit_karma")

  val hide_ads = valBoolean("hide_ads")
  val public_traffic = valBoolean("public_traffic")
  val public_description = valString("public_description")
  val comment_score_hide_mins = valInt("comment_score_hide_mins")
  val subreddit_type = valString("subreddit_type")
  val exclude_banned_modqueue = valBoolean("exclude_banned_modqueue")

  val content_options = valString("content_options")

}
