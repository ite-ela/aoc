import scala.io.Source
import scala.util.Using

object Util {

  def printPart[T](part: String, fileName: String, fun: Source => T): Unit = {
    println(part + ": " + readFile(fileName, fun).getOrElse("No Result, Missing input file?"))
  }

  def readFile[T](fileName: String, consumer: Source => T): Option[T] = {
    val filename = "../resources/input/" + fileName
    Using(Source.fromFile(filename)) { source =>
      return Some(consumer(source))
    }

    None
  }
}
