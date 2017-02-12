package com.orendain.sraw.api

import akka.http.scaladsl.model.HttpMethods.{ GET, POST }
import com.orendain.sraw.model._
import com.orendain.sraw.request._

/**
 *
 * @see http://www.reddit.com/dev/api#section_subreddits
 */
object SubredditAPI extends API {

  // Blueprints
  val BannedBP = RequestStubBlueprint(GET, "<param>/about/banned", Scope.Read)
  val WikiBannedBP = RequestStubBlueprint(GET, "<param>/about/wikibanned", Scope.Read)
  val ContributorsBP = RequestStubBlueprint(GET, "<param>/about/contributors", Scope.Read)
  val WikiContributorsBP = RequestStubBlueprint(GET, "<param>/about/wikicontributors", Scope.Read)
  val ModeratorsBP = RequestStubBlueprint(GET, "<param>/about/moderators", Scope.Read)

  val DeleteBannerBP = RequestStubBlueprint(POST, "<param>/api/delete_sr_banner", Scope.ModConfig)
  val DeleteHeaderBP = RequestStubBlueprint(POST, "<param>/api/delete_sr_header", Scope.ModConfig)
  val DeleteIconBP = RequestStubBlueprint(POST, "<param>/api/delete_sr_icon", Scope.ModConfig)
  val DeleteImageBP = RequestStubBlueprint(POST, "<param>/api/delete_sr_img", Scope.ModConfig)

  val RecommendSubredditsBP = RequestStubBlueprint(GET, "/api/recommend/sr/<param>", Scope.Read)
  val SearchRedditNamesBP = RequestStubBlueprint(POST, "/api/search_reddit_names", Scope.Read)
  val SiteAdminBP = RequestStubBlueprint(POST, "/api/site_admin", Scope.ModConfig)
  val SubmissionTextBP = RequestStubBlueprint(GET, "<param>/api/submit_text", Scope.Submit)
  val StylesheetBP = RequestStubBlueprint(POST, "<param>/api/subreddit_stylesheet", Scope.ModConfig)
  val ByTopicBP = RequestStubBlueprint(GET, "/api/subreddits_by_topic", Scope.Read)
  val SubscribeBP = RequestStubBlueprint(POST, "/api/subscribe", Scope.Subscribe)

  val UploadImageBP = RequestStubBlueprint(POST, "<param>/api/upload_sr_img", Scope.ModConfig)

  val AboutBP = RequestStubBlueprint(GET, "<param>/about", Scope.Read)
  val SettingsBP = RequestStubBlueprint(GET, "<param>/about/edit", Scope.ModConfig)
  val SidebarBP = RequestStubBlueprint(GET, "<param>/sidebar", Scope.Read)
  val StickyBP = RequestStubBlueprint(GET, "<param>/sticky", Scope.Read)

  val MineSubscriberBP = RequestStubBlueprint(GET, "/subreddits/mine/subscriber", Scope.MySubreddits)
  val MineContributorBP = RequestStubBlueprint(GET, "/subreddits/mine/contributor", Scope.MySubreddits)
  val MineModeratorBP = RequestStubBlueprint(GET, "/subreddits/mine/moderator", Scope.MySubreddits)

  val SearchBP = RequestStubBlueprint(GET, "/subreddits/search", Scope.Read)

  val PopularBP = RequestStubBlueprint(GET, "/subreddits/popular", Scope.Read)
  val NewBP = RequestStubBlueprint(GET, "/subreddits/new", Scope.Read)
  val EmployeeBP = RequestStubBlueprint(GET, "/subreddits/employee", Scope.Read)
  val GoldBP = RequestStubBlueprint(GET, "/subreddits/gold", Scope.Read)

  /**
    * Accesses the endpoint: GET [/r/subreddit]/about/banned
    *
    * Must be a moderator in the subreddit.
    *
    * Requires [[Scope.Read]] access.
    *
    * @param subreddit the subreddit to query
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[UserNote]]].
    * @see http://www.reddit.com/dev/api#GET_about_banned
    */
  def banned(subreddit: String) =
    BannedBP.instantiate(
        params = Seq(sr(subreddit)),
        extractor = Listing.extractor(UserNote))

  /**
    * Accesses the endpoint: GET [/r/subreddit]/about/wikibanned
    *
    * Must be a moderator in the subreddit.
    *
    * Requires [[Scope.Read]] access.
    *
    * @param subreddit the subreddit to query
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[UserNote]]].
    * @see http://www.reddit.com/dev/api#GET_about_wikibanned
    */
  def wikiBanned(subreddit: String) =
    WikiBannedBP.instantiate(
        params = Seq(sr(subreddit)),
        extractor = Listing.extractor(UserNote))

  /**
    * Accesses the endpoint: GET [/r/subreddit]/about/contributors
    *
    * Must be a moderator in the subreddit.
    *
    * Requires [[Scope.Read]] access.
    *
    * @param subreddit the subreddit to query
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[UserNote]]].
    * @see http://www.reddit.com/dev/api#GET_about_contributors
    */
  def contributors(subreddit: String) =
    ContributorsBP.instantiate(
        params = Seq(sr(subreddit)),
        extractor = Listing.extractor(UserNote))

  /**
    * Accesses the endpoint: GET [/r/subreddit]/about/wikicontributors
    *
    * Must be a moderator in the subreddit.
    *
    * Requires [[Scope.Read]] access.
    *
    * @param subreddit the subreddit to query
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[UserNote]]].
    * @see http://www.reddit.com/dev/api#GET_about_wikicontributors
    */
  def wikiContributors(subreddit: String) =
    WikiContributorsBP.instantiate(
        params = Seq(sr(subreddit)),
        extractor = Listing.extractor(UserNote))

  /**
    * Accesses the endpoint: GET [/r/subreddit]/about/moderators
    *
    * Must be a moderator in the subreddit.
    *
    * Requires [[Scope.Read]] access.
    *
    * @param subreddit the subreddit to query
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[UserNote]]].
    * @see http://www.reddit.com/dev/api#GET_about_moderators
    * @todo: UserList extractor broken
    */
  def moderators(subreddit: String) =
    ModeratorsBP.instantiate(
        params = Seq(sr(subreddit)),
        extractor = Listing.extractor(UserList))

  /**
    * Accesses the endpoint: POST [/r/subreddit]/api/delete_sr_banner
    *
    * Requires [[Scope.ModConfig]] access.
    *
    * @param subreddit the subreddit to affect
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Remove the subreddit's custom mobile banner.
    * @see http://www.reddit.com/dev/api#POST_api_delete_sr_banner
    */
  def deleteBanner(subreddit: String) =
    DeleteBannerBP.instantiate(
      params = Seq(sr(subreddit)),
      input = RequestInput("api_type" -> "json"),
      extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST [/r/subreddit]/api/delete_sr_header
    *
    * Requires [[Scope.ModConfig]] access.
    *
    * @param subreddit the subreddit to affect
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Remove the subreddit's custom header image.  The sitewide-default header image will be shown again after this call.
    * @see http://www.reddit.com/dev/api#POST_api_delete_sr_header
    */
  def deleteHeader(subreddit: String) =
    DeleteHeaderBP.instantiate(
      params = Seq(sr(subreddit)),
      input = RequestInput("api_type" -> "json"),
      extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST [/r/subreddit]/api/delete_sr_icon
    *
    * Requires [[Scope.ModConfig]] access.
    *
    * @param subreddit the subreddit to affect
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Remove the subreddit's custom mobile icon.
    * @see http://www.reddit.com/dev/api#POST_api_delete_sr_icon
    */
  def deleteIcon(subreddit: String) =
    DeleteIconBP.instantiate(
      params = Seq(sr(subreddit)),
      input = RequestInput("api_type" -> "json"),
      extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST [/r/subreddit]/api/delete_sr_img
    *
    * Requires [[Scope.ModConfig]] access.
    *
    * @param subreddit the subreddit to affect
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Remove an image from the subreddit's custom image set.
    * @see http://www.reddit.com/dev/api#POST_api_delete_sr_img
    */
  def deleteImage(subreddit: String, imageName: String) =
    DeleteImageBP.instantiate(
      params = Seq(subreddit),
      input = RequestInput(
          ("api_type" -> "json"),
          ("img_name" -> imageName)),
      extractor = RedditNothing)

  /**
    * Accesses the endpoint: GET /api/recommend/sr/[subreddits]
    *
    * Requires [[Scope.Read]] access.
    *
    * @param subreddits subreddits to base the recommendation on
    * @param omits a collection of subreddits to omit from results
    * @return a [[RequestStub]] that, when processed, yields [[???]].
    * @note From Reddit API docs: Return subreddits recommended for the given subreddit(s).
    * @see http://www.reddit.com/dev/api#GET_api_recommend_sr_{srnames}
    * @todo was working at some point, now it always returns an empty array
    */
  def recommendSubreddits(subreddits: Seq[String], omits: Seq[String] = Seq.empty[String]) =
    RecommendSubredditsBP.instantiate(
      params = Seq(subreddits.mkString(",")),
      input = RequestInput(("omit" -> omits.mkString(","))),
      extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST /api/search_reddit_names
    *
    * Requires [[Scope.Read]] access.
    *
    * @param query a string up to 50 characters long, consisting of printable characters.
    * @oaram exact true to find subreddits with the exact specified name, false otherwise
    * @param over18 true to include subreddits with over-18 content.
    * @return a [[RequestStub]] that, when processed, yields a [[GenericRedditEntity]].
    * @note From Reddit API docs: List subreddit names that begin with a query string.
    * @see http://www.reddit.com/dev/api#POST_api_search_reddit_names
    * @todo implement
    */
  def searchRedditNames(query: String, exact: Boolean, over18: Boolean) =
    SearchRedditNamesBP.instantiate(
      input = RequestInput(
        ("query" -> query),
        ("exact" -> exact.toString()),
        ("include_over_18" -> over18.toString())),
      extractor = GenericEntity)

  //  make subredditEdit class
  //  whose companion object can create one from a subreddit
  //  should have a method to serialize, to send through as post parameter
  /**
    * Accesses the endpoint: POST /api/site_admin
    *
    * Requires [[Scope.ModConfig]] access.
    *
    * @param subreddit the subreddit to affect
    * @return a [[RequestStub]] that, when processed, yields a [[???]].
    * @note From Reddit API docs: Create or configure a subreddit.
    * @see http://www.reddit.com/dev/api#POST_api_site_admin
    * @todo implement
    */
  def siteAdmin(query: String, exact: Boolean, over18: Boolean) =
    SiteAdminBP.instantiate(
      input = RequestInput(
        ("query" -> query),
        ("exact" -> exact.toString()),
        ("include_over_18" -> over18.toString())))

  /**
    * Accesses the endpoint: GET [/r/subreddit]/api/submit_text
    *
    * Requires [[Scope.Submit]] access.
    *
    * @param subreddits subreddits to base the recommendation on
    * @return a [[RequestStub]] that, when processed, yields a [[GenericEntity]].
    * @note From Reddit API docs: Get the submission text for the subreddit. This text is set by the subreddit moderators and intended to be displayed on the submission form.
    * @see http://www.reddit.com/dev/api#GET_api_submit_text
    * @todo implement non-generic return
    */
  def submissionText(subreddit: String) =
    SubmissionTextBP.instantiate(
        params = Seq(sr(subreddit)),
        extractor = GenericEntity)

  // no permission = MISSED = {"error": 403}
  // can return {"json": {"errors": [["BAD_CSS", "invalid css", "stylesheet_contents"]]}}
  /**
    * Accesses the endpoint: POST [<param>]/api/subreddit_stylesheet
    *
    * Requires [[Scope.ModConfig]] access.
    *
    * @param subreddit the subreddit to affect
    * @param reason a string up to 256 characters long, consisting of printable characters.
    * @param contents the new stylesheet content
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Update a subreddit's stylesheet.
    * @see http://www.reddit.com/dev/api#POST_api_subreddit_stylesheet
    * @todo
    */
  def stylesheet(subreddit: String, reason: String, contents: String) =
    StylesheetBP.instantiate(
      params = Seq(sr(subreddit)),
      input = RequestInput(
        ("api_type" -> "json"),
        ("op" -> "save"),
        ("reason" -> reason),
        ("stylesheet_contents" -> contents)),
      extractor = RedditNothing)

  /**
    * Accesses the endpoint: GET /api/subreddits_by_topic
    *
    * Requires [[Scope.Read]] access.
    *
    * @param query a string no longer than 50 characters
    * @return a [[RequestStub]] that, when processed, yields a [[GenericEntity]].
    * @note From Reddit API docs: Return a list of subreddits that are relevant to a search query.
    * @see http://www.reddit.com/dev/api#GET_api_subreddits_by_topic
    * @todo extractor
    */
  def subredditsByTopic(query: String) =
    ByTopicBP.instantiate(
        input = RequestInput(("query" -> query)),
        extractor = GenericEntity)

  /**
    * Accesses the endpoint: POST /api/subscribe
    *
    * Requires [[Scope.Subscribe]] access.
    *
    * @param fullname the fullname of a subreddit
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Subscribe to or unsubscribe from a subreddit.
    * @see http://www.reddit.com/dev/api#POST_api_subscribe
    */
  def subscribe(fullname: String) =
    SubscribeBP.instantiate(
      input = RequestInput(
        ("sr" -> fullname),
        ("action" -> "sub")),
      extractor = RedditNothing)

  // 404 when already unsubbed
  /**
    * Accesses the endpoint: POST /api/subscribe
    *
    * Requires [[Scope.Subscribe]] access.
    *
    * @param fullname the fullname of a subreddit
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Subscribe to or unsubscribe from a subreddit.
    * @see http://www.reddit.com/dev/api#POST_api_subscribe
    */
  def unsubscribe(fullname: String) =
    SubscribeBP.instantiate(
      input = RequestInput(
        ("sr" -> fullname),
        ("action" -> "unsub")),
      extractor = RedditNothing)

  // eww ... this endpoint is ugly AF
  // TODO: come back to this later
  def uploadImage(subreddit: String, file: String, header: Int, img_type: String, name: String, upload_type: String) =
    UploadImageBP.instantiate(input = RequestInput(("action" -> subreddit)))

  /**
    * Accesses the endpoint: GET /r/subreddit/about
    *
    * Requires [[Scope.Read]] access.
    *
    * @param subreddit TODO
    * @return a [[RequestStub]] that, when processed, yields a [[?Subreddit]].
    * @note From Reddit API docs: Return information about the subreddit. Data includes the subscriber count, description, and header image.
    * @see http://www.reddit.com/dev/api#GET_r_{subreddit}_about
    */
  def about(subreddit: String) =
    AboutBP.instantiate(
        params = Seq(sr(subreddit)),
        extractor = Subreddit)

  // 404 when no access (not a mod, etc.)
  /**
    * Accesses the endpoint: GET /r/subreddit/about/edit
    *
    * Requires [[Scope.ModConfig]] access.
    *
    * @param subreddit the subreddit to retrieve the settings for
    * @param created ???
    * @return a [[RequestStub]] that, when processed, yields a [[SubredditSettings]].
    * @note From Reddit API docs: Get the current settings of a subreddit.
    * @see http://www.reddit.com/dev/api#GET_r_{subreddit}_about_edit
    */
  def settings(subreddit: String, created: Boolean) =
    SettingsBP.instantiate(
      params = Seq(sr(subreddit)),
      input = RequestInput(("created" -> created.toString)),
      extractor = SubredditSettings)

  // 400 error .... damn you reddit API for not working...
  /**
    * Accesses the endpoint: GET [/r/subreddit]/sidebar
    *
    * Requires [[Scope.Read]] access.
    *
    * @param subreddit the subreddit to retrieve the settings for
    * @return a [[RequestStub]] that, when processed, yields a [[SubredditSettings]].
    * @note From Reddit API docs: Get the sidebar for the current subreddit
    * @see http://www.reddit.com/dev/api#GET_sidebar
    */
  def sidebar(subreddit: String = "") =
    SidebarBP.instantiate(
        params = Seq(sr(subreddit)))

  // 400 error ... -_-
  /**
    * Accesses the endpoint: GET [/r/subreddit]/sticky
    *
    * Requires [[Scope.Read]] access.
    *
    * @param subreddit the subreddit to probe
    * @return a [[RequestStub]] that, when processed, yields a [[RedditNothing]].
    * @note From Reddit API docs: Get the post stickied to the current subreddit. Will 404 if there is not currently a sticky post in this subreddit
    * @see http://www.reddit.com/dev/api#GET_sticky
    * @todo not working
    */
  def sticky(subreddit: String = "") =
    StickyBP.instantiate(
        params = Seq(sr(subreddit)),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: GET /subreddits/mine/subscriber
    *
    * Requires [[Scope.MySubreddits]] access.
    *
    * @param subreddit the subreddit to probe
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[Subreddit]]].
    * @note From Reddit API docs: Get subreddits the user has a relationship with. Subreddits the user is subscribed to.
    * @see http://www.reddit.com/dev/api#GET_sticky
    */
  def mineSubscriber() =
    MineSubscriberBP.instantiate(extractor = Listing.extractor(Subreddit))

  /**
    * Accesses the endpoint: GET /subreddits/mine/contributor
    *
    * Requires [[Scope.MySubreddits]] access.
    *
    * @param subreddit the subreddit to probe
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[Subreddit]]].
    * @note From Reddit API docs: Get subreddits the user has a relationship with. Subreddits the user is an approved submitter in.
    * @see http://www.reddit.com/dev/api#GET_subreddits_mine_contributor
    */
  def mineContributor() =
    MineContributorBP.instantiate(extractor = Listing.extractor(Subreddit))

  /**
    * Accesses the endpoint: GET /subreddits/mine/moderator
    *
    * Requires [[Scope.MySubreddits]] access.
    *
    * @param subreddit the subreddit to probe
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[Subreddit]]].
    * @note From Reddit API docs: Get subreddits the user has a relationship with. Subreddits the user is a moderator of.
    * @see http://www.reddit.com/dev/api#GET_subreddits_mine_contributor
    */
  def mineModerator() =
    MineModeratorBP.instantiate(extractor = Listing.extractor(Subreddit))

  /**
    * Accesses the endpoint: GET /subreddits/search
    *
    * Requires [[Scope.Read]] access.
    *
    * @param query the search query
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[Subreddit]]].
    * @note From Reddit API docs: Search subreddits by title and description.
    * @see http://www.reddit.com/dev/api#GET_subreddits_search
    */
  def search(query: String) =
    SearchBP.instantiate(
        input = RequestInput(("q" -> query)),
        extractor = Listing.extractor(Subreddit))

  /**
    * Accesses the endpoint: GET /subreddits/default
    *
    * Requires [[Scope.Read]] access.
    *
    * @param listOptions list options (optional)
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[Subreddit]]].
    * @note From Reddit API docs: Get all subreddits.
    * @see http://www.reddit.com/dev/api#GET_subreddits_default
    */
  def default(listOptions: ListOptions = ListOptions.Empty) =
    PopularBP.instantiate(
        input = RequestInput(listOptions.toMap),
        extractor = Listing.extractor(Subreddit))

  /**
    * Accesses the endpoint: GET /subreddits/popular
    *
    * Requires [[Scope.Read]] access.
    *
    * @param listOptions list options (optional)
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[Subreddit]]].
    * @note From Reddit API docs: Get all subreddits. Popular sorts on the activity of the subreddit and the position of the subreddits can shift around.
    * @see http://www.reddit.com/dev/api#GET_subreddits_popular
    */
  def popular(listOptions: ListOptions = ListOptions.Empty) =
    PopularBP.instantiate(
        input = RequestInput(listOptions.toMap),
        extractor = Listing.extractor(Subreddit))

  /**
    * Accesses the endpoint: GET /subreddits/new
    *
    * Requires [[Scope.Read]] access.
    *
    * @param listOptions list options (optional)
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[Subreddit]]].
    * @note From Reddit API docs: Get all subreddits. New sorts the subreddits based on their creation date, newest first.
    * @see http://www.reddit.com/dev/api#GET_subreddits_new
    */
  def neww(listOptions: ListOptions = ListOptions.Empty) =
    NewBP.instantiate(
        input = RequestInput(listOptions.toMap),
        extractor = Listing.extractor(Subreddit))

  /**
    * Accesses the endpoint: GET /subreddits/employee
    *
    * Requires [[Scope.Read]] access.
    *
    * @param listOptions list options (optional)
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[Subreddit]]].
    * @note From Reddit API docs: Get all subreddits.
    * @see http://www.reddit.com/dev/api#GET_subreddits_employee
    */
  def employee(listOptions: ListOptions = ListOptions.Empty) =
    EmployeeBP.instantiate(
        input = RequestInput(listOptions.toMap),
        extractor = Listing.extractor(Subreddit))

  /**
    * Accesses the endpoint: GET /subreddits/gold
    *
    * Requires [[Scope.Read]] access.
    *
    * @param listOptions list options (optional)
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[Subreddit]]].
    * @note From Reddit API docs: Get all subreddits.
    * @see http://www.reddit.com/dev/api#GET_subreddits_gold
    */
  def gold(listOptions: ListOptions = ListOptions.Empty) =
    GoldBP.instantiate(
        input = RequestInput(listOptions.toMap),
        extractor = Listing.extractor(Subreddit))
}
