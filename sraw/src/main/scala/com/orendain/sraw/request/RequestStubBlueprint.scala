package com.orendain.sraw.request

import akka.http.scaladsl.model.HttpMethod
import com.orendain.sraw.model.GenericEntity
import com.orendain.sraw.model.extract.HasExtractor

// applies to some requestblueprints
trait NoOAuth
trait PostInput

case class RequestStubBlueprint(val method: HttpMethod, val URI: String, val scope: Scope.Value) {

  /**
   *
   */
  def instantiate[T](
      params: Seq[String] = Seq.empty[String],
      input: RequestInput = NoInput,
      extractor: HasExtractor[T] = GenericEntity
    ) = RequestStub(this, params, input, extractor)
}
