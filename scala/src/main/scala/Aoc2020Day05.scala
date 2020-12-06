import scala.collection.mutable
import scala.io.Source

object Aoc2020Day05 {

  val ROWS = 128
  val COLS = 8

  def main(args: Array[String]): Unit = {
    new Util()
      .printPart("part01", "2020day05", part01)
      .printPart("part02", "2020day05", part02)
  }

  def part01(source: Source): Int = {
    var maxSeatId = 0
    for (ticket <- source.getLines()) {
      val seat = decodeSeat(ticket)
      maxSeatId = maxSeatId max getSeatID(seat)
    }

    maxSeatId
  }

  def part02(source: Source): Int = {
    val allSeats = mutable.TreeSet[Int]()
    for (ticket <- source.getLines()) {
      allSeats.addOne(getSeatID(decodeSeat(ticket)))
    }

    for (id <- allSeats) {
      if (!allSeats.contains(id + 1)) {
        return id + 1
      }
    }

    -1
  }

  def getSeatID(seat: XY): Int = {
    seat.y * 8 + seat.x
  }

  def decodeSeat(ticket: String): XY = {
    val row = decodePart(ticket, 0 to 6, ROWS, 'B')
    val col = decodePart(ticket, 7 to 9, COLS, 'R')
    XY(col, row)
  }

  def decodePart(ticket: String, range: Range, max: Int, char: Char): Int = {
    var size = max
    var index = 0
    for (i <- range) {
      size = size / 2
      val c = ticket(i)
      if (c == char) {
        index += size
      }
    }
    index
  }

  case class XY(x: Int, y: Int)

}
