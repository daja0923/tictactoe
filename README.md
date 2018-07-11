# tictactoe in scala

The game is written in OOP style.
It is designed with events with Observer pattern. 
This design decision was made with the assumption that players could be remote.
Each player can subscribe to the events of the game. Game engine raises event on each change (player joins, player moves, game starts, board state changes, game won, etc).
Although this implementation contains only console ui, it can easily be incorporated with Websocket for remote players.
The compute player is dummy in this implementation. It just marks the first position it finds empty.


# Run
To run the code sbt is necessary, the testing and conf dependencies are included in /build.sbt file.<br/>
To run the code run the following command in root: sbt run


# Test
To test run command: sbt test<br/>
To run only one test class run command: sbt "testOnly [path to testfile]"