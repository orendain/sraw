package com.orendain.sraw.api

import akka.http.scaladsl.model.HttpMethods.GET
import com.orendain.sraw.model._
import com.orendain.sraw.request._

/**
  *
  * @see http://www.reddit.com/dev/api#section_search
  */
object SearchAPI extends API {

  // Blueprints
  val SearchBP = RequestStubBlueprint(GET, "<param>/search", Scope.Read)

  /**
    * Accesses the endpoint: GET [/r/subreddit]/search
    *
    * Requires [[Scope.Read]] access.
    *
    * @param query a string no longer than 512 characters
    * @param subreddit the subreddit to filter by (optional)
    * @param time one of (hour, day, week, month, year, all) (optional)
    * @param typ comma-delimited list of result types (sr, link) (optional)
    * @param listOptions list options (optional)
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[Submission]]].
    * @note From Reddit API docs: Search links page.
    * @see https://www.reddit.com/dev/api#GET_search
    */
  def search(query: String, subreddit: String = "", time: String = "all", listOptions: ListOptions = ListOptions.Empty) = {
    val map = Map(
        ("t" -> time),
        ("q" -> query),
        ("restrict_sr" -> "true"),
        ("include_facets" -> "false"),
        ("syntax" -> "cloudsearch")) ++ listOptions.toMap

    SearchBP.instantiate(
        params = Seq(sr(subreddit)),
        input = RequestInput(map),
        extractor = Listing.extractor(Submission))
  }
}
