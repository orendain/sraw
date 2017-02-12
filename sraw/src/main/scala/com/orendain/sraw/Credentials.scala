package com.orendain.sraw

import akka.http.scaladsl.model.headers.{Authorization => AuthHeader, BasicHttpCredentials}
import java.util.UUID

/**
  *
  *
  * @param clientID the credential's client ID
  * @param clientSecret the credential's secret
  * @param redirectURL the credential's redirect URL
  */
sealed abstract class Credentials(val clientID: String, val clientSecret: String, val redirectURL: String) {

  /**
    *
    */
  val header = AuthHeader(BasicHttpCredentials(clientID, clientSecret))
}

/**
  *
  */
case class ScriptCredentials(ID: String, secret: String, URL: String)
  extends Credentials(ID, secret, URL)

/**
  *
  */
case class WebAppCredentials(ID: String, secret: String, URL: String)
  extends Credentials(ID, secret, URL)

/**
  *
  */
case class InstalledAppCredentials(ID: String, URL: String, val deviceID: String = UUID.randomUUID().toString)
  extends Credentials(ID, "", URL)
