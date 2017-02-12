package com.orendain.sraw.api

import akka.http.scaladsl.model.HttpMethods.GET
//import scala.collection.mutable.Map
import com.orendain.sraw.model._
import com.orendain.sraw.request._
import com.orendain.sraw.util.RedditExtractors

/**
 *
 * @see http://www.reddit.com/dev/api#section_listings
 */
object ListingAPI extends API {

  //TODO most/all of these are listings

  // Blueprints
  val ByNamesBP = RequestStubBlueprint(GET, "/by_id/<param>", Scope.Read)
  val CommentsBP = RequestStubBlueprint(GET, "<param>/comments/<param>", Scope.Read)
  val DuplicatesBP = RequestStubBlueprint(GET, "/duplicates/<param>", Scope.Read)
  val HotBP = RequestStubBlueprint(GET, "<param>/hot", Scope.Read)
  val NewBP = RequestStubBlueprint(GET, "<param>/new", Scope.Read)
  val RandomBP = RequestStubBlueprint(GET, "<param>/random", Scope.Read)
  val RelatedBP = RequestStubBlueprint(GET, "/related/<param>", Scope.Read)
  val TopBP = RequestStubBlueprint(GET, "<param>/top", Scope.Read)
  val ControversialBP = RequestStubBlueprint(GET, "<param>/controversial", Scope.Read)

  /**
    * Accesses the endpoint: GET /by_id/<param>
    *
    * Requires [[Scope.Read]] access.
    *
    * @param fullnames list of fullnames
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[Submission]]].
    * @note From Reddit API docs: Get a listing of links by fullname.
    * @see http://www.reddit.com/dev/api#GET_by_id_{names}
    */
  def byNames(fullnames: Seq[String]) =
    ByNamesBP.instantiate(
        params = Seq(fullnames.mkString(",")),
        extractor = Listing.extractor(Submission))

  /**
    * Accesses the endpoint: GET [/r/subreddit]/comments/<article>
    *
    * Requires [[Scope.Read]] access.
    *
    * @param article ID36 of a link (e.g. "39wzcs" from "t3_39wzcs")
    * @param context an integer between 0 and 8
    * @param sort one of (confidence, top, new, hot, controversial, old, random, qa)
    * @param comment ID36 of a comment (optional)
    * @param subreddit the subreddit to filter by (optional)
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[Comment]]].
    * @note From Reddit API docs: Get the comment tree for a given Link article. If supplied, comment is the ID36 of a comment in the comment tree for article. This comment will be the (highlighted) focal point of the returned view and context will be the number of parents shown.
    * @see https://www.reddit.com/dev/api#GET_comments_{article}
    */
  def comments(article: String, context: Int, sort: String, comment: String = "", subreddit: String = "") = {

    var in = Map(("context" -> context.toString), ("sort" -> sort))
    if (comment.isEmpty) in += ("comment" -> comment)

    CommentsBP.instantiate(
        params = Seq(sr(subreddit), article),
        input = RequestInput(in),
        extractor = RedditExtractors.CommentTree)
  }

  // returns a JArray with 2 Listing objects.  the first listing obj is the single-elem list which is the original article we linked,
  // the second is the list of duplicates
  // unlike related(), no facets here it seems
  // maybe dependent on search results?  doubtful
  def duplicates(article: String) = DuplicatesBP.instantiate(params = Seq(article))

  /**
    * Accesses the endpoint: GET [/r/subreddit]/hot
    *
    * Requires [[Scope.Read]] access.
    *
    * @param subreddit the subreddit to filter by (optional)
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[Submission]]].
    * @see http://www.reddit.com/dev/api#GET_hot
    * @TODO is a listing
    */
  def hot(subreddit: String = "") =
    HotBP.instantiate(
        params = Seq(sr(subreddit)),
        extractor = Listing.extractor(Submission))

  /**
    * Accesses the endpoint: GET [/r/subreddit]/new
    *
    * Requires [[Scope.Read]] access.
    *
    * @param subreddit the subreddit to filter by (optional)
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[Submission]]].
    * @see http://www.reddit.com/dev/api#GET_new
    */
  def neww(subreddit: String = "") =
    NewBP.instantiate(
        params = Seq(sr(subreddit)),
        extractor = Listing.extractor(Submission))

  /**
    * Accesses the endpoint: GET [/r/subreddit]/random
    *
    * Requires [[Scope.Read]] access.
    *
    * @param subreddit the subreddit to filter by (optional)
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[Submission]]].
    * @note From Reddit API docs: The Serendipity button
    * @note Currently *not functioning*.  Reddit API complains with a 302 error.
    * @see http://www.reddit.com/dev/api#GET_random
    * @todo investigate
    */
  def random(subreddit: String = "") =
    RandomBP.instantiate(
        params = Seq(sr(subreddit)),
        extractor = Listing.extractor(Submission))

  // like duplicates()
  /**
    * Accesses the endpoint: GET /related/<article>
    *
    * Requires [[Scope.Read]] access.
    *
    * @param article the base 36 ID of a Link (e.g. "39wzcs" from "t3_39wzcs")
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[Submission]]].
    * @note From Reddit API docs: Related page: performs a search using title of article as the search query.
    * @see http://www.reddit.com/dev/api#GET_related_{article}
    * @todo is a listing
    * @todo finish
    */
  def related(article: String) = RelatedBP.instantiate(params = Seq(article))

  /**
    * Accesses the endpoint: GET [/r/subreddit]/top
    *
    * Requires [[Scope.Read]] access.
    *
    * @param subreddit the subreddit to filter by (optional)
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[Submission]]].
    * @see http://www.reddit.com/dev/api#GET_top
    * @todo is a listing
    */
  def top(subreddit: String = "") =
   TopBP.instantiate(
        params = Seq(sr(subreddit)),
        extractor = Listing.extractor(Submission))

  /**
    * Accesses the endpoint: GET [/r/subreddit]/controversial
    *
    * Requires [[Scope.Read]] access.
    *
    * @param subreddit the subreddit to filter by (optional)
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[Submission]]].
    * @see http://www.reddit.com/dev/api#GET_controversial
    * @todo is a listing
    */
  def controversial(subreddit: String = "") =
    ControversialBP.instantiate(
        params = Seq(sr(subreddit)),
        extractor = Listing.extractor(Submission))

}
