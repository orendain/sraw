package com.orendain.sraw.api

import akka.http.scaladsl.model.HttpMethods.{GET, POST}
import com.orendain.sraw.request._

/**
 *
 * @see http://www.reddit.com/dev/api#section_live
 */
object ThreadAPI {

  // Blueprints
  val CreateBP = RequestStubBlueprint(POST, "/api/live/create", Scope.Submit)
  val AccepContributorInviteBP = RequestStubBlueprint(POST, "/api/live/<param>/accept_contributor_invite", Scope.LiveManage)
  val CloseThreadBP = RequestStubBlueprint(POST, "/api/live/<param>/close_thread", Scope.LiveManage)
  val DeleteUpdateBP = RequestStubBlueprint(POST, "/api/live/<param>/delete_update", Scope.Edit)
  val EditBP = RequestStubBlueprint(POST, "/api/live/<param>/edit", Scope.LiveManage)
  val InviteContributorBP = RequestStubBlueprint(POST, "/api/live/<param>/invite_contributor", Scope.LiveManage)
  val LeaveContributorBP = RequestStubBlueprint(POST, "/api/live/<param>/leave_contributor", Scope.LiveManage)
  val ReportBP = RequestStubBlueprint(POST, "/api/live/<param>/report", Scope.Report)
  val RemoveContributorBP = RequestStubBlueprint(POST, "/api/live/<param>/rm_contributor", Scope.LiveManage)
  val RemoveContributorInviteBP = RequestStubBlueprint(POST, "/api/live/<param>/rm_contributor_invite", Scope.LiveManage)
  val SetContributorPermissionsBP = RequestStubBlueprint(POST, "/api/live/<param>/set_contributor_permissions", Scope.LiveManage)
  val StrikeUpdateBP = RequestStubBlueprint(POST, "/api/live/<param>/strike_update", Scope.Edit)
  val PostUpdateBP = RequestStubBlueprint(POST, "/api/live/<param>/update", Scope.Submit)
  val ListUpdatesBP = RequestStubBlueprint(GET, "/live/<param>", Scope.Read)
  val AboutBP = RequestStubBlueprint(GET, "/live/<param>/about", Scope.Read)
  val ContributorsBP = RequestStubBlueprint(GET, "/live/<param>/contributors", Scope.Read)
  val DiscussionsBP = RequestStubBlueprint(GET, "/live/<param>/discussions", Scope.Read)

  // returns: original = {"json": {"errors": [], "data": {"id": "uqv2e1m4o82w"}}}
  def create(description: String, nsfw: Boolean, resources: String, title: String) =
    CreateBP.instantiate(input = RequestInput(
        ("api_type" -> "json"),
        ("description" -> description),
        ("nsfw" -> nsfw.toString),
        ("resources" -> resources),
        ("title" -> title)))

  // /original = {"json": {"errors": [["LIVEUPDATE_NO_INVITE_FOUND", "there is no pending invite for that thread", null]]}}
  // original = {"json": {"errors": []}}
  def acceptContributorInvite(thread: String) =
    AccepContributorInviteBP.instantiate(
        params = Seq(thread),
        input = RequestInput("api_type" -> "json"))

  // original = {"json": {"errors": []}}
  // 403 when already closed
  def close(thread: String) =
    CloseThreadBP.instantiate(
        params = Seq(thread),
        input = RequestInput("api_type" -> "json"))

  // original = {"json": {"errors": []}}
  /**
    * Accesses the endpoint: /api/live/<thread>/delete_update
    *
    * Requires [[Scope.Edit]] access.
    *
    * @param ? ?
    * @param updateID the ID of a single update
    * @return a [[RequestStub]] that, when processed, yields a [[RedditNothing]].
    * @note From Reddit API docs: Delete an update from the thread. Requires that specified update must have been authored by the user or that you have the edit permission for this thread.
    * @see http://www.reddit.com/dev/api#POST_api_live_{thread}_delete_update
    */
  def deleteUpdate(thread: String, updateID: String) =
    DeleteUpdateBP.instantiate(
        params = Seq(thread),
        input = RequestInput(
            ("api_type" -> "json"),
            ("id" -> updateID)))

  // original = {"json": {"errors": []}}
  /**
    * Accesses the endpoint: /api/live/<thread>/edit
    *
    * Requires [[Scope.LiveManage]] access.
    *
    * @param ? ?
    * @return a [[RequestStub]] that, when processed, yields a [[RedditNothing]].
    * @note From Reddit API docs: Configure the thread. Requires the settings permission for this thread.
    * @see http://www.reddit.com/dev/api#POST_api_live_{thread}_edit
    */
  def edit(thread: String, description: String, nsfw: Boolean, resources: String, title: String) =
    EditBP.instantiate(
        params = Seq(thread),
        input = RequestInput(
            ("api_type" -> "json"),
            ("description" -> description),
            ("nsfw" -> nsfw.toString),
            ("resources" -> resources),
            ("title" -> title)))

  // type = one of (liveupdate_contributor_invite, liveupdate_contributor)
  // original = {"json": {"errors": []}}
  /**
    * Accesses the endpoint: /api/live/<thread>/invite_contributor
    *
    * Invite another user to contribute to the thread.
    *
    * Requires [[Scope.LiveManage]] access.
    *
    * @param ? ?
    * @return a [[RequestStub]] that, when processed, yields a [[RedditNothing]].
    * @note From Reddit API docs: Requires the manage permission for this thread. If the recipient accepts the invite, they will be granted the permissions specified.
    * @see http://www.reddit.com/dev/api#POST_api_live_{thread}_invite_contributor
    */
  def inviteContributor(thread: String, name: String, typ: String, permissions: String) =
    InviteContributorBP.instantiate(
        params = Seq(thread),
        input = RequestInput(
            ("api_type" -> "json"),
            ("name" -> name),
            ("type" -> typ),
            ("permissions" -> permissions)))


  // original = {"json": {"errors": []}}
  /**
    * Accesses the endpoint: /api/live/<thread>/leave_contributor
    *
    * Requires [[Scope.LiveManage]] access.
    *
    * @param thread ?
    * @return a [[RequestStub]] that, when processed, yields a [[RedditNothing]].
    * @note From Reddit API docs: Abdicate contributorship of the thread.
    * @see http://www.reddit.com/dev/api#POST_api_live_{thread}_leave_contributor
    */
  def leaveContributor(thread: String) =
    LeaveContributorBP.instantiate(
        params = Seq(thread),
        input = RequestInput(("api_type" -> "json")))

  // type = one of (spam, vote-manipulation, personal-information, sexualizing-minors, site-breaking)
  /**
    * Accesses the endpoint: /api/live/<thread>/report
    *
    * Requires [[Scope.Report]] access.
    *
    * @param thread ?
    * @return a [[RequestStub]] that, when processed, yields a [[??]].
    * @note From Reddit API docs: Report the thread for violating the rules of reddit.
    * @see http://www.reddit.com/dev/api#POST_api_live_{thread}_report
    */
  def report(thread: String, typ: String) =
    ReportBP.instantiate(
        params = Seq(thread),
        input = RequestInput(("api_type" -> "json"), ("type" -> typ)))

  //original = {"json": {"errors": []}}
  /**
    * Accesses the endpoint: /api/live/<thread>/rm_contributor
    *
    * Requires [[Scope.LiveManage]] access.
    *
    * @param thread ?
    * @return a [[RequestStub]] that, when processed, yields a [[RedditNothing]].
    * @note From Reddit API docs: Revoke another user's contributorship. Requires the manage permission for this thread.
    * @see http://www.reddit.com/dev/api#POST_api_live_{thread}_rm_contributor
    */
  def removeContributor(thread: String, fullname: String) =
    RemoveContributorBP.instantiate(
        params = Seq(thread),
        input = RequestInput(("api_type" -> "json"),("id" -> fullname)))


  //original = {"json": {"errors": []}}
  /**
    * Accesses the endpoint: /api/live/<thread>/rm_contributor_invite
    *
    * Requires [[Scope.LiveManage]] access.
    *
    * @param thread ?
    * @return a [[RequestStub]] that, when processed, yields a [[RedditNothing]].
    * @note From Reddit API docs: Revoke an outstanding contributor invite. Requires the manage permission for this thread.
    * @see http://www.reddit.com/dev/api#POST_api_live_{thread}_rm_contributor_invite
    */
  def removeContributorInvite(thread: String, fullname: String) =
    RemoveContributorInviteBP.instantiate(
        params = Seq(thread),
        input = RequestInput(("api_type" -> "json"),("id" -> fullname)))


  // original = {"json": {"errors": [["USER_DOESNT_EXIST", "that user doesn't exist", "name"]]}}
  // original = {"json": {"errors": [["INVALID_PERMISSIONS", "invalid permissions string", "permissions"]]}}
  // original = {"json": {"errors": [["LIVEUPDATE_NO_INVITE_FOUND", "there is no pending invite for that thread", "user"]]}}
  // seems to work ... just no idea what the diff. permissions do (+update,+edit,-manage)
  // "edit","close","manage","update","settings", and finally "all"
  // not working -_-  manual changing permissions through UI works fine
  // removing a single self permission removes them all, cant add them back. very buggy
  def setContributorPermissions(thread: String, name: String, typ: String, permissions: String) =
    SetContributorPermissionsBP.instantiate(
        params = Seq(thread),
        input = RequestInput(
            ("api_type" -> "json"),
            ("name" -> name),
            ("permissions" -> permissions),
            ("type" -> typ)))


  // original = {"json": {"errors": [["NO_THING_ID", "id not specified", "id"]]}}
  // make sure updateID is in the form "LiveUpdate_bd6dc658-e3aa-11e4-ba8d-22000bb30b88",
  // and not just bd6dc658-e3aa-11e4-ba8d-22000bb30b88
  def strikeUpdate(thread: String, updateID: String) =
    StrikeUpdateBP.instantiate(
        params = Seq(thread),
        input = RequestInput(
            ("api_type" -> "json"),
            ("id" -> updateID)))


  // original = {"json": {"errors": []}}
  /**
    * Accesses the endpoint: /api/live/<thread>/update
    *
    * Requires [[Scope.Submit]] access.
    *
    * @param thread ?
    * @return a [[RequestStub]] that, when processed, yields a [[RedditNothing]].
    * @note From Reddit API docs: Post an update to the thread. Requires the update permission for this thread.
    * @see http://www.reddit.com/dev/api#POST_api_live_{thread}_update
    */
  def update(thread: String, body: String) =
    PostUpdateBP.instantiate(
        params = Seq(thread),
        input = RequestInput(
            ("api_type" -> "json"),
            ("body" -> body)))


  // returns listing of LiveUpdate's
  def listUpdates(thread: String) = ListUpdatesBP.instantiate(params = Seq(thread))


  // returns LiveUpdateEvent
  def about(thread: String) = AboutBP.instantiate(params = Seq(thread))


  // returns JArray of UserLists
  // you heard me right ... JArray(List[UserList]), UserList = id/name/permissions
  // inject:  the 2nd userlist is empty
  // permissions = JArray[String] ("sraw_bot" has "all", orendain has blank)
  def contributors(thread: String) = ContributorsBP.instantiate(params = Seq(thread))

  // should be straight foward, but its returning an empty listing ... maybe need to give it some
  // time to propogate
  def discussions(thread: String) = DiscussionsBP.instantiate(params = Seq(thread))


}
