import Util.printPart

import scala.collection._
import scala.collection.immutable.Seq
import scala.collection.mutable.ListBuffer
import scala.io.Source

object Aoc2020Day09 {

  def main(args: Array[String]): Unit = {
    printPart("part01", "2020day09", part01)
    printPart("part02", "2020day09", part02)
  }

  def part01(source: Source): Int = {
    val preambleLength = 25
    val guide = new FixedList[Int](preambleLength)

    val iterator = source.getLines()
    var i = -1
    while (iterator.hasNext) {
      val num = iterator.next().toInt
      i += 1
      if (i < preambleLength || isValidNumber(guide, num)) {
        guide.append(num)
      } else {
        return num
      }
    }

    -1
  }

  def isValidNumber(guide: Seq[Int], num: Int): Boolean = {
    for (i <- guide.indices) {
      for (j <- 0 until guide.size - 1) {
        if (guide(i) + guide(j) == num) {
          return true
        }
      }
    }

    false
  }

  def part02(source: Source): Int = {
    // list is naturally ascending and exploit is within int range, therefore we can ignore > int
    val exploit = 144381670
    val numbers = source.getLines()
      .map(x => x.toIntOption)
      .filter(_.isDefined)
      .map(x => x.get)
      .toList

    // probably faster to start from the middle and expand than the other way around
    var smallestIndex = numbers.length / 2
    var largestIndex = numbers.length / 2 + 1
    while (smallestIndex >= 0 && largestIndex < numbers.length) {
      var small = smallestIndex
      var large = largestIndex

      while (large > small) {
        val weakness = findWeakness(exploit, numbers, small, large)
        if (weakness.isDefined) {
          return weakness.get
        }
        large -= 1
      }

      large = largestIndex
      small = smallestIndex + 1
      while (small < large) {
        val weakness = findWeakness(exploit, numbers, small, large)
        if (weakness.isDefined) {
          return weakness.get
        }
        small += 1
      }

      smallestIndex -= 1
      largestIndex += 1 // ignore edge case here because it is apparent there is no edge case
    }

    -1
  }

  def findWeakness(exploit: Int, numbers: Seq[Int], small: Int, large: Int): Option[Int] = {
    val slice = numbers.slice(small, large + 1)
    if (slice.sum == exploit) {
      return Some(slice.min + slice.max)
    }
    None
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
