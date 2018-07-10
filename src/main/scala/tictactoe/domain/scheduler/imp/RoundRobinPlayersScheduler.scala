package tictactoe.domain.scheduler.imp

import tictactoe.domain.player.Player
import tictactoe.domain.scheduler.PlayersScheduler
import scala.collection.mutable

/**
  * Round-robin scheduler for Players
  */
class RoundRobinPlayersScheduler(numOfPlayers: Int) extends PlayersScheduler{

  private var _queue = mutable.Queue[Player]()

  override def contains(player: Player): Boolean = {
    _queue.contains(player)
  }

  override def size: Int = _queue.length

  override def isEmpty: Boolean = _queue.isEmpty

  override def add(player:Player):Boolean = {
    if(_queue.exists(_.equals(player)) || isFull){
      false
    }else{
      _queue.enqueue(player)
      _queue.last.equals(player)
    }
  }

  override def remove(player:Player):Boolean = {
    val exists = _queue.exists(_.equals(player))
    if(exists){
      _queue = _queue.filterNot(_.equals(player))
    }
    exists && !_queue.contains(player)
  }

  override def current: Option[Player] = if(hasEnoughPlayers) _queue.headOption else None

  override def next():Option[Player] = {
    if(!hasEnoughPlayers) None
    else{
      val head = _queue.dequeue()
      _queue.enqueue(head)
      current
    }
  }

  override def isFull: Boolean = size >= numOfPlayers

  override def hasEnoughPlayers: Boolean = size >= numOfPlayers
}
