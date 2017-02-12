package com.orendain.sraw.api

import akka.http.scaladsl.model.HttpMethods.POST
import com.orendain.sraw.Authorization
import com.orendain.sraw.model.{AccessToken, RedditNothing}
import com.orendain.sraw.request._

/**
 *
 * @see https://github.com/reddit/reddit/wiki/OAuth2
 */
object AuthorizationAPI {

  // Blueprints
  val AccessTokenBP = new RequestStubBlueprint(POST, "/api/v1/access_token", Scope.Any) with NoOAuth with PostInput
  val RevokeTokenBP = new RequestStubBlueprint(POST, "/api/v1/revoke_token", Scope.Any) with NoOAuth with PostInput

  /**
    * Accesses the endpoint: POST /api/v1/access_token
    *
    * Requires [[Scope.Identity]] access.
    *
    * @param authorization an [[Authorization]] object
    * @return a [[RequestStub]] that, when processed, yields an [[AccessToken]].
    * @see https://github.com/reddit/reddit/wiki/OAuth2
    */
  def accessToken(authorization: Authorization) =
    AccessTokenBP.instantiate(input = authorization, extractor = AccessToken)

  /**
    * Accesses the endpoint: POST /api/v1/revoke_token
    *
    * @param token the token string
    * @param tokenType the token type ("access_token" or "refresh_token")
    */
  def revokeToken(token: String, tokenType: String) =
    RevokeTokenBP.instantiate(
        input = RequestInput(("token" -> token), ("token_type_hint" -> tokenType)),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST /api/v1/revoke_token
    *
    * @param token the [[AccessToken]] to revoke
    */
  def revokeToken(token: AccessToken): RequestStub[RedditNothing] =
    token.refreshToken match {
      case Some(str) => revokeToken(str, "refresh_token")
      case None => revokeToken(token.accessToken, "access_token")
    }
}
