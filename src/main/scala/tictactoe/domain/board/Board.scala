package tictactoe.domain.board

import tictactoe.domain.Commons.{BoardData, PlayerSymbol, Pos}
import tictactoe.domain.board.imp.BoardImp

/**
  * Represents a Square board.
  */
trait Board {

  def size: Int

  def field: BoardData

  /**
    * Verifies if all the positions are marked
    * @return
    */
  def isFull: Boolean

  /**
    * Verifies if the board is marked horizontally,
    * vertically or diagonally by the same mark through the given position
    * @param pos Position that mark should go through
    * @return
    */
  def hasMarkLineThrough(pos: Pos): Boolean

  /**
    * Marks boards position with the given marking symbol
    * @param pos Position of the board to mark
    * @param symbol Marking symbol to mark with
    * @return Boolean, true if there is a mark line through this position after marking this pos,
    *         false if there is no mark line through this position (see hasMarkLineThrough).
    *         Throw exception if the position has already marked
    */
  def mark(pos: Pos, symbol: PlayerSymbol): Boolean

  /**
    * Verifies if the board has no marks in any position
    * @return Boolean
    */
  def isEmpty: Boolean
}


object Board{

  def defaultImp(size: Int): Board = new BoardImp(size)

}
