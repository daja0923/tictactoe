package tictactoe.domain.scheduler.imp

import tictactoe.domain.player.Player
import tictactoe.domain.scheduler.PlayersScheduler
import scala.collection.mutable

/**
  * Round-robin scheduler for Players
  */
class RoundRobinPlayersScheduler(numOfPlayers: Int) extends PlayersScheduler{

  assert(numOfPlayers >= 2)

  private var _queue = mutable.Queue[Player]()

  override def contains(player: Player): Boolean = _queue.contains(player)

  override def size: Int = _queue.length

  override def isEmpty: Boolean = _queue.isEmpty

  override def isFull: Boolean = size >= numOfPlayers

  override def hasEnoughPlayers: Boolean = size >= numOfPlayers

  override def current: Option[Player] = if(hasEnoughPlayers) _queue.headOption else None

  override def next():Option[Player] = {
    if(!hasEnoughPlayers) None
    else{
      val head = _queue.dequeue()
      _queue.enqueue(head)
      current
    }
  }

  override def add(player:Player):Boolean = {
    if(_queue.contains(player) || isFull){
      false
    }else{
      _queue.enqueue(player)
      _queue.lastOption.contains(player)
    }
  }

  override def remove(player:Player):Boolean = {
    val exists = _queue.contains(player)
    if(exists){
      _queue = _queue.filterNot(_.equals(player))
    }
    exists && !_queue.contains(player)
  }
}
