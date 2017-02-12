package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.model.extract._

// TODO: extend SeqFactory/Array-equivalent

object Listing {

  def extractor[T](ex: HasExtractor[T]) = new HasExtractor[Listing[T]] {
    val extractor = new ObjectExtractor[Listing[T]] {
      def canExtract(json: JObject) = (json \ "kind") == JString("Listing")
      def extract(json: JObject) = new Listing(json, ex)
    }
  }
}

//class Listing[T](val json: JObject) extends RedditThing {
// TODO: extend Seq/ArrayBuffer w/ Parallelization?
class Listing[T](val json: JObject, val ex: HasExtractor[T]) extends RedditThing {

//  type x = Int

  // in "data"

//  val children = (json \ "data" \ "children") match {
//    case JArray(a) => a map { v => RedditExtractor.parseThing(v) }
//  }

//  println(children)

  //val childrens = (json \ "data" \ "children").asInstanceOf[JArray].children map { x => RedditThing(x.asInstanceOf[JObject]) }
  //val childrens = (json \ "data" \ "children").asInstanceOf[JArray] map { x => x.asInstanceOf[JObject] }

  //println(childrens)

  val kind = "Listing"

  val modhash = valOp[String]("modhash")
  val before = valOp[String]("before")
  val after = valOp[String]("after")

  val children = (json \ "data" \ "children") match {
    case JArray(lst) => lst.filter(ex.extractor.canExtract(_)).map(ex.extractor.extract(_))
    case _ => throw new ExtractionException("Bad Match")
  }

  val more = (json \ "data" \ "children") match {
    case JArray(lst) => {
      lst.find(More.extractor.canExtract(_)) match {
        case Some(j) => Some(More.extractor.extract(j))
        case None => None
      }
    }
    case _ => None
  }
}
