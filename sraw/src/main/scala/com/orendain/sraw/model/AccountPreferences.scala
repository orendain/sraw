package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.model.extract._

object AccountPreferences extends HasExtractor[AccountPreferences]{

  def apply(prefs: AccountPreferencesEditable) = extractor.extract(prefs.toJSON)

  val extractor = new ObjectExtractor[AccountPreferences] {
    def canExtract(json: JObject) = (json \ "collapse_left_bar") != JNothing
    def extract(json: JObject) = new AccountPreferences(json)
  }
}

class AccountPreferences(val json: JObject) extends RedditObject {

  val collapseLeftBar = valBoolean("collapse_left_bar")
  val forceHTTPS = valBoolean("force_https")
  val frameCommentsPanel = valBoolean("frame_commentspanel")
  val publicServerSeconds = valBoolean("public_server_seconds")
  val showSnoovatar = valBoolean("show_snoovatar")
  val threadedMessages = valBoolean("threaded_messages")
  val hideDownvotedPosts = valBoolean("hide_downs")
  val emailMessages = valBoolean("email_messages")
  val frame = valBoolean("frame")
  val showLinkFlair = valBoolean("show_link_flair")
  val credditAutorenew = valBoolean("creddit_autorenew")
  val showTrending = valBoolean("show_trending")
  val privateFeeds = valBoolean("private_feeds")
  val monitorMentions = valBoolean("monitor_mentions")
  val clickgadget = valBoolean("clickgadget")
  val media = valString("media")
  val research = valBoolean("research")
  val useGlobal_defaults = valBoolean("use_global_defaults")
  val labelNSFWPosts = valBoolean("label_nsfw")
  val domainDetails = valBoolean("domain_details")
  val showStylesheets = valBoolean("show_stylesheets")
  val highlightControversial = valBoolean("highlight_controversial")
  val noProfanity = valBoolean("no_profanity")
  val over18 = valBoolean("over_18")
  val minCommentScore = valInt("min_comment_score")
  val hideUpvotedPosts = valBoolean("hide_ups")
  val hideFromRobots = valBoolean("hide_from_robots")
  val compress = valBoolean("compress")
  val storeVisits = valBoolean("store_visits")
  val lang = valString("lang")
  val publicVotes = valBoolean("public_votes")
  val organic = valBoolean("organic")
  val collapseReadMessages = valBoolean("collapse_read_messages")
  val showFlair = valBoolean("show_flair")
  val markMessagesRead = valBoolean("mark_messages_read")
  val minLinkScore = valInt("min_link_score")
  val newWindow = valBoolean("newwindow")
  val numSites = valInt("numsites")
  val numComments = valInt("num_comments")
  val highlightNewComments = valBoolean("highlight_new_comments")
  val hideLocationBar = valBoolean("hide_locationbar")
  val stylesheetSverride = valOp[String]("stylesheet_override")
  val showPromote = valOp[Boolean]("show_promote")
  val content_langs = valArr("content_langs")

  // Following three missing, possibly gilded only
//  val showSponsorships = valBoolean("show_sponsorships")
//  val showSponsors = valBoolean("show_sponsors")
//  val showAdbox = valBoolean("show_adbox")


  // todo: enable
  //////def editable() = AccountPreferencesEditable(json)
}
