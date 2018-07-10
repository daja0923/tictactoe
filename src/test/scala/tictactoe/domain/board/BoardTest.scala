package tictactoe.domain.board

import org.scalatest.{FunSuite, Matchers}
import tictactoe.domain.Commons.Pos
import tictactoe.domain.Exceptions.PositionAlreadyMarked

class BoardTest extends FunSuite with Matchers{

  test("Board is empty"){
    val board = Board.defaultImp(8)
    board.isEmpty shouldBe true
  }

  test("No marking line at the beginning"){
    val board = Board.defaultImp(3)
    for(i <- 0 until 3){
      for(j <- 0 until 3){
        board.hasMarkLineThrough(Pos(i, j)) shouldBe false
      }
    }
  }

  test("Try marking location twice"){
    val board: Board = Board.defaultImp(3)
    board.mark(Pos(2, 1), 'X') shouldBe false
    intercept[PositionAlreadyMarked]{
      board.mark(Pos(2, 1), 'X')
    }
  }

  test("board cannot have size less than 3 and more than 10"){
    intercept[AssertionError]{
      val board: Board = Board.defaultImp(2)
    }

    intercept[AssertionError]{
      val board: Board = Board.defaultImp(11)
    }
  }

  test("Find marked line after a number of moves"){
    val board = Board.defaultImp(3)
    board.mark(Pos(0, 0), 'X') shouldBe false
    board.mark(Pos(0, 1), 'X') shouldBe false
    board.mark(Pos(0, 2), 'O') shouldBe false
    board.mark(Pos(1, 1), 'X') shouldBe false
    board.mark(Pos(2, 1), 'O') shouldBe false
    board.mark(Pos(2, 2), 'X') shouldBe true
  }

  test("Mark by diagonals"){
    val board = Board.defaultImp(3)
    board.mark(Pos(0, 0), 'X') shouldBe false
    board.mark(Pos(1, 1), 'X') shouldBe false
    board.mark(Pos(2, 2), 'X') shouldBe true
    board.mark(Pos(0, 2), 'X') shouldBe false
    board.hasMarkLineThrough(Pos(0, 2)) shouldBe false
    board.mark(Pos(2, 0), 'X') shouldBe true
    board.hasMarkLineThrough(Pos(2, 0)) shouldBe true
    board.hasMarkLineThrough(Pos(0, 2)) shouldBe true
    board.hasMarkLineThrough(Pos(1, 1)) shouldBe true
  }

  test("Mark by row"){
    val board = Board.defaultImp(4)
    board.mark(Pos(0, 0), 'X') shouldBe false
    board.mark(Pos(0, 1), 'X') shouldBe false
    board.mark(Pos(0, 2), 'X') shouldBe false
    board.mark(Pos(0, 3), 'X') shouldBe true
  }

  test("Mark by column"){
    val board = Board.defaultImp(4)
    board.mark(Pos(0, 1), 'X') shouldBe false
    board.mark(Pos(1, 1), 'X') shouldBe false
    board.mark(Pos(2, 1), 'X') shouldBe false
    board.mark(Pos(3, 1), 'X') shouldBe true
    board.isFull shouldBe false
  }

  test("Fill the board"){
    val board = Board.defaultImp(3)
    board.isFull shouldBe false
    for(i <- 0 to 2){
      for(j <- 0 to 2){
        board.mark(Pos(i, j), 'D')
      }
    }
    board.isFull shouldBe true
  }

}
