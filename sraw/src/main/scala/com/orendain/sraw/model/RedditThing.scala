package com.orendain.sraw.model

trait RedditThing extends RedditObject {

  val kind: String

  //val name = values("name").toString
  //val id = values("id").toString

  //override val values = json.values("data").asInstanceOf[Map[String,Any]]
  //override def values(key: String) = (json \ "data" \ key).asInstanceOf[Map[String,Any]]
  override def values(key: String) = (json \ "data" \ key)
}
