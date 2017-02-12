package com.orendain.sraw.api

import akka.http.scaladsl.model.HttpMethods.{GET, POST}
import com.orendain.sraw.model._
import com.orendain.sraw.request._

/**
 *
 * @see http://www.reddit.com/dev/api#section_links_and_comments
 */
object LinkAndCommentAPI extends API {

  // Blueprints
  val CommentBP = RequestStubBlueprint(POST, "/api/comment", Scope.Submit)
  val InfoBP = RequestStubBlueprint(GET, "/api/info", Scope.Read)
  val SubmitBP = RequestStubBlueprint(POST, "/api/submit", Scope.Submit)
  val DelBP = RequestStubBlueprint(POST, "/api/del", Scope.Edit)
  val EditUserTextBP = RequestStubBlueprint(POST, "/api/editusertext", Scope.Edit)
  val HideBP = RequestStubBlueprint(POST, "/api/hide", Scope.Report)
  val UnhideBP = RequestStubBlueprint(POST, "/api/unhide", Scope.Report)
  val MarkNSFWBP = RequestStubBlueprint(POST, "/api/marknsfw", Scope.ModPosts)
  val UnmarkNSFWBP = RequestStubBlueprint(POST, "/api/unmarknsfw", Scope.ModPosts)
  val MoreChildrenBP = RequestStubBlueprint(GET, "/api/morechildren", Scope.Read)
  val ReportBP = RequestStubBlueprint(POST, "/api/report", Scope.Report)
  val SaveBP = RequestStubBlueprint(POST, "/api/save", Scope.Save)
  val UnsaveBP = RequestStubBlueprint(POST, "/api/unsave", Scope.Save)
  val SavedCategoriesBP = RequestStubBlueprint(GET, "/api/saved_categories", Scope.Save)
  val SendRepliesBP = RequestStubBlueprint(POST, "/api/sendreplies", Scope.Edit)
  val SetContestModeBP = RequestStubBlueprint(POST, "/api/set_contest_mode", Scope.ModPosts)
  val SetSubredditStickyBP = RequestStubBlueprint(POST, "/api/set_subreddit_sticky", Scope.ModPosts)
  val StoreVisitsBP = RequestStubBlueprint(POST, "/api/store_visits", Scope.Save)
  val VoteBP = RequestStubBlueprint(POST, "/api/vote", Scope.Vote)

  /**
    * Accesses the endpoint: POST /api/comment
    *
    * Submit a new comment or reply to a message.  The yielded [[Post]] is the message recorded.
    *
    * Requires [[Scope.Submit]] access.
    *
    * @param parentFullname fullname of the thing being replied to. Can be a link, comment or message.
    * @param text raw markdown body of the comment or message.
    * @return a [[RequestStub]] that, when processed, yields a [[Post]].
    * @note From Reddit API docs: Submit a new comment or reply to a message.
    * @see http://www.reddit.com/dev/api#POST_api_comment
    */
  def comment(parentFullname: String, text: String) =
    CommentBP.instantiate(
        input = RequestInput(
            ("api_type" -> "json"),
            ("text" -> text),
            ("thing_id" -> parentFullname)),
        extractor = Post)

  /**
    * Accesses the endpoint: POST /api/del
    *
    * Delete a Link or Comment.
    *
    * Requires [[Scope.Edit]] access.
    *
    * @param fullname fullname of the thing to delete.
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Delete a Link or Comment.
    * @see http://www.reddit.com/dev/api#POST_api_del
    */
  def delete(fullname: String) = DelBP.instantiate(
      input = RequestInput("id" -> fullname),
      extractor = RedditNothing)

  // when captcha is wrong: {"json": {"captcha": "kFk7Ydwlzkua4XOfQ9jFKFjCm0ZsgV0q", "errors": [["BAD_CAPTCHA", "care to try these again?", "captcha"]]}}
  // extension should be "json" ... "compact" is mobile-friendly site to view submission
  //
  // For parameter "then", "tb" and "comments" both return the exact same thing.
  // Only difference is the format of the "url" field.
  // (comments) https://www.reddit.com/r/SRAWTesting/comments/39u0dr/somerandomsubmissiontitle/.json
  // vs
  // (tb)       https://www.reddit.com/tb/39ufy0.json
  /**
    * Accesses the endpoint: POST /api/submit
    *
    * Submit a self-post to a subreddit.
    *
    * Requires [[Scope.Submit]] access.
    *
    * @param subreddit the subreddit to submit to
    * @param title the title of the post
    * @param text the content
    * @param sendReplies true to subscribe to replies, false otherwise
    * @return a [[RequestStub]] that, when processed, yields a [[SubmissionReceipt]].
    * @note From Reddit API docs: Submit a link to a subreddit.
    * @see http://www.reddit.com/dev/api#POST_api_submit
    */
  def submitSelfPost(subreddit: String, title: String, text: String, sendReplies: Boolean) =
    SubmitBP.instantiate(
        input = RequestInput(
            ("api_type" -> "json"),
            ("iden" -> "TWx9BzCtZedpHFgn9d70zBvzY0M4uxJ7"),
            ("captcha" -> "iaeivc"),
            ("kind" -> "self"),
            ("sendreplies" -> sendReplies.toString),
            ("sr" -> subreddit),
            ("text" -> text),
            ("title" -> title),
            ("then" -> "tb"),
            ("extension" -> "json")),
        extractor = SubmissionReceipt)

  /**
    * Accesses the endpoint: POST /api/submit
    *
    * Submit a link to a subreddit.
    *
    * Requires [[Scope.Submit]] access.
    *
    * @param subreddit the subreddit to submit to
    * @param title the title of the post
    * @param url the url of the content to link to
    * @param resubmit false to halt posting if the url has already been shared, true otherwise
    * @param sendReplies true to subscribe to replies, false otherwise
    * @return a [[RequestStub]] that, when processed, yields a [[SubmissionReceipt]].
    * @note From Reddit API docs: Submit a link to a subreddit.
    * @see http://www.reddit.com/dev/api#POST_api_submit
    */
  def submitLinkPost(subreddit: String, title: String, url: String, resubmit: Boolean, sendReplies: Boolean) =
    SubmitBP.instantiate(
        input = RequestInput(
            ("api_type" -> "json"),
            ("iden" -> "yzVMpzRKSIZezMSXgTU7fN3fXdYTke11"),
            ("captcha" -> "irnugl"),
            ("kind" -> "link"),
            ("resubmit" -> resubmit.toString),
            ("sendreplies" -> sendReplies.toString),
            ("sr" -> subreddit),
            ("url" -> url),
            ("title" -> title),
            ("then" -> "tb"),
            ("extension" -> "json")),
        extractor = SubmissionReceipt)

  /**
    * Accesses the endpoint: POST /api/editusertext
    *
    * Edit a comment or self-post.
    *
    * Requires [[Scope.Edit]] access.
    *
    * @param fullname fullname of the post to edit.
    * @param text raw markdown text to replace the original with.
    * @return a [[RequestStub]] that, when processed, yields the edited [[Submission]].
    * @note From Reddit API docs: Edit the body text of a comment or self-post.
    * @see http://www.reddit.com/dev/api#POST_api_editusertext
    */
  def editUserText(fullname: String, text: String) =
    EditUserTextBP.instantiate(
        input = RequestInput(
            ("api_type" -> "json"),
            ("text" -> text),
            ("thing_id" -> fullname)),
        extractor = Submission)

  // needs more testing, maybe being a mod counters this
  // TODO: test
  def hide(fullname: String) = HideBP.instantiate(input = RequestInput("id" -> fullname))
  def unhide(fullname: String) = UnhideBP.instantiate(input = RequestInput(("id" -> fullname)))

  /**
    * Accesses the endpoint: GET [/r/subreddit]/api/info
    *
    * Requires [[Scope.Read]] access.
    *
    * @param fullnames fullnames of things to get info on. Can be links, comments and subreddits.
    * @return a [[RequestStub]] that, when processed, yields a [[???]].
    * @note From Reddit API docs: Return a listing of things specified by their fullnames. Only Links, Comments, and Subreddits are allowed.
    * @see https://www.reddit.com/dev/api#GET_api_info
    * @todo add support for subreddits by including appropriate extractor
    */
  def info(fullnames: Seq[String]) =
    InfoBP.instantiate(
        input = RequestInput(("id" -> fullnames.mkString(","))),
        extractor = Listing.extractor(PublicPost))

  /**
    * Accesses the endpoint: GET [/r/subreddit]/api/info
    *
    * Requires [[Scope.Read]] access.
    *
    * @param url url of thing to get info on.
    * @return a [[RequestStub]] that, when processed, yields a [[???]].
    * @note From Reddit API docs: Return a listing of things specified by their fullnames. Only Links, Comments, and Subreddits are allowed.
    * @see http://www.reddit.com/dev/api#GET_api_info
    * @todo allow filtering by subreddit (/r/someFilter/endpointHere)
    */
  def info(url: String) =
    InfoBP.instantiate(
        input = RequestInput(("url" -> url)),
        extractor = Listing.extractor(Submission))

  // 403 forbiden when no permission
  /**
    * Accesses the endpoint: POST /api/marknsfw
    *
    * Requires [[Scope.ModPosts]] access.
    *
    * @param fullname fullname of the thing
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Mark a link NSFW.
    * @see http://www.reddit.com/dev/api#POST_api_marknsfw
    */
  def markNSFW(fullname: String) = MarkNSFWBP.instantiate(
      input = RequestInput("id" -> fullname),
      extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST /api/unmarknsfw
    *
    * Requires [[Scope.ModPosts]] access.
    *
    * @param fullname fullname of the thing
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Remove the NSFW marking from a link.
    * @see http://www.reddit.com/dev/api#POST_api_unmarknsfw
    */
  def unmarkNSFW(fullname: String) =
    UnmarkNSFWBP.instantiate(
        input = RequestInput("id" -> fullname),
        extractor = RedditNothing)

  // havent even touched this
  // TODO: do
  def moreChildren(fullname: String) =
    MoreChildrenBP.instantiate(input = RequestInput("id" -> fullname))

  /**
    * Accesses the endpoint: POST /api/report
    *
    * Requires [[Scope.Report]] access.
    *
    * @param fullname fullname of the thing
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Report a link, comment or message. Reporting a thing brings it to the attention of the subreddit's moderators. Reporting a message sends it to a system for admin review. For links and comments, the thing is implicitly hidden as well.
    * @see http://www.reddit.com/dev/api#POST_api_report
    */
  def report(fullname: String, reason: String) =
    ReportBP.instantiate(
        input = RequestInput(
            ("api_type" -> "json"),
            ("reason" -> reason),
            //("other_reason" -> (reason + "other")), // doesn't seem to do anything... removed for now
            ("thing_id" -> fullname)),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST /api/report
    *
    * Requires [[Scope.Save]] access.
    *
    * @param category a category name
    * @param fullname fullname of the thing to save
    * @return a [[RequestStub]] that, when processed, yields a [[RedditNothing]].
    * @note From Reddit API docs: Save a link or comment. Saved things are kept in the user's saved listing for later perusal.
    * @see http://www.reddit.com/dev/api#POST_api_save
    */
  def save(category: String, fullname: String) =
    SaveBP.instantiate(
        input = RequestInput(
            ("category" -> category),
            ("id" -> fullname)),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST /api/unsave
    *
    * Requires [[Scope.Save]] access.
    *
    * @param fullname fullname of the thing to save
    * @return a [[RequestStub]] that, when processed, yields a [[RedditNothing]].
    * @note From Reddit API docs: Unsave a link or comment. This removes the thing from the user's saved listings as well.
    * @see http://www.reddit.com/dev/api#POST_api_unsave
    */
  def unsave(fullname: String) =
    UnsaveBP.instantiate(
        input = RequestInput("id" -> fullname),
        extractor = RedditNothing)


  // dont think this is implemented reddit-side.  not working and cant find "categories" on reddit site itself
  // TODO: investigate
  def savedCategories() = SavedCategoriesBP.instantiate()

  /**
    * Accesses the endpoint: POST /api/sendreplies
    *
    * Requires [[Scope.Edit]] access.
    *
    * @param fullname fullname of the thing to affect.
    * @param state true to enable replies, false to disable.
    * @return a [[RequestStub]] that, when processed, yields a [[RedditNothing]].
    * @note From Reddit API docs: Enable or disable inbox replies for a link or comment. state is a boolean that indicates whether you are enabling or disabling inbox replies - true to enable, false to disable.
    * @see http://www.reddit.com/dev/api#POST_api_sendreplies
    * @todo API calls going through, but not doing anything reddit-side. -_-
    */
  def sendReplies(fullname: String, state: Boolean) =
    SendRepliesBP.instantiate(
        input = RequestInput(
            ("state" -> state.toString),
            ("id" -> fullname)))

  /**
    * Accesses the endpoint: POST /api/set_contest_mode
    *
    * Requires [[Scope.ModPosts]] access.
    *
    * @param fullname fullname of the thing to affect.
    * @param state true to enable contest mode, false to disable.
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Set or unset "contest mode" for a link's comments.
    * @see http://www.reddit.com/dev/api#POST_api_set_contest_mode
    */
  def setContestMode(fullname: String, state: Boolean) =
    SetContestModeBP.instantiate(
        input = RequestInput(
            ("api_type" -> "json"),
            ("id" -> fullname),
            ("state" -> state.toString)),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST /api/set_subreddit_sticky
    *
    * Set or unset a self-post as the sticky post in its subreddit.
    *
    * Requires [[Scope.Modposts]] access.
    *
    * @param fullname fullname of the post to sticky.
    * @param state true to sticky, false to unsticky.
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Note that if another post was previously stickied, stickying a new one will replace the previous one.
    *
    * @see http://www.reddit.com/dev/api#POST_api_set_subreddit_sticky
    */
  def setSubredditSticky(fullname: String, state: Boolean) =
    SetSubredditStickyBP.instantiate(
        input = RequestInput(
            ("api_type" -> "json"),
            ("id" -> fullname),
            ("state" -> state.toString)),
        extractor = RedditNothing)

  // TODO: test after needs reddit gold
  def storeVisits(fullnames: Array[String]) =
    StoreVisitsBP.instantiate(input = RequestInput("id" -> fullnames.mkString(",")))

  // v (optional) internal use only ???
  // TODO: test
  def vote(fullname: String, dir: Int) =
    VoteBP.instantiate(input = RequestInput(
        ("id" -> fullname),
        ("dir" -> dir.toString)))

}
