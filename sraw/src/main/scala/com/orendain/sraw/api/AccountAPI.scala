package com.orendain.sraw.api

import akka.http.scaladsl.model.HttpMethods.{GET, PATCH}
import com.orendain.sraw.model._
import com.orendain.sraw.request._

/**
 *
 * @see http://www.reddit.com/dev/api#section_account
 */
object AccountAPI {

  // Blueprints
  val MeBP = RequestStubBlueprint(GET, "/api/v1/me", Scope.Identity)
  val KarmaBP = RequestStubBlueprint(GET, "/api/v1/me/karma", Scope.MySubreddits)
  val PrefsBP = RequestStubBlueprint(GET, "/api/v1/me/prefs", Scope.Identity)
  val PatchPrefsBP = RequestStubBlueprint(PATCH, "/api/v1/me/prefs", Scope.Account)
  val TrophiesBP = RequestStubBlueprint(GET, "/api/v1/me/trophies", Scope.Identity)
  val FriendsBP = RequestStubBlueprint(GET, "/api/v1/me/friends", Scope.Read)
  val BlockedBP = RequestStubBlueprint(GET, "/prefs/blocked", Scope.Read)

  /**
    * Accesses the endpoint: GET /api/v1/me
    *
    * Requires [[Scope.Identity]] access.
    *
    * @return a [[RequestStub]] that, when processed, yields a [[LoggedInUser]].
    * @note From Reddit API docs: Returns the identity of the user currently authenticated via OAuth.
    * @see http://www.reddit.com/dev/api#GET_api_v1_me
    */
  def me() = MeBP.instantiate(extractor = LoggedInUser)

  /**
    * Accesses the endpoint: GET /api/v1/me/karma
    *
    * Requires [[Scope.MySubreddits]] access.
    *
    * @return ?
    * @note From Reddit API docs: Return a breakdown of subreddit karma.
    * @see http://www.reddit.com/dev/api#GET_api_v1_me_karma
    * @TODO figure out.
    */
  def karma() = KarmaBP.instantiate()

  /**
    * Accesses the endpoint: GET /api/v1/me/prefs
    *
    * Requires [[Scope.Identity]] access.
    *
    * @return a [[RequestStub]] that, when processed, yields a [[AccountPreferences]].
    * @note From Reddit API docs: Return the preference settings of the logged in user
    * @see http://www.reddit.com/dev/api#GET_api_v1_me_prefs
    */
  def prefs() = PrefsBP.instantiate(extractor = AccountPreferences)


  // Can return {"fields": ["json"], "explanation": "unable to parse JSON data", "reason": "JSON_PARSE_ERROR"}
  /**
    * Accesses the endpoint: PATCH /api/v1/me/prefs
    *
    * Requires [[Scope.Account]] access.
    *
    * @return a [[RequestStub]] that, when processed, yields a [[AccountPreferences]].
    * @see http://www.reddit.com/dev/api#PATCH_api_v1_me_prefs
    */
  def patchPrefs(preferences: AccountPreferencesEditable) =
    PatchPrefsBP.instantiate(input = preferences, extractor = AccountPreferences)

  /**
    * Accesses the endpoint: GET /api/v1/me/trophies
    *
    * Requires [[Scope.Identity]] access.
    *
    * @return a [[RequestStub]] that, when processed, yields a [[TrophyList]].
    * @note From Reddit API docs: Return a list of trophies for the current user.
    * @see http://www.reddit.com/dev/api#GET_api_v1_me_trophies
    */
  def trophies() = TrophiesBP.instantiate(extractor = TrophyList)

  /**
    * Accesses the endpoint: GET /api/v1/me/friends
    *
    * Requires [[Scope.Read]] access.
    *
    * @return a [[RequestStub]] that, when processed, yields a [[UserList]].
    * @see http://www.reddit.com/dev/api#GET_prefs_{where}
    */
  def friends(listingOptions: ListOptions = ListOptions.Empty) =
    FriendsBP.instantiate(
        input = RequestInput(listingOptions.toMap),
        extractor = UserList)

  /**
    * Accesses the endpoint: GET /api/v1/me/blocked
    *
    * Requires [[Scope.Read]] access.
    *
    * @return a [[RequestStub]] that, when processed, yields a [[UserList]].
    * @see http://www.reddit.com/dev/api#GET_prefs_{where}
    */
  def blocked(listingOptions: ListOptions = ListOptions.Empty) =
    BlockedBP.instantiate(
        input = RequestInput(listingOptions.toMap),
        extractor = UserList)
}
