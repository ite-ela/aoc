
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object Aoc2020Day18 {
  def main(args: Array[String]): Unit = {
    Util.printPart("part01", "2020day18", part01)
    Util.printPart("part02", "2020day18", part02)
  }

  def findEndIndex(chars: Seq[Char], start: Int): Int = {
    var level = 0
    var i = start
    for (char <- chars.slice(start, chars.length)) {
      if (char == '(') {
        level += 1
      } else if (char == ')') {
        level -= 1
      }

      if (level == 0) {
        return i
      }

      i += 1
    }

    -1
  }

  def part01(source: Source): Long = {
    calculate(source.getLines().toList)
  }

  def calculate(allLines: Seq[String]): Long = {
    val results = ArrayBuffer.empty[Long]
    val sumFunction = (a: Long, b: Long) => a + b
    val mulFunction = (a: Long, b: Long) => a * b

    def calculate(chars: Seq[Char]): Long = {
      var currentOp = (_: Long, b: Long) => b
      var result = 0L
      var i = 0
      while (i < chars.length) {
        val char = chars(i)
        if (char == '*') {
          currentOp = mulFunction
        } else if (char == '+') {
          currentOp = sumFunction
        } else if (char == '(') {
          val endIndex = findEndIndex(chars, i)
          result = currentOp.apply(result, calculate(chars.slice(i + 1, endIndex + 1)))
          i = endIndex
        } else if (char >= '0' && char <= '9') {
          result = currentOp.apply(result, char - '0')
        }
        i += 1
      }

      result
    }

    for (line <- allLines) {
      results.addOne(calculate(line.toCharArray))
    }

    results.sum
  }

  def findStartIndex(chars: Array[Char], start: Int): Int = {
    var level = 0
    var i = start
    for (char <- chars.slice(0, start + 1).reverse) {
      if (char == ')') {
        level += 1
      } else if (char == '(') {
        level -= 1
      }

      if (level == 0) {
        return i
      }

      i -= 1
    }

    -1
  }

  def fixStr(str: String): String = {
    val sb = new StringBuilder(str)
    var i = 0
    var length = str.length
    while (i < length) {
      val chars = sb.toCharArray
      if (chars(i) == '+') {
        sb.insert(findEndIndex(chars, i + 2) + 1, ")")
        sb.insert(findStartIndex(chars, i - 2), "(")
        i += 1
        length += 2
      }
      i += 1
    }

    sb.toString()
  }

  def part02(source: Source): Long = {
    val allLines = ArrayBuffer.from(source.getLines())
    for (i <- allLines.indices) {
      val str = allLines(i)
      allLines(i) = fixStr(str)
    }

    calculate(allLines.toList)
  }

}
