
import scala.collection.mutable
import scala.io.Source
import scala.util.Using

object Aoc2020Day03 {

  def main(args: Array[String]): Unit = {
    println("part01: " + part01)
    println("part02: " + part02)
  }

  def part01: Integer = {
    countTrees(XY(3, 1), getMapOfTrees)
  }

  def part02: Integer = {
    /** <ul>
     * <li> Right 1, down 1.
     * <li> Right 3, down 1. (This is the slope you already checked.)
     * <li> Right 5, down 1.
     * <li> Right 7, down 1.
     * <li> Right 1, down 2.
     * </ul>
     */
    val mapOfTrees = getMapOfTrees
    List(XY(1, 1), XY(3, 1), XY(5, 1), XY(7, 1), XY(1, 2))
      .map(countTrees(_, mapOfTrees))
      .product
  }

  def getMapOfTrees: MapOfTrees = {
    val filename = "../resources/input/2020day03"
    val treeSet = new mutable.HashSet[XY]()

    val position = XY(0, 0)
    var mapWidth = 0
    var mapHeight = 0


    Using(Source.fromFile(filename)) { source =>
      for (row <- source.getLines) {
        for (column <- row.toCharArray) {
          if (column == '#') {
            treeSet.add(XY(position.x, position.y))
          }
          position.x += 1
        }

        mapWidth = position.x
        position.x = 0
        position.y += 1
      }
      mapHeight = position.y
    }

    MapOfTrees(treeSet = treeSet, dimensions = XY(mapWidth, mapHeight))
  }

  def countTrees(direction: XY, mapOfTrees: MapOfTrees): Int = {
    val position = XY(0, 0)
    var numberOfTrees = 0
    while (position.y < mapOfTrees.dimensions.y) {
      if (mapOfTrees.treeSet.contains(position)) {
        numberOfTrees += 1
      }

      position.x += direction.x
      position.y += direction.y

      if (position.x > mapOfTrees.dimensions.x - 1) {
        position.x -= mapOfTrees.dimensions.x
      }
    }
    numberOfTrees
  }

  case class XY(var x: Integer, var y: Integer)

  case class MapOfTrees(treeSet: mutable.HashSet[XY], dimensions: XY)

}
