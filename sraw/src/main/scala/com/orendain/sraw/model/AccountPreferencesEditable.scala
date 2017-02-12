package com.orendain.sraw.model

import org.json4s.JsonAST.JObject
import scala.collection.mutable.Map
import com.orendain.sraw.request.RequestInput

// For encoded()
import org.json4s.{Extraction, NoTypeHints}
import org.json4s.native.Serialization

object AccountPreferencesEditable {

  /**
   *
   */
  def apply(prefs: AccountPreferences): AccountPreferencesEditable =
    AccountPreferencesEditable(prefs.json.values)

  /**
   *
   */
  def apply(values: Iterable[(String, Any)]): AccountPreferencesEditable =
    new AccountPreferencesEditable(Map[String, Any]() ++= values)
}

// extends RedditObject?
class AccountPreferencesEditable(val values: Map[String, Any]) extends RequestInput {

  // The following not updating for me (and not listed in API as input).
  // Either non-updatable or requires reddit gold
  def collapseLeftBar(value: Boolean) = set("collapse_left_bar", value)
  def content_langs(value: Seq[String]) = set("content_langs", value)
  def forceHTTPS(value: Boolean) = set("force_https", value)
  def frameCommentspanel(value: Boolean) = set("frame_commentspanel", value)
  def publicServerSeconds(value: Boolean) = set("public_server_seconds", value)
  def showSnoovatar(value: Boolean) = set("show_snoovatar", value)

  def threadedMessages(value: Boolean) = set("threaded_messages", value)
  def hideDownvotedPosts(value: Boolean) = set("hide_downs", value)
  def emailMessages(value: Boolean) = set("email_messages", value)
  def frame(value: Boolean) = set("frame", value)
  def showLinkFlair(value: Boolean) = set("show_link_flair", value)
  def credditAutorenew(value: Boolean) = set("creddit_autorenew", value)
  def showTrending(value: Boolean) = set("show_trending", value)
  def privateFeeds(value: Boolean) = set("private_feeds", value)
  def monitorMentions(value: Boolean) = set("monitor_mentions", value)
  def clickgadget(value: Boolean) = set("clickgadget", value)
  def showSponsors(value: Boolean) = set("show_sponsors", value)
  def research(value: Boolean) = set("research", value)
  def useGlobalDefaults(value: Boolean) = set("use_global_defaults", value)
  def labelNSFWPosts(value: Boolean) = set("label_nsfw", value)
  def domainDetails(value: Boolean) = set("domain_details", value)
  def showStylesheets(value: Boolean) = set("show_stylesheets", value)
  def highlightControversial(value: Boolean) = set("highlight_controversial", value)
  def noProfanity(value: Boolean) = set("no_profanity", value)
  def over18(value: Boolean) = set("over_18", value)
  def hideUpvotedPosts(value: Boolean) = set("hide_ups", value)
  def hideFromRobots(value: Boolean) = set("hide_from_robots", value)
  def compress(value: Boolean) = set("compress", value)
  def storeVisits(value: Boolean) = set("store_visits", value)
  def showAdbox(value: Boolean) = set("show_adbox", value)
  def publicVotes(value: Boolean) = set("public_votes", value)
  def organic(value: Boolean) = set("organic", value)
  def collapseReadMessages(value: Boolean) = set("collapse_read_messages", value)
  def showFlair(value: Boolean) = set("show_flair", value)
  def markMessagesRead(value: Boolean) = set("mark_messages_read", value)
  def newWindow(value: Boolean) = set("newwindow", value)
  def highlightNewComments(value: Boolean) = set("highlight_new_comments", value)
  def hideLocationBar(value: Boolean) = set("hide_locationbar", value)
  def showSponsorships(value: Boolean) = set("show_sponsorships", value)
  def showPromote(value: Boolean) = set("show_promote", value)

  def minLinkScore(value: Int) = set("min_link_score", value)
  def minCommentScore(value: Int) = set("min_comment_score", value)
  def numsites(value: Int) = set("numsites", value)
  def numComments(value: Int) = set("num_comments", value)

  def lang(value: String) = set("lang", value)
  def media(value: String) = set("media", value)
  def stylesheetOverride(value: String) = set("stylesheet_override", value)

  def set(field: String, value: Any) = {
    values += (field -> value)
    this
  }

  // take in an implicit CON and save
  def save() = {
    //
  }

  // TODO: could be simpler
  implicit val formats = Serialization.formats(NoTypeHints)
  def toJSON = Extraction.decompose(values)

  def encoded = RequestInput(toJSON).encoded

  def toAccountPreferences = AccountPreferences(this)
}
