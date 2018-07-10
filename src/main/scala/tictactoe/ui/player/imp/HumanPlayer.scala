package tictactoe.ui.player.imp

import java.util.Scanner

import tictactoe.domain.Commons
import tictactoe.domain.Commons.PlayerSymbol
import tictactoe.domain.game.Game
import tictactoe.domain.player.Player


case class HumanPlayer(id: PlayerSymbol) extends Player{
  val scanner = new Scanner(System.in)

  override def move(game: Game, pos: Commons.Pos): Unit = game.makeMove(this, pos)

  override def join(game: Game): Unit = game.addPlayer(this)

  override def leave(game: Game): Unit = game.removePlayer(this)
}
