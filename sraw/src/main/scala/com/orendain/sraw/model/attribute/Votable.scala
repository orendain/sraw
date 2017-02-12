package com.orendain.sraw.model.attribute

trait Votable {
  /**
   * The number of upvotes. (includes own)
   */
  val upvotes: Int

  /**
   * the number of downvotes. (includes own)
   */
  val downvotes: Int

  /**
   * true if thing is liked by the user, false if thing is disliked, null if the user has not voted or you are not logged in. Certain languages such as Java may need to use a boolean wrapper that supports null assignment.
   */
  val isLiked: Option[Boolean]
}
