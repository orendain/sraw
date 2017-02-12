package com.orendain.sraw.model.attribute

trait Created {

  /**
   * the time of creation in local epoch-second format. ex: 1331042771.0
   */
  val created: Double

  /**
   * the time of creation in UTC epoch-second format. Note that neither of these ever have a non-zero fraction.
   */
  val createdUTC: Double
}
