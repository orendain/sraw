package com.orendainx.srawexamples.karmacounter

import akka.actor.ActorSystem
import akka.stream.scaladsl.Sink
import akka.stream.ActorFlowMaterializer
import com.orendain.sraw
import sraw._
import sraw.api._
import sraw.model._
import sraw.util._

import scala.collection.mutable.HashMap

object Main extends App {

  favSpots()
  //highLevel()
  //lowLevel()

  println("done")
  System.exit(0)

  def favSpots() = {
    // Setup
    val userAgent = UserAgent("desktop", "com.orendain.srawexamples.karmacounter", "v1.0.0", "sraw_bot")
    val creds = ScriptCredentials("", "", "http://www.orendainx.com")
    implicit val connection = Connection(userAgent, creds)

    // Authenticate
    val accessToken = PasswordAuth("sraw_bot", "").accessToken()
    connection.gainAccess(accessToken)

    val hashmap = HashMap.empty[String, Int]
    val hashmap2 = HashMap.empty[String, Int]

    val lo = ListOptions(showAll = true, limit = 100)
    val subList = UserAPI.submitted("cakebeerandmorebeer", lo).process()

    countSubmissions(subList)

    def countSubmissions(subList: Listing[Submission]) {
      val submissions = subList.children
      submissions foreach { s =>
        val sr = s.subreddit match {
          case Some(n) => n
          case None => s.subreddit_id
        }
        hashmap.update(sr, hashmap.getOrElse(sr, 0) + 1)
      }

      println("before")
      subList.after match {
        case Some(n) => {
          val lo = ListOptions(after = n, limit = 100)
          countSubmissions(UserAPI.submitted("cakebeerandmorebeer", lo).process())
        }
        case None =>
      }
      println("after")
    }

    val comments = UserAPI.comments("cakebeerandmorebeer").process().children
    comments foreach { c =>
      val sr = c.subreddit match {
        case Some(n) => n
        case None => c.subreddit_id
      }
      hashmap2.update(sr, hashmap2.getOrElse(sr, 0) + 1)
    }

    println("\n# of posts \t subreddit")
    println("----------------------------------------")
    hashmap.toList foreach { case (sr, cnt) => println(cnt + "\t\t" + sr) }

    println("\n# of comments \t subreddit")
    println("----------------------------------------")
    hashmap2.toList foreach { case (sr, cnt) => println(cnt + "\t\t" + sr) }
  }


  def highLevel() = {
    // Setup
    val userAgent = UserAgent("desktop", "com.orendain.srawexamples.karmacounter", "v1.0.0", "sraw_bot")
    val creds = ScriptCredentials("", "", "http://www.orendainx.com")
    implicit val connection = Connection(userAgent, creds)

    // Authenticate
    val accessToken = PasswordAuth("sraw_bot", "").accessToken()
    connection.gainAccess(accessToken)

    // Retrieve all user's submissions
    val submissions = User("cakebeerandmorebeer").submitted.children
    //val submissions = User("sraw_bot").submitted.children

    var totalKarma = 0

    // Iterate through the submissions
    submissions foreach { s =>
      println("Submission name: " + s.name)
      totalKarma += s.score

      // Sum and display the net karma of all comments in this thread
      //val karma = s.comments().children.map(_.score).fold(0)(_ + _)
      //println("Net Karma: " + karma)
      //totalComments += 1
    }

    println("Net Karma across all this user's submissions: " + totalKarma)
    println("Total submissions: " + submissions.length)
  }


  def lowLevel() = {
    // Setup
    val userAgent = UserAgent("desktop", "com.orendain.srawexamples.karmacounter", "v1.0.0", "sraw_bot")
    val creds = ScriptCredentials("", "", "http://www.orendainx.com")
    implicit val connection = Connection(userAgent, creds)

    // Authenticate
    val accessToken = PasswordAuth("sraw_bot", "").accessToken()
    connection.gainAccess(accessToken)

    // Future[Listing[Submission]]
    val a1 = UserAPI.submitted("cakebeerandmorebeer").futureProcess()
    val a2 = connection.futureProcess(UserAPI.submitted("cakebeerandmorebeer"))

    // BYOF (Build your own Flow)
    implicit val system = ActorSystem()
    implicit val materializer: ActorFlowMaterializer = ActorFlowMaterializer()

    val requestStub = UserAPI.submitted("cakebeerandmorebeer")
    val a3 = ConnectionBuilder.buildSource(requestStub)
    .via(ConnectionBuilder.stubToRequest(connection.headers))
    .via(ConnectionBuilder.requestToResponse(requestStub))
    .via(ConnectionBuilder.responseToEntity)
    .runWith(Sink.head)

  }
}
