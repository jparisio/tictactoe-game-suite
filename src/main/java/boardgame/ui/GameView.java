package boardgame.ui;

import javax.swing.SwingConstants;
import boardgame.numericalTicTacToe.NumericalUI;
import boardgame.tictactoe.TicTacToeUI;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;




public class GameView extends JFrame implements ActionListener {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    private TicTacToeUI ticTacToeGame = new TicTacToeUI();
    private NumericalUI numGame = new NumericalUI();
    private PlayerProfile profile = new PlayerProfile();
    private JPanel panel;
    
    public GameView() {
        super();
        this.setTitle("TIC TAC TOE");
        this.setSize(WIDTH, HEIGHT);
        panel = new JPanel(); // main panel
        this.add(mainMenu());
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setVisible(true);
        // profile.loadProfileFromCsv();
    }

   /**
    * sets up the main menu for user interface
    * @return panel
    */
   public JPanel mainMenu(){
        panel = new JPanel(); // main panel
        panel.setLayout(new GridLayout(2, 1));
        //main panel
        JPanel gameModes = new JPanel();
        gameModes.setLayout(new GridLayout(1,2));
        //button1
        JButton game1 = new JButton("TicTacToe");
        game1.setFont(new Font("Arial", Font.PLAIN, 28));
        game1.addActionListener(this);
        //button2
        JButton game2 = new JButton("<html>Numerical\n<br />TicTacToe</html>");
        game2.setFont(new Font("Arial", Font.PLAIN, 28));
        game2.addActionListener(this);
        //button 3
        JButton playerProfile = new JButton("<html>Player\n<br />Profile</html>");
        playerProfile.setFont(new Font("Arial", Font.PLAIN, 28));
        playerProfile.addActionListener(this);
        //add buttons
        gameModes.add(game1);
        gameModes.add(game2);
        gameModes.add(playerProfile);

        JLabel title = new JLabel("Game Suite", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 64));
        panel.add(title);
        panel.add(gameModes);

        return panel;
   }

   /**
    * method for button presses of the three buttons (game1 game2 and player profile)
    */
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand() == "TicTacToe"){
            setUpTicTacToe();
        } else if(e.getActionCommand() == "<html>Numerical\n<br />TicTacToe</html>"){
            setUpNumTacToe();
        } else if(e.getActionCommand() == "<html>Player\n<br />Profile</html>"){
            JOptionPane.showMessageDialog(null, 
            "Please select file called playerProfile.csv\n navigate to A3->playerProfile.csv");
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null);
            if(response == fileChooser.APPROVE_OPTION){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                String fileString = file.toString();
                profile.loadProfileFromCsv(fileString);
            }
            int option = JOptionPane.showConfirmDialog(null,
            "Games played: "+profile.returnGamesPlayed()+"\nPlayer X/Even wins: "+profile.returnXWins()
            +"\nPlayer O/Odd wins: "+profile.returnOWins()+"\n\nReset lifetime profile?","Confirm",
            JOptionPane.YES_NO_OPTION);
            if(option ==  JOptionPane.YES_OPTION){
                try {
                    profile.resetAll();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * clears panel and sets up the tictactoe game
     */
    public void setUpTicTacToe(){
        panel.removeAll();
        panel.repaint();
        panel.revalidate();
        panel.setLayout(new GridLayout(2,1));
        panel.add(ticTacToeGame.gameBoardAssembler());
        panel.repaint();
        panel.revalidate();
    }

    /**
     * clears panel and sets up Numerical tictactoe game
     */
    public void setUpNumTacToe(){
        panel.removeAll();
        panel.repaint();panel.revalidate();
        panel.setLayout(new GridLayout(2,1));
        panel.add(numGame.gameBoardAssembler());
        panel.repaint();
        panel.revalidate();
    }



public static void main(String[] args) {
        GameView game = new GameView();
    }
}
