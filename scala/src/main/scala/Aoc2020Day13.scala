import Util.printPart

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object Aoc2020Day13 {
  def main(args: Array[String]): Unit = {
    printPart("part01", "2020day13", part01)
    printPart("part02", "2020day13", part02)
  }

  def part01(source: Source): Int = {
    val allLines = source.getLines().toList
    val timestamp = allLines.head.toInt
    val buses = mutable.HashMap.empty[Int, Int]
    for (bus <- allLines(1).split(",")) {
      if (bus != "x") {
        val busNumber = bus.toInt
        val waitTime = Math.abs((timestamp % busNumber) - busNumber)
        buses.put(waitTime, busNumber)
      }
    }

    val minWaitTime = buses.keySet.min
    minWaitTime * buses(minWaitTime)
  }

  def part02(source: Source): Long = {
    var i = 0
    val buses = ArrayBuffer.empty[(Long, Int)]
    for (bus <- source.getLines().drop(1).next().split(",")) {
      if (bus != "x") {
        buses.addOne(bus.toInt, i)
      }
      i += 1
    }

    var timestamp = 0L
    var (skip, _) = buses(0)
    for ((busNumber, order) <- buses.drop(1)) {
      while ((timestamp + order) % busNumber != 0) {
        timestamp += skip
      }
      skip *= busNumber // works because we only need to look at the products of prime numbers
    }

    timestamp
  }
}