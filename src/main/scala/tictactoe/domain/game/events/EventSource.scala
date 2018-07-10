package tictactoe.domain.game.events

import tictactoe.domain.Commons.{BoardData, Pos}
import tictactoe.domain.player.Player


trait EventSource{

  def addSubscriber(s: EventSubscriber): Unit

  def removeSubscriber(s: EventSubscriber): Unit


  def playerJoined(player: Player): Unit

  def playerLeft(player: Player): Unit

  def gameStarted(numOfPlayers: Int, boardData: BoardData): Unit

  def playerMoved(player: Player, pos: Pos): Unit

  def boardStateChanged(boardData: BoardData): Unit

  def nextMoverDeclared(playerSymbol: Player): Unit

  def gameTied(): Unit

  def gameWon(winner: Player): Unit
}