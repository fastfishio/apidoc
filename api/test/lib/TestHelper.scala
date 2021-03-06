package lib

import db.Authorization
import builder.OriginalValidator
import com.bryzek.apidoc.api.v0.models.{Original, OriginalType}
import com.bryzek.apidoc.spec.v0.models.{Service, ResponseCode, ResponseCodeOption, ResponseCodeUndefinedType, ResponseCodeInt}

import org.scalatest.{FunSpec, ShouldMatchers}

object TestHelper {

  def readFile(path: String): String = {
    scala.io.Source.fromFile(path).getLines.mkString("\n")
  }

  def readService(path: String): Service = {
    val config = ServiceConfiguration(
      orgKey = "gilt",
      orgNamespace = "com.bryzek",
      version = "0.9.10"
    )

    val contents = readFile(path)
    val validator = OriginalValidator(config, Original(OriginalType.ApiJson, contents), DatabaseServiceFetcher(Authorization.All))
    validator.validate match {
      case Left(errors) => sys.error(s"Errors: $errors")
      case Right(service) => service
    }
  }

}

