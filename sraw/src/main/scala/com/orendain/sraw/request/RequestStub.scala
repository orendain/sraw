package com.orendain.sraw.request

import com.orendain.sraw.Connection
import com.orendain.sraw.model.extract.HasExtractor

case class RequestStub[T](
    blueprint: RequestStubBlueprint,
    params: Seq[String],
    input: RequestInput,
    extractor: HasExtractor[T]
  ) {

  /**
   *
   */
  def process()(implicit con: Connection) = con.process(this)

  def futureProcess()(implicit con: Connection) = con.futureProcess(this)
}
