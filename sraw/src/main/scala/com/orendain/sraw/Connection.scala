package com.orendain.sraw

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpHeader
import akka.http.scaladsl.model.headers.`User-Agent`
import akka.stream.ActorFlowMaterializer
import akka.stream.scaladsl.Sink
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random
import com.orendain.sraw.model.AccessToken
import com.orendain.sraw.request._
import com.orendain.sraw.util.ConnectionBuilder
import scala.concurrent.Future

object Connection {

  /**
    *
    */
  def apply(userAgent: `User-Agent`, credentials: Credentials) =
    new Connection(userAgent, credentials)
}


class Connection(userAgent: `User-Agent`, credentials: Credentials) {

  implicit val system = ActorSystem()
  implicit val materializer: ActorFlowMaterializer = ActorFlowMaterializer()

  val MaxGroupSize = 10000
  val MaxTimeout = 30 seconds

  implicit val thisConnection = this

  var accessToken: Option[AccessToken] = None

  def headers = accessToken match {
    case Some(token) => Seq(userAgent, token.header)
    case None => Seq(userAgent, credentials.header)
  }

  def gainAccess(token: AccessToken) = {
    accessToken = Some(token)
    thisConnection
  }

  def dropAccess() = {
    accessToken = None
    thisConnection
  }

  def revokeAccess() {
    accessToken match {
      case Some(token) => token.revoke()
      case _ =>
    }
  }

  /**
    *
    */
  def futureProcess[T](stub: RequestStub[T]) = {

    val fut = ConnectionBuilder.buildSource(stub)
    .via(ConnectionBuilder.stubToRequest(headers))
    .via(ConnectionBuilder.requestToResponse(stub))
    .via(ConnectionBuilder.responseToEntity)
    .runWith(Sink.head)

    fut flatMap { identity }
  }

  /**
    *
    */
  def futureProcess[T](stubs: RequestStub[T]*) = {
    ConnectionBuilder.buildSource(stubs:_*)
    .via(ConnectionBuilder.stubToRequest(headers))
    .via(ConnectionBuilder.requestToResponse(stubs.head))
    .via(ConnectionBuilder.responseToEntity)
    .grouped(MaxGroupSize)
    .runWith(Sink.head)
  }

  /**
    *
    */
  def process[T](stub: RequestStub[T]) =
    Await.result(futureProcess(stub), MaxTimeout)

  def process[T](stubs: RequestStub[T]*) =
    Await.result(futureProcess(stubs:_*), MaxTimeout)

  /**
    *
    */
  def authorizationURL(permanent: Boolean, mobile: Boolean, scopes: Scope.Value*) = {
    val (url, stateStr) = implicitAuthorizationURL(mobile, scopes:_*)
    val newUrl = url + "&duration=" + (if (permanent) "permanent" else "temporary")
    (newUrl, stateStr)
  }

  /**
    *
    */
  def implicitAuthorizationURL(mobile: Boolean, scopes: Scope.Value*) = {
    val endpoint = if (mobile) "authorize" else "authorize.compact"
    val stateStr = Random.nextString(16)
    val scopeStr = scopes map { identity } mkString ","

    val url = s"https://www.reddit.com/api/v1/${endpoint}?" +
    s"response_type=code&client_id=${credentials.clientID}&redirect_uri=${credentials.redirectURL}&" +
    s"state=${stateStr}&scope=${scopeStr}"

    (url, stateStr)
  }
}
