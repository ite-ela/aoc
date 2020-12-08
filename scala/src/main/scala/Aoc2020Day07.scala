import Util.printPart

import scala.collection.immutable.HashSet
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object Aoc2020Day07 {

  def main(args: Array[String]): Unit = {
    printPart("part01", "2020day07", part01)
    printPart("part02", "2020day07", part02)
  }

  def part01(source: Source): Int = {
    val (_, parents) = extractChildrenAndParents(source)
    uniqueParentsInHierarchy("shiny gold", mutable.HashSet.empty, parents).size
  }

  def uniqueParentsInHierarchy(node: String, unique: mutable.HashSet[String],
                               parentMap: mutable.Map[String, HashSet[String]]): mutable.HashSet[String] = {
    val parents = parentMap(node)
    if (parents.nonEmpty) {
      unique.addAll(parents)
      parents.foreach(parent => uniqueParentsInHierarchy(parent, unique, parentMap))
    }

    unique
  }

  def part02(source: Source): Int = {
    val (children, _) = extractChildrenAndParents(source)
    countChildrenInHierarchy("shiny gold", 1, children)
  }

  def countChildrenInHierarchy(node: String, parentNodeCount: Int, childMap: mutable.Map[String, Iterable[(Int, String)]]): Int = {
    var counter = 0
    val children = childMap(node)

    for ((count, description) <- children) {
      val nodeCount = count * parentNodeCount
      counter += nodeCount + countChildrenInHierarchy(description, nodeCount, childMap)
    }

    counter
  }

  private def extractChildrenAndParents(source: Source): (mutable.Map[String, Iterable[(Int, String)]], mutable.Map[String, HashSet[String]]) = {
    val children = mutable.HashMap[String, Iterable[(Int, String)]]()
    val parents = mutable.HashMap[String, HashSet[String]]().withDefaultValue(HashSet.empty)

    for (line <- source.getLines()) {
      val contains = line.split("contain")
      val rule = contains(0).substring(0, contains(0).indexOf("bags") - 1)
      val childList = ArrayBuffer.empty[(Int, String)]
      children.put(rule, childList)
      for (str <- contains(1).split(",")) {
        val pattern = " ([0-9]+) (.+) bag[s]?.?".r
        if (pattern.matches(str)) {
          val pattern(count, descriptor) = str
          childList.addOne((count.toInt, descriptor))
          parents.put(descriptor, parents(descriptor) + rule)
        }
      }
    }
    (children, parents)
  }
}
