import scala.collection.Iterator
import scala.collection.immutable.Seq
import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.util.Using

object Util {

  def printPart[T](part: String, fileName: String, fun: Source => T): Unit = {
    println(part + ": " + readFile(fileName, fun).getOrElse("No Result, Missing input file?"))
  }

  def readFile[T](fileName: String, consumer: Source => T): Option[T] = {
    val filename = "../resources/input/" + fileName
    val attempt = Using(Source.fromFile(filename)) { source =>
      return Some(consumer(source))
    }

    attempt.getOrElse(throw attempt.failed.get)
  }

  class FixedList[A](max: Int) extends Seq[A] {

    val list: ListBuffer[A] = ListBuffer()

    def append(elem: A) {
      if (list.size == max) {
        list.dropInPlace(1)
      }
      list.append(elem)
    }

    override def apply(i: Int): A = list.apply(i)

    override def length: Int = list.length

    override def iterator: Iterator[A] = list.iterator
  }

}
