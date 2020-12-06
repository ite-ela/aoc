import scala.io.Source
import scala.util.Using

class Util[T] {

  def printPart(part: String, fileName: String, fun: Source => T): Util[T] = {
    println(part + ": " + readFile(fileName, fun).getOrElse("No Result, Missing input file?"))
    this
  }

  def readFile(fileName: String, consumer: Source => T): Option[T] = {
    val filename = "../resources/input/" + fileName
    Using(Source.fromFile(filename)) { source =>
      return Some(consumer(source))
    }

    None
  }
}
