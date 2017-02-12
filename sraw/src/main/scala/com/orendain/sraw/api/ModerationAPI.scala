package com.orendain.sraw.api

import akka.http.scaladsl.model.HttpMethods.{GET, POST}
import com.orendain.sraw.model._
import com.orendain.sraw.request._

/**
 *
 * @see http://www.reddit.com/dev/api#section_moderation
 */
object ModerationAPI extends API {

  // Blueprints
  val LogBP = RequestStubBlueprint(GET, "<param>/about/log", Scope.ModLog)
  val AboutReportsBP = RequestStubBlueprint(GET, "<param>/about/reports", Scope.Read)
  val AboutSpamBP = RequestStubBlueprint(GET, "<param>/about/spam", Scope.Read)
  val AboutModqueueBP = RequestStubBlueprint(GET, "<param>/about/modqueue", Scope.Read)
  val AboutUnmoderatedBP = RequestStubBlueprint(GET, "<param>/about/unmoderated", Scope.Read)
  val AboutEditedBP = RequestStubBlueprint(GET, "<param>/about/edited", Scope.Read)
  val AcceptModeratorInviteBP = RequestStubBlueprint(POST, "<param>/api/accept_moderator_invite", Scope.ModSelf)
  val ApproveBP = RequestStubBlueprint(POST, "/api/approve", Scope.ModPosts)
  val DistinguishBP = RequestStubBlueprint(POST, "/api/distinguish", Scope.ModPosts)
  val IgnoreReportsBP = RequestStubBlueprint(POST, "/api/ignore_reports", Scope.ModPosts)
  val LeaveContributorBP = RequestStubBlueprint(POST, "/api/leavecontributor", Scope.ModSelf)
  val LeaveModeratorBP = RequestStubBlueprint(POST, "/api/leavemoderator", Scope.ModSelf)
  val RemoveBP = RequestStubBlueprint(POST, "/api/remove", Scope.ModPosts)
  val UnignoreReportsBP = RequestStubBlueprint(POST, "/api/unignore_reports", Scope.ModPosts)
  val StylesheetBP = RequestStubBlueprint(GET, "<param>/stylesheet", Scope.ModConfig)

  /**
    * Accesses the endpoint: GET [/r/subreddit]/about/log
    *
    * Requires [[Scope.ModLog]] access.
    *
    * @param subreddit the subreddit to filter by
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[ModAction]]].
    * @note From Reddit API docs: Get a list of recent moderation actions. Moderator actions taken within a subreddit are logged. This listing is a view of that log with various filters to aid in analyzing the information.
    * @see http://www.reddit.com/dev/api#GET_about_log
    */
  def log(subreddit: String) =
    LogBP.instantiate(
        params = Seq(sr(subreddit)),
        extractor = Listing.extractor(ModAction))

  /**
    * Accesses the endpoint: GET [/r/subreddit]/about/reports
    *
    * Requires [[Scope.Read]] access.
    *
    * @param subreddit the subreddit to filter by
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[PublicPost]]].
    * @note From Reddit API docs: Return a listing of posts relevant to moderators. Things that have been reported.
    * @see http://www.reddit.com/dev/api#GET_about_reports
    */
  def reports(subreddit: String) =
    AboutReportsBP.instantiate(
        params = Seq(sr(subreddit)),
        extractor = Listing.extractor(PublicPost))

  /**
    * Accesses the endpoint: GET [/r/subreddit]/about/spam
    *
    * Requires [[Scope.Read]] access.
    *
    * @param subreddit the subreddit to filter by
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[PublicPost]]].
    * @note From Reddit API docs: Return a listing of posts relevant to moderators. Things that have been marked as spam or otherwise removed.
    * @see http://www.reddit.com/dev/api#GET_about_spam
    */
  def spam(subreddit: String) =
    AboutSpamBP.instantiate(
        params = Seq(sr(subreddit)),
        extractor = Listing.extractor(PublicPost))

  /**
    * Accesses the endpoint: GET [/r/subreddit]/about/modqueue
    *
    * Requires [[Scope.Read]] access.
    *
    * @param subreddit the subreddit to filter by
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[PublicPost]]].
    * @note From Reddit API docs: Return a listing of posts relevant to moderators. Things requiring moderator review, such as reported things and items caught by the spam filter.
    * @see http://www.reddit.com/dev/api#GET_about_modqueue
    */
  def modqueue(subreddit: String) =
    AboutModqueueBP.instantiate(
        params = Seq(sr(subreddit)),
        extractor = Listing.extractor(PublicPost))

  /**
    * Accesses the endpoint: GET [/r/subreddit]/about/unmoderated
    *
    * Requires [[Scope.Read]] access.
    *
    * @param subreddit the subreddit to filter by
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[PublicPost]]].
    * @note From Reddit API docs: Return a listing of posts relevant to moderators. Things that have yet to be approved/removed by a mod.
    * @see http://www.reddit.com/dev/api#GET_about_unmoderated
    */
  def unmoderated(subreddit: String) =
    AboutUnmoderatedBP.instantiate(
        params = Seq(sr(subreddit)),
        extractor = Listing.extractor(PublicPost))

  /**
    * Accesses the endpoint: GET [/r/subreddit]/about/edited
    *
    * Requires [[Scope.Read]] access.
    *
    * @param subreddit the subreddit to filter by
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[PublicPost]]].
    * @note From Reddit API docs: Return a listing of posts relevant to moderators. Things that have been edited recently.
    * @see http://www.reddit.com/dev/api#GET_about_edited
    */
  def edited(subreddit: String) =
    AboutEditedBP.instantiate(
        params = Seq(sr(subreddit)),
        extractor = Listing.extractor(PublicPost))


  // {"json": {"errors": [["NO_INVITE_FOUND", "there is no pending invite for that subreddit", null]]}}
  /**
    * Accesses the endpoint: POST [/r/subreddit]/api/accept_moderator_invite
    *
    * Requires [[Scope.ModSelf]] access.
    *
    * @param subreddit the subreddit to filter by
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Accept an invite to moderate the specified subreddit. The authenticated user must have been invited to moderate the subreddit by one of its current moderators.
    * @see http://www.reddit.com/dev/api#POST_api_accept_moderator_invite
    */
  def acceptModeratorInvite(subreddit: String) =
    AcceptModeratorInviteBP.instantiate(
        params = Seq(subreddit),
        input = RequestInput(("api_type" -> "json")),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST /api/approve
    *
    * Requires [[Scope.ModPosts]] access.
    *
    * @param fullname fullname of the thing to approve.
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Approve a link or comment. If the thing was removed, it will be re-inserted into appropriate listings. Any reports on the approved thing will be discarded.
    * @see http://www.reddit.com/dev/api#POST_api_approve
    */
  def approve(fullname: String) =
    ApproveBP.instantiate(
        input = RequestInput("id" -> fullname),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST /api/distinguish
    *
    * Distinguish options:
    * yes - add a moderator distinguish ([M]). only if the user is a moderator of the subreddit the thing is in.
    * no - remove any distinguishes.
    * admin - add an admin distinguish ([A]). admin accounts only.
    * special - add a user-specific distinguish. depends on user.
    *
    * Requires [[Scope.ModPosts]] access.
    *
    * @param fullname fullname of the thing to distinguish.
    * @param how how to distinguish the thing.  Can be yes, no, admin, special.
    * @return a [[RequestStub]] that, when processed, yields a [[PublicPost]].
    * @note From Reddit API docs: Distinguish a thing's author with a sigil. This can be useful to draw attention to and confirm the identity of the user in the context of a link or comment of theirs.
    * @see http://www.reddit.com/dev/api#POST_api_distinguish
    */
  def distinguish(fullname: String, how: String) =
    DistinguishBP.instantiate(
        input = RequestInput(
            ("api_type" -> "json"),
            ("id" -> fullname),
            ("how" -> how)),
        extractor = PublicPost)

  /**
    * Accesses the endpoint: POST /api/ignore_reports
    *
    * Requires [[Scope.ModPosts]] access.
    *
    * @param fullname fullname of the thing to distinguish.
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Prevent future reports on a thing from causing notifications. Any reports made about a thing after this flag is set on it will not cause notifications or make the thing show up in the various moderation listings.
    * @see http://www.reddit.com/dev/api#POST_api_ignore_reports
    */
  def ignoreReports(fullname: String) =
    IgnoreReportsBP.instantiate(
        input = RequestInput("id" -> fullname),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST /api/leavecontributor
    *
    * Requires [[Scope.ModSelf]] access.
    *
    * @param fullname fullname of the subreddit to abdicate approved submitter status from.
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Abdicate approved submitter status in a subreddit.
    * @see http://www.reddit.com/dev/api#POST_api_leavecontributor
    */
  def leaveContributor(fullname: String) =
    LeaveContributorBP.instantiate(
        input = RequestInput("id" -> fullname),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST /api/leavemoderator
    *
    * Requires [[Scope.ModSelf]] access.
    *
    * @param fullname fullname of the subreddit to abdicate moderator status from.
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Abdicate moderator status in a subreddit.
    * @see http://www.reddit.com/dev/api#POST_api_leavemoderator
    */
  def leaveModerator(fullname: String) =
    LeaveModeratorBP.instantiate(
        input = RequestInput("id" -> fullname),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST /api/remove
    *
    * Requires [[Scope.ModPosts]] access.
    *
    * @param fullname fullname of the thing to remove.
    * @param spam true to mark as spam, false otherwise.
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Remove a link, comment, or modmail message. If the thing is a link, it will be removed from all subreddit listings. If the thing is a comment, it will be redacted and removed from all subreddit comment listings.
    * @see http://www.reddit.com/dev/api#POST_api_remove
    */
  def remove(fullname: String, spam: Boolean) =
    RemoveBP.instantiate(
        input = RequestInput(
            ("id" -> fullname),
            ("spam" -> spam.toString)),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST /api/unignore_reports
    *
    * Requires [[Scope.ModPosts]] access.
    *
    * @param fullname fullname of the thing to unignore.
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Allow future reports on a thing to cause notifications.
    * @see http://www.reddit.com/dev/api#POST_api_unignore_reports
    */
  def unignoreReports(fullname: String) =
    UnignoreReportsBP.instantiate(
        params = Seq(fullname),
        input = RequestInput("id" -> fullname),
        extractor = RedditNothing)

  // results in a 302 redirection!  maybe not support immediately? or check for other approaches
  def stylesheet(subreddit: String) = StylesheetBP.instantiate(params = Seq(subreddit))
}
