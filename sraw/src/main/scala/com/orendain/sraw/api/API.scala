package com.orendain.sraw.api

trait API {

  /**
    * Url-ize a subreddit by name
    *
    * Passing in an empty string yields an empty string.
    *
    * @example sr("sraw") returns the string "/r/sraw"
    * @example sr("") returns the string ""
    */
  def sr(subreddit: String) = subreddit.isEmpty match {
    case false => "/r/" + subreddit
    case true => ""
  }
}
