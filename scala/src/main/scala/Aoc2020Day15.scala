import Util.{FixedList, printPart}

import scala.collection.mutable
import scala.io.Source

object Aoc2020Day15 {
  def main(args: Array[String]): Unit = {
    printPart("part01", "2020day15", part01)
    printPart("part02", "2020day15", part02)
  }

  def extractNumberSpoken(source: Source, number: Int): Int = {
    val spoken = mutable.HashMap.empty[Int, FixedList[Int]]

    def createFixedList(firstNumber: Int): FixedList[Int] = {
      val list = new FixedList[Int](2)
      list.append(firstNumber)
      list
    }

    def createOrAppend(speak: Int, index: Int): Unit = {
      if (spoken.contains(speak)) {
        spoken(speak).append(index)
      } else {
        spoken.put(speak, createFixedList(index))
      }
    }

    var i = 0
    var lastSpokenNumber = -1

    for (numStr <- source.getLines().iterator.next().split(",")) {
      lastSpokenNumber = numStr.toInt
      createOrAppend(lastSpokenNumber, i)
      i += 1
    }

    for (j <- i until number) {
      val opt = spoken.get(lastSpokenNumber)
      if (opt.isDefined) {
        val indices = opt.get
        if (indices.size == 1) {
          lastSpokenNumber = 0
        } else {
          lastSpokenNumber = indices.last - indices.head
        }
      } else {
        lastSpokenNumber = 0
      }

      createOrAppend(lastSpokenNumber, j)
    }
    lastSpokenNumber
  }

  def part01(source: Source): Int = {
    extractNumberSpoken(source, 2020)
  }

  def part02(source: Source): Int = {
    extractNumberSpoken(source, 30000000) // exactly the same brute force used in part 1. slow, but works
  }
}
