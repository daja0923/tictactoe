package tictactoe.domain.game.events

import tictactoe.domain.Commons.{BoardData, Pos}
import tictactoe.domain.game.Game
import tictactoe.domain.player.Player

trait EventSubscriber{

  def onGameStarted(source: Game, numOfPlayers: Int, boardData: BoardData): Unit

  def onPlayerJoined(source: Game, player: Player): Unit

  def onPlayerLeft(source: Game, player: Player): Unit

  def onPlayerMoved(source: Game, player: Player, pos: Pos): Unit

  def onNextMoverDeclared(source: Game, player: Player): Unit

  def onGameWon(source: Game, winner: Player): Unit

  def onGameTied(source: Game): Unit

  def onBoardStateChanged(source: Game, boardData: BoardData): Unit

}