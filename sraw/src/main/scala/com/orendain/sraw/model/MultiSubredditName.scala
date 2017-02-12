package com.orendain.sraw.model

import org.json4s.JsonAST.JObject

class MultiSubredditName(val json: JObject) extends MultiSubreddit {

  val name = valString("name")
}
