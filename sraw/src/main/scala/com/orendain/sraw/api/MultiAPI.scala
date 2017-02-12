package com.orendain.sraw.api

import akka.http.scaladsl.model.HttpMethods.{DELETE, GET, POST, PUT}
import com.orendain.sraw.model._
import com.orendain.sraw.request._

/**
 *
 * @see http://www.reddit.com/dev/api#section_multis
 */
object MultiAPI extends API {

  // Blueprints
  val CopyBP = RequestStubBlueprint(POST, "/api/multi/copy", Scope.Subscribe)
  val MineBP = RequestStubBlueprint(GET, "/api/multi/mine", Scope.Read)
  val RenameBP = RequestStubBlueprint(POST, "/api/multi/rename", Scope.Subscribe)
  val ByBP = RequestStubBlueprint(GET, "/api/multi/user/<param>", Scope.Read)
  val DeleteBP = RequestStubBlueprint(DELETE, "/api/multi/<param>", Scope.Subscribe)
  val ByNameBP = RequestStubBlueprint(GET, "/api/multi/<param>", Scope.Read)

  // honestly, probably just need PUT - no need for both PUT/POST here
  val CreateBP = RequestStubBlueprint(POST, "/api/multi/<param>", Scope.Subscribe)
  val UpdateBP = RequestStubBlueprint(PUT, "/api/multi/<param>", Scope.Subscribe)

  val DescriptionBP = RequestStubBlueprint(GET, "/api/multi/<param>/description", Scope.Read)
  val UpdateDescriptionBP = RequestStubBlueprint(PUT, "/api/multi/<param>/description", Scope.Read)
  val RemoveSubredditBP = RequestStubBlueprint(DELETE, "/api/multi/<param>/r/<param>", Scope.Subscribe)
  val AboutSubredditBP = RequestStubBlueprint(GET, "/api/multi/<param>/r/<param>", Scope.Read)
  val AddSubredditBP = new RequestStubBlueprint(PUT, "/api/multi/<param>/r/<param>", Scope.Subscribe) with PostInput
  // the above APIs also have "filter" instead of the first "multi", investigate

  // MISSED = {"fields": ["from"], "explanation": "that multireddit doesn't exist", "reason": "MULTI_NOT_FOUND"}
  // MISSED = {"fields": ["to"], "explanation": "invalid multi path", "reason": "BAD_MULTI_PATH"}
  // MISSED = {"fields": ["multipath"], "explanation": "that multireddit already exists", "reason": "MULTI_EXISTS"}
  /**
    * Accesses the endpoint: POST /api/multi/copy
    *
    * Requires [[Scope.Subscribe]] access.
    *
    * @param displayName name of the multi, no longer than 50 characters
    * @param from multireddit url path
    * @param to destination multireddit url path
    * @return a [[RequestStub]] that, when processed, yields a [[LabeledMulti]].
    * @note From Reddit API docs: Copy a multi. A "copied from ..." line will automatically be appended to the description.
    * @see http://www.reddit.com/dev/api#POST_api_multi_copy
    * @example copy("My New Multireddit", "/user/orendain/m/testMulti", "/user/orendain/m/myNewMulti")
    */
  def copy(displayName: String, from: String, to: String) =
    CopyBP.instantiate(
        input = RequestInput(("display_name" -> displayName), ("from" -> from), ("to" -> to)),
        extractor = LabeledMulti)


  // returns a JArray of LabeledMulti's
  /**
    * Accesses the endpoint: GET /api/multi/mine
    *
    * Requires [[Scope.Read]] access.
    *
    * @param expand ???
    * @return a [[RequestStub]] that, when processed, yields a [[???]].
    * @note From Reddit API docs: Fetch a list of multis belonging to the current user.
    * @see http://www.reddit.com/dev/api#GET_api_multi_mine
    * @todo needs an extractor that can parse a jarray
    */
  def mine(expand: Boolean) =
    MineBP.instantiate(
        input = RequestInput(("expand_srs" -> expand.toString)),
        extractor = LabeledMulti)

  /**
    * Accesses the endpoint: POST /api/multi/rename
    *
    * Requires [[Scope.Read]] access.
    *
    * @param displayName name of the multi, no longer than 50 characters
    * @param from multireddit url path
    * @param to destination multireddit url path
    * @return a [[RequestStub]] that, when processed, yields a [[LabeledMulti]].
    * @note From Reddit API docs: Rename a multi.
    * @see http://www.reddit.com/dev/api#POST_api_multi_rename
    * @todo needs an extractor that can parse a jarray
    */
  def rename(displayName: String, from: String, to: String) =
    RenameBP.instantiate(
        input = RequestInput(("display_name" -> displayName), ("from" -> from), ("to" -> to)),
        extractor = LabeledMulti)

  /**
    * Accesses the endpoint: GET /api/multi/user/[username]
    *
    * Requires [[Scope.Read]] access.
    *
    * @param username a valid, existing reddit username
    * @param expand ???
    * @return a [[RequestStub]] that, when processed, yields a [[LabeledMulti]].
    * @note From Reddit API docs: Fetch a list of public multis belonging to a username.
    * @see http://www.reddit.com/dev/api#GET_api_multi_user_{username}
    * @todo parse, its a jarray of multi's like rename()
    */
  def by(username: String, expand: Boolean) =
    ByBP.instantiate(
        params = Seq(username),
        input = RequestInput(("expand_srs" -> expand.toString)),
        extractor = LabeledMulti)


  // MISSED = {"fields": ["multipath"], "explanation": "that multireddit doesn't exist", "reason": "MULTI_NOT_FOUND"}
  /**
    * Accesses the endpoint: DELETE /api/multi/[multipath]
    *
    * Requires [[Scope.Subscribe]] access.
    *
    * @param multipath multireddit url path
    * @param expand ???
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Delete a multi.
    * @see http://www.reddit.com/dev/api#DELETE_api_multi_{multipath}
    */
  def delete(multipath: String, expand: Boolean) =
    DeleteBP.instantiate(
        params = Seq(multipath),
        input = RequestInput(("expand_srs" -> expand.toString)),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: GET /api/multi/[multipath]
    *
    * Requires [[Scope.Read]] access.
    *
    * @param multipath multireddit url path
    * @param expand ???
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Fetch a multi's data and subreddit list by name.
    * @see http://www.reddit.com/dev/api#GET_api_multi_{multipath}
    * @example byName("/user/orendain/m/testMulti", true)
    */
  def byName(multipath: String, expand: Boolean) =
    ByNameBP.instantiate(
        params = Seq(multipath),
        input = RequestInput(("expand_srs" -> expand.toString)),
        extractor = LabeledMulti)


   // TODO
  // need to feed JSON into endpoint
//  def create(multipath: String, model: JObject, expand: Boolean) = {
//    val params = Seq(multipath)
//    val data = MapInput(Map(("model" -> model), ("expand_srs" -> expand.toString)))
//    con.process(RequestStub(MultiCreate, params, data), GenericRedditObjectResponse)
//  }

  /**
    * Accesses the endpoint: GET /api/multi/[multipath]/description
    *
    * Requires [[Scope.Read]] access.
    *
    * @param multipath multireddit url path
    * @return a [[RequestStub]] that, when processed, yields a [[LabeledMultiDescription]].
    * @note From Reddit API docs: Get a multi's description.
    * @see http://www.reddit.com/dev/api#GET_api_multi_{multipath}_description
    */
  def description(multipath: String) =
    DescriptionBP.instantiate(
        params = Seq(multipath),
        extractor = LabeledMultiDescription)

    // TODO: need to feed JSON into endpoint
//  def updateDescription(multipath: String, model: JObject) = {
//    val params = Seq(multipath)
//    val data = MapInput(Map(("page" -> page)))
//    con.process(RequestStub(MultiUpdateDescription, params, data), GenericRedditObjectResponse)
//  }
//


  /**
    * Accesses the endpoint: DELETE /api/multi/[multipath]/r/[subreddit]
    *
    * Requires [[Scope.Read]] access.
    *
    * @param multipath multireddit url path
    * @param subreddit subreddit name
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Remove a subreddit from a multi.
    * @see http://www.reddit.com/dev/api#DELETE_api_multi_{multipath}_r_{srname}
    */
  def removeSubreddit(multipath: String, subreddit: String) =
    RemoveSubredditBP.instantiate(
        params = Seq(multipath, subreddit),
        extractor = RedditNothing)

  // TODO
  // {"name": "SRAW"}
  // tried with expand parameter, not recognizing ... really is that basic
  /**
    * Accesses the endpoint: GET /api/multi/[multipath]/r/[subreddit]
    *
    * Requires [[Scope.Read]] access.
    *
    * @param multipath multireddit url path
    * @param subreddit subreddit name
    * @return a [[RequestStub]] that, when processed, yields [[???]].
    * @note From Reddit API docs: Remove a subreddit from a multi.
    * @see http://www.reddit.com/dev/api#DELETE_api_multi_{multipath}_r_{srname}
    */
  def aboutSubreddit(multipath: String, subreddit: String) =
    AboutSubredditBP.instantiate(
        params = Seq(multipath, subreddit))


  /**
    * Accesses the endpoint: PUT /api/multi/[multipath]/r/[subreddit]
    *
    * Requires [[Scope.Subscribe]] access.
    *
    * @param multipath multireddit url path
    * @param subreddit subreddit name
    * @return a [[RequestStub]] that, when processed, yields [[???]].
    * @note From Reddit API docs: Add a subreddit to a multi.
    * @see http://www.reddit.com/dev/api#PUT_api_multi_{multipath}_r_{srname}
    * @todo fix nonkey input ... also ... well-define multi paths (with or w/o leading "/")
    */
  def addSubreddit(multipath: String, subreddit: String) = {
//    val params = Seq(multipath, subreddit)
//    val data = MapInput(Map(("model" -> model)))
//    con.process(RequestStub(MultiAddSubreddit, params, data), GenericRedditObjectResponse)
    import org.json4s.JsonDSL._
    AddSubredditBP.instantiate(
        params = Seq(multipath, subreddit),
        input = RequestInput.nonKey(("name" -> subreddit)))
  }


}
