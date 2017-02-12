package com.orendain.sraw

import com.orendain.sraw.api.AuthorizationAPI
import com.orendain.sraw.request.RequestInput

trait Authorization extends RequestInput {
  def accessToken()(implicit con: Connection) = AuthorizationAPI.accessToken(this).process()
}

/**
 *
 */
case class BasicAuth(tokenCode: String, credentials: Credentials) extends Authorization {
  val encoded = RequestInput(
      ("grant_type" -> "authorization_code"),
      ("code" -> tokenCode),
      ("redirect_uri" -> credentials.redirectURL)).encoded
}

/**
 *
 */
case class RefreshAuth(refreshToken: String) extends Authorization {
  val encoded = RequestInput(
      ("grant_type" -> "refresh_token"),
      ("refresh_token" -> refreshToken)).encoded
}

/**
 *
 */
case class PasswordAuth(username: String, password: String) extends Authorization {
  val encoded = RequestInput(
      ("grant_type" -> "password"),
      ("username" -> username),
      ("password" -> password)).encoded
}

/**
 *
 */
case class AppOnlyAuth() extends Authorization {
  val encoded = RequestInput(("grant_type" -> "client_credentials")).encoded
}

/**
 *
 */
case class AppOnlyInstalledAuth(credentials: InstalledAppCredentials) extends Authorization {
  val encoded = RequestInput(
      ("grant_type" -> "https://oauth.reddit.com/grants/installed_client"),
      ("device_id" -> credentials.deviceID)).encoded
}
