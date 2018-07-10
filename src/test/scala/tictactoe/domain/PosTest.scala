package tictactoe.domain

import org.scalatest.{FunSuite, Matchers}
import tictactoe.domain.Commons.Pos
import scala.collection.SortedSet

class PosTest extends FunSuite with Matchers{

  test("compare by rows"){
    val pos1 = Pos(0, 1)
    val pos2 = Pos(2, 0)

    pos1.compareTo(pos2) shouldBe -1
    pos2.compareTo(pos1) shouldBe 1
  }

  test("compare by column"){
    val pos1 = Pos(3, 1)
    val pos2 = Pos(3, 0)

    pos1.compareTo(pos2) shouldBe 1
    pos2.compareTo(pos1) shouldBe -1
  }

  test("compare equal positions"){
    val pos1 = Pos(3, 1)
    val pos2 = Pos(3, 1)
    pos1.compareTo(pos2) shouldBe 0
    pos2.compareTo(pos1) shouldBe 0
  }

  test("Sort positions"){
    val pos1 = Pos(3, 1)
    val pos2 = Pos(2, 0)
    val pos3 = Pos(3, 0)
    val set: SortedSet[Pos] = SortedSet.apply(pos1, pos2, pos3)
    set.firstKey shouldBe pos2
    set.last shouldBe pos1
    set.size shouldBe 3
  }
}
