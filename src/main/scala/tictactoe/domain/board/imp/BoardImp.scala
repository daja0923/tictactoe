package tictactoe.domain.board.imp

import tictactoe.domain.Commons._
import tictactoe.domain.Exceptions.{InvalidBoardPosition, PositionAlreadyMarked}
import tictactoe.domain.board.Board


class BoardImp(val size: Int) extends Board{

  assert(size >= 3 && size <= 10)

  val board: Array[Array[PlayerSymbol]] = Array.ofDim[PlayerSymbol](size, size)


  override def isFull: Boolean = !board.exists(_.contains(0))

  override def isEmpty: Boolean = board.forall(_.contains(0))

  override def field: Array[Array[PlayerSymbol]] = {
    val arr = Array.ofDim[PlayerSymbol](size, size)
    board.copyToArray(arr)
    arr
  }

  def mark(point: Pos, char: PlayerSymbol): Boolean = {
    if(!isInField(point))
      throw InvalidBoardPosition("Cannot mark outside the board")
    if(isOccupied(point))
      throw PositionAlreadyMarked("Location already marked")
    else {
      board(point.row)(point.col) = char
      hasMarkLineThrough(point)
    }
  }

  private def isInField(pos: Pos): Boolean =
    pos.col >= 0 &&
      pos.col < size &&
      pos.row >= 0 &&
      pos.row < size


  private def isOccupied(pos: Pos): Boolean = board(pos.row)(pos.col) != 0

  override def hasMarkLineThrough(pos: Pos): Boolean =
    rowMarkedThrough(pos) || columnMarkedThrough(pos) || diagonalMarkedThrough(pos) || cornersMarked(pos)

  private def rowMarkedThrough(pos: Pos): Boolean = {
    val ch = board(pos.row)(pos.col)
    ch != 0 && board(pos.row).forall(c => c == ch)
  }

  private def columnMarkedThrough(pos: Pos): Boolean = {
    val ch = board(pos.row)(pos.col)
    ch != 0 && board.forall{col =>
      col(pos.col) == ch
    }
  }

  private def diagonalMarkedThrough(pos: Pos): Boolean = {
    val ch = board(pos.row)(pos.col)
    val range = 0 until size
    ch != 0 &&
      (range.forall{i => board(i)(i) == ch} && range.exists(i => pos equals Pos(i, i)) ||
        range.forall{j => board(j)(size - 1 - j) == ch} && range.exists(j => pos equals Pos(j, size - 1 - j)))
  }

  private def cornersMarked(pos: Pos): Boolean = {
    val mark = getMark(pos)

    mark match {
      case None => false
      case Some(m) =>
        val corners = List(Pos(0, 0), Pos(0, size - 1), Pos(size - 1, 0), Pos(size - 1, size -1))
        corners.contains(pos) &&
          corners.forall{pos =>
            getMark(pos).contains(m)
          }
    }
  }

  private def getMark(pos: Pos): Option[PlayerSymbol] = {
    if(pos.row >= size || pos.col >= size)
      None
    else if(board(pos.row)(pos.col) == 0)
      None
    else
      Some(board(pos.row)(pos.col))
  }
}
