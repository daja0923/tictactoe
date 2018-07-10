package tictactoe.domain.scheduler

import org.scalatest.{BeforeAndAfterAll, FunSpec, Matchers}
import tictactoe.domain.Commons.PlayerSymbol
import tictactoe.domain.player.Player
import tictactoe.ui.player.imp.HumanPlayer
import tictactoe.domain.scheduler.imp.RoundRobinPlayersScheduler


class PlayersSchedulerTest extends FunSpec with Matchers with BeforeAndAfterAll{

  var queue: PlayersScheduler = _

  override def beforeAll(): Unit = {
    super.beforeAll()
    queue = new RoundRobinPlayersScheduler(3)
  }

  describe("A scheduler with size 8"){

    describe("When created"){
      it("should be empty"){
        queue.isEmpty shouldBe true
        queue.nonEmpty shouldBe false
        queue.size shouldBe 0
      }
      it("should not be full"){
        queue.isFull shouldBe false
      }

      it("should not have enough players"){
        queue.hasEnoughPlayers shouldBe false
      }

      it("should have no current player"){
        queue.current shouldBe None
      }

      it("should have no next player"){
        queue.next() shouldBe None
      }
    }


    describe("And when player A added"){
      it("action"){
        queue.add(getPlayer('A')) shouldBe true
      }

      describe("Then"){
        it("should not be empty"){
          queue.isEmpty shouldBe false
          queue.nonEmpty shouldBe true
        }

        it("should have size 1"){
          queue.size shouldBe 1
        }

        it("should not be full"){
          queue.isFull shouldBe false
        }

        it("should not have enough players"){
          queue.hasEnoughPlayers shouldBe false
        }

        it("should have no current player"){
          queue.current shouldBe None
        }

        it("should not have impact on next()"){
          queue.next() shouldBe None
          queue.current shouldBe None
        }

        it("should not add already existing player A"){
          queue.add(getPlayer('A')) shouldBe false
          queue.size shouldBe 1
        }

      }
    }


    describe("And when player B and C added"){
      it("action"){
        queue.add(getPlayer('B')) shouldBe true
        queue.add(getPlayer('C')) shouldBe true
      }

      describe("Then"){
        it("should be full"){
          queue.isFull shouldBe true
        }

        it("should have size 3"){
          queue.size shouldBe 3
        }

        it("should not have enough players"){
          queue.hasEnoughPlayers shouldBe true
        }

        it("should have the first player A as current"){
          queue.current shouldBe Some(getPlayer('A'))
        }

        it("should change the current player to B on calling next()"){
          queue.next() shouldBe Some(getPlayer('B'))
          queue.current shouldBe Some(getPlayer('B'))
        }

        it("should change the current player to C on calling next()"){
          queue.next() shouldBe Some(getPlayer('C'))
          queue.current shouldBe Some(getPlayer('C'))
        }

        it("should change current back to A on calling next()"){
          queue.next() shouldBe Some(getPlayer('A'))
          queue.current shouldBe Some(getPlayer('A'))
        }
      }
    }


    describe("And when player A removed"){
      it("action"){
        queue.remove(getPlayer('A')) shouldBe true
      }

      describe("Then"){
        it("should not have enough players again"){
          queue.hasEnoughPlayers shouldBe false
        }

        it("should not be full"){
          queue.isFull shouldBe false
        }

        it("should not be empty"){
          queue.isEmpty shouldBe false
          queue.nonEmpty shouldBe true
        }

        it("should have no current player"){
          queue.current shouldBe None
        }

        it("should not be able to remove player A again"){
          queue.remove(getPlayer('A')) shouldBe false
        }
      }
    }


    describe("And when player A added back"){
      it("action"){
        queue.add(getPlayer('A'))
      }

      describe("Then"){
        it("should have enough players again"){
          queue.hasEnoughPlayers shouldBe true
        }

        it("current player should be B"){
          queue.current shouldBe Some(getPlayer('B'))
        }

        it("should change current player to C on calling next()"){
          queue.next() shouldBe Some(getPlayer('C'))
          queue.current shouldBe Some(getPlayer('C'))
        }

        it("should schedule from A to C with round robin algorithm"){
          queue.next() shouldBe Some(getPlayer('A'))
          queue.current shouldBe Some(getPlayer('A'))

          queue.next() shouldBe Some(getPlayer('B'))
          queue.current shouldBe Some(getPlayer('B'))

          queue.next() shouldBe Some(getPlayer('C'))
          queue.current shouldBe Some(getPlayer('C'))

        }
      }
    }


    describe("And when player A removed again"){
      it("action"){
        queue.remove(getPlayer('A')) shouldBe true
      }

      describe("Then"){
        it("should change current to None"){
          queue.current shouldBe None
        }
      }
    }


    describe("And when player C is removed"){
      it("action"){
        queue.remove(getPlayer('C')) shouldBe true
      }

      describe("Then"){
        it("should not be full"){
          queue.isFull shouldBe false
        }

        it("should not be empty"){
          queue.isEmpty shouldBe false
          queue.nonEmpty shouldBe true
        }

        it("should have size 1"){
          queue.size shouldBe 1
        }

        it("should not have enough players"){
          queue.hasEnoughPlayers shouldBe false
        }

        it("should only contain player B"){
          queue.contains(getPlayer('A')) shouldBe false
          queue.contains(getPlayer('B')) shouldBe true
          queue.contains(getPlayer('C')) shouldBe false
          queue.contains(getPlayer('D')) shouldBe false
          queue.contains(getPlayer('E')) shouldBe false
          queue.contains(getPlayer('F')) shouldBe false
          queue.contains(getPlayer('G')) shouldBe false
        }
      }
    }

  }


  def getPlayer(id: PlayerSymbol): Player = HumanPlayer(id)
}
