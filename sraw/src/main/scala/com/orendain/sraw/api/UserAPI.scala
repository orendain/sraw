package com.orendain.sraw.api

import akka.http.scaladsl.model.HttpMethods.{DELETE, GET, PATCH, POST, PUT}
import org.json4s.JsonAST._
import com.orendain.sraw.model._
import com.orendain.sraw.request._

/**
 *
 * @see http://www.reddit.com/dev/api#section_users
 */
object UserAPI extends API {

  // Blueprints
  val FriendBP = RequestStubBlueprint(POST, "<param>/api/friend", Scope.Any)
  val SetPermissionsBP = RequestStubBlueprint(POST, "<param>/api/setpermissions", Scope.ModOthers)
  val UnFriendBP = RequestStubBlueprint(POST, "<param>/api/unfriend", Scope.Any)

  val NameAvailableBP = RequestStubBlueprint(GET, "/api/username_available", Scope.Any)

  val DeleteBP = RequestStubBlueprint(DELETE, "/api/v1/me/friends/<param>", Scope.Subscribe)
  val AboutFriendBP = RequestStubBlueprint(GET, "/api/v1/me/friends/<param>", Scope.MySubreddits)
  val UpdateBP = RequestStubBlueprint(PUT, "/api/v1/me/friends/<param>", Scope.Subscribe)
  val NotificationsBP = RequestStubBlueprint(GET, "/api/v1/me/notifications", Scope.PrivateMessages)
  val UpdateNotificationsBP = RequestStubBlueprint(PATCH, "/api/v1/me/notifications/<param>", Scope.PrivateMessages)
  val TrophiesBP = RequestStubBlueprint(GET, "/api/v1/user/<param>/trophies", Scope.Read)

  val AboutBP = RequestStubBlueprint(GET, "/user/<param>/about", Scope.Read)
  val OverviewBP = RequestStubBlueprint(GET, "/user/<param>/overview", Scope.History)
  val SubmittedBP = RequestStubBlueprint(GET, "/user/<param>/submitted", Scope.History)
  val CommentsBP = RequestStubBlueprint(GET, "/user/<param>/comments", Scope.History)
  val LikedBP = RequestStubBlueprint(GET, "/user/<param>/liked", Scope.History)
  val DislikedBP = RequestStubBlueprint(GET, "/user/<param>/disliked", Scope.History)
  val HiddenBP = RequestStubBlueprint(GET, "/user/<param>/hidden", Scope.History)
  val SavedBP = RequestStubBlueprint(GET, "/user/<param>/saved", Scope.History)
  val GildedBP = RequestStubBlueprint(GET, "/user/<param>/gilded", Scope.History)

  /**
    * Accesses the endpoint: POST [/r/subreddit]/api/friend
    *
    * OAuth2 use requires appropriate scope based on the 'type' of the relationship:
    * moderator: Use "moderator_invite"
    * moderator_invite: modothers
    * contributor: modcontributors
    * banned: modcontributors
    * wikibanned: modcontributors and modwiki
    * wikicontributor: modcontributors and modwiki
    *
    * @param name the name of an existing user
    * @param typ one of (friend, moderator, moderator_invite, contributor, banned, wikibanned, wikicontributor)
    * @param banMsg raw markdown text
    * @param note a string no longer than 300 characters
    * @param duration integer between 1 and 999
    * @param subreddit an existing subreddit (optional)
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Create a relationship between a user and another user or subreddit.
    * @see http://www.reddit.com/dev/api#POST_api_friend
    */
  def friend(name: String, typ: String, note: String, banMsg: String, duration: Int, subreddit: String = "") =
    FriendBP.instantiate(
        params = Seq(sr(subreddit)),
        input = RequestInput(
            ("api_type" -> "json"),
            ("name" -> name),
            ("type" -> typ),
            ("ban_message" -> banMsg),
            ("note" -> note)),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST [/r/subreddit]/api/unfriend
    *
    * OAuth2 use requires appropriate scope based on the 'type' of the relationship:
    * moderator: modothers
    * moderator_invite: modothers
    * contributor: modcontributors
    * banned: modcontributors
    * wikibanned: modcontributors and modwiki
    * wikicontributor: modcontributors and modwiki
    * enemy: privatemessages
    *
    * @param id fullname of a thing
    * @param name the name of an existing user
    * @param typ one of (friend, enemy, moderator, moderator_invite, contributor, banned, wikibanned, wikicontributor)
    * @param subreddit an existing subreddit (optional)
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Create a relationship between a user and another user or subreddit.
    * @see https://www.reddit.com/dev/api#POST_api_unfriend
    */
  def unfriend(id: String, name: String, typ: String, subreddit: String = "") =
    FriendBP.instantiate(
        params = Seq(sr(subreddit)),
        input = RequestInput(
            ("id" -> id),
            ("name" -> name),
            ("type" -> typ)),
        extractor = RedditNothing)

  // @todo
  // come back to this, seems we need to dig up the diff. parameter values
  // {"json": {"errors": [["INVALID_PERMISSION_TYPE", "permissions don't apply to that type of user", "type"]]}}
  def setPermissions(subreddit: String, user: String) =
    SetPermissionsBP.instantiate(
        params = Seq(sr(subreddit)),
        input = RequestInput(
            ("api_type" -> "json"),
            ("name" -> user)))
            //("type" -> "friend")

  // TODO: I keep getting error 400
  /**
    * Accesses the endpoint: GET /api/username_available
    *
    * Requires [[Scope.Any]] access.
    *
    * @param name a valid, unused, username
    * @return a [[RequestStub]] that, when processed, yields [[???]].
    * @note From Reddit API docs: Check whether a username is available for registration.
    * @see http://www.reddit.com/dev/api#GET_api_username_available
    */
  def usernameAvailable(user: String) =
    NameAvailableBP.instantiate(
        input = RequestInput("user" -> user))

  // TODO
  //MISSED = {"fields": ["id"], "explanation": "that user doesn't exist", "reason": "USER_DOESNT_EXIST"}
  //MISSED = {"fields": ["id"], "explanation": "you are not friends with that user", "reason": "NOT_FRIEND"}
  // when successfull, returns: "MISSED = "
  // which comes from:
  // res = HttpResponse(204 No Content,Empty,List(Server: cloudflare-nginx, Date: Wed, 15 Apr 2015 14:51:17 GMT, Content-Type: application/json; charset=UTF-8, Content-Length: 0, Connection: keep-alive, Set-Cookie: __cfduid=d5a39dcd223ff7650054b71213509e5c81429109477; Path=/; HttpOnly; domain=.reddit.com, x-frame-options: SAMEORIGIN, x-content-type-options: nosniff, x-xss-protection: 1; mode=block, Cache-Control: no-cache, pragma: no-cache, x-ratelimit-remaining: 596.0, x-ratelimit-used: 4, x-ratelimit-reset: 523, X-Moose: majestic, Cache-Control: no-cache, CF-RAY: 1d7877bb3beb012c-SJC),HTTP/1.1)
  /**
    * Accesses the endpoint: DELETE /api/v1/me/friends/<username>
    *
    * Unfriend a user.
    *
    * Requires [[Scope.Subscribe]] access.
    *
    * @param username a valid, existing reddit username.
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Stop being friends with a user.
    * @see http://www.reddit.com/dev/api#DELETE_api_v1_me_friends_{username}
    */
  def delete(username: String) =
    DeleteBP.instantiate(
        params = Seq(username),
        extractor = RedditNothing)

  // also user does not exist/not friends error catching from above
  // 400 when not friends with
  /**
    * Accesses the endpoint: GET /api/v1/me/friends/<param>
    *
    * Get information on a friended user.
    *
    * Requires [[Scope.MySubreddits]] access.
    *
    * @param username a valid, existing reddit username.
    * @return a [[RequestStub]] that, when processed, yields a [[UserNote]].
    * @note From Reddit API docs: Get information about a specific 'friend', such as notes.
    * @see http://www.reddit.com/dev/api#GET_api_v1_me_friends_{username}
    */
  def aboutFriend(username: String) =
    AboutFriendBP.instantiate(
        params = Seq(username),
        extractor = UserNote)

  // we have an issue
  // MISSED = {"fields": ["json"], "explanation": "unable to parse JSON data", "reason": "JSON_PARSE_ERROR"}
  // try encoding json that gets passed in
  // todo: retry ... wasn't responding when i tested this endpoint
  /**
    * Accesses the endpoint: PUT /api/v1/me/friends/<param>
    *
    * Add or update a friended user.
    *
    * Requires [[Scope.Subscribe]] access.
    *
    * @param username a valid, existing reddit username.
    * @return a [[RequestStub]] that, when processed, yields a [[??]].
    * @note From Reddit API docs: Create or update a "friend" relationship. This operation is idempotent. It can be used to add a new friend, or update an existing friend (e.g., add/change the note on that friend)
    * @see http://www.reddit.com/dev/api#PUT_api_v1_me_friends_{username}
    */
  def update(username: String, note: String) =
    UpdateBP.instantiate(
        params = Seq(username),
        input = RequestInput(
            JObject(JField("name", JString(username)), JField("note", JString(note)))))

  // return always shows up as "[]", even when im sure there are new messag enotificaitons
  // todo: debug
  def notifications() = NotificationsBP.instantiate()

  // follows notifications() fix
  def updateNotifications(notificationID: String, read: Boolean) =
    UpdateNotificationsBP.instantiate(
        params = Seq(notificationID),
        input = RequestInput(JObject(JField("read", JBool(read)))))

  /**
    * Accesses the endpoint: GET /api/v1/user/<username>/trophies
    *
    * Return a list of trophies for the a given user.
    *
    * Requires [[Scope.Read]] access.
    *
    * @param username a valid, existing reddit username.
    * @return a [[RequestStub]] that, when processed, yields a [[TrophyList]].
    * @note From Reddit API docs: Return a list of trophies for the a given user.
    * @see http://www.reddit.com/dev/api#GET_api_v1_user_{username}_trophies
    */
  def trophies(username: String) =
    TrophiesBP.instantiate(
        params = Seq(username),
        input = RequestInput(("id" -> username)),
        extractor = TrophyList)

  /**
    * Accesses the endpoint: GET /user/<username>/about
    *
    * Get information about the user.
    *
    * Requires [[Scope.Read]] access.
    *
    * @param username a valid, existing reddit username.
    * @return a [[RequestStub]] that, when processed, yields a [[User]].
    * @note From Reddit API docs: Return information about the user, including karma and gold status.
    * @see http://www.reddit.com/dev/api#GET_user_{username}_about
    */
  def about(username: String) = AboutBP.instantiate(
      params = Seq(username),
      extractor = User)

  /**
    * Accesses the endpoint: GET  /user/<username>/overview
    *
    * Requires [[Scope.History]] access.
    *
    * @param username a valid, existing reddit username.
    * @param listOptions list options (optional)
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[PublicPost]]].
    * @see https://www.reddit.com/dev/api#GET_user_{username}_overview
    */
  def overview(username: String, listOptions: ListOptions = ListOptions.Empty) =
    OverviewBP.instantiate(
        params = Seq(username),
        input = RequestInput(listOptions.toMap),
        extractor = Listing.extractor(PublicPost))

  /**
    * Accesses the endpoint: GET  /user/<username>/submitted
    *
    * Requires [[Scope.History]] access.
    *
    * @param username a valid, existing reddit username.
    * @param listOptions list options (optional)
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[Submission]]].
    * @see https://www.reddit.com/dev/api#GET_user_{username}_submitted
    */
  def submitted(username: String, listOptions: ListOptions = ListOptions.Empty) =
    SubmittedBP.instantiate(
        params = Seq(username),
        input = RequestInput(listOptions.toMap),
        extractor = Listing.extractor(Submission))

  /**
    * Accesses the endpoint: GET  /user/<username>/comments
    *
    * Requires [[Scope.History]] access.
    *
    * @param username a valid, existing reddit username.
    * @param listOptions list options (optional)
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[Comment]]].
    * @see https://www.reddit.com/dev/api#GET_user_{username}_comments
    */
  def comments(username: String, listOptions: ListOptions = ListOptions.Empty) =
    CommentsBP.instantiate(
        params = Seq(username),
        input = RequestInput(listOptions.toMap),
        extractor = Listing.extractor(Comment))

  // only visible if user is himself, or user has their votes public
  // otherwise, MISSED = 403 (404 is user does not exist)
  // t1 and t3 data
  /**
    * Accesses the endpoint: GET  /user/<username>/upvoted
    *
    * Requires [[Scope.History]] access.
    *
    * @param username a valid, existing reddit username.
    * @param listOptions list options (optional)
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[PublicPost]]].
    * @see https://www.reddit.com/dev/api#GET_user_{username}_upvoted
    */
  def liked(username: String, listOptions: ListOptions = ListOptions.Empty) =
    LikedBP.instantiate(
        params = Seq(username),
        input = RequestInput(listOptions.toMap),
        extractor = Listing.extractor(PublicPost))

  /**
    * Accesses the endpoint: GET  /user/<username>/downvoted
    *
    * Requires [[Scope.History]] access.
    *
    * @param username a valid, existing reddit username.
    * @param listOptions list options (optional)
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[PublicPost]]].
    * @see https://www.reddit.com/dev/api#GET_user_{username}_downvoted
    */
  def disliked(username: String, listOptions: ListOptions = ListOptions.Empty) =
    DislikedBP.instantiate(
        params = Seq(username),
        input = RequestInput(listOptions.toMap),
        extractor = Listing.extractor(PublicPost))

  // only works on logged in user
  /**
    * Accesses the endpoint: GET  /user/<username>/hidden
    *
    * Requires [[Scope.History]] access.
    *
    * @param username a valid, existing reddit username.
    * @param listOptions list options (optional)
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[PublicPost]]].
    * @see https://www.reddit.com/dev/api#GET_user_{username}_hidden
    */
  def hidden(username: String, listOptions: ListOptions = ListOptions.Empty) =
    HiddenBP.instantiate(
        params = Seq(username),
        input = RequestInput(listOptions.toMap),
        extractor = Listing.extractor(PublicPost))

  // only works on logged in user
  /**
    * Accesses the endpoint: GET  /user/<username>/saved
    *
    * Requires [[Scope.History]] access.
    *
    * @param username a valid, existing reddit username.
    * @param listOptions list options (optional)
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[PublicPost]]].
    * @see https://www.reddit.com/dev/api#GET_user_{username}_saved
    */
  def saved(username: String, listOptions: ListOptions = ListOptions.Empty) =
    SavedBP.instantiate(
        params = Seq(username),
        input = RequestInput(listOptions.toMap),
        extractor = Listing.extractor(PublicPost))

  /**
    * Accesses the endpoint: GET  /user/<username>/gilded
    *
    * Requires [[Scope.History]] access.
    *
    * @param username a valid, existing reddit username.
    * @param listOptions list options (optional)
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[PublicPost]]].
    * @see https://www.reddit.com/dev/api#GET_user_{username}_gilded
    */
  def gilded(username: String, listOptions: ListOptions = ListOptions.Empty) =
    GildedBP.instantiate(
        params = Seq(username),
        input = RequestInput(listOptions.toMap),
        extractor = Listing.extractor(PublicPost))
}
