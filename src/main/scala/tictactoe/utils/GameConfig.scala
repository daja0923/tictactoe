package tictactoe.utils

import com.typesafe.config.ConfigFactory

object GameConfig {

  private val config =  ConfigFactory.load()

  def getBoardSize: Int = config.getInt("boardsize")

  def getComputerPlayerSymbol: Char = config.getString("player.computer.mark").head

  def getHumanPlayer1Symbol: Char = config.getString("player.human.1.mark").head

  def getHumanPlayer2Symbol: Char = config.getString("player.human.2.mark").head
}
