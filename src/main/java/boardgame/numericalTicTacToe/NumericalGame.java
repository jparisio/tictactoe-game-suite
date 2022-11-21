package boardgame.numericalTicTacToe;

public class NumericalGame {

/**
 * returns the player turn text being either even or odd
 * @param even
 * @return even player turn or odd player turn
 */
public String getPlayerTurn(boolean even){
    String text = "";

    if(even){
        text = "Player turn: Even";
        return  text;
    } else{
        text = "Player turn: Odd";
        return text;
    }
}

/**
 * returns the winner text being either even or odd
 * @param even
 * @return even or odd
 */
public String returnWinner(boolean even){
    if(even){
        return "Winner is Even";
    } else{
        return "Winner is Odd";
    }
}

}
