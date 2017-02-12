package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.Connection
import com.orendain.sraw.api._
import com.orendain.sraw.model.extract._

object LoggedInUser extends HasExtractor[LoggedInUser] {

  val extractor = new ObjectExtractor[LoggedInUser] {
    def canExtract(json: JObject) = (json \ "inbox_count") != JNothing
    def extract(json: JObject) = {
      val newJson = json merge JObject(JField("is_friend", JBool(false)))
      val newJson2 = JObject(JField("data", newJson))
      //println(newJson2)
      //println(compact(render(newJson2)))
      new LoggedInUser(newJson2)
    }
  }
}

class LoggedInUser(json: JObject) extends User(json) {

  val over18 = valBoolean("over_18")
  val inboxCount = valInt("inbox_count")
  val hasMail = valBoolean("has_mail")
  val hasModMail = valBoolean("has_mod_mail")
  val goldCreddits = valInt("gold_creddits")
  val goldExpiration = valOp[Long]("gold_expiration")

  // Overrides User
  // although when listing wikirevisions (wikiapi.revisions()),
  // it has is_friend attribute for loggedin accnt
  override val isFriend = false

  def karma(implicit con: Connection) = AccountAPI.karma().process()
  def preferences(implicit con: Connection) = AccountAPI.prefs().process()
  def preferences(prefs: AccountPreferencesEditable)(implicit con: Connection) = AccountAPI.patchPrefs(prefs).process()
  //def trophies(implicit con: Connection) = AccountAPI.trophies().process()
  def friends(implicit con: Connection) = AccountAPI.friends().process()
  def blocked(implicit con: Connection) = AccountAPI.blocked().process()

  def needsCaptcha(implicit con: Connection) = CaptchaAPI.needsCaptcha().process()

  def flair(subreddit: String)(implicit con: Connection) = FlairAPI.userFlairSelector(subreddit)
  def toggleFlair(subreddit: String, enable: Boolean)(implicit con: Connection) = FlairAPI.setEnabled(subreddit, enable)
}
