package com.orendain.sraw.model.extract

import org.json4s.JsonAST._

//trait CanExtract[T] {
//  def extractor: Extractor[T]
//}

// TODO: can remove gen param?
trait HasExtractor[T] {

  val extractor: Extractor[T]
}

// TODO: +T ?
trait Extractor[+T] {

  def canExtract(json: JValue): Boolean
  def extract(json: JValue): T
}

trait ObjectExtractor[+T] extends Extractor[T] {

  def canExtract(json: JValue) = json match {
    case obj: JObject => canExtract(obj)
    case _ => false
  }
  def extract(json: JValue) =
    json match { case obj: JObject if canExtract(json) => extract(obj) }


  def canExtract(json: JObject): Boolean
  def extract(json: JObject): T
}

//
//class Ex1 extends Extractor {
//  type rt = Seq[Int]
//}
//
//class Ex2 extends Extractor {
//  type rt = Ex2
//}
//
//class End {
//  def end(ex: Extractor) = {
//    ex.extract(JNothing)
//  }
//}

//class XY {
//  //type WT = String
//
//  val e = new End()
//  val e1 = new Ex1
//
//  val s = e.end(e1)
//}
