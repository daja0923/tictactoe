package tictactoe.domain.game.default_game

import tictactoe.domain.Commons.GameState.GameState
import tictactoe.domain.Commons.BoardData
import tictactoe.domain.game.Game
import tictactoe.domain.player.Player


trait DefaultGame extends Game{

  def isGoingOn: Boolean

  def isOver: Boolean

  def gameState: GameState

  def winner: Option[Player]

  def currentTurn: Option[Player]

  def boardState: BoardData
}