package tictactoe.domain.game.events

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FunSuite, Matchers}
import tictactoe.domain.Commons.{PlayerSymbol, Pos}
import tictactoe.domain.Exceptions._
import tictactoe.domain.game.Game
import tictactoe.domain.player.Player
import tictactoe.ui.player.imp.ComputerPlayer


class EventSourcedGameTest extends FunSuite with Matchers with MockFactory{

  test("New game cannot be played without players"){
    val game = Game.initGameWithEvents(3,2)
    intercept[GameHasNotStarted]{
      game.makeMove(getPlayer('A'), Pos(0,0))
    }
    game.addPlayer(getPlayer('A'))
  }

  test("Game cannot be played with too few players"){
    val game = Game.initGameWithEvents(3,2)
    game.addPlayer(getPlayer('A'))
    intercept[GameHasNotStarted]{
      game.makeMove(getPlayer('A'), Pos(0,0))
    }
  }

  test("Game cannot be played with no joined player"){
    val game = Game.initGameWithEvents(3,2)
    game.addPlayer(getPlayer('A'))
    game.addPlayer(getPlayer('B'))

    intercept[PlayerNotMember]{
      game.makeMove(getPlayer('C'), Pos(0,0))
    }
  }

  test("Cannot add more player than number of players"){
    val game = Game.initGameWithEvents(3,2)
    game.addPlayer(getPlayer('A'))
    game.addPlayer(getPlayer('B'))

    intercept[PlayersPositionsAlreadyFilled]{
      game.addPlayer(getPlayer('C'))
    }
  }

  test("Game rejects player's move out of its turn"){
    val game = Game.initGameWithEvents(3,2)
    game.addPlayer(getPlayer('A'))
    game.addPlayer(getPlayer('B'))

    intercept[WrongPlayersTurn]{
      game.makeMove(getPlayer('B'), Pos(0,0))
    }
  }

  test("Cannot make move outside board"){
    val game = Game.initGameWithEvents(3,2)
    game.addPlayer(getPlayer('A'))
    game.addPlayer(getPlayer('B'))

    intercept[InvalidBoardPosition]{
      game.makeMove(getPlayer('A'), Pos(0,3))
    }
  }

  test("Cannot make move on already marked position"){
    val game = Game.initGameWithEvents(3,2)
    game.addPlayer(getPlayer('A'))
    game.addPlayer(getPlayer('B'))
    game.makeMove(getPlayer('A'), Pos(0,0))

    intercept[PositionAlreadyMarked]{
      game.makeMove(getPlayer('B'), Pos(0,0))
    }
  }

  test("Cannot add player when Game is over"){
    val game = Game.initGameWithEvents(3,2)
    val playerA = getPlayer('A')
    val playerB = getPlayer('B')
    playerA.join(game)
    playerB.join(game)
    game.makeMove(playerA, Pos(0,0))
    game.makeMove(playerB, Pos(1,0))
    game.makeMove(playerA, Pos(0,1))

    game.makeMove(playerB, Pos(1,1))
    game.makeMove(playerA, Pos(0,2))

    intercept[GameAlreadyOver]{
      game.makeMove(playerB, Pos(2,2))
    }
  }

  test("notify on PlayerJoined and PlayerLeft events"){
    val game = Game.initGameWithEvents(3,2)
    val playerA = getPlayer('A')

    val mockSubscriber = mock[EventSubscriber]
    game.addSubscriber(mockSubscriber)
    (mockSubscriber.onPlayerJoined(_: Game, _: Player)).expects(game, playerA)
    playerA.join(game)
    (mockSubscriber.onPlayerLeft(_: Game, _: Player)).expects(game, playerA)
    playerA.leave(game)
  }

  def getPlayer(id: PlayerSymbol): Player = ComputerPlayer(id)
}
