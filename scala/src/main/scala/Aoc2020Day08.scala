import Util.printPart

import scala.collection.mutable
import scala.io.Source

object Aoc2020Day08 {

  def main(args: Array[String]): Unit = {
    printPart("part01", "2020day08", part01)
    printPart("part02", "2020day08", part02)
  }

  def part01(source: Source): Int = {
    runInstructions(source.getLines().toList, (_, i) => i.perform).accumulator
  }

  def part02(source: Source): Int = {
    val lines = source.getLines().toList

    for (index <- 0 to lines.size) {
      val result = runInstructions(lines, (line, i) => {
        if (index == line) {
          i.performSwapped
        } else {
          i.perform
        }
      })
      if (result.line == lines.size) {
        return result.accumulator
      }
    }

    -1 // no way to fix this broken code
  }

  private def parseLine(source: List[String], lineNumber: Int) = {
    val line = source(lineNumber)
    val pattern = "([a-z]{3}) ([+-][0-9]+)".r
    val pattern(operation, numStr) = line
    val num = numStr.toInt
    (operation, num)
  }

  def runInstructions(source: List[String], instructor: (Int, Instruction) => Result): Result = {
    val visitedLineNumbers = mutable.HashSet[Int]()

    var accumulator = 0
    var lineNumber = 0
    val rows = source.size
    do {
      val (operation: String, num: Int) = parseLine(source, lineNumber)
      visitedLineNumbers.add(lineNumber)
      val instruction = Instruction(operation, num)
      val result = instructor.apply(lineNumber, instruction)
      lineNumber += result.line
      accumulator += result.accumulator
    } while (lineNumber < rows && !visitedLineNumbers.contains(lineNumber))

    Result(accumulator, lineNumber)
  }

  object Instruction {
    def apply(op: String, num: Int): Instruction = new Instruction(op, num)
  }

  class Instruction(op: String, num: Int) {

    def perform: Result = {
      perform(op, num)
    }

    def performSwapped: Result = {
      op match {
        case "jmp" => perform("nop", num)
        case "nop" => perform("jmp", num)
        case _ => perform(op, num)
      }
    }

    def perform(operation: String, inputNumber: Int): Result = {
      operation match {
        case "jmp" => Result(0, inputNumber)
        case "nop" => Result(0, 1)
        case "acc" => Result(inputNumber, 1)
        case x => throw new IllegalArgumentException("Unknown command:" + x)
      }
    }
  }

  case class Result(accumulator: Int, line: Int)

}
