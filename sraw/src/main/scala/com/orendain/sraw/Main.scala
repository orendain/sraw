package com.orendain.sraw

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import com.orendain.sraw.api._
import com.orendain.sraw.model._

object Main extends App {

//  val userAgent = UserAgent("desktop", "com.orendain.sraw_test", "v0.1.0", "sraw_bot")
//  val creds = ScriptCredentials("CredentialsGoHere", "CredentialsGoHere", "http://www.orendainx.com")

//  implicit val connection = Connection(userAgent, creds)
//
//  val data = PasswordAuth("sraw_bot", "PassGoesHere")
//  val data = PasswordAuth("UsernameGoesHere", "PassGoesHere")

  //val startTime = System.currentTimeMillis()
//  val result = AuthorizationAPI.accessToken(data).process()
  //val endTime = System.currentTimeMillis()

//    val res = connection.process(ress)
//    res onSuccess { case x =>
//      println(x)
//    }
//
//    val result = Await.result(res, 2000 seconds)
  //val endTime2 = System.currentTimeMillis()

  //println("time diff #1 = " + (endTime-startTime))
  //println("time diff #2 = " + (endTime2-startTime))

//  println("Result: " + result)

//  val userAccess2 = result match {
//    //case x: Error => x
//    case u: AccessToken => u
//  }
//
//  connection.gainAccess(userAccess2)

  //val res2 = WikiAPI.add("test", "t3_316aq4", "http://i.imgur.com/DMtu514.jpg")
  //val res2 = AccountAPI.me()
  //val res2 = AccountAPI.blocked()
  //val res2 = CaptchaAPI.needsCaptcha()

  //val ress2 = FlairAPI.clearTemplates("srawtesting", "LINK_FLAIR")
  //val ress2 = FlairAPI.deleteFlair("srawtesting", "sraw_bot")
  //val ress2 = FlairAPI.deleteFlairTemplate("srawtesting", "596677fc-dffb-11e4-831d-22000bb2cc8a")
  //val ress2 = FlairAPI.set("srawtesting", "someClass", "t3_3273vv", "sraw_bot", "lolNoob3")
  //val ress2 = FlairAPI.config("srawtesting", true, "left", true, "left", true)
  //val ress2 = FlairAPI.list("srawtesting")
  //val ress2 = FlairAPI.template("srawtesting", "somenewtemplate", "someotherstring", "USER_FLAIR", "sometexthere", true)
  //val ress2 = FlairAPI.selector("srawtesting", "", "")
  //val ress2 = FlairAPI.select("srawtesting", "4fe932c0-e008-11e4-a099-22000b6e0035", "t3_3273vv", "orendain", "ima22unicorn2")
  //val ress2 = FlairAPI.setEnabled("srawtesting", true)

  //val res2 = GoldAPI.gild("t3_3273vv")
  //val res2 = CaptchaAPI.captcha("FZYA73xQH8PkCW7JJr2JKtaO7ZGyCkEK")
  //val res2 = SubredditsAPI.about("funny")
  //val res2 = LinksAndComments.getFromURL("http://i.imgur.com/p1I6ziU.jpg")
  //val res2 = LinksAndComments.get(Array("t1_cq6ispr"))
  //val res2 = LinksAndComments.comment("t4_3ab6ev", "autoMessage")
  //val res2 = CaptchaAPI.newCaptcha()
  //val res2 = LinksAndComments.delete("t3_327ums")
  //val res2 = LinksAndComments.unhide("t3_327kh2")
  //val res2 = LinksAndComments.info("srawtesting", Array("t3_327kh2", "t3_327jz6"))
  //val res2 = LinksAndComments.info(Array("t1_cq8qe2n", "t3_327jz6", "t4_3ab6jq"))
  //val res2 = LinksAndComments.info("http://i.imgur.com/8i3n1zo.png")
  //val res2 = LinksAndComments.unmarkNSFW("t3_327kh2")
  //val res2 = LinksAndComments.report("t3_327z7w", "testingSRAW2")
  //val res2 = LinksAndComments.save("myCategory222", "t3_3273vv")
  //val res2 = LinksAndComments.unsave("t3_3273vv")
  //val res2 = LinksAndComments.vote("t3_3273vv", 0)
  //val res2 = LinksAndComments.savedCategories()
  //val res2 = LinksAndComments.sendReplies("t3_327jz6", false)
  //val res2 = LinksAndComments.setContestMode("t3_327jz6", true)
  //val res2 = LinksAndComments.setSubredditSticky("t3_327jz6", true)

  //val res2 = ListingsAPI.byNames(Array("t3_327kh2"))
  //val res2 = ListingsAPI.comments("327iwh", 3, "confidence")
//    val res2 = ListingsAPI.duplicates("32jq8f")
//    val res2 = ListingsAPI.hot("funny")
//    val res2 = ListingsAPI.neww("funny")
//    val res2 = ListingsAPI.random("funny")
//    val res2 = ListingsAPI.related("32ntso")
//    val res2 = ListingsAPI.top("funny")
//    val res2 = ListingsAPI.controversial("funny")




  //val res2 = MessageAPI.block("t5_37s9d")
  //val res2 = MessageAPI.compose("srawtesting", "heyWhatsUp!", "subjectHere", "orendain")
  //val res2 = MessageAPI.readAll()
  //val res2 = MessageAPI.readMessage(Array("t4_3adhj6", "t4_3adhi9"))
  //val res2 = MessageAPI.unblockSubreddit("t5_37s9d")
  //val res2 = MessageAPI.unreadMessage(Array("t4_3adhj6", "t4_3adhi9"))
  //val res2 = MessageAPI.sent()

  //val res2 = ModerationAPI.log("srawtesting")
  //val res2 = ModerationAPI.acceptModeratorInvite("sraw")
  //val res2 = ModerationAPI.approve("t3_32hh7n")
  //val res2 = ModerationAPI.distinguish("t1_cqbdjej", "special")
  //val res2 = ModerationAPI.stylesheet("srawtesting")


  //val res2 = SubredditsAPI.recommendSubreddits(Seq("funny"), Seq.empty[String])
  //val res2 = SubredditsAPI.submissionText("funny")
  //val res2 = SubredditsAPI.stylesheet("funny", "save", "testreason", ".testing-noop{color:red;}")
  //val res2 = SubredditsAPI.subredditsByTopic("scala")
  //val res2 = SubredditsAPI.subscribe("t5_37s9d", "sub")
  //val res2 = SubredditsAPI.settings("srawtesting", true)
  //val res2 = SubredditsAPI.mineSubscriber()
  //val res2 = SubredditsAPI.mineContributor()
  //val res2 = SubredditsAPI.mineModerator()

  //val res2 = SubredditsAPI.search("sraw")
  //val res2 = SubredditsAPI.popular()
  //val res2 = SubredditsAPI.neww()
  //val res2 = SubredditsAPI.employee()
  //val res2 = SubredditsAPI.gold()






  //val res2 = UsersAPI.friend()
  //val res2 = UsersAPI.setPermissions("srawtesting", "orendain")
  //val res2 = UsersAPI.usernameAvailable("oreandinssszz")

//    val res2 = UsersAPI.delete("orendain")
//    val res2 = UsersAPI.aboutFriend("orendainx")
//    val res2 = UsersAPI.update("orendainx", "myFriend")
//    val res2 = UsersAPI.notifications()
//    val res2 = UsersAPI.updateNotifications("orendain")

//    val res2 = UsersAPI.trophies("hotchocletylesbian")
  //val res2 = UsersAPI.about("hotchocletylesbian")

//    val res2 = UsersAPI.overview("sraw_bot")
//    val res2 = UsersAPI.submitted("orendain")
//    val res2 = UsersAPI.comments("sraw_bot")
//    val res2 = UsersAPI.liked("orendain")
//    val res2 = UsersAPI.disliked("sraw_bot")
//    val res2 = UsersAPI.hidden("orendain")
//    val res2 = UsersAPI.saved("orendain")
  //val res2 = UsersAPI.gilded("hotchocletylesbian")


  //val res2 = WikiAPI.add("srawtesting", "config/description", "soyedgar")
//    val res2 = WikiAPI.del("srawtesting", "index", "soyedgar")

//    val res2 = WikiAPI.edit("srawtesting", "index", "testreplacement", "apitest")
  //val res2 = WikiAPI.hide("srawtesting", "index", "d660f760-e388-11e4-8973-22000bb30918")
  //val res2 = WikiAPI.revert("srawtesting", "index", "d660f760-e388-11e4-8973-22000bb30918")


  //val res2 = WikiAPI.discussions("srawtesting", "index")
  //val res2 = WikiAPI.pages("srawtesting")
  //val res2 = WikiAPI.revisions("srawtesting")
  //val res2 = WikiAPI.pageRevisions("srawtesting", "index")

  //val res2 = WikiAPI.pagePermissions("srawtesting", "index")
  //val res2 = WikiAPI.setPagePermissions("srawtesting", "index", false, 1)

  //val res2 = WikiAPI.pageContent("srawtesting", "indexx")




//    val res2 = MultisAPI.copy("anothermreddit5", "/user/sraw_bot/m/mymultireddit4", "/user/sraw_bot/m/mymultireddit6")
//    val res2 = MultisAPI.mine(false)
//    val res2 = MultisAPI.rename("mythirdmultireddit", "/user/sraw_bot/m/mymultireddit6", "/user/sraw_bot/m/mymultireddit7")
//    val res2 = MultisAPI.by("sraw_bot", false)
//      val res2 = MultisAPI.delete("/user/sraw_bot/m/mymultireddit2", true)
//    val res2 = MultisAPI.byName("/user/sraw_bot/m/mymultireddit4", false)


//    ///////val res2 = MultisAPI.create("srawtesting", "indexx")
  //val res2 = MultisAPI.description("/user/sraw_bot/m/mymultireddit4")


//    //////////val res2 = MultisAPI.updateDescription("srawtesting", "indexx")
//    val res2 = MultisAPI.removeSubreddit("/user/sraw_bot/m/mymultireddit4", "scala")
//    val res2 = MultisAPI.aboutSubreddit("/user/sraw_bot/m/mymultireddit4", "sraw")
  ///////////val res2 = MultisAPI.addSubreddit("/user/sraw_bot/m/mymultireddit4", "indexx")

//    val ress2 = SearchAPI.search("funny", "dog")

//    val res2 = ThreadsAPI.create("testThread", false, "noResources", "someRandomTestTitle")
//    val res2 = ThreadsAPI.close("uqv3qyvqxwpn")
//    val res2 = ThreadsAPI.inviteContributor("uqv3qyvqxwpn", "orendain", "liveupdate_contributor", "+manage")
//    val res2 = ThreadsAPI.leaveContributor("uquzxjju3pu2")
//    val res2 = ThreadsAPI.removeContributorInvite("uqv3qyvqxwpn", "t2_mgr1z")
//    val res2 = ThreadsAPI.removeContributor("uqv3qyvqxwpn", "t2_mgr1z")
    //val res2 = ThreadsAPI.setContributorPermissions("uqv3qyvqxwpn", "sraw_bot", "liveupdate_contributor", "-close")
//    val res2 = ThreadsAPI.edit("uqv3qyvqxwpn", "newDescription!", true, "noResources!", "someRandomTestTitle!!!")
//    val res2 = ThreadsAPI.update("uqv3qyvqxwpn", "thisIsAThreadUpdate2")
//    val res2 = ThreadsAPI.strikeUpdate("uqv3qyvqxwpn", "LiveUpdate_bd6dc658-e3aa-11e4-ba8d-22000bb30b88")
//    val res2 = ThreadsAPI.deleteUpdate("uqv3qyvqxwpn", "LiveUpdate_bd6dc658-e3aa-11e4-ba8d-22000bb30b88")
//    val res2 = ThreadsAPI.listUpdates("uqv3qyvqxwpn")
//    val res2 = ThreadsAPI.about("uqv3qyvqxwpn")
//    val res2 = ThreadsAPI.contributors("uqv3qyvqxwpn")
//    val res2 = ThreadsAPI.discussions("uqv3qyvqxwpn")
//    val res2 = ThreadsAPI.acceptContributorInvite("uqv3qyvqxwpn")
//
  //val res2 = MessageAPI.inbox()
  //val res2 = AccountAPI.me()
  //val res2 = AccountAPI.meTrophies()

  //val ress2 = AccountAPI.prefs()
  //val result2 = Subreddit("srawtesting").settings()
//  val result2 = LinkAndCommentAPI.info(Seq(
//      "t3_39uhgj", "t1_cs6lyak", "t3_39uv7j")).process()
  //val result2 = MultiAPI.addSubreddit("/user/SRAW_bot/m/mymultireddit4", "tifu").process()
  //val captcha = FilledCaptcha("IUuogzE7qM5RZQ1jU4KV0peyY5vAqDv4", "rjgsjw")
  //val result2 = SubredditAPI.submissionText("askreddit").process()
  //val result2 = WikiAPI.hide("srawtesting", "testwikipage1", "f8ac67f4-2876-11e5-b1c9-0e11236ae7ff").process
  //val result2 = WikiAPI.pageContent("srawtesting", "testwikipage1").process
  //val result2 = WikiAPI.pageRevisions("srawtesting", "testwikipage1").process
  //val result2 = FlairAPI.list("srawtesting").process()
//
//  val lo = ListOptions(limit = 2)
//  val result2 = SearchAPI.search("funny", listOptions = lo).process()
//  val lo2 = ListOptions(after = result2.after.get, count = 2)
//  val result3 = SearchAPI.search("funny", listOptions = lo2).process()
//  println("Result2: " + result2)

  import scala.collection.mutable.HashMap
  favSpots()
//  test()
  
  def test() = {
    val userAgent = UserAgent("desktop", "com.orendain.sraw_test", "v0.1.1", "sraw_bot")
    val creds = ScriptCredentials("CredentialsGoHere", "CredentialsGoHere", "http://www.orendainx.com")
    
    implicit val connection = Connection(userAgent, creds)    
    val data = PasswordAuth("sraw_bot", "PassGoesHere")
    val accessToken = AuthorizationAPI.accessToken(data).process()
    //val accessToken = PasswordAute.h("sraw_bot", "PassGoesHere").accessToken()
    
    connection.gainAccess(accessToken)
    
    AccountAPI.me().process()
  }

  def favSpots() = {
    // Setup
    val userAgent = UserAgent("desktop", "com.orendain.srawexamples.karmacounter", "v1.0.0", "sraw_bot")
    val creds = ScriptCredentials("CredentialsGoHere", "CredentialsGoHere", "http://www.orendainx.com")
    implicit val connection = Connection(userAgent, creds)

    // Authenticate
    val accessToken = PasswordAuth("sraw_bot", "PassGoesHere").accessToken()
    connection.gainAccess(accessToken)

    val hashmap = HashMap.empty[String, Int]
    val hashmap2 = HashMap.empty[String, Int]

    val username = "GallowBoob"
    
    val lo = ListOptions(showAll = true, limit = 100)
    val subList = UserAPI.submitted(username, lo).process()

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

      subList.after match {
        case Some(n) => {
          val lo = ListOptions(after = n, limit = 100)
          countSubmissions(UserAPI.submitted(username, lo).process())
        }
        case None =>
      }
    }
    
    val comments = UserAPI.comments(username).process()
    countComments(comments)
    def countComments(comList: Listing[Comment]) {
      val comments = comList.children
      comments foreach { s =>
        val sr = s.subreddit match {
          case Some(n) => n
          case None => s.subreddit_id
        }
        hashmap2.update(sr, hashmap2.getOrElse(sr, 0) + 1)
      }

      comList.after match {
        case Some(n) => {
          val lo = ListOptions(after = n, limit = 100)
          countComments(UserAPI.comments(username, lo).process())
        }
        case None =>
      }
    }

    println("\n# of posts \t subreddit")
    println("----------------------------------------")
    hashmap.toList foreach { case (sr, cnt) => println(cnt + "\t\t" + sr) }
    val total = hashmap.toList.map{ case(sr, cnt) => cnt }.fold(0)(_ + _)
    println("Total: \t" + total)

    println("\n# of comments \t subreddit")
    println("----------------------------------------")
    hashmap2.toList foreach { case (sr, cnt) => println(cnt + "\t\t" + sr) }
    val total2 = hashmap2.toList.map{ case(sr, cnt) => cnt }.fold(0)(_ + _)
    println("Total: \t" + total2)
  }

//  ListingAPI.byNames(Seq("t3_39n1ct", "t5_2qh33")).process()

//    val result2 = Await.result(res2, 20000 seconds)

  //println("url = " + result2.url)

  System.exit(0)

}
