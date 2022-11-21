package boardgame.numericalTicTacToe;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class NumericalBoard implements boardgame.Saveable, boardgame.Board{

    private String[] board = new String[9];
    private int[] numBoard = new int[9];
    private String[] splitString = new String[10];
    private String currTurn = "E";
    private String turnChar = "";
    private ArrayList<Integer> numList = new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,8,9));
   
    public NumericalBoard(){
        for(int i = 0; i < 9; i++){
            board[i] = "-1";
        }
    }

    /**
     * updates the boards index with the current player move
     * @param index
     * @param turn
     */
    @Override
    public void setBoardIndex(int index, String turn) {
        board[index] = turn;
    }

    /**
     * returns the value at the boards given index
     * @param i
     * @return the boards index at board[i]
     */
    @Override
    public String returnBoardIndex(int i) {
        return board[i];
    }

    /**
     * checks if there is a winner (three numbers add to 15 on the board)
     * @param none
     * @return true or false
     */
    @Override
    public boolean checkWinner() {
        setStringToInt();
        if(numBoard[0]+numBoard[1]+numBoard[2]==15&&(numBoard[0]>=0)&&(numBoard[1]>=0)&&(numBoard[2]>=0)){
            return true;
        }else if(numBoard[3]+numBoard[4]+numBoard[5]==15&&(numBoard[3]>=0)&&(numBoard[4]>=0)&&(numBoard[5]>=0)){
            return true;
        }else if(numBoard[6]+numBoard[7]+numBoard[8]==15&&(numBoard[6]>=0)&&(numBoard[7]>=0)&&(numBoard[8]>=0)){
            return true;
        }else if(numBoard[0]+numBoard[3]+numBoard[6]==15&&(numBoard[0]>=0)&&(numBoard[3]>=0)&&(numBoard[6]>=0)){
            return true;
        }else if(numBoard[1]+numBoard[4]+numBoard[7]==15&&(numBoard[1]>=0)&&(numBoard[4]>=0)&&(numBoard[7]>=0)){
            return true;
        }else if(numBoard[2]+numBoard[5]+numBoard[8]==15&&(numBoard[2]>=0)&&(numBoard[5]>=0)&&(numBoard[8]>=0)){
            return true;
        }else if(numBoard[0]+numBoard[4]+numBoard[8]==15&&(numBoard[0]>=0)&&(numBoard[4]>=0)&&(numBoard[8]>=0)){
            return true;
        }else if(numBoard[2]+numBoard[4]+numBoard[6]==15&&(numBoard[2]>=0)&&(numBoard[4]>=0)&&(numBoard[6]>=0)){
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
    public boolean checkTie(int turns) {
        if(turns>=9){
            return true;
        }
        return false;
    }

    /**
     * debugger for board, prints to system
     */
    @Override
    public void printBoard() {
        for(int i = 0; i < 9; i++){
            System.out.println(board[i]);
        }
    }

    /**
     * @param none
     * resets board to empty board
     */
    @Override
    public void resetBoard() {
        for(int i = 0; i < 9; i++){
            board[i] = "-1";
        }
    }

    /**
     * sets the current turn that is being taken.  Used for when saving and loading
     * @param check
     */
    @Override
    public void setCurrTurn(boolean check) {
        if(check){
            currTurn = "E\n";
        } else {
            currTurn = "O\n";
        }
        
    }
    /**
     * saves game board to csv file
     * @throws IOException
     */
    @Override
    public void saveGameToCsvFile() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("numTicTacToeSavedGame.csv"));
        writer.write(getStringToSave());
        writer.close();
    }

    /**
     * repopulates board after retriving loaded string from csv file
     */
    @Override
    public void repopulateBoard() {
        for(int i = 1; i<=9; i++){
            board[i-1] = splitString[i];
            if(!(board[i-1].equals("-1"))){
                numList.remove(Integer.parseInt(board[i-1].strip()));
            }
        }
        turnChar = splitString[0];
    }

    /**
     * return the curretn turn character
     * @return the current turn
     */
    @Override
    public String returnTurnChar() {
        return turnChar;
    }

    /**
     * splits the loaded string at every comma and popultes string array with it
     * @param line
     */
    @Override
    public void lineSplitter(String line) {
    line = line.replace("\n",",");
    line = line + ", ";
    splitString = line.split(",");
        
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
        resetNumList();
        lineSplitter(toParse);
        repopulateBoard();
    }

    /**
     * checks if the user can play with a given input (not previously used input)
     * @param i
     * @returns true or false
     */
    @Override
    public boolean canPlay(int i){
        for(int j = 0; j < numList.size(); j++){
            if(numList.get(j)==i){
                numList.remove(j);
                return true;
            }
        }
        return false;
    }

    /**
     * converts board of strings to board of ints to make math easier
     */
    public void setStringToInt(){
        for(int i = 0; i < 9; i++){
            numBoard[i] = Integer.parseInt(board[i]);
        }
    }

    /**
     * replaces all -1's with blank string to be converted to string array
     */
    public void replaceZeros(){
        for(int i = 0; i < 9; i++){
            if(board[i].equals("-1")){
                board[i] = "";
            }
        }
    }

    /**
     * replaces all blank string with -1 to be converted to int array
     */
    public void addZeros(){
        for(int i = 0; i < 9; i++){
            if(board[i].equals("")){
                board[i] = "-1";
            }
        }
    }

    /**
     * resets the numbers that the player is able to use
     */
    public void resetNumList(){
        numList.removeAll(numList);
        numList.add(0);
        numList.add(1);
        numList.add(2);
        numList.add(3);
        numList.add(4);
        numList.add(5);
        numList.add(6);
        numList.add(7);
        numList.add(8);
        numList.add(9);
    }


}
    

