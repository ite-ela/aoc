import Util.printPart

import scala.io.Source

object Aoc2020Day12 {
  def main(args: Array[String]): Unit = {
    printPart("part01", "2020day12", part01)
    printPart("part02", "2020day12", part02)
  }

  def part01(source: Source): Int = {
    val directions = List(XY(1, 0), XY(0, 1), XY(-1, 0), XY(0, -1))
    val boat = Boat(directions.head, XY(0, 0))
    val pattern = raw"([a-zA-Z])(\d+)".r
    for (line <- source.getLines()) {
      if (pattern.matches(line)) {
        val pattern(instruction, numStr) = line
        val num = numStr.toInt

        instruction match {
          case "N" => boat.position.y -= num
          case "S" => boat.position.y += num
          case "W" => boat.position.x -= num
          case "E" => boat.position.x += num
          case "R" => boat.direction = directions((directions.indexOf(boat.direction) + num / 90) % directions.size)
          case "L" => boat.direction = directions((directions.indexOf(boat.direction) - num / 90 + directions.size) % directions.size)
          case "F" => boat.position = XY(boat.position.x + boat.direction.x * num, boat.position.y + boat.direction.y * num)
        }
      }
    }

    Math.abs(boat.position.x) + Math.abs(boat.position.y)
  }

  def part02(source: Source): Int = {
    var wayPoint = XY(10, -1)
    var boat = XY(0, 0)

    val pattern = raw"([a-zA-Z])(\d+)".r
    for (line <- source.getLines()) {
      if (pattern.matches(line)) {
        val pattern(instruction, numStr) = line
        val num = numStr.toInt

        instruction match {
          case "N" => wayPoint.y -= num
          case "S" => wayPoint.y += num
          case "W" => wayPoint.x -= num
          case "E" => wayPoint.x += num
          case "R" => for (_ <- 1 to num / 90) wayPoint = XY(-wayPoint.y, wayPoint.x)
          case "L" => for (_ <- 1 to num / 90) wayPoint = XY(wayPoint.y, -wayPoint.x)
          case "F" => boat = XY(boat.x + wayPoint.x * num, boat.y + wayPoint.y * num)
        }
      }
    }

    Math.abs(boat.x) + Math.abs(boat.y)
  }

  case class Boat(var direction: XY, var position: XY)

  case class XY(var x: Int, var y: Int)

}
