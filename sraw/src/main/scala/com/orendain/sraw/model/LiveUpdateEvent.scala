package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.model.attribute.Created
import com.orendain.sraw.model.extract._

object LiveUpdateEvent extends HasExtractor[LiveUpdateEvent] {

  val extractor = new ObjectExtractor[LiveUpdateEvent] {
    def canExtract(json: JObject) = (json \ "kind") == JString("LiveUpdateEvent")
    def extract(json: JObject) = new LiveUpdateEvent(json)
  }
}

class LiveUpdateEvent(val json: JObject) extends RedditThing with Created {

  val kind = "LiveUpdateEvent"

  val description = valString("description")
  val description_html = valString("description_html")
  val title = valString("title")
  val websocket_url = valString("websocket_url")
  val name = valString("name")
  val state = valString("state")
  val resources = valString("resources")
  val viewer_count_fuzzed = valBoolean("viewer_count_fuzzed")
  val id = valString("id")
  val viewer_count = valInt("viewer_count")
  val resources_html = valString("resources_html")


  // Created
  val created = valDouble("created")
  val createdUTC = valDouble("created_utc")
}
