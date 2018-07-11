package tictactoe

import tictactoe.domain.game.Game
import tictactoe.domain.game.events.EventSourcedGame
import tictactoe.domain.player.Player
import tictactoe.ui.GameConsole
import tictactoe.ui.player.imp.{ComputerPlayer, HumanPlayer}
import tictactoe.utils.GameConfig
import scala.util.Random

class GameBuilder {

  def build(): Unit = {
    val game: EventSourcedGame = buildGame()

    val gameUI = new GameConsole(game)

    game.addSubscriber(gameUI)

    val players = buildPlayers()

    joinPlayersToGameRandomly(game, players)

  }

  private def buildGame(): EventSourcedGame = {
    val boardSize = GameConfig.getBoardSize
    Game.initGameWithEvents(boardSize, 3)
  }

  private def buildPlayers(): List[Player] = {
    val computerPlayer = ComputerPlayer(GameConfig.getComputerPlayerSymbol)
    val humanPlayerOne = HumanPlayer(GameConfig.getHumanPlayer1Symbol)
    val humanPlayerTwo = HumanPlayer(GameConfig.getHumanPlayer2Symbol)
    List(computerPlayer, humanPlayerOne, humanPlayerTwo)
  }

  private def joinPlayersToGameRandomly(game: Game, players: List[Player]): Unit = {
    val random = Random
    var playersToJoin = players
    val first = playersToJoin(random.nextInt(3))
    playersToJoin = playersToJoin.filterNot(_.equals(first))
    val second = playersToJoin(random.nextInt(2))
    playersToJoin = playersToJoin.filterNot(_.equals(second))
    val third = playersToJoin.head

    first.join(game)
    second.join(game)
    third.join(game)
  }
}
