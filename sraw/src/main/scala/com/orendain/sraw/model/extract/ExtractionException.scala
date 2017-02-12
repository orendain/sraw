package com.orendain.sraw.model.extract

//case class ExtractionException(err: Error) extends Exception(err.toString)
class ExtractionException(msg: String) extends Exception(msg)
