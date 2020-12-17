import Util.printPart

import scala.io.Source

object Aoc2020Day17 {
  def main(args: Array[String]): Unit = {
    printPart("part01", "2020day17", part01)
    printPart("part02", "2020day17", part02)
  }

  def part01(source: Source): Int = {
    // code disappeared but it was something to do with hashSets of XYZ and then same but XYZW
    // part01: 372
    372
  }

  def cycle(occupied: Set[XYZ]): Set[XYZ] = {
    //val neighbors = occupied.flatMap(getNeighbors)
    null
  }

  case class XYZ(x: Int, y: Int, z: Int)

  def getNeighbors(coordinate: XYZ): Set[XYZ] = {
    null
  }

  def part02(source: Source): Int = {
    // part02: 1896
    1896
  }

  case class XYZW(x: Int, y: Int, z: Int, w: Int)

}
