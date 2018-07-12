package tictactoe.domain.game.events

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FunSuite, Matchers}
import tictactoe.domain.Commons.{PlayerSymbol, Pos}
import tictactoe.domain.Exceptions._
import tictactoe.domain.game.Game
import tictactoe.domain.player.Player
import tictactoe.ui.player.imp.ComputerPlayer


class EventSourcedGameTest extends FunSuite with Matchers with MockFactory{

  private val playerA = getPlayer('A')
  private val playerB = getPlayer('B')
  private val playerC = getPlayer('C')

  test("New game cannot be played without joined players"){
    val game = Game.initGameWithEvents(3,2)
    intercept[GameHasNotStarted]{
      playerA.move(game, Pos(0,0))
    }
  }

  test("Game cannot be played with too few players"){
    val game = Game.initGameWithEvents(3,2)
    playerA.join(game)
    intercept[GameHasNotStarted]{
      playerA.move(game, Pos(0,0))
    }
  }

  test("Game cannot be played with a player that has not joined"){
    val game = Game.initGameWithEvents(3,2)
    playerA.join(game)
    playerB.join(game)

    intercept[PlayerNotMember]{
      playerC.move(game, Pos(0,0))
    }
  }

  test("Cannot add more player than defined number of players"){
    val game = Game.initGameWithEvents(3,2)
    playerA.join(game)
    playerB.join(game)

    intercept[PlayersPositionsAlreadyFilled]{
      playerC.join(game)
    }
  }

  test("Game rejects player's move out of its turn"){
    val game = Game.initGameWithEvents(3,2)
    playerA.join(game)
    playerB.join(game)

    intercept[WrongPlayersTurn]{
      playerB.move(game, Pos(0,0))
    }
  }

  test("Cannot make move outside board"){
    val game = Game.initGameWithEvents(3,2)
    playerA.join(game)
    playerB.join(game)

    intercept[InvalidBoardPosition]{
      playerA.move(game, Pos(0,3))
    }
  }

  test("Cannot make move on already marked position"){
    val game = Game.initGameWithEvents(3,2)
    playerA.join(game)
    playerB.join(game)
    playerA.move(game, Pos(0,0))

    intercept[PositionAlreadyMarked]{
      playerB.move(game, Pos(0,0))
    }
  }

  test("Cannot play when Game is over"){
    val game = Game.initGameWithEvents(3,2)
    playerA.join(game)
    playerB.join(game)
    playerA.move(game, Pos(0,0))
    playerB.move(game, Pos(1,0))
    playerA.move(game, Pos(0,1))

    playerB.move(game,  Pos(1,1))
    playerA.move(game, Pos(0,2))

    intercept[GameAlreadyOver]{
      playerC.move(game, Pos(2,2))
    }
  }

  def getPlayer(id: PlayerSymbol): Player = ComputerPlayer(id)
}
