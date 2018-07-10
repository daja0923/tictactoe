package tictactoe.domain.game.events

import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfterAll, FunSpec, Matchers}
import tictactoe.domain.Commons.{BoardData, PlayerSymbol, Pos}
import tictactoe.domain.Exceptions.PlayersPositionsAlreadyFilled
import tictactoe.domain.game.Game
import tictactoe.domain.player.Player
import tictactoe.ui.player.imp.ComputerPlayer

class EventSourcedGameSpec extends FunSpec with Matchers with BeforeAndAfterAll with MockFactory{

  var game: EventSourcedGame = _
  var mockSubscriber: EventSubscriber = _

  override def beforeAll(): Unit = {
    super.beforeAll()
    game = Game.initGameWithEvents(3,2)
    mockSubscriber = mock[EventSubscriber]
  }


  describe("EventSourcedGame"){
    val playerA = getPlayer('A')
    val playerB = getPlayer('B')
    val playerC = getPlayer('C')
    describe("When player joins"){
      it("should notify subscriber"){
        game.addSubscriber(mockSubscriber)
        (mockSubscriber.onPlayerJoined(_: Game, _: Player)).expects(game, playerA)
        playerA.join(game)
      }
    }

    describe("And when player leaves"){
      it("should notify subscriber"){
        (mockSubscriber.onPlayerLeft(_: Game, _: Player)).expects(game, playerA)
        playerA.leave(game)
      }
    }

    describe("And when enough players join"){

      describe("Then"){
        it("should start game and raise events - PlayerJoined, GameStarted, NextMoverDeclared"){
          (mockSubscriber.onPlayerJoined(_: Game, _: Player)).expects(game, playerA)
          playerA.join(game)
          (mockSubscriber.onPlayerJoined(_: Game, _: Player)).expects(game, playerB)
          (mockSubscriber.onGameStarted(_: Game, _: Int, _: BoardData)).expects(game, 2, *)
          (mockSubscriber.onNextMoverDeclared(_: Game, _: Player)).expects(game, playerA)
          playerB.join(game)
        }
      }
    }

    describe("And when one more player tries to join"){
      describe("Then"){
        it("it should accept player"){
          intercept[PlayersPositionsAlreadyFilled]{
            playerC.join(game)
          }
        }
      }
    }

    describe("And when players make moves"){
      describe("Then"){
        it("should raise events - PlayerMoved, BoardStateChanged and NextPlayerDeclared"){
          (mockSubscriber.onPlayerMoved(_: Game, _: Player, _: Pos)).expects(game, playerA, *)
          (mockSubscriber.onBoardStateChanged(_: Game, _: BoardData)).expects(game, *)
          (mockSubscriber.onNextMoverDeclared(_: Game, _: Player)).expects(game, playerB)
          playerA.move(game, Pos(0,0))

          (mockSubscriber.onPlayerMoved(_: Game, _: Player, _: Pos)).expects(game, playerB, *)
          (mockSubscriber.onBoardStateChanged(_: Game, _: BoardData)).expects(game, *)
          (mockSubscriber.onNextMoverDeclared(_: Game, _: Player)).expects(game, playerA)
          playerB.move(game, Pos(1,0))

          (mockSubscriber.onPlayerMoved(_: Game, _: Player, _: Pos)).expects(game, playerA, *)
          (mockSubscriber.onBoardStateChanged(_: Game, _: BoardData)).expects(game, *)
          (mockSubscriber.onNextMoverDeclared(_: Game, _: Player)).expects(game, playerB)
          playerA.move(game, Pos(1,1))

          (mockSubscriber.onPlayerMoved(_: Game, _: Player, _: Pos)).expects(game, playerB, *)
          (mockSubscriber.onBoardStateChanged(_: Game, _: BoardData)).expects(game, *)
          (mockSubscriber.onNextMoverDeclared(_: Game, _: Player)).expects(game, playerA)
          playerB.move(game, Pos(1,2))

        }
      }

      describe("And when game is won"){
        describe("Then"){
          it("should raise GameWon event"){
            (mockSubscriber.onPlayerMoved(_: Game, _: Player, _: Pos)).expects(game, playerA, *)
            (mockSubscriber.onBoardStateChanged(_: Game, _: BoardData)).expects(game, *)

            (mockSubscriber.onGameWon(_: Game, _:Player)).expects(game, playerA)
            playerA.move(game, Pos(2,2))
          }
        }
      }
    }
  }


  def getPlayer(id: PlayerSymbol): Player = ComputerPlayer(id)

  def getEmptyBoardData: BoardData = {
    Array.ofDim[PlayerSymbol](3, 3)
  }
}
