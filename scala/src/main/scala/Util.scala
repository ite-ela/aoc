import scala.io.Source
import scala.util.Using

class Util[T] {

  def printPart(part: String, fileName: String, fun: Source => T): Unit = {
    println(part + ": " + readFile(fileName, fun))
  }

  def readFile(fileName: String, consumer: Source => T): T = {
    val filename = "../resources/input/" + fileName
    Using(Source.fromFile(filename)) { source =>
      return consumer(source)
    }

    None.get
  }
}
