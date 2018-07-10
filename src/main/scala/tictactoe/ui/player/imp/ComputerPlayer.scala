package tictactoe.ui.player.imp

import tictactoe.domain.Commons
import tictactoe.domain.Commons.{BoardData, PlayerSymbol, Pos}
import tictactoe.domain.game.Game
import tictactoe.domain.player.Player

case class ComputerPlayer(id: PlayerSymbol) extends Player{

  override def move(game: Game, pos: Commons.Pos): Unit = game.makeMove(this, pos)

  override def join(game: Game): Unit = game.addPlayer(this)

  override def leave(game: Game): Unit = game.removePlayer(this)

  def move(game: Game, boardState: BoardData): Unit = {
    val pos = findOptimalMove(boardState)
    move(game, pos)
  }

  def findOptimalMove(bordData: BoardData): Pos = {
    for(i <- bordData.indices){
      for(j <- bordData.indices){
        if(bordData(i)(j) == 0)
          return Pos(i, j)
      }
    }
    Pos(0,0)
  }

  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case other: ComputerPlayer =>
        other.id == id
      case _ => false
    }
  }

}
