package boardgame;

import java.io.IOException;

public interface Board {
    
void setBoardIndex(int index, String turn);

String returnBoardIndex(int i);

boolean checkWinner();

boolean checkTie(int turns);

void printBoard();

void resetBoard();

void setCurrTurn(boolean check);

void saveGameToCsvFile() throws IOException;

void repopulateBoard();

String returnTurnChar();

void lineSplitter(String line);

boolean canPlay(int i, boolean b);
}
