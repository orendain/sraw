package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.model.extract._

object UserNote extends HasExtractor[UserNote] {

  //val tmp = new Test[Boolean, String]()

  val extractor = new ObjectExtractor[UserNote] {
    def canExtract(json: JObject) = (json \ "date") != JNothing && (json \ "id") != JNothing
    def extract(json: JObject) = new UserNote(json)
  }
}

class UserNote(val json: JObject) extends RedditObject {

  val id = valString("id")
  val date = valString("date") //TODO: parse error with long, double works though
  val name = valString("name")
//  val note = valOp[String]("note")
}

class UserNoteBackup(val json: JObject) extends RedditObject {

  val id = valString("id")
  val date = valLong("date")
  val name = valString("name")
  val note = valOp[String]("note")
}


object UserList extends HasExtractor[UserList] {

  //val tmp = new Test[Boolean, String]()

  val extractor = new ObjectExtractor[UserList] {
    def canExtract(json: JObject) = (json \ "kind") == JString("UserList")
    def extract(json: JObject) = new UserList(json)
  }
}

class UserList(val json: JObject) extends RedditThing {
  val kind = "UserList"
  val children = (json \ "data" \ "children") match {
    case JArray(lst) => lst map { UserNote.extractor.extract }
    case _ => throw new ExtractionException("Bad Match")
  }
}
