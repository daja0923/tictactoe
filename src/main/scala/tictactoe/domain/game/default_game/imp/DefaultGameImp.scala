package tictactoe.domain.game.default_game.imp

import tictactoe.domain.Commons.GameState.GameState
import tictactoe.domain.Commons.{BoardData, GameState}
import tictactoe.domain.game.default_game.DefaultGame
import tictactoe.domain.scheduler.PlayersScheduler
import tictactoe.domain.Commons
import tictactoe.domain.board.Board
import tictactoe.domain.player.Player


class DefaultGameImp(board: Board, scheduler: PlayersScheduler) extends DefaultGame{

  private var _winner: Option[Player] = None

  override def gameState: GameState =
    if(_winner.isDefined || board.isFull)
      GameState.ENDED
    else if(scheduler.hasEnoughPlayers)
      GameState.ONGOING
    else
      GameState.COLLECTING_PLAYERS

  override def isGoingOn: Boolean = gameState equals GameState.ONGOING

  override def isOver: Boolean = gameState equals GameState.ENDED

  override def winner: Option[Player] = _winner

  override def currentTurn: Option[Player] = if(isGoingOn) scheduler.current else  None

  override def boardState: BoardData = board.field

  override def addPlayer(player: Player): Unit = {
    scheduler.add(player)
  }

  override def removePlayer(player: Player): Unit = {
    scheduler.remove(player)
  }

  override def makeMove(player: Player, pos: Commons.Pos): Unit = gameState match {
    case GameState.COLLECTING_PLAYERS => throw new Exception("Waiting for players to Join")
    case GameState.ENDED => throw new Exception("Game is already over")

    case GameState.ONGOING =>
      if(!scheduler.contains(player))
        throw new Exception("Player not found")
      else if(!scheduler.current.exists(_.equals(player)))
        throw new Exception("Wrong player's move")
      else if(board.mark(pos, player.id))
        _winner = Some(player)
  }
}
