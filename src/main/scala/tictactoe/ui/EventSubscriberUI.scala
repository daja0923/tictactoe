package tictactoe.ui

import tictactoe.domain.game.Game
import tictactoe.domain.game.events.EventSubscriber
import tictactoe.domain.player.Player


trait EventSubscriberUI extends EventSubscriber{
  def addPlayer(player: Player)
}

object EventSubscriberUI{

  def apply(game: Game): EventSubscriberUI = new GameConsole(game)

}
