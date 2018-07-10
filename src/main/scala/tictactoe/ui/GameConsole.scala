package tictactoe.ui

import java.util.Scanner

import tictactoe.domain.Commons
import tictactoe.domain.Commons.{BoardData, Pos}
import tictactoe.domain.game.Game
import tictactoe.domain.player.Player
import tictactoe.ui.player.imp.{ComputerPlayer, HumanPlayer}


class GameConsole(game: Game) extends GameUI with EventSubscriberUI{

  private val scanner: Scanner = new Scanner(System.in)

  private var boardData: BoardData = _

  override def onGameStarted(source: Game, numOfPlayers: Int, boardData: BoardData): Unit = {
    this.boardData = boardData
    System.out.println("Game started")
    draw(boardData)
  }

  override def onPlayerJoined(source: Game, player: Player): Unit = {
    System.out.println(s"Player ${player.id} joined")
  }

  override def onPlayerLeft(source: Game, player: Player): Unit = {
    System.out.println(s"Player ${player.id} left")
  }

  override def onPlayerMoved(source: Game, player: Player, pos: Commons.Pos): Unit = {
    System.out.println(s"Player ${player.id} marked $pos")
  }

  override def onNextMoverDeclared(source: Game, player: Player): Unit = {
    player match {
      case p: ComputerPlayer => p.move(game, boardData)
      case p: HumanPlayer => makeHumanPlayerMove(source, p)
    }
  }

  private def makeHumanPlayerMove(game: Game, humanPlayer: HumanPlayer): Unit = {
    System.out.println(s"Player ${humanPlayer.id}, make your move:")
    val pos = getPlayersMove(humanPlayer)
    try{
      game.makeMove(humanPlayer, pos)
    }
    catch {
      case f: Throwable =>
        System.out.println(f.getMessage)
        onNextMoverDeclared(game, humanPlayer)
    }
  }

  override def onGameWon(source: Game, winner: Player): Unit = {
    declareWinner(winner)
  }

  override def onGameTied(source: Game): Unit = {
    System.out.println("Game over. Tie!")
  }

  override def onBoardStateChanged(source: Game, boardData: BoardData): Unit = {
    System.out.println()
    this.boardData = boardData
    draw(boardData)
  }

  override def getPlayersMove(player: Player): Commons.Pos = {
    val line = scanner.nextLine()
    try{
      val points = line.split(",").map(_.trim.toInt)
      if(points.length != 2)
        throw new Exception("Separate your points by comma")

      val x = points(0)
      val y = points(1)
      val pos = Pos(x, y)
      pos
    }catch {
      case f: Throwable =>
        System.out.println(f.getMessage + ". Please try again!")
        getPlayersMove(player)
    }
  }

  override def draw(board: BoardData): Unit = {
    val length = board.length
    (0 until length * 6).foreach(_ => print("-"))

    board.foreach{row =>
      println()
      row.foreach(c =>
        if(c == 0) print("_\t")
        else print(c + "\t")
      )
      println()
    }

    (0 until length * 6).foreach(_ => print("-"))
    println()
    println()
  }

  override def declareWinner(winner: Player): Unit = {
    System.out.println(s"Game over. Game won by player ${winner.id}!")
  }

  override def displayError(message: String): Unit = {
    System.out.println(message)
  }

  override def addPlayer(player: Player): Unit = {
    player.join(game)
  }
}
