compile GameView in order to run game suite
compile TicTacToeGame to run cmd line version of TicTacToeGame

# Project Title

Game Suite

## Description

A package of two seperate games, tic tac toe and numerical tic tac toe.  For tic tac plaers
must make a line of 3 in a row to win.  For numerical tic tac toe players must create a line that
adds to 15 to win.  Player profile keeps track of players wins and can be reset whenever.

## Getting Started

### Dependencies
only the boardgame package
### Executing program
```
gradle build
```
```
gradle run
```
```
to run GUI:
java -jar build/libs/A3.jar
```
to run cmd line game:
java -cp build/classes/java/main boardgame.tictactoe.TicTacToeGame 


## Limitations

error hanndling is set up so that nothing causes an error other than an exception is thrown if there is an inccorrectly formatted file.
sometimes inputting strings into the num tic tac toe can cause inccorect inputs
## Author Information

Justin Parisio
jparisio@uoguelph.ca

## Development History

Keep a log of what things you accomplish when.  You can use git's tagging feature to tag the versions or you can reference commits.
* 0.8
    * finalized project
* 0.7
    * save/load features
* 0.6
    * win conditions and tie conditions
* 0.5
    * interface designn
* 0.4
    * user input and exceptions
* 0.3
    * board creation
* 0.1
    * method layout

## Acknowledgments

Inspiration, code snippets, etc.
* my previous assignment tictactoe (code repurposed from the user input and game loop (turns selector, printing etc))
*online java swing docs
