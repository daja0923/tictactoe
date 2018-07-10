package tictactoe.domain.game.events.imp

import tictactoe.domain.Commons.GameState.GameState
import tictactoe.domain.Commons.{GameState, Pos}
import tictactoe.domain.game.events.EventSourcedGame
import tictactoe.domain.scheduler.PlayersScheduler
import tictactoe.domain.Commons
import tictactoe.domain.Exceptions._
import tictactoe.domain.board.Board
import tictactoe.domain.player.Player


class EventSourcedGameImp(board: Board,
                          scheduler: PlayersScheduler) extends EventSourcedGame{

  private var _winner: Option[Player] = None

  private def gameState: GameState =
    if(_winner.isDefined || board.isFull)
      GameState.ENDED
    else if(scheduler.hasEnoughPlayers)
      GameState.ONGOING
    else
      GameState.COLLECTING_PLAYERS

  override def makeMove(player: Player, pos: Commons.Pos): Unit = gameState match {
    case GameState.ONGOING =>
      if(!scheduler.contains(player))
        throw PlayerNotMember("Player not found")
      else if(!scheduler.current.exists(_.equals(player)))
        throw WrongPlayersTurn("Wrong player's move")
      else
        handleMove(player, pos)

    case GameState.COLLECTING_PLAYERS => throw GameHasNotStarted("Waiting for players to Join")
    case GameState.ENDED => throw GameAlreadyOver("Game is already over")
  }

  private def handleMove(player: Player, pos: Pos): Unit =  {
    if(board.mark(pos, player.id))
      handleMoveWithVictory(player, pos)
    else if(board.isFull)
      handleMoveWithTie(player, pos)
    else
      handleMoveWithContinue(player, pos)
  }

  private def handleMoveWithVictory(player: Player, pos: Pos): Unit = {
    _winner = Some(player)
    playerMoved(player, pos)
    boardStateChanged(board.field)
    gameWon(player)
  }

  private def handleMoveWithTie(player: Player, pos: Pos): Unit = {
    playerMoved(player, pos)
    boardStateChanged(board.field)
    gameTied()
  }

  private def handleMoveWithContinue(player: Player, pos: Pos): Unit = {
    playerMoved(player, pos)
    boardStateChanged(board.field)
    scheduler.next().foreach(nextMoverDeclared)
  }

  override def addPlayer(player: Player): Unit = {
    if(scheduler.isFull){
      throw PlayersPositionsAlreadyFilled("No more player!")
    }
    else if(scheduler.add(player)){
      playerJoined(player)
      if(scheduler.hasEnoughPlayers){
        startGame()
      }
    }
  }

  private def startGame(): Unit = {
    gameStarted(scheduler.size, board.field)
    scheduler.current.foreach(nextMoverDeclared)
  }

  override def removePlayer(player: Player): Unit = {
    if(scheduler.remove(player)){
      playerLeft(player)
    }
  }

}
