package com.orendain.sraw

import akka.http.scaladsl.model.headers.{ProductVersion, `User-Agent`}

object UserAgent {

  def apply(platform: String, appID: String, version: String, username: String) =
    `User-Agent`(ProductVersion(platform + ":" + appID, version, s"by /u/${username}"))
}

