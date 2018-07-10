package tictactoe.domain.player

import tictactoe.domain.Commons.{PlayerSymbol, Pos}
import tictactoe.domain.game.Game

trait Player{

  def id: PlayerSymbol

  def move(game: Game, pos: Pos): Unit

  def join(game: Game): Unit

  def leave(game: Game): Unit

}
