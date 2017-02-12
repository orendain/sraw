package com.orendain.sraw.model

import org.json4s.JsonAST._
import org.json4s.JsonAST.JNumber

trait RedditObject extends RedditEntity {

  val json: JObject
  //val values = json.values
  def values(key: String) = (json \ key)

  def valString(key: String) = values(key) match {
    case JString(s) => s
    case JInt(n) => n.toString
    case JDouble(n) => n.toString
  }
  
  def valInt(key: String) = values(key) match {
    case JInt(n) => n.toInt
  }
  
  def valLong(key: String) = valInt(key).toLong
  
  def valBoolean(key: String) = values(key) match {
    case JBool(b) => b
  }
  
  def valDouble(key: String) = values(key) match {
    case JDouble(n) => n
  }

  // todo: meh...
  //def valArr(key: String) = values(key) match { case s: Seq[_] => s }
  
  def valArr(key: String) = values(key) match {
    case JArray(arr) => arr
  }

  //def valOp[T](key: String) = values.get(key) match {
  def valOp[T](key: String) = values(key) match {
    case JNull => None
    case JNothing => None
    case JString(s) => Some(s.asInstanceOf[T])
    case JInt(n) => Some(n.asInstanceOf[T])
    case JDouble(n) => Some(n.asInstanceOf[T])
    case JDecimal(n) => Some(n.asInstanceOf[T])
    case JBool(b) => Some(b.asInstanceOf[T])
    case JObject(obj) => Some(obj.asInstanceOf[T])
    case JArray(arr) => Some(arr.asInstanceOf[T])
  }
}

