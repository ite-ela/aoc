import Util.printPart

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object Aoc2020Day14 {
  def main(args: Array[String]): Unit = {
    printPart("part01", "2020day14", part01)
    printPart("part02", "2020day14", part02) //2008458958998 is too low
  }

  def part01(source: Source): Long = {
    var toZero = 0L
    var toOne = 0L
    val maskPattern = raw"mask = ([X01]+)".r
    val memPattern = raw"mem\[(\d+)\] = (\d+)".r
    val memory = mutable.HashMap.empty[Long, Long]
    for (line <- source.getLines()) {
      if (maskPattern.matches(line)) {
        toOne = 0
        toZero = 0
        var i = 0L
        val maskPattern(bitmask) = line
        for (char <- bitmask.toCharArray.reverse) {
          char match {
            case '1' => toOne = toOne | i
            case '0' => toZero = toZero | i
            case _ =>
          }

          i *= 2L
        }
      } else if (memPattern.matches(line)) {
        val memPattern(addr, value) = line
        var valueToPut = value.toLong | toOne
        valueToPut = valueToPut & ~toZero

        memory.updateWith(addr.toLong)(_ => Some(valueToPut))
      }
    }

    memory.values.sum
  }

  def findAddresses(unknowns: Seq[Int], addr: Long): Seq[Long] = {
    val allAddresses = mutable.HashSet.empty[Long]

    def addBitSets(unknown: Int, addr: Long) = {
      val newBitSetToAdd = mutable.BitSet.fromBitMask(Array(addr))
      allAddresses.addOne(newBitSetToAdd.addOne(unknown).toBitMask.head)
      newBitSetToAdd.remove(unknown)
      allAddresses.addOne(newBitSetToAdd.toBitMask.head)
    }

    addBitSets(unknowns.head, addr)
    for (unknown <- unknowns.drop(1)) {
      for (bitSet <- allAddresses.toList) { // toList to avoid editing while iterating same sequence
        addBitSets(unknown, bitSet)
      }
    }

    allAddresses.toList
  }

  def part02(source: Source): Long = {
    val maskPattern = raw"mask = ([X01]+)".r
    val memPattern = raw"mem\[(\d+)\] = (\d+)".r
    val memory = mutable.HashMap.empty[Long, Long]
    val unknowns = ArrayBuffer.empty[Int]
    val bitSet = mutable.BitSet.empty // playing around with bitsets to learn about them
    for (line <- source.getLines()) {
      if (maskPattern.matches(line)) {
        var i = 0
        bitSet.clear()
        unknowns.clear()
        val maskPattern(bitmask) = line
        for (char <- bitmask.toCharArray.reverse) {
          char match {
            case '1' => bitSet.addOne(i)
            case 'X' => unknowns.addOne(i)
            case _ =>
          }

          i += 1
        }
      } else if (memPattern.matches(line)) {
        val memPattern(addr, value) = line
        for (addr <- findAddresses(unknowns.toSeq, addr.toLong | bitSet.toBitMask.head)) {
          memory.put(addr, value.toLong)
        }
      }
    }

    memory.values.sum
  }
}
