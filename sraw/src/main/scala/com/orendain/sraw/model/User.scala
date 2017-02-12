package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.Connection
import com.orendain.sraw.api._
import com.orendain.sraw.model.attribute.Created
import com.orendain.sraw.model.extract._

object User extends HasExtractor[User] {

  def apply(username: String)(implicit con: Connection) =
    UserAPI.about(username).process()

  val extractor = new ObjectExtractor[User] {
    def canExtract(json: JObject) = (json \ "kind") == JString("t2")
    def extract(json: JObject) = new User(json)
  }
}

class User(val json: JObject) extends RedditThing with Created {

  // Override for LoggedInUser
  // besides, kind is checked during User.extract
  val kind = "t2"

  val isFriend = valBoolean("is_friend")
  val ID = valString("id")
  val username = valString("name")
  val hideFromRobots = valBoolean("hide_from_robots")
  val linkKarma = valInt("link_karma")
  val commentKarma = valInt("comment_karma")
  val isGold = valBoolean("is_gold")
  val isMod = valBoolean("is_mod")
  val hasVerifiedEmail = valBoolean("has_verified_email")

  // Created
  val created = valDouble("created")
  val createdUTC = valDouble("created_utc")

  // Gold API
  def gild(months: Int)(implicit con: Connection) = GoldAPI.give(username, months)

  // Flair API
  def deleteFlair(subreddit: String)(implicit con: Connection) =
    FlairAPI.deleteFlair(subreddit, username)

  def setFlair(subreddit: String, CSSClass: String, text: String)(implicit con: Connection) =
    FlairAPI.setUserFlair(subreddit, CSSClass, username, text)

  def flairOptions(subreddit: String)(implicit con: Connection) =
    FlairAPI.userFlairSelector(subreddit, username)

  def selectFlair(subreddit: String, templateID: String, text: String)(implicit con: Connection) =
    FlairAPI.selectUserFlair(subreddit, templateID, username, text)

  // Multi API
  def multis(expand: Boolean)(implicit con: Connection) =
    MultiAPI.by(username, expand)

  // Private Message API
  def message(subject: String, text: String, captcha: FilledCaptcha)(implicit con: Connection) =
    PrivateMessageAPI.composeMessage(username, subject, text, captcha)

  // Thread API
  def threadInvite(thread: String, typ: String, permissions: String)(implicit con: Connection) =
    ThreadAPI.inviteContributor(thread, username, typ, permissions)

  def setThreadPermissions(thread: String, typ: String, permissions: String)(implicit con: Connection) =
    ThreadAPI.setContributorPermissions(thread, username, typ, permissions)

  // User API
  //def friend()
  //def setPermissions(typ: String, permissions: String)(implicit con: Connection) =
    //

  def delete(implicit con: Connection) = UserAPI.delete(username)

  def aboutFriend(implicit con: Connection) = UserAPI.aboutFriend(username)

  def trophies(implicit con: Connection) = UserAPI.trophies(username)

  // bunch of userAPI calls, end of userApi
  def overview(implicit con: Connection) = UserAPI.overview(username)
  def submitted(implicit con: Connection) = UserAPI.submitted(username).process()
  def comments(implicit con: Connection) = UserAPI.comments(username)
  def liked(implicit con: Connection) = UserAPI.liked(username)
  def disliked(implicit con: Connection) = UserAPI.disliked(username)
  def hidden(implicit con: Connection) = UserAPI.hidden(username)
  def saved(implicit con: Connection) = UserAPI.saved(username)
  def gilded(implicit con: Connection) = UserAPI.gilded(username)

  // Wiki API
  def allowWikiEdits(subreddit: String, page: String)(implicit con: Connection) =
    WikiAPI.add(subreddit, page, username)

  def denyWikiEdits(subreddit: String, page: String)(implicit con: Connection) =
    WikiAPI.del(subreddit, page, username)
}
