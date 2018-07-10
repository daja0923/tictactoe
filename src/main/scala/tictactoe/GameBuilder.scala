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
    val boardSize = GameConfig.getBoardSize
    val game: EventSourcedGame = Game.initGameWithEvents(boardSize, 3)
    val computerPlayer = ComputerPlayer(GameConfig.getComputerPlayerSymbol)
    val humanPlayerOne = HumanPlayer(GameConfig.getHumanPlayer1Symbol)
    val humanPlayerTwo = HumanPlayer(GameConfig.getHumanPlayer2Symbol)

    val gameUI = new GameConsole(game)

    game.addSubscriber(gameUI)

    var players: List[Player] = List(computerPlayer, humanPlayerOne, humanPlayerTwo)

    val random = Random
    val first = players(random.nextInt(3))
    players = players.filterNot(_.equals(first))
    val second = players(random.nextInt(2))
    players = players.filterNot(_.equals(second))
    val third = players.head
    gameUI.addPlayer(first)
    gameUI.addPlayer(second)
    gameUI.addPlayer(third)
  }
}
