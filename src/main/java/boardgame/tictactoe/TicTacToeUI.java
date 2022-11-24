package boardgame.tictactoe;
import javax.swing.JButton;
import boardgame.ui.GameView;
import boardgame.ui.PlayerProfile;
import java.awt.GridLayout;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class TicTacToeUI extends JFrame implements ActionListener{
    
private JButton[] ticTacToeButton = new JButton[9];
private boolean player = true;
private JMenu turn = new JMenu();
private  TicTacToeBoard board;
private int turnCounter;
private JButton exit;
private JPanel ticTacToe;
private GameView exiter;
private JButton load;
private TicTacToeGame playerTurn = new TicTacToeGame();
private PlayerProfile profile = new PlayerProfile();

    public TicTacToeUI(){
        board = new TicTacToeBoard();
        turnCounter = 0;
    }

    /**
    * handles any interction with the game board.  
    * Sees which spot on the board was pressed,
    * calls mehtods accordingly to get input, save, exit etc
    * @param e
    */
    public void actionPerformed(ActionEvent e) {
       for(int i = 0; i<9; i++){
        if(e.getSource()==ticTacToeButton[i]){
            if(ticTacToeButton[i].getText() == ""){
                ticTacToeButton[i].setText(playerTurn.getTurn(player));
                player = !player;
                board.setCurrTurn(player);
                turnCounter = playerTurn.turnIncrementer(turnCounter);
                turn.setText(playerTurn.setPlayerTurnText(player));
                board.setBoardIndex(i, ticTacToeButton[i].getText());
                if(board.checkWinner()){
                 win();
                }else if(board.checkTie(turnCounter)){
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
    * @return ticTacToe
    */
    public JPanel gameBoardAssembler(){
        ticTacToe = new JPanel();
        ticTacToe.setLayout(new GridLayout(1,2));
        JPanel boardDisplay = new JPanel();
        boardDisplay.setLayout(new GridLayout(3,3));
        boardDisplay.setSize(300,300);
        for(int i = 0; i<9; i++){
            ticTacToeButton[i] = new JButton("");
            boardDisplay.add(ticTacToeButton[i]);
            ticTacToeButton[i].addActionListener(e->actionPerformed(e));
        }
        JMenuBar sideMenu = new JMenuBar();
        sideMenu.setLayout(new GridLayout(3,1));
        turn.setText("Player turn: X");
        turn.setFont(new Font("Arial", Font.PLAIN, 32));
        sideMenu.add(turn);
        load = new JButton("Load Game");
        sideMenu.add(load);
        load.addActionListener(e->actionPerformed(e));
        exit = new JButton("EXIT");
        exit.addActionListener(e->actionPerformed(e));
        sideMenu.add(exit);
        ticTacToe.add(boardDisplay);
        ticTacToe.add(sideMenu);
        return ticTacToe;
    }

    /**
     * returns a JLabel for the current turn char
     * @param turnChar
     * @return newLabel
     */
    public JLabel setTurn(String turnChar){

        JLabel newLabel = new JLabel(turnChar);
        return newLabel;
        
    }

    /**
     * resets all of the games assets
     */
    public void resetGame(){
        ticTacToe.removeAll();
        ticTacToe.repaint();
        ticTacToe.revalidate();
        turnCounter = 0;
        board.resetBoard();
        player = true;
        ticTacToe.add(gameBoardAssembler());
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
        board.loadSavedString("ticTacToeSavedGame.csv");
        JOptionPane.showMessageDialog(null, 
        "Game Loaded","title",JOptionPane.PLAIN_MESSAGE);
        turn.setText("Player turn: "+board.returnTurnChar());
        if(board.returnTurnChar().equals("X")){
            player = true;
            turn.setText("Player turn: X");
        } else {
            player = false;
            turn.setText("Player turn: O");
        }
        for(int i = 0; i<9; i++){
            ticTacToeButton[i].setText(board.returnBoardIndex(i));
            if(board.returnBoardIndex(i)!=""){
                turnCounter+=1;
            }
        }
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
    * calls baord methods to check for a tie game
    */
    public void tie(){
        int option1 = JOptionPane.showConfirmDialog(null, 
        "Tie Game\nPlay Again?", "title",JOptionPane.YES_NO_OPTION);
        profile.gamePlayedIncrementor();
        if(option1 == JOptionPane.YES_OPTION){
            resetGame();
        } else{
            exiter = new GameView();
        }
    }

    /**
     * calls board methods to check if there is a winner
     */
    public void win(){
        int option2 = JOptionPane.showConfirmDialog(null, 
        "Winner is "+playerTurn.getTurn(!player)+"\nPlay Again?", "Confirm",JOptionPane.YES_NO_OPTION);
        profile.determineWinner(!player);
        profile.gamePlayedIncrementor();
        if(option2 == JOptionPane.YES_OPTION){
            resetGame();
        } else{
            exiter = new GameView();
        }
    }
    
    
}
