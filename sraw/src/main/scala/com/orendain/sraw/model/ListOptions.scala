package com.orendain.sraw.model

import scala.collection.mutable.Map

/**
  *
  * @param before fullname of a thing
  * @param after fullname of a thing
  * @param count a positive integer (default: 0)
  * @param limit the maximum number of items desired (default: 25, maximum: 100)
  * @param showAll true to show all items, false otherwise (optional)
  * @param sr_detail true to expand subreddits, false otherwise (optional)
  */
case class ListOptions(
    before: String = "",
    after: String = "",
    count: Int = -1,
    limit: Int = -1,
    showAll: Boolean = false,
    sr_detail: Boolean = false
) {

  val toMap = {
    val map = Map.empty[String, String]

    if (count >= 0) map += ("count" -> count.toString)
    if (limit >= 0) map += ("limit" -> limit.toString)
    if (showAll) map += ("show" -> "all")
    if (sr_detail) map += ("sr_detail" -> "true")
    if (!before.isEmpty) map += ("before" -> before)
    else if (!after.isEmpty) map += ("after" -> after)

    map.toMap
  }
}

object ListOptions {
  val Empty = ListOptions()
}
