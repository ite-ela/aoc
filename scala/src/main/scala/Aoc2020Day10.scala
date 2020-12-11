import Util.printPart

import scala.collection.mutable
import scala.io.Source

object Aoc2020Day10 {

  def main(args: Array[String]): Unit = {
    printPart("part01", "2020day10", part01)
    printPart("part02", "2020day10", part02)
  }

  def part01(source: Source): Int = {
    var node = 0
    val diffs = mutable.HashMap[Int, Int]((3, 1)) // the last node is always there with a diff of 3
    for (jolt <- source.getLines().toList
      .map(x => x.toInt)
      .sorted) {
      val diff = jolt - node
      diffs.put(diff, diffs.getOrElse(diff, 0) + 1)
      node += diff
    }

    diffs.getOrElse(1, 0) * diffs.getOrElse(3, 0)
  }

  def part02(source: Source): BigInt = {
    val jolts = 0 :: source.getLines()
      .map(x => x.toInt)
      .toList
      .sorted // ignore last, as that path does not add any new possible paths

    val paths = Array.fill[Long](jolts.size)(0)
    paths(0) = 1
    for (i <- jolts.indices) {
      for (j <- 1 to 3) {
        if (i + j < jolts.size) {
          val diff = jolts(i + j) - jolts(i)
          if (diff <= 3) { // if the next node is within range of this node
            paths(i + j) += paths(i)
          }
        }
      }
    }
    paths.last
  }
}
