package com.orendain.sraw.model.attribute

import com.orendain.sraw.Connection
import com.orendain.sraw.model.{Comment, Listing}

trait Commentable {

  def comments(sort: String)(implicit con: Connection): Listing[Comment]
}
