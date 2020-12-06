import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object Aoc2020Day06 {

  def main(args: Array[String]): Unit = {
    new Util()
      .printPart("part01", "2020day06", part01)
      .printPart("part02", "2020day06", part02)
  }

  def part01(source: Source): Int = {
    var groupAnswers = mutable.HashSet[Char]()
    val allGroupAnswerSums = ArrayBuffer.empty[Int]
    for (line <- source.getLines()) {
      if (line.isBlank) {
        allGroupAnswerSums.addOne(groupAnswers.size)
        groupAnswers = mutable.HashSet[Char]()
      } else {
        for (char <- line.toCharArray) {
          groupAnswers.add(char)
        }
      }
    }
    allGroupAnswerSums.addOne(groupAnswers.size)

    allGroupAnswerSums.sum
  }

  def part02(source: Source): Int = {
    var individualAnswer: mutable.HashSet[Char] = null
    var intersected = mutable.HashSet[Char]()
    var allGroupAnswers = mutable.HashSet[Char]()
    val intersectedAnswerSums = ArrayBuffer.empty[Int]
    for (line <- source.getLines()) {
      if (line.isBlank) {
        intersectedAnswerSums.addOne(intersected.size)
        allGroupAnswers = mutable.HashSet[Char]()
      } else {
        individualAnswer = mutable.HashSet[Char]()
        for (char <- line.toCharArray) {
          individualAnswer.add(char)
        }
        if (allGroupAnswers.isEmpty) {
          intersected = individualAnswer
          allGroupAnswers = individualAnswer
        } else {
          intersected = individualAnswer.intersect(intersected)
          allGroupAnswers.addAll(individualAnswer)
        }
      }
    }
    intersectedAnswerSums.addOne(intersected.size)

    intersectedAnswerSums.sum
  }

}
