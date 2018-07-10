package tictactoe.domain.scheduler

/**
  * Represents a scheduler's core functionalities
  * @tparam I
  */
trait Scheduler[I] {

  def contains(item: I): Boolean

  def size: Int

  def isEmpty: Boolean

  def nonEmpty: Boolean = !isEmpty

  /**
    * Add item to queue
    * @param item I
    * @return true if item actually added. If item already exists returns false
    */
  def add(item: I):Boolean

  /**
    * Removes item from queue
    * @param item I
    * @return true if item actually removed. If item does not exist returns false
    */
  def remove(item: I):Boolean

  /**
    * Returns item that has its turn.
    * This function has no side effect
    * @return Option of item
    */
  def current: Option[I]

  /**
    * Moves the current to the next item and return that item.
    * This function has side effect.
    * @return Option of item
    */
  def next():Option[I]
}
