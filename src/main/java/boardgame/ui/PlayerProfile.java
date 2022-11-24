package boardgame.ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PlayerProfile {
    
    private int gamesPlayed = 0;
    private int xWins = 0;
    private int oWins = 0;
    private String[] splitString = new String[3];


    public PlayerProfile(){
       loadProfileFromCsv("playerProfile.csv");
    }
    
    /**
     * increments the games played by 1 
     */
    public void gamePlayedIncrementor(){
        gamesPlayed += 1;
        try{
            saveProfileToCsv();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    /**
     * increments player X/Evens wins by 1
     */
    public void xWinIncrementor(){
        xWins += 1;
    }
    /**
     * increments player O/Odds winns by 1
     */
    public void oWinIncrementor(){
        oWins += 1;
    }
    /**
     * checks who won the current game, calls method that
     * increments that players wins by 1
     * @param winner
     */
    public void determineWinner(boolean winner){
        if(winner){
            xWinIncrementor();
        } else {
            oWinIncrementor();
        }
    }

    /**
     * returns X/Even wins count
     * @return xWins
     */
    public int returnXWins(){
        return xWins;
    }

    /**
     * return O/Odd win count
     * @return oWins
     */
    public int returnOWins(){
        return oWins;
    }

    /**
     * return gamesPlayed count
     * @return gamesPlayed
     */
    public int returnGamesPlayed(){
        return gamesPlayed;
    }

    /**
     * saves player profile to csv file
     * @throws IOException
     */
    public void saveProfileToCsv() throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter("playerProfile.csv"));
        writer.write(gamesPlayed+"\n"+xWins+"\n"+oWins+"\n");
        writer.close();
    }

    /**
     * loads player profile from csv file
     */
    public void loadProfileFromCsv(String filePath){
        File file = new File(filePath);
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
    }

    /**
     * splits the loaded string accordingly to be loaded back into private vars
     * @param s
     */
    public void lineSplitter(String s){
        splitString = s.split("\n");
        gamesPlayed = Integer.parseInt(splitString[0]);
        xWins = Integer.parseInt(splitString[1]);
        oWins = Integer.parseInt(splitString[2]);
    }

    /**
     * resets all statistics
     * @throws IOException
     */
    public void resetAll() throws IOException{
        gamesPlayed = 0;
        xWins = 0;
        oWins = 0;
        saveProfileToCsv();
    }


}
