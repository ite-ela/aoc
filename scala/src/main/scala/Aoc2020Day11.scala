import Util.printPart

import scala.collection.mutable
import scala.io.Source

object Aoc2020Day11 {

  private val allDirections = {
    val directions = mutable.HashSet.empty[XY]
    for (x <- -1 to 1) {
      for (y <- -1 to 1) {
        directions.addOne(XY(x, y))
      }
    }
    directions.remove(XY(0, 0))
    directions
  }

  def main(args: Array[String]): Unit = {
    printPart("part01", "2020day11", part01)
    printPart("part02", "2020day11", part02)
  }

  def part01(source: Source): Int = {
    runUntilStable(source)
  }

  def part02(source: Source): Int = {
    runUntilStable(source, -1, 5)
  }

  def runUntilStable(source: Source, fogOfWar: Int = 1, maxNeighbors: Int = 4): Int = {
    val current = initializeSeats(source)
    var toChange: (mutable.HashSet[XY], mutable.HashSet[XY]) = (null, null)

    do {
      toChange = evolve(current, fogOfWar, maxNeighbors)
      val (toEmpty, toFill) = toChange
      toEmpty.foreach(xy => current.seats.put(xy, false))
      toFill.foreach(xy => current.seats.put(xy, true))
    } while (toChange._1.nonEmpty || toChange._2.nonEmpty)

    current.seats.values.count(identity)
  }

  private def initializeSeats(source: Source): SeatMap = {
    val seats = mutable.HashMap.empty[XY, Boolean]
    var x = 0
    var y = 0
    for (line <- source.getLines()) {
      x = 0
      for (char <- line.toCharArray) {
        if (char == 'L') {
          seats.put(XY(x, y), false)
        }
        x += 1
      }
      y += 1
    }

    SeatMap(XY(x, y), seats)
  }

  def evolve(seatMap: SeatMap, fogOfWar: Int, maxNeighbors: Int): (mutable.HashSet[XY], mutable.HashSet[XY]) = {
    val toEmpty = mutable.HashSet.empty[XY]
    val toFill = mutable.HashSet.empty[XY]

    for ((position, occupied) <- seatMap.seats) {
      val neighbors = getNeighbors(seatMap, position, fogOfWar, maxNeighbors)
      if (occupied && neighbors >= maxNeighbors) {
        toEmpty.add(position)
      } else if (!occupied && neighbors == 0) {
        toFill.add(position)
      }
    }

    (toEmpty, toFill)
  }

  def getNeighbors(seatMap: SeatMap, seat: XY, fogOfWar: Int, maxNeighbors: Int): Int = {
    var neighbors = 0

    def seatTaken(direction: XY, length: Int): Option[XY] = {
      for (len <- 1 to length) {
        val observedSeat = XY(seat.x + direction.x * len, seat.y + direction.y * len)
        if (observedSeat.x < 0
          || observedSeat.y < 0
          || observedSeat.x >= seatMap.dimensions.x
          || observedSeat.y >= seatMap.dimensions.y) {
          return None
        } else {
          val other = seatMap.seats.get(observedSeat)
          if (other.isDefined) {
            return other
              .filter(identity)
              .map(_ => observedSeat)
          }
        }
      }
      None
    }

    val vision = if (fogOfWar > 0) fogOfWar else seatMap.dimensions.x max seatMap.dimensions.y
    for (direction <- allDirections) {
      seatTaken(direction, vision)
        .foreach(_ => neighbors += 1)
      if (neighbors > maxNeighbors) {
        return neighbors
      }
    }

    neighbors
  }

  case class XY(x: Int, y: Int)

  case class SeatMap(dimensions: XY, seats: mutable.HashMap[XY, Boolean])

}
