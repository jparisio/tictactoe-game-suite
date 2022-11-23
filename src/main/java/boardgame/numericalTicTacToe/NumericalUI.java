package boardgame.numericalTicTacToe;

import boardgame.ui.GameView;
import boardgame.ui.PlayerProfile;

import java.io.FileNotFoundException;
import java.io.IOException;
// import javax.swing.filechooser.*;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class NumericalUI extends JFrame implements ActionListener{
    
private JPanel numTacToe;
private JButton[] numTacToeButton = new JButton[9];
private JMenu turn = new JMenu();
private JButton load;
private JButton exit;
private boolean player = true;
private NumericalGame numGameText = new NumericalGame();
private NumericalBoard board = new NumericalBoard();
private int turnCounter = 0;
private GameView exiter;
private PlayerProfile profile = new PlayerProfile();

/**
 * handles any interction with the game board.  
 * Sees which spot on the board was pressed,
 * calls mehtods accordingly to get input, save, exit etc
 * @param e
 */
public void actionPerformed(ActionEvent e) {
    for(int i = 0; i<9; i++){
        if(e.getSource()==numTacToeButton[i]){
        if(numTacToeButton[i].getText() == ""){
            initiateTurn(i);
            if(board.checkWinner()){
                int option2 = JOptionPane.showConfirmDialog(null, 
                numGameText.returnWinner(!player)+"\nPlay Again?","Confirm",JOptionPane.YES_NO_OPTION);
                profile.determineWinner(!player);
                profile.gamePlayedIncrementor();
                if(option2 == JOptionPane.YES_OPTION){
                    resetGame();
                } else{
                    exit();
                }
            }
            if(board.checkTie(turnCounter)){
                tie();
            }
          }
       }
    }
    if(e.getSource()==load){
        load();
      }
    if(e.getSource() == exit){
        exit();
      }   
}

/**
 * assembles the game board layout
 * @return numTacToe
 */
public JPanel gameBoardAssembler(){
    numTacToe = new JPanel();
    numTacToe.setLayout(new GridLayout(1,2));

    JPanel boardDisplay = new JPanel();
    boardDisplay.setLayout(new GridLayout(3,3));
    boardDisplay.setSize(300,300);

    for(int i = 0; i<9; i++){
        numTacToeButton[i] = new JButton("");
        boardDisplay.add(numTacToeButton[i]);
        numTacToeButton[i].addActionListener(e->actionPerformed(e));
    }

    JMenuBar sideMenu = new JMenuBar();
    sideMenu.setLayout(new GridLayout(3,1));
    turn.setText("Player turn: Even");
    turn.setFont(new Font("Arial", Font.PLAIN, 20));
    sideMenu.add(turn);
    load = new JButton("Load Game");
    sideMenu.add(load);
    load.addActionListener(e->actionPerformed(e));
    JLabel exitText = new JLabel("EXIT");
    exit = new JButton(exitText.getText());
    exit.addActionListener(e->actionPerformed(e));
    sideMenu.add(exit);
    numTacToe.add(boardDisplay);
    numTacToe.add(sideMenu);
    return numTacToe;
}

/**
 * initiate the players first turn
 * @param i
 */
public void initiateTurn(int i){
    board.setCurrTurn(!player);
    int numInt = getInput(player);
    String numString = Integer.toString(numInt);
    board.setBoardIndex(i,numString);
    numTacToeButton[i].setText(numString);
    player = !player;
    turn.setText(numGameText.getPlayerTurn(player));
    turnCounter += 1;
}

/**
 * displays message to exit to main screen or save current progress
 */
public void exit(){
    int result = JOptionPane.showConfirmDialog(null, 
    "Would you Like to save game?","Confirm",JOptionPane.YES_NO_OPTION);
    if(result == JOptionPane.YES_OPTION){
      saveMethod();
    }
        exiter = new GameView();
   }

/**
 * resets and clears game board
 */
public void resetGame(){
    numTacToe.removeAll();
    numTacToe.repaint();
    numTacToe.revalidate();
    turnCounter = 0;
    board.resetBoard();
    player = true;
    numTacToe.add(gameBoardAssembler());
    board.resetNumList();
}

/**
 * calls baord to save game to csv file
 */
public void saveMethod(){
    try {
        board.saveGameToCsvFile();
        JOptionPane.showMessageDialog(null, 
        "Game Saved","title",JOptionPane.PLAIN_MESSAGE);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

/**
 * loads in player text and turn count and sets JButton text to board content
 * @throws FileNotFoundException
 */
public void loadMethod() throws FileNotFoundException{
    turnCounter = 0;
    board.loadSavedString("numTicTacToeSavedGame.csv");
    JOptionPane.showMessageDialog(null, 
    "Game Loaded","title",JOptionPane.PLAIN_MESSAGE);
    turn.setText("Player turn: "+board.returnTurnChar());
    if(board.returnTurnChar().equals("E")){
        player = true;
        turn.setText("Player turn: Even");
    } else {
        player = false;
        turn.setText("Player turn: Odd");
    }
    board.replaceZeros();
    for(int i = 0; i<9; i++){
        numTacToeButton[i].setText(board.returnBoardIndex(i));
        if(board.returnBoardIndex(i)!=""){
            turnCounter+=1;
        }
    }
    board.addZeros();
}

/**
 * calls board to load board contents from csv file
 */
public void load(){
    int result = JOptionPane.showConfirmDialog(null, 
    "Would you Like to load game?","Confirm",JOptionPane.YES_NO_OPTION);
    if(result == JOptionPane.YES_OPTION){
      try {
        loadMethod();
    } catch (FileNotFoundException e1) {
        e1.printStackTrace();
    }
    }
}

/**
 * calls baord to check for a tie game
 */
public void tie(){
    int option1 = JOptionPane.showConfirmDialog(null, 
    "Tie Game\nPlay Again?", "title",JOptionPane.YES_NO_OPTION);
    if(option1 == JOptionPane.YES_OPTION){
        resetGame();
    } else{
        exiter = new GameView();
    }
}

/**
 * passes in player turn and gets even or odd input depending on turn taken, returns int input
 * @param b player turn true for even false for odd
 * @return int input
 */
public int getInput(boolean b){
    boolean repeat = b;
    if(repeat){
        int num = -1;
        try{
          while((!board.canPlay(num, repeat)||!isEven(num))){
            num = Integer.parseInt(JOptionPane.showInputDialog(
                "Input EVEN number\nbetween 0-9\nnumber must not already be used")); // get new user input
          }
        } catch(Exception e){
          int num2 = getInput(repeat);
        }
        return num;
    } else if(!repeat){

        int num = -2;
        try{
          while(!board.canPlay(num, repeat)||isEven(num)){
            num = Integer.parseInt(JOptionPane.showInputDialog(
                "Input ODD number\n between 0-9\nnumber must not already be used")); // get new user input
          }
        } catch(Exception e){
          int num3 = getInput(repeat);
        }
        return num;
    }
    return 1000;
  }

  /**
   * checks to see if a number is even or odd
   * @param num
   * @return true or false
   */
  private boolean isEven(int num){
    if(num%2 == 0){
        return true;
    }
    return false;
  }
 
}