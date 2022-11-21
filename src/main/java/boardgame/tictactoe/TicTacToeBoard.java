package boardgame.tictactoe;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TicTacToeBoard implements boardgame.Saveable, boardgame.Board{

private String[] board = new String[9];
private String currTurn = "";
private String[] splitString = new String[10];
private String turnChar = "";

public TicTacToeBoard(){
    for(int i = 0; i < 9; i++){
        board[i] = "";
    }
}
/**
 * updates the boards index with the current player move
 * @param index
 * @param turn
 */
@Override
public void setBoardIndex(int index, String turn){
    
    board[index] = turn;
}
/**
 * returns the value at the boards given index
 * @param i
 * @return the boards index at board[i]
 */
@Override
public String returnBoardIndex(int i){
    return board[i];
}
 /**
 * checks if there is a winner (three numbers add to 15 on the board)
 * @param none
 * @return true or false
 */
@Override
public boolean checkWinner(){
if((board[0] == board[1]) && (board[1] == board[2])&&(board[0]!="")&&(board[1]!="")&&(board[2]!="")){
    return true;
}else if((board[3].equals(board[4])) && (board[4].equals(board[5]))&&(board[3]!="")&&(board[4]!="")&&(board[5]!="")){
    return true;
}else if((board[6].equals(board[7])) && (board[7].equals(board[8]))&&(board[6]!="")&&(board[7]!="")&&(board[8]!="")){
    return true;
}else if((board[0].equals(board[3])) && (board[3].equals(board[6]))&&(board[0]!="")&&(board[3]!="")&&(board[6]!="")){
    return true;
}else if((board[1].equals(board[4])) && (board[4].equals(board[7]))&&(board[1]!="")&&(board[4]!="")&&(board[7]!="")){
    return true;
}else if((board[2].equals(board[5])) && (board[5].equals(board[8]))&&(board[2]!="")&&(board[5]!="")&&(board[8]!="")){
    return true;
} else if((board[0].equals(board[4])) && (board[4].equals(board[8]))&&(board[0]!="")&&(board[4]!="")&&(board[8]!="")){
    return true;
} else if((board[2].equals(board[4])) && (board[4].equals(board[6]))&&(board[2]!="")&&(board[4]!="")&&(board[6]!="")){
    return true;
}
return false;
}
/**
 * checks to see if the game ends in a tie (mac turns have occured for the length of the game)
 * @param turns 
 * returns true or false
 */
@Override
public boolean checkTie(int turns){
    if(turns>=9){
        return true;
    }
    return false;
}

/**
 * debugger for printing board
 */
@Override
public void printBoard(){
   System.out.print("|");
   System.out.print(board[0]);
   System.out.print(" |");
   System.out.print(board[1]);
   System.out.print(" |");
   System.out.print(board[2]);
   System.out.print(" |");
   System.out.println();
   System.out.print("|");
   System.out.print(board[3]);
   System.out.print(" |");
   System.out.print(board[4]);
   System.out.print(" |");
   System.out.print(board[5]);
   System.out.print(" |");
   System.out.println();
   System.out.print("|");
   System.out.print(board[6]);
   System.out.print(" |");
   System.out.print(board[7]);
   System.out.print(" |");
   System.out.print(board[8]);
   System.out.print(" |");
   System.out.println();
}
/**
 * @param none
 * resets board to empty board
 */
@Override
public void resetBoard(){
    for(int i = 0; i < 9; i++){
        board[i] = "";
    }
}
/**
 * sets the current turn that is being taken.  Used for when saving and loading
 * @param check true for X false for O
 */
@Override
public void setCurrTurn(boolean check){
    if(check){
        currTurn = "X\n";
    } else {
        currTurn = "O\n";
    }
}

 /**
 * converts board contents to csv format
 */
@Override
public String getStringToSave() {
    String commaAdded = "";
    commaAdded = commaAdded + currTurn;
    for(int i = 0; i < 9; i++){
        if(i == 0 || i == 1 || i == 3 || i == 4 || i == 6 || i == 7){
            commaAdded = commaAdded + board[i] + ",";
        }else{
            commaAdded = commaAdded + board[i] + "\n";
        }
    } 
    return commaAdded;
}
 /**
 * loads csv file into a String
 * @param toLoad
 */
@Override
public void loadSavedString(String toLoad) {
    
    File file = new File(toLoad);
    String toParse = "";
    try {
      Scanner sc = new Scanner(file);
      while(sc.hasNextLine()){
        toParse = toParse.concat(sc.nextLine()+"\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    lineSplitter(toParse);
    repopulateBoard();
}
/**
 * saves game board to csv file
 * @throws IOException
 */
@Override
public void saveGameToCsvFile() throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter("ticTacToeSavedGame.csv"));
    writer.write(getStringToSave());
    writer.close();
}
/**
 * repopulates board after retriving loaded string from csv file
 */
@Override
public void repopulateBoard(){
    for(int i = 1; i<=9; i++){
        board[i-1] = splitString[i];
    }
    turnChar = splitString[0];
}
/**
 * return the curretn turn character
 * @return the current turn
 */
@Override
public String returnTurnChar(){
    return turnChar;
}
/**
 * splits the loaded string at every comma and popultes string array with it
 * @param line
 */
@Override
public void lineSplitter(String line){
    line = line.replace("\n",",");
    line = line + ", ";
    splitString = line.split(",");
}
/**
 * checks if the user can play with a given input (empty or occupied space)
 * @param i
 * @returns true or false
 */
@Override
public boolean canPlay(int i){
    if(returnBoardIndex(i)==""){
        return true;
    }
    return false;
}


}

