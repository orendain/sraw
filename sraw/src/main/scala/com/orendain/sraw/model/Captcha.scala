package com.orendain.sraw.model

import org.json4s.JsonAST._
import com.orendain.sraw.api.CaptchaAPI
import com.orendain.sraw.model.extract._

object Captcha extends HasExtractor[Captcha] {

  val extractor = new ObjectExtractor[Captcha] {
    def canExtract(json: JObject) = (json \ "json" \ "data" \ "iden") != JNothing
    def extract(json: JObject) = (json \ "json" \ "data" \ "iden") match {
      case JString(iden) => new Captcha(iden)
      case _ => throw new ExtractionException("Bad Extraction")
    }
  }
}

class Captcha(val iden: String) extends RedditEntity {

  val url = s"https://www.reddit.com/captcha/${iden}.png"

  // TODO: retrieve image either through regular HTTP using the URL above,
  // saving an API call
}


case class FilledCaptcha(override val iden: String, response: String) extends Captcha(iden)


object CaptchaImage extends HasExtractor[CaptchaImage] {

  val extractor = new Extractor[CaptchaImage] {
    def canExtract(json: JValue) = json match { case JString(s) => true; case _ => false }
    def extract(json: JValue) = json match { case JString(s) => new CaptchaImage(s) }
  }
}

class CaptchaImage(val pngString: String) extends RedditEntity
