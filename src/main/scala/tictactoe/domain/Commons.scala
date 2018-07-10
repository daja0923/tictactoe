package tictactoe.domain

object Commons {

  type PlayerSymbol = Char

  type BoardData = Array[Array[PlayerSymbol]]

  case class Pos(row: Int, col: Int) extends Comparable[Pos] {
    override def compareTo(o: Pos): Int = {
      if(row < o.row)
        -1
      else if(row > o.row)
        1
      else if(col < o.col)
        -1
      else if(col > o.col)
        1
      else 0
    }

    override def toString: String = s"($row, $col)"
  }

  object GameState extends Enumeration{
    type GameState = Value
    val COLLECTING_PLAYERS: GameState.Value = Value(1, "collecting_players")
    val ONGOING: GameState.Value = Value(2, "ongoing")
    val ENDED: GameState.Value= Value(3, "ended")
  }
}
