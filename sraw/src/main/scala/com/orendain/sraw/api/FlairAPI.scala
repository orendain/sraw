package com.orendain.sraw.api

import akka.http.scaladsl.model.HttpMethods.{GET, POST}
import com.orendain.sraw.model._
import com.orendain.sraw.request._

/**
  *
  * @see http://www.reddit.com/dev/api#section_flair
  */
object FlairAPI extends API {

  // Blueprints
  val ClearBP = RequestStubBlueprint(POST, "<param>/api/clearflairtemplates", Scope.ModFlair)
  val DeleteBP = RequestStubBlueprint(POST, "<param>/api/deleteflair", Scope.ModFlair)
  val DeleteTemplateBP = RequestStubBlueprint(POST, "<param>/api/deleteflairtemplate", Scope.ModFlair)
  val SetBP = RequestStubBlueprint(POST, "<param>/api/flair", Scope.ModFlair)
  val ConfigBP = RequestStubBlueprint(POST, "<param>/api/flairconfig", Scope.ModFlair)
  val CSVBP = RequestStubBlueprint(POST, "<param>/api/flaircsv", Scope.ModFlair)
  val ListBP = RequestStubBlueprint(GET, "<param>/api/flairlist", Scope.ModFlair)
  val SelectorBP = RequestStubBlueprint(POST, "<param>/api/flairselector", Scope.Flair)
  val TemplateBP = RequestStubBlueprint(POST, "<param>/api/flairtemplate", Scope.ModFlair)
  val SelectBP = RequestStubBlueprint(POST, "<param>/api/selectflair", Scope.Flair)
  val SetEnabledBP = RequestStubBlueprint(POST, "<param>/api/setflairenabled", Scope.Flair)

  /**
    * Accesses the endpoint: POST [/r/subreddit]/api/clearflairtemplates
    *
    * Remove all of the flair templates defined for a subreddit.
    *
    * Requires [[Scope.ModFlair]] access.
    *
    * @param subreddit name of the subreddit to affect.
    * @param flairType type of flair temlates to remove. Can be "USER_FLAIR" or "LINK_FLAIR"
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @see http://www.reddit.com/dev/api#POST_api_clearflairtemplates
    */
  def clearTemplates(subreddit: String, flairType: String) =
    ClearBP.instantiate(
        params = Seq(sr(subreddit)),
        input = RequestInput(("api_type" -> "json"), ("flair_type" -> flairType)),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST [/r/subreddit]/api/deleteflair
    *
    * Removes a user's subreddit flair.
    *
    * Requires [[Scope.ModFlair]] access.
    *
    * @param subreddit name of the subreddit to affect.
    * @param username name of the user whose flair to remove.
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @see http://www.reddit.com/dev/api#POST_api_deleteflair
    */
  def deleteFlair(subreddit: String, username: String) =
    DeleteBP.instantiate(
        params = Seq(sr(subreddit)),
        input = RequestInput(("api_type" -> "json"),("name" -> username)),
        extractor = RedditNothing)

  // returns error500 on bad tempalteID
  /**
    * Accesses the endpoint: POST [/r/subreddit]/api/deleteflairtemplate
    *
    * Delete a flair template.
    *
    * Requires [[Scope.ModFlair]] access.
    *
    * @param subreddit name of the subreddit to affect.
    * @param templateID ID of the template to remove
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @see http://www.reddit.com/dev/api#POST_api_deleteflairtemplate
    */
  def deleteFlairTemplate(subreddit: String, templateID: String) =
    DeleteTemplateBP.instantiate(
        params = Seq(sr(subreddit)),
        input = RequestInput(("api_type" -> "json"), ("flair_template_id" -> templateID)),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST [/r/subreddit]/api/flair
    *
    * Requires [[Scope.ModFlair]] access.
    *
    * @param subreddit name of the subreddit to affect.
    * @param CSSClass a valid subreddit image name
    * @param linkFullname a fullname of a link
    * @param text a string no longer than 64 characters
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @see http://www.reddit.com/dev/api#POST_api_flair
    */
  def setLinkFlair(subreddit: String, CSSClass: String, linkFullname: String, text: String) =
    SetBP.instantiate(
        params = Seq(sr(subreddit)),
        input = RequestInput(
            ("api_type" -> "json"),
            ("css_class" -> CSSClass),
            ("link" -> linkFullname),
            ("text" -> text)),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST [/r/subreddit]/api/flair
    *
    * Requires [[Scope.ModFlair]] access.
    *
    * @param subreddit name of the subreddit to affect.
    * @param CSSClass a valid subreddit image name
    * @param username a user by name
    * @param text a string no longer than 64 characters
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @see http://www.reddit.com/dev/api#POST_api_flair
    */
  def setUserFlair(subreddit: String, CSSClass: String, username: String, text: String) =
    SetBP.instantiate(
        params = Seq(sr(subreddit)),
        input = RequestInput(
            ("api_type" -> "json"),
            ("css_class" -> CSSClass),
            ("name" -> username),
            ("text" -> text)),
        extractor = RedditNothing)

  // no documentation on reddit, no idea what this endpoint expects for input
  // check other libraries for reference
  /**
    * Accesses the endpoint: POST [/r/subreddit]/api/flaircsv
    *
    * Requires [[Scope.ModFlair]] access.
    *
    * @return a [[RequestStub]] that, when processed, yields a [[???]].
    * @see http://www.reddit.com/dev/api#POST_api_flaircsv
    * @todo figure out
    */
  def CSV(subreddit: String) =
    CSVBP.instantiate(
        params = Seq(sr(subreddit)),
        input = RequestInput("flair_csv" -> ""))

  /**
    * Accesses the endpoint: GET [/r/subreddit]/api/flairlist
    *
    * Requires [[Scope.ModFlair]] access.
    *
    * @param subreddit the subreddit to query.
    * @param listOptions listing options
    * @return a [[RequestStub]] that, when processed, yields a [[UserFlairList]].
    * @see https://www.reddit.com/dev/api#GET_api_flairlist
    */
  def list(subreddit: String, listOptions: ListOptions = ListOptions.Empty) =
    ListBP.instantiate(
        params = Seq(sr(subreddit)),
        input = RequestInput(listOptions.toMap),
        extractor = UserFlairList)

  // but what does templateID do?  to edit?
  /**
    * Accesses the endpoint: POST [/r/subreddit]/api/flairtemplate
    *
    * Creates flair templates for use in subreddits.
    *
    * Requires [[Scope.ModFlair]] access.
    *
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @see http://www.reddit.com/dev/api#POST_api_flairtemplate
    */
  def template(subreddit: String, CSSClass: String, templateID: String, flairType: String, text: String, editable: Boolean) =
    TemplateBP.instantiate(
        params = Seq(sr(subreddit)),
        input = RequestInput(
            ("api_type" -> "json"),
            ("css_class" -> CSSClass),
            ("flair_template_id" -> templateID),
            ("flair_type" -> flairType),
            ("text" -> text),
            ("text_editable" -> editable.toString)),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST [/r/subreddit]/api/selectflair
    *
    * Requires [[Scope.Flair]] access.
    *
    * @param subreddit name of the subreddit to affect.
    * @param templateID
    * @param linkFullname fullname of the link
    * @param text flair text
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @see http://www.reddit.com/dev/api#POST_api_selectflair
    */
  def selectLinkFlair(subreddit: String, templateID: String, linkFullname: String, text: String) =
    SelectBP.instantiate(
        params = Seq(sr(subreddit)),
        input = RequestInput(
            ("api_type" -> "json"),
            ("flair_template_id" -> templateID),
            ("link" -> linkFullname),
            ("text" -> text)),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST [/r/subreddit]/api/selectflair
    *
    * Requires [[Scope.Flair]] access.
    *
    * @param subreddit name of the subreddit to affect.
    * @param templateID
    * @param username username
    * @param text flair text
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @see http://www.reddit.com/dev/api#POST_api_selectflair
    */
  def selectUserFlair(subreddit: String, templateID: String, username: String, text: String) =
    SelectBP.instantiate(
        params = Seq(sr(subreddit)),
        input = RequestInput(
            ("api_type" -> "json"),
            ("flair_template_id" -> templateID),
            ("name" -> username),
            ("text" -> text)),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST [/r/subreddit]/api/setflairenabled
    *
    * Toggles user flair on a subreddit on/off.
    *
    * Requires [[Scope.Flair]] access.
    *
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @see http://www.reddit.com/dev/api#POST_api_setflairenabled
    */
  def setEnabled(subreddit: String, enabled: Boolean) =
    SetEnabledBP.instantiate(
        params = Seq(sr(subreddit)),
        input = RequestInput(
            ("api_type" -> "json"),
            ("flair_enabled" -> enabled.toString)),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST [/r/subreddit]/api/flairconfig
    *
    * Requires [[Scope.ModFlair]] access.
    *
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @see http://www.reddit.com/dev/api#POST_api_flairconfig
    */
  def config(subreddit: String, flairEnabled: Boolean, flairPosition: String, flairSelfEnabled: Boolean,
      linkFlairPosition: String, linkFlairSelfEnabled: Boolean) =
        ConfigBP.instantiate(
            params = Seq(sr(subreddit)),
            input = RequestInput(
                ("api_type" -> "json"),
                ("flair_enabled" -> flairEnabled.toString),
                ("flair_position" -> flairPosition),
                ("flair_self_assign_enabled" -> flairSelfEnabled.toString),
                ("link_flair_position" -> linkFlairPosition),
                ("link_flair_self_assign_enabled" -> linkFlairSelfEnabled.toString)),
            extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST [/r/subreddit]/api/flairselector
    *
    * Requires [[Scope.Flair]] access.
    *
    * @param subreddit name of the subreddit to affect.
    * @param linkFullname fullname of the link
    * @return a [[RequestStub]] that, when processed, yields a [[FlairList]].
    * @note From Reddit API docs: Return information about a users's flair options. If link is given, return link flair options. Otherwise, return user flair options for this subreddit. The signed in user's flair is also returned. Subreddit moderators may give a user by name to instead retrieve that user's flair.
    * @see http://www.reddit.com/dev/api#POST_api_flairselector
    */
  def linkFlairSelector(subreddit: String, linkFullname: String) =
    SelectorBP.instantiate(
        params = Seq(sr(subreddit)),
        input = RequestInput("link" -> linkFullname),
        extractor = FlairList)

  /**
    * Accesses the endpoint: POST [/r/subreddit]/api/flairselector
    *
    * Requires [[Scope.Flair]] access.
    *
    * @param subreddit name of the subreddit to affect.
    * @param linkFullname fullname of the link
    * @return a [[RequestStub]] that, when processed, yields a [[FlairList]].
    * @note From Reddit API docs: Return information about a users's flair options. If link is given, return link flair options. Otherwise, return user flair options for this subreddit. The signed in user's flair is also returned. Subreddit moderators may give a user by name to instead retrieve that user's flair.
    * @see http://www.reddit.com/dev/api#POST_api_flairselector
    */
  def userFlairSelector(subreddit: String, username: String = "") = {
    SelectorBP.instantiate(
        params = Seq(sr(subreddit)),
        input = if (username.isEmpty()) RequestInput("name" -> username) else NoInput,
        extractor = FlairList)
  }
}
