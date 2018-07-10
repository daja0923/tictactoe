package tictactoe.domain.game

import tictactoe.domain.Commons.Pos
import tictactoe.domain.board.Board
import tictactoe.domain.game.events.EventSourcedGame
import tictactoe.domain.game.events.imp.EventSourcedGameImp
import tictactoe.domain.game.default_game.imp.DefaultGameImp
import tictactoe.domain.player.Player
import tictactoe.domain.scheduler.imp.RoundRobinPlayersScheduler

/**
  * Defines game's core functionalities
  */
trait Game{

  def addPlayer(player: Player): Unit

  def removePlayer(player: Player): Unit

  def makeMove(player: Player, pos: Pos): Unit
}


object Game{

  def initGameWithEvents(boardSize: Int, numOfPlayers: Int): EventSourcedGame = {
    val board = Board.defaultImp(boardSize)
    val scheduler = new RoundRobinPlayersScheduler(numOfPlayers)
    new EventSourcedGameImp(board, scheduler)
  }

  def initGameWithState(boardSize: Int, numOfPlayers: Int): Game = {
    val board = Board.defaultImp(boardSize)
    val scheduler = new RoundRobinPlayersScheduler(numOfPlayers)
    new DefaultGameImp(board, scheduler)
  }

}
