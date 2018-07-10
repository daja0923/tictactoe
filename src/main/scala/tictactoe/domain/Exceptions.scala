package tictactoe.domain

object Exceptions {

  case class PlayerNotMember(msg: String) extends Exception(msg)

  case class GameHasNotStarted(msg: String) extends Exception(msg)

  case class WrongPlayersTurn(msg: String) extends Exception(msg)

  case class InvalidBoardPosition(msg: String) extends Exception(msg)

  case class PositionAlreadyMarked(msg: String) extends Exception(msg)

  case class PlayersPositionsAlreadyFilled(msg: String) extends Exception(msg)

  case class GameAlreadyOver(msg: String) extends Exception(msg)
}
