package com.orendain.sraw.api

import akka.http.scaladsl.model.HttpMethods.POST
import com.orendain.sraw.request._

/**
  * @see http://www.reddit.com/dev/api#section_gold
  */
object GoldAPI extends API {

  // Blueprints
  val GildBP = RequestStubBlueprint(POST, "/api/v1/gold/gild/<param>", Scope.Credits)
  val GiveBP = RequestStubBlueprint(POST, "/api/v1/gold/give/<param>", Scope.Credits)


  // cant test properly -- need gold credits
  //MISSED = {"explanation": "insufficient creddits", "reason": "INSUFFICIENT_CREDDITS"}
  /**
    * Accesses the endpoint: /api/v1/gold/gild/<param>
    *
    * Requires [[Scope.Creddits]] access.
    *
    * @return a [[RequestStub]] that, when processed, yields a [[???]].
    * @see http://www.reddit.com/dev/api#POST_api_v1_gold_gild_{fullname}
    */
  def gild(fullname: String) = GildBP.instantiate(params = Seq(fullname))

  // cant test properly -- need gold credits
  //MISSED = {"explanation": "insufficient creddits", "reason": "INSUFFICIENT_CREDDITS"}
  /**
    * Accesses the endpoint: /api/v1/gold/give/<param>
    *
    * Requires [[Scope.Creddits]] access.
    *
    * @return a [[RequestStub]] that, when processed, yields a [[???]].
    * @see http://www.reddit.com/dev/api#POST_api_v1_gold_give_{username}
    */
  def give(username: String, months: Int) =
    GiveBP.instantiate(
        params = Seq(username),
        input = RequestInput(("months" -> months.toString)))
}
