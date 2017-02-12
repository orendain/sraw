package com.orendain.sraw.model

import akka.http.scaladsl.model.headers.{Authorization, OAuth2BearerToken}
import org.json4s.JsonAST._
import com.orendain.sraw.Connection
import com.orendain.sraw.api.AuthorizationAPI
import com.orendain.sraw.model.extract._

/**
  *
  */
object AccessToken extends HasExtractor[AccessToken] {

  val extractor = new ObjectExtractor[AccessToken] {
    def canExtract(json: JObject) = (json \ "access_token") != JNothing
    def extract(json: JObject) = new AccessToken(json)
  }
}

/**
  *
  */
class AccessToken(val json: JObject) extends RedditObject {

  val accessToken = valString("access_token")
  val tokenType = valString("token_type")
  val expiresIn = valLong("expires_in")
  val refreshToken = valOp[String]("refresh_token")
  val scope = values("scope")

  val header = Authorization(OAuth2BearerToken(accessToken))

  val expiration = System.currentTimeMillis() + expiresIn
  def hasExpired = System.currentTimeMillis() > expiration

  def revoke()(implicit con: Connection) {
    val stub = refreshToken match {
      case Some(str) => AuthorizationAPI.revokeToken(refreshToken.get, "refresh_token")
      case None => AuthorizationAPI.revokeToken(accessToken, "access_token")
    }
    stub.process()
  }
}
