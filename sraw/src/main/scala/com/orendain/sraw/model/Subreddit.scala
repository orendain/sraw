package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.Connection
import com.orendain.sraw.api.{FlairAPI, SubredditAPI, WikiAPI}
import com.orendain.sraw.model.attribute.Created
import com.orendain.sraw.model.extract._

object Subreddit extends HasExtractor[Subreddit] {

  /**
   *
   */
  def apply(name: String)(implicit con: Connection) = SubredditAPI.about(name).process()

  implicit def Subreddit2String(subreddit: Subreddit) = subreddit.displayName

  val extractor = new ObjectExtractor[Subreddit] {
    def canExtract(json: JObject) = (json \ "kind") == JString("t5")
    def extract(json: JObject) = new Subreddit(json)
  }
}

class Subreddit(val json: JObject) extends RedditThing with Created {

  val kind = "t5"

  val bannerImage = valString("banner_img")
  val activeAccounts = valOp[Int]("accounts_active")
  val comment_score_hide_mins = valInt("comment_score_hide_mins")
  val description = valString("description")
  val descriptionHtml = valOp[String]("description_html")
  val displayName = valString("display_name")
  val headerImg = valOp[String]("header_img")
  val headerTitle = valOp[String]("header_title")
  val over18 = valBoolean("over18")
  val publicDescription = valString("public_description")
  val publicTraffic = valBoolean("public_traffic")
  val subscribers = valInt("subscribers")
  val submissionType = valString("submission_type")
  val submit_link_label = valOp[String]("submit_link_label")
  val submit_text_label = valOp[String]("submit_text_label")
  val submit_text = valString("submit_text")
  val subreddit_type = valString("subreddit_type")
  val title = valString("title")
  val url = valString("url")
  val user_is_banned = valBoolean("user_is_banned")
  val user_is_contributor = valBoolean("user_is_contributor")
  val user_is_moderator = valBoolean("user_is_moderator")
  val user_is_subscriber = valBoolean("user_is_subscriber")
  val collapse_deleted_comments = valBoolean("collapse_deleted_comments")
  val icon_img = valString("icon_img")
  val hide_ads = valBoolean("hide_ads")

  // Created
  val created = valDouble("created")
  val createdUTC = valDouble("created_utc")

  // Options
  val submitTextHtml = valOp[String]("submit_text_html")
  val bannerSize = valOp[String]("banner_size")
  val publicDescription_html = valOp[String]("public_description_html")
  val iconSize = valOp[String]("icon_size")
  val headerSize = valOp[List[Int]]("header_size")


  /**
    *
    */
  def settings()(implicit con: Connection) = SubredditAPI.settings(displayName, true).process()

  /**
    *
    */
  def createWikiPage(pageName: String, content: String, reason: String)(implicit con: Connection) {
    editWikiPage(pageName, content, reason)
  }

  /**
    *
    */
  def editWikiPage(pageName: String, content: String, reason: String)(implicit con: Connection) {
    WikiAPI.edit(displayName, pageName, content, reason).futureProcess()
  }

  /**
    * Accesses the endpoint: [r/<param>]/api/clearflairtemplates
    *
    * @param flairType USER_FLAIR or LINK_FLAIR
    */
  def clearFlairTemplates(flairType: String)(implicit con: Connection) {
    FlairAPI.clearTemplates(displayName, flairType).futureProcess()
  }

  /**
    *
    */
  def deleteFlair(username: String)(implicit con: Connection) {
    FlairAPI.deleteFlair(displayName, username).futureProcess()
  }

  /**
    *
    */
  def deleteFlairTemplate(templateID: String)(implicit con: Connection) {
    FlairAPI.deleteFlairTemplate(displayName, templateID).futureProcess()
  }

  /**
    *
    */
  def setLinkFlair(CSSClass: String, linkFullname: String, text: String)(implicit con: Connection) {
    FlairAPI.setLinkFlair(displayName, CSSClass, linkFullname, text).futureProcess()
  }

  /**
    *
    */
  def setuserFlair(CSSClass: String, username: String, text: String)(implicit con: Connection) {
    FlairAPI.setUserFlair(displayName, CSSClass, username, text).futureProcess()
  }
}
