package com.orendain.sraw.test.api

import org.scalatest._
import com.orendain.sraw._
import com.orendain.sraw.api.AccountAPI
import com.orendain.sraw.model.LoggedInUser
import com.orendain.sraw.test.UnitSpec

class AccountAPISpec extends UnitSpec with BeforeAndAfter {

  val userAgent = UserAgent("desktop", "com.orendain.sraw_test", "v0.1.0", "sraw_bot")
  val creds = ScriptCredentials("", "", "http://www.orendainx.com")
  implicit val connection = Connection(userAgent, creds)

  before {
    val accessToken = PasswordAuth("sraw_bot", "").accessToken()
    connection.gainAccess(accessToken)
  }

  after {
    connection.revokeAccess()
  }

  "AccountAPI.me()" should "return a RequestStub that yields a LoggedInUser" in {
    val yielded = AccountAPI.me().process()(connection.dropAccess())
    yielded shouldBe a [LoggedInUser]
  }
}
