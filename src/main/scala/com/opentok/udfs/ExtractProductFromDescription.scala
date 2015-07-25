package com.opentok.udfs

import org.apache.hadoop.hive.ql.exec.{Description, UDF}

import scala.util.{Failure, Success, Try}

@Description(
  name = "extract_productid",
  value = "_FUNC_(str, int, str) extracts a product id based on the invoice description"
)
class ExtractProductFromDescription extends UDF {

  def parse(description: String, amount: Int, customerName:String): Try[String] = Try {
    val patternAudio = "(.*audio.*)".r
    val patternSupport = (".*(support | engineer services | one tiem set up fee | reliability fee | consulting fee).*").r
    val patternSlices = "slices".r
    val patternArchive = ".*(archive mins | archiving ).*".r
    val patternMinBase = (".*( video communication | video conferencing | base subscription (includes 10k mins) | " +
      "video communicatio service |  min )").r
    val patternMinTier1 = "([0-9]+) ([A-Za-z]+)".r
    val patternMinTier2 = "([0-9]+) ([A-Za-z]+)".r
    val patternMinTier3 = "([0-9]+) ([A-Za-z]+)".r
    val patternMinTier4 = "([0-9]+) ([A-Za-z]+)".r

    val d = description.toLowerCase
    d match {
      case patternAudio(d:String)  => "audio_services"
      case patternSupport(d:String)  => "professional_services"
      case patternSlices(d:String)  => "archive_slices"
      case patternArchive(d:String)  => "archive_overaged_minutes"
      case patternMinBase(d:String)  => "archive_slices"
      case patternMinTier1(d:String)  => "archive_slices"
      case patternMinTier2(d:String)  => "archive_slices"
      case patternMinTier3(d:String)  => "archive_slices"
      case patternMinTier4(d:String)  => "archive_slices"


      case _ => "others"
    }
  }

  def evaluate(desc: String, amount: Int, customerName: String): String = {
    parse(desc, amount, customerName).getOrElse(null)
  }
}