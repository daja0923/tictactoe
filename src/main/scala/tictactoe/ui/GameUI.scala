package tictactoe.ui

import tictactoe.domain.Commons.{BoardData, Pos}
import tictactoe.domain.player.Player

trait GameUI {

  def getPlayersMove(player: Player): Pos

  def draw(board: BoardData): Unit

  def declareWinner(player: Player): Unit

  def displayError(message: String): Unit
}
