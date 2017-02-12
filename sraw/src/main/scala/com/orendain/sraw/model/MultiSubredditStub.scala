package com.orendain.sraw.model

import org.json4s.JsonAST.JObject

class MultiSubredditStub(val json: JObject) extends MultiSubreddit {

  val icon_img = valString("icon_img")
  val key_color = valString("icon_img")
  val header_img = valOp[String]("header_img")
  val user_is_banned = valBoolean("user_is_banned")
  val user_is_contributor = valBoolean("user_is_contributor")
  val user_is_moderator = valBoolean("user_is_moderator")
  val header_size = valOp[List[Int]]("header_size")
  val fullname = valString("name")
  val icon_size = valOp[String]("icon_size")
  val display_name = valString("display_name")

  val name = (json \ "name").toString
}
