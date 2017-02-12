package com.orendain.sraw.api

import akka.http.scaladsl.model.HttpMethods.{GET, POST}
import com.orendain.sraw.model._
import com.orendain.sraw.request._

/**
 *
 * @see http://www.reddit.com/dev/api#section_wiki
 */
object WikiAPI extends API {

  // Blueprints
  val AddBP = RequestStubBlueprint(POST, "<param>/api/wiki/alloweditor/add", Scope.ModWiki)
  val DelBP = RequestStubBlueprint(POST, "<param>/api/wiki/alloweditor/del", Scope.ModWiki)
  val EditBP = RequestStubBlueprint(POST, "<param>/api/wiki/edit", Scope.WikiEdit)
  val HideBP = RequestStubBlueprint(POST, "<param>/api/wiki/hide", Scope.ModWiki)
  val RevertBP = RequestStubBlueprint(POST, "<param>/api/wiki/revert", Scope.ModWiki)
  val DiscussionsBP = RequestStubBlueprint(GET, "<param>/wiki/discussions/<param>", Scope.WikiRead)
  val PagesBP = RequestStubBlueprint(GET, "<param>/wiki/pages", Scope.WikiRead)
  val RevisionsBP = RequestStubBlueprint(GET, "<param>/wiki/revisions", Scope.WikiRead)
  val PageRevisionsBP = RequestStubBlueprint(GET, "<param>/wiki/revisions/<param>", Scope.WikiRead)

  val GetPagePermissionsBP = RequestStubBlueprint(GET, "<param>/wiki/settings/<param>", Scope.ModWiki)
  val SetPagePermissionsBP = RequestStubBlueprint(POST, "<param>/wiki/settings/<param>", Scope.ModWiki)
  val PageContentBP = RequestStubBlueprint(GET, "<param>/wiki/<param>", Scope.WikiRead)

  // when not a mod: MISSED = {"reason": "MODERATOR_REQUIRED"}
  // MISSED = {"reason": "PAGE_NOT_FOUND"}
  /**
    * Accesses the endpoint: POST [/r/<subreddit>]/api/wiki/alloweditor/add
    *
    * Requires [[Scope.ModWiki]] access.
    *
    * @param subreddit the name of the subreddit to affect
    * @param page the name of an existing wiki page
    * @param username the name of an existing user
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Allow username to edit this wiki page
    * @see https://www.reddit.com/dev/api#POST_api_wiki_alloweditor_add
    */
  def add(subreddit: String, page: String, username: String) =
    AddBP.instantiate(
        params = Seq(sr(subreddit)),
        input = RequestInput(("page" -> page), ("username" -> username)),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST [/r/<subreddit>]/api/wiki/alloweditor/del
    *
    * Requires [[Scope.ModWiki]] access.
    *
    * @param subreddit the name of the subreddit to affect
    * @param page the name of an existing wiki page
    * @param username the name of an existing user
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Deny username to edit this wiki page
    * @see https://www.reddit.com/dev/api#POST_api_wiki_alloweditor_del
    */
  def del(subreddit: String, page: String, username: String) =
    DelBP.instantiate(
        params = Seq(sr(subreddit)),
        input = RequestInput(("page" -> page), ("username" -> username)),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST [/r/<subreddit>]/api/wiki/edit
    *
    * Requires [[Scope.WikiEdit]] access.
    *
    * @param subreddit the name of the subreddit to affect
    * @param pageName the name of an existing page or a new page to create
    * @param content the content to add
    * @param reason a string up to 256 characters long, consisting of printable characters (optional)
    * @param previous the starting point revision for this edit (optional)
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Edit a wiki page.
    * @see http://www.reddit.com/dev/api#POST_api_wiki_edit
    */
  def edit(subreddit: String, pageName: String, content: String, reason: String = "", previous: String = "") =
    EditBP.instantiate(
        params = Seq(sr(subreddit)),
        input = RequestInput(
            ("page" -> pageName),
            ("content" -> content),
            ("reason" -> reason),
            ("previous" -> previous)),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST [/r/subreddit]/api/wiki/hide
    *
    * Requires [[Scope.ModWiki]] access.
    *
    * @param subreddit the name of the subreddit to affect
    * @param pageName the name of an existing wiki page
    * @param revision a wiki revision ID
    * @return a [[RequestStub]] that, when processed, yields [[???]].
    * @note From Reddit API docs: Toggle the public visibility of a wiki page revision
    * @see https://www.reddit.com/dev/api#POST_api_wiki_hide
    */
  def hide(subreddit: String, page: String, revision: String) =
    HideBP.instantiate(
        params = Seq(sr(subreddit)),
        input = RequestInput(("page" -> page), ("revision" -> revision)),
        extractor = RedditBoolean)

  /**
    * Accesses the endpoint: POST [/r/<subreddit>]/api/wiki/revert
    *
    * Requires [[Scope.ModWiki]] access.
    *
    * @param subreddit the name of the subreddit to affect
    * @param page the name of an existing wiki page
    * @param revision a wiki revision ID
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Revert a wiki page to revision.
    * @see https://www.reddit.com/dev/api#POST_api_wiki_revert
    */
  def revert(subreddit: String, page: String, revision: String) =
    RevertBP.instantiate(
        params = Seq(sr(subreddit)),
        input = RequestInput(("page" -> page), ("revision" -> revision)),
        extractor = RedditNothing)

  // Listing endpoint
  // MISSED = {"reason": "INVALID_PAGE_NAME"}
  // TODO: list always coming up empty ... hmmm
  def discussions(subreddit: String, page: String) =
    DiscussionsBP.instantiate(
        params = Seq(sr(subreddit), page),
        input = RequestInput(("page" -> page), ("show" -> "all")))

  /**
    * Accesses the endpoint: GET [/r/subreddit]/wiki/pages
    *
    * Requires [[Scope.WikiRead]] access.
    *
    * @param subreddit the name of the subreddit to inspect
    * @return a [[RequestStub]] that, when processed, yields a [[WikiPageListing]].
    * @note From Reddit API docs: Retrieve a list of wiki pages in this subreddit
    * @see https://www.reddit.com/dev/api#GET_wiki_pages
    */
  def pages(subreddit: String) =
    PagesBP.instantiate(
        params = Seq(sr(subreddit)),
        extractor = WikiPageListing)

  /**
    * Accesses the endpoint: GET [/r/subreddit]/wiki/revisions
    *
    * Requires [[Scope.WikiRead]] access.
    *
    * @param subreddit the name of the subreddit to inspect
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[WikiRevision]]].
    * @note From Reddit API docs: Retrieve a list of recently changed wiki pages in this subreddit
    * @see https://www.reddit.com/dev/api#GET_wiki_revisions
    */
  def revisions(subreddit: String) =
    RevisionsBP.instantiate(
        params = Seq(sr(subreddit)),
        extractor = Listing.extractor(WikiRevision))

  /**
    * Accesses the endpoint: GET [/r/subreddit]/wiki/revisions/page
    *
    * Requires [[Scope.WikiRead]] access.
    *
    * @param subreddit the name of the subreddit to inspect
    * @param page the name of an existing wiki page
    * @param listOptions list options (optional)
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[WikiRevision]]].
    * @note From Reddit API docs: Retrieve a list of revisions of this wiki page
    * @see https://www.reddit.com/dev/api#GET_wiki_revisions_{page}
    */
  def pageRevisions(subreddit: String, page: String, listOptions: ListOptions = ListOptions.Empty) =
    PageRevisionsBP.instantiate(
        params = Seq(sr(subreddit), page),
        input = RequestInput(listOptions.toMap),
        extractor = Listing.extractor(WikiRevision))

  /**
    * Accesses the endpoint: GET [/r/<subreddit>]/api/wiki/settings/<page>
    *
    * Requires [[Scope.ModWiki]] access.
    *
    * @param subreddit the subreddit the wiki is in
    * @param page the name of an existing wiki page
    * @return a [[RequestStub]] that, when processed, yields a [[WikiPageSettings]].
    * @note From Reddit API docs: Retrieve the current permission settings for page.
    * @see http://www.reddit.com/dev/api#GET_wiki_settings_{page}
    */
  def pagePermissions(subreddit: String, page: String) =
    GetPagePermissionsBP.instantiate(
        params = Seq(sr(subreddit), page),
        extractor = WikiPageSettings)

  /**
    * Accesses the endpoint: POST [/r/<subreddit>]/api/wiki/settings/<page>
    *
    * Requires [[Scope.ModWiki]] access.
    *
    * @param subreddit the subreddit the wiki is in
    * @param page the name of an existing wiki page
    * @param listed true to list it in the wiki page list, false otherwise
    * @param permlevel an integer
    * @return a [[RequestStub]] that, when processed, yields a [[WikiPageSettings]].
    * @note From Reddit API docs: Update the permissions and visibility of wiki page.
    * @see http://www.reddit.com/dev/api#POST_wiki_settings_{page}
    */
  def setPagePermissions(subreddit: String, page: String, listed: Boolean, permLevel: Int) =
    SetPagePermissionsBP.instantiate(
        params = Seq(sr(subreddit), page),
        input = RequestInput(("listed" -> listed.toString), ("permlevel" -> permLevel.toString)),
        extractor = WikiPageSettings)

  // MISSED = {"reason": "PAGE_NOT_CREATED"}
  /**
    * Accesses the endpoint: GET [/r/<subreddit>]/wiki/<page>
    *
    * Requires [[Scope.WikiRead]] access.
    *
    * @param subreddit the name of the subreddit to affect
    * @param page the name of an existing wiki page
    * @param rev1 a wiki revision ID
    * @param rev2 a wiki revision ID
    * @return a [[RequestStub]] that, when processed, yields a [[WikiPage]].
    * @note From Reddit API docs: Return the content of a wiki page. If v is given, show the wiki page as it was at that version If both v and v2 are given, show a diff of the two.
    * @see http://www.reddit.com/dev/api#GET_wiki_{page}
    */
  def pageContent(subreddit: String, page: String, rev1: String = "", rev2: String = "") = {
    val in = {
      if (!rev2.isEmpty) RequestInput(("v" -> rev1), ("v2" -> rev2))
      else if (!rev1.isEmpty) RequestInput(("v" -> rev1))
      else RequestInput()
    }

    PageContentBP.instantiate(
        params = Seq(sr(subreddit), page),
        input = in,
        extractor = WikiPage)
  }

}
