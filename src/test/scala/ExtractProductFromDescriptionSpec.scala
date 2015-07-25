import com.opentok.udfs.ExtractProductFromDescription
import org.scalatest._

class ExtractProductFromDescriptionSpec extends WordSpec with Matchers {

  val udf = new ExtractProductFromDescription()

  "should parse a description correctly" in {
    val parsed = udf.parse("Licensing Tokbox audio communication service- 2014", 50, "mlb")
    parsed.get shouldEqual "audio_services"
  }

  "should parse a support description correctly" in {
    val parsed = udf.parse("Licensing Tokbox support service- 2014", 50, "mlb")
    parsed.get shouldEqual "support_service"
  }
}
