package tictactoe.domain.game.events

import tictactoe.domain.Commons.{BoardData, Pos}
import tictactoe.domain.game.Game
import tictactoe.domain.player.Player

trait EventSourcedGame extends Game with EventSource{

  private var subscribers: Set[EventSubscriber] = Set.empty

  override def addSubscriber(subscriber: EventSubscriber): Unit = {
    subscribers = subscribers + subscriber
  }

  override def removeSubscriber(subscriber: EventSubscriber): Unit = {
    subscribers = subscribers - subscriber
  }


  override def playerJoined(player: Player): Unit = {
    subscribers.foreach(_.onPlayerJoined(this, player))
  }

  override def playerLeft(player: Player): Unit = {
    subscribers.foreach(_.onPlayerLeft(this, player))
  }

  override def gameStarted(numOfPlayers: Int, boardData: BoardData): Unit = {
    subscribers.foreach(_.onGameStarted(this, numOfPlayers, boardData))
  }

  override def playerMoved(player: Player, pos: Pos): Unit = {
    subscribers.foreach(_.onPlayerMoved(this, player, pos))
  }

  override def boardStateChanged(boardData: BoardData): Unit = {
    subscribers.foreach(_.onBoardStateChanged(this, boardData))
  }

  override def nextMoverDeclared(player: Player): Unit = {
    subscribers.foreach(_.onNextMoverDeclared(this, player))
  }

  override def gameTied(): Unit = {
    subscribers.foreach(_.onGameTied(this))
  }

  override def gameWon(winner: Player): Unit = {
    subscribers.foreach(_.onGameWon(this, winner))
  }
}
