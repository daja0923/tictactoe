package tictactoe.domain.scheduler

import tictactoe.domain.player.Player

/**
  * Scheduler that handles queue of players
  */
trait PlayersScheduler extends Scheduler[Player]{

  def hasEnoughPlayers: Boolean

  def isFull: Boolean
}
