
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.util.Using

object Aoc2020Day04 {

  private val validSet = mutable.HashSet[String]("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid") // "cid"

  def main(args: Array[String]): Unit = {
    println("part01: " + part01)
    println("part02: " + part02)
  }

  def part01: Int = {
    def isValidPassport(passport: mutable.HashMap[String, String]) = {
      validSet.subsetOf(passport.keySet)
    }

    countValidPassports(isValidPassport)
  }

  /** <ul>
   * <li>byr (Birth Year) - four digits; at least 1920 and at most 2002.
   * <li>iyr (Issue Year) - four digits; at least 2010 and at most 2020.
   * <li>eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
   * <li>hgt (Height) - a number followed by either cm or in:
   * If cm, the number must be at least 150 and at most 193.
   * If in, the number must be at least 59 and at most 76.
   * <li>hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
   * <li>ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
   * <li>pid (Passport ID) - a nine-digit number, including leading zeroes.
   * </ul> */
  def part02: Int = {
    def isValidPassport(passport: mutable.HashMap[String, String]): Boolean = {
      validSet.subsetOf(passport.keySet) &&
        raw"\d{4}".r.matches(passport("byr")) && matchRange(passport("byr").toInt, 1920, 2003) &&
        raw"\d{4}".r.matches(passport("iyr")) && matchRange(passport("iyr").toInt, 2010, 2021) &&
        raw"\d{4}".r.matches(passport("eyr")) && matchRange(passport("eyr").toInt, 2020, 2031) &&
        checkHeight(passport("hgt")) &&
        raw"#[a-f0-9]{6}".r.matches(passport("hcl")) &&
        raw"amb|blu|brn|gry|grn|hzl|oth".r.matches(passport("ecl")) &&
        raw"\d{9}".r.matches(passport("pid"))
    }

    countValidPassports(isValidPassport)
  }

  def matchRange(t: Int, min: Int, max: Int): Boolean = {
    t match {
      case x if min until max contains x => true
      case _ => false
    }
  }

  def checkHeight(height: String): Boolean = {
    if (raw"\d{3}cm".r.matches(height)) {
      return matchRange(height.substring(0, 3).toInt, 150, 194)
    } else if (raw"\d{2}in".r.matches(height)) {
      return matchRange(height.substring(0, 2).toInt, 59, 77)
    }

    false
  }

  def countValidPassports(isValidPassport: mutable.HashMap[String, String] => Boolean): Int = {
    val filename = "../resources/input/2020day04"
    val validPassports = ArrayBuffer.empty[mutable.HashMap[String, String]]

    def addCurrentPassportIfValid(passport: mutable.HashMap[String, String]) = {
      if (isValidPassport(passport)) {
        validPassports.addOne(passport)
      }
    }

    var currentPassport = mutable.HashMap[String, String]()
    Using(Source.fromFile(filename)) { source =>
      for (row <- source.getLines) {
        if (row.isBlank) {
          addCurrentPassportIfValid(currentPassport)
          currentPassport = mutable.HashMap[String, String]()
        } else {
          for (entry <- row.split(" ")) {
            val kv = entry.split(":")
            currentPassport.put(kv(0), kv(1))
          }
        }
      }
      addCurrentPassportIfValid(currentPassport)
    }

    validPassports.size
  }
}
