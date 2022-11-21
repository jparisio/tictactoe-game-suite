package boardgame.tictactoe;

import java.util.Scanner;

public class TicTacToeGame {
    private TicTacToeBoard board = new TicTacToeBoard();
    public TicTacToeGame(){
      
    }

    /**
     * returns the player turn (true for X odd for O)
     * @param turn
     * @return X or O
     */
    public String getTurn(boolean turn){
        if(turn){
            return "X";
        } else{
            return "O";
        }
    }

    /**
     * increments the amount of turns there are in the 
     * \current game and returns the number
     * @param numTurns
     * @return
     */
    public int turnIncrementer(int numTurns){
        numTurns += 1;
        return numTurns;
    }
    
    /**
     * returns playr turn text (true for X false for 0)
     * @param turn
     * @return player turn x or player turn o
     */
    public String setPlayerTurnText(boolean turn){
        if(turn){
            String label = ("Player turn: X");
            return label;
        } else{
       
        String label2 = ("Player turn: O");
            return label2;
        }
    }

    /**
     * gets input for cmd line tictac toe and returns int input
     * @return int input
     */
    public int getInput(){
        //get input
        Scanner sc = new Scanner(System.in);
        //catch letter inputs and recall scanner if so
        int num = -1;
        try{
          System.out.println("Select a spot to play 0-8");
          while(!(num <= 8 && num >= 0)){
            num = sc.nextInt(); // get new user input
            // check to see if its an occupied cell
            // if occupied, continue
          }
          System.out.println("input was " + num);
        } catch(Exception e){
          System.out.println("invalid input, input number 0-8");
          getInput();
        }
        return num;
      }


    public static void main(String[] args) {
        TicTacToeBoard board = new TicTacToeBoard();
        TicTacToeGame game = new TicTacToeGame();
        int turnCounter = 0;
        boolean turn = true;


        while(turnCounter<9){
            String turnChar = game.getTurn(turn);
            System.out.println(game.getTurn(turn)+"'s turn\n");
            board.printBoard();
            int newNum = game.getInput();
            if(board.canPlay(newNum)){
                board.setBoardIndex(newNum, turnChar);
                turnCounter+=1;
            } else {
                turn = !turn;
            }
            if(board.checkWinner()){
                System.out.println(game.getTurn(turn)+" WINS\n");
                board.printBoard();
                System.exit(0);
            }
            turn = !turn;
        }
        System.out.println("Tie Game\n");
        System.exit(0);
    }

 


}
