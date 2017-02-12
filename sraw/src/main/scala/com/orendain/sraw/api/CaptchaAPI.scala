package com.orendain.sraw.api

import akka.http.scaladsl.model.HttpMethods.{GET, POST}
import com.orendain.sraw.model.{Captcha, CaptchaImage, RedditBoolean}
import com.orendain.sraw.request._

/**
 *
 * @see http://www.reddit.com/dev/api#section_captcha
 */
object CaptchaAPI {

  // Blueprints
  val NeedsCaptchaBP = RequestStubBlueprint(GET, "/api/needs_captcha", Scope.Any)
  val NewCaptchaBP = RequestStubBlueprint(POST, "/api/new_captcha", Scope.Any)
  val IdenBP = RequestStubBlueprint(GET, "/captcha/<param>", Scope.Any)

  /**
    * Accesses the endpoint: GET /api/needs_captcha
    *
    * Requires [[Scope.Any]] access.
    *
    * @return a [[RequestStub]] that, when processed, yields a [[RedditBoolean]].
    * @note From Reddit API docs: Check whether CAPTCHAs are needed for API methods that define the "captcha" and "iden" parameters.
    * @see http://www.reddit.com/dev/api#GET_api_needs_captcha
    */
  def needsCaptcha() = NeedsCaptchaBP.instantiate(extractor = RedditBoolean)

  /**
    * Accesses the endpoint: POST /api/new_captcha
    *
    * Requires [[Scope.Any]] access.
    *
    * @return a [[RequestStub]] that, when processed, yields a [[Captcha]].
    * @note From Reddit API docs: Responds with an iden of a new CAPTCHA. Use this endpoint if a user cannot read a given CAPTCHA, and wishes to receive a new CAPTCHA. To request the CAPTCHA image for an iden, use /captcha/iden.
    * @see http://www.reddit.com/dev/api#POST_api_new_captcha
    */
  def newCaptcha() = NewCaptchaBP.instantiate(
      input = RequestInput(("api_type" -> "json")),
      extractor = Captcha)

  /**
    * Accesses the endpoint: GET /captcha/[iden]
    *
    * Requires [[Scope.Any]] access.
    *
    * @param iden a CAPTCHA's iden string.
    * @return a [[RequestStub]] that, when processed, yields a [[CaptchaImage]].
    * @note From Reddit API docs: Responds with an iden of a new CAPTCHA. Use this endpoint if a user cannot read a given CAPTCHA, and wishes to receive a new CAPTCHA. To request the CAPTCHA image for an iden, use /captcha/iden.
    * @see http://www.reddit.com/dev/api#GET_captcha_{iden}
    */
  def captcha(iden: String) = IdenBP.instantiate(
      params = Seq(iden),
      extractor = CaptchaImage)
}
