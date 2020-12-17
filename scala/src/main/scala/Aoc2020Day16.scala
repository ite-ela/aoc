import Util.printPart

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object Aoc2020Day16 {
  def main(args: Array[String]): Unit = {
    printPart("part01", "2020day16", part01)
    printPart("part02", "2020day16", part02)
  }

  def part01(source: Source): Int = {
    val rangePattern = raw".+: (\d+)-(\d+) or (\d+)-(\d+)".r
    val ranges = mutable.HashSet.empty[Range]
    val invalids = mutable.ArrayBuffer.empty[Int]

    def noneMatchesRange(num: Int): Boolean = {
      for (range <- ranges) {
        if (range.contains(num)) {
          return false
        }
      }

      true
    }

    for (line <- source.getLines()) {
      if (rangePattern.matches(line)) {
        val rangePattern(aMin, aMax, bMin, bMax) = line
        ranges.addOne(aMin.toInt to aMax.toInt)
        ranges.addOne(bMin.toInt to bMax.toInt)
      } else if (line.contains(",")) {
        line.split(",")
          .map(num => num.toInt)
          .filter(noneMatchesRange)
          .foreach(invalids.addOne)
      }
    }

    invalids.sum
  }

  def part02(source: Source): Long = {
    val rangePattern = raw"(.+): (\d+)-(\d+) or (\d+)-(\d+)".r
    val ranges = mutable.HashMap.empty[String, (Range, Range)]
    val validTickets = mutable.ArrayBuffer.empty[Seq[Int]]

    def noneMatchesRange(num: Int): Boolean = {
      for ((a, b) <- ranges.values) {
        if (a.contains(num) || b.contains(num)) {
          return false
        }
      }

      true
    }

    var myTicket = Seq.empty[Int]
    for (line <- source.getLines()) {
      if (rangePattern.matches(line)) {
        val rangePattern(name, aMin, aMax, bMin, bMax) = line
        ranges.addOne(name, (aMin.toInt to aMax.toInt, bMin.toInt to bMax.toInt))
      } else if (line.contains(",")) {
        val ticket = line.split(",")
          .map(num => num.toInt)
        if (myTicket == Seq.empty) {
          myTicket = ticket
        }

        if (!ticket.exists(noneMatchesRange)) {
          validTickets.addOne(ticket)
        }
      }
    }

    def matchesAnyRange(i: Int, a: Range, b: Range): Boolean = {
      for (ticket <- validTickets) {
        val value = ticket(i)
        if (!a.contains(value) && !b.contains(value)) {
          return false
        }
      }
      true
    }

    def getNamesThatMatchesAllTickets(index: Int): ArrayBuffer[String] = {
      val namesMatch = ArrayBuffer.empty[String]
      for ((key, (a, b)) <- ranges) {
        if (matchesAnyRange(index, a, b)) {
          namesMatch.addOne(key)
        }
      }

      namesMatch
    }

    val myValues = mutable.HashMap.empty[String, Int]
    val possibleNamesForValues = mutable.HashMap.empty[Int, ArrayBuffer[String]]
    for (i <- myTicket.indices) {
      val names = getNamesThatMatchesAllTickets(i)
      possibleNamesForValues.put(myTicket(i), names)
    }

    while (myValues.size < possibleNamesForValues.size) {
      val (value, names) = possibleNamesForValues
        .filter(e => e._2.nonEmpty)
        .minBy(x => x._2.length)

      val name = names(0)
      myValues.put(name, value)

      for ((_, nameList) <- possibleNamesForValues.filter(e => e._2.contains(name))) {
        nameList.remove(nameList.indexOf(name))
      }
    }

    myValues.filter(e => e._1.startsWith("departure"))
      .values
      .map(value => value.toLong)
      .product
  }
}
