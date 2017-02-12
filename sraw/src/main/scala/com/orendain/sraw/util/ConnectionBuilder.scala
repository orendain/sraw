package com.orendain.sraw.util

import akka.actor.ActorSystem
import akka.event.{Logging, LogSource}
import akka.http.HostConnectionPoolSetup
import akka.http.scaladsl._
import akka.http.scaladsl.model.{HttpEntity, HttpHeader, HttpRequest, HttpResponse}
import akka.http.scaladsl.model.MediaTypes.`application/x-www-form-urlencoded`
import akka.http.scaladsl.model.StatusCodes.OK
import akka.stream.scaladsl._
import akka.util.Timeout
import org.json4s.native.JsonMethods.parse
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import com.orendain.sraw.model._
import com.orendain.sraw.request._
import com.orendain.sraw.model.extract._
import akka.http.scaladsl.Http.HostConnectionPool
import scala.util.{Try, Success, Failure}
import scala.reflect.macros.ParseException
import org.json4s.JsonAST._

object ConnectionBuilder {

  // execution context for futures
  implicit val system = ActorSystem()
  import system.dispatcher
  implicit val timeout: Timeout = Timeout(15.seconds)

  /* Setup */
  import akka.http.scaladsl.Http
  import akka.http.HostConnectionPoolSetup
  import akka.http.ConnectionPoolSetup
  import akka.http.ConnectionPoolSettings

  //import akka.actor.ActorSystem
  import akka.stream.ActorFlowMaterializer
  //implicit val system = ActorSystem()
  implicit val materializer: ActorFlowMaterializer = ActorFlowMaterializer()

  val MaxTimeout = 30 seconds


  val NoOAuthHost = "www.reddit.com"
  val OAuthHost = "oauth.reddit.com"

  val httpExt = Http()
  def NoOAuthConnection[T] = httpExt.cachedHostConnectionPoolTls[Extractor[T]](host = NoOAuthHost, port = 443)
  def OAuthConnection[T] = httpExt.cachedHostConnectionPoolTls[Extractor[T]](host = OAuthHost, port = 443)

  val NoOAuthConnection2 = Http().outgoingConnectionTls(host = NoOAuthHost)
  val OAuthConnection2 = Http().outgoingConnectionTls(host = OAuthHost)

  /* Sources */
  def buildSource[T](stubs: RequestStub[T]*) = {
    Source(stubs.toVector)
  }


  /* Flows */
  def stubToRequest[T](headers: Seq[HttpHeader] = Seq.empty[HttpHeader]): Flow[RequestStub[T], (HttpRequest, Extractor[T]), Unit] =
    Flow[RequestStub[T]] map { stub =>
      val request = buildRequest(stub).withHeaders(headers:_*)
      val extractor = stub.extractor.extractor
      println("Request to send: " + request)
      (request, extractor)
    }

  def requestToResponse[T](stub: RequestStub[T]) = stub.blueprint match {
    case _: NoOAuth => NoOAuthConnection[T]
    case _ => OAuthConnection[T]
  }

  def responseToEntity[T]: Flow[(Try[HttpResponse], Extractor[T]), Future[T], Unit] = {
    Flow[(Try[HttpResponse], Extractor[T])] map { case (response, extractor) =>
      response match {
        case Success(res) => extractResponseWith(res, extractor)
        case Failure(exc) => throw exc
      }
    }
  }


  /* Sinks */
//  def collect: Sink[Future[RedditObject], Future[Seq[Future[RedditObject]]]] =
//    Sink.fold(Seq.empty[Future[RedditObject]]) {
//      (sq: Seq[Future[RedditObject]], obj: Future[RedditObject]) => {
//        sq :+ obj
//      }
//    }


  /* Helpers */

  /**
    *
    */
  def buildRequest[T](stub: RequestStub[T]): HttpRequest = {
    stub.blueprint match {
      case _: PostInput =>Http
        HttpRequest(method = stub.blueprint.method, uri = buildURI(stub)).
          withEntity(HttpEntity(`application/x-www-form-urlencoded`, stub.input.encoded))
      case _ => {
        val query = stub.input.encoded match { case s: String => "?" + s; case _ => "" }
        HttpRequest(method = stub.blueprint.method, uri = buildURI(stub) + query)
      }
    }
  }

  /**
    *
    */
  def buildURI[T](stub: RequestStub[T]) =
    stub.params.isEmpty match {
      case true => stub.blueprint.URI
      case false => {
        var uri = stub.blueprint.URI
        //uri = uri.replaceFirst("<param>", "what")
        stub.params foreach { s => uri = uri.replaceFirst("<param>", s) }
        uri
      }
    }

  /**
    *
    */
  def extractResponseWith[T](response: HttpResponse, extractor: Extractor[T]) =
    response.status match {
      case OK => {
        response.entity.toStrict(MaxTimeout) map { s =>
          val res = s.data.decodeString("utf-8")
          println("Response OK: " + res)
          // TODO: check if parsing fails (result might be straight string/boolean)

          val json = Try(parse(res))
          json match {
            case Success(j) => extractor.extract(j)
            case Failure(e) => extractor.extract(JString(res))
          }
        }
      }
      case _ => {
        println("Response not OK: " + response.entity.toString)
        //throw new ExtractionException(HttpResponseError(response.status.intValue, response.entity.asString))
        throw new ExtractionException(response.status.toString)
      }
    }
}
