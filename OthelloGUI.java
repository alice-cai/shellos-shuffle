/**
 * OthelloGUI.java
 * Provide the GUI for the Othello game
 */
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.*;

public class OthelloGUI {
	// the name of the configuration file
   private final String CONFIGFILE = "images/config.txt";
	
   private JLabel[][] slots;
   private JFrame mainFrame;
   private JTextField[] playerScore;
   private ImageIcon[] playerIcon;
   private JLabel nextPlayerIcon;

   private Color background = new Color(229, 230, 232);

   private String logoIcon;
   private String[] iconFile;

/**
* Number of players
*/
   public final int NUMPLAYER = 2;

/**
* Number of rows on the game board
*/
   public final int NUMROW = 8;

/**
* Number of colums on the game board
*/
   public final int NUMCOL = 8;

/**
* Number of games needed to be won to win the match
*/
   public int MAXGAME;
	
/**
* Number of games needed to be won to win the match
*/
   public int PLAYER1 = 0;
   public int PLAYER2 = 1;

/**
* Constants defining the demensions of the different components
* on the GUI
*/ 
   private final int PIECESIZE = 70;
   private final int PLAYPANEWIDTH = NUMCOL * PIECESIZE;
   private final int PLAYPANEHEIGHT = NUMROW * PIECESIZE;

   private final int INFOPANEWIDTH = 2 * PIECESIZE;
   private final int INFOPANEHEIGHT = PLAYPANEHEIGHT;

   private final int LOGOHEIGHT = 2 * PIECESIZE;
   private final int LOGOWIDTH = PLAYPANEWIDTH + INFOPANEWIDTH;

   private final int FRAMEWIDTH = (int)(LOGOWIDTH * 1.03);
   private final int FRAMEHEIGHT = (int)((LOGOHEIGHT + PLAYPANEHEIGHT) * 1.1);

// Constructor:  OthelloGUI
// - intialize variables from config files
// - initialize the imageIcon array
// - initialize the slots array
// - create the main frame
   public OthelloGUI () {
      initConfig();
      initImageIcon();
      initSlots();
      createMainFrame();
   }

   private void initConfig() {
   /* TO DO:  initialize the following variables with information read from the config file
   *         - MAXGAME
   *         - logoIcon
   *         - iconFile
      */
      //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
      try {
         BufferedReader in = new BufferedReader(new FileReader(CONFIGFILE));
         MAXGAME = Integer.parseInt(in.readLine());
         logoIcon = in.readLine();
      	
         iconFile = new String[] {in.readLine(), in.readLine()};
         in.close();
      } 
      catch (IOException iox) {
         Throwable th = new Throwable();
         System.out.println(th.getMessage());
         System.out.println("Error reading file.");
      }
   }

// initImageIcon
// Initialize playerIcon arrays with graphic files
   private void initImageIcon() {
      playerIcon = new ImageIcon[NUMPLAYER];
      for (int i = 0; i < NUMPLAYER; i++) {
         playerIcon[i] = new ImageIcon(iconFile[i]);
      }
   }

// initSlots
// initialize the array of JLabels
   private void initSlots() {
      slots = new JLabel[NUMROW][NUMCOL];
      for (int i = 0; i < NUMROW; i++) {
         for (int j = 0; j < NUMCOL; j++) {
            slots [i] [j] = new JLabel ();
         // slots[i][j].setFont(new Font("SansSerif", Font.BOLD, 18));
            slots[i][j].setPreferredSize(new Dimension(PIECESIZE, PIECESIZE));
            slots [i] [j].setHorizontalAlignment (SwingConstants.CENTER);
            slots [i] [j].setBorder (new LineBorder (Color.white));        
         }
      }
   }

// createPlayPanel
   private JPanel createPlayPanel() {
      JPanel panel = new JPanel(); 
      panel.setPreferredSize(new Dimension(PLAYPANEWIDTH, PLAYPANEHEIGHT));
      panel.setBackground(background);
      panel.setLayout(new GridLayout(NUMROW, NUMCOL));
      for (int i = 0; i < NUMROW; i++) {
         for (int j = 0; j < NUMCOL; j++) {
            panel.add(slots[i][j]);
         }
      }
      return panel;    
   }

// createInfoPanel
   private JPanel createInfoPanel() {
   
      JPanel panel = new JPanel();
      panel.setPreferredSize(new Dimension(INFOPANEWIDTH, INFOPANEHEIGHT));
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      panel.setBackground (background);
   
      Font headingFont = new Font ("Calibri", Font.BOLD, 20);
      Font regularFont = new Font ("Serif", Font.BOLD, 16);
   
   // Create a panel for the scoreboard
      JPanel scorePanel = new JPanel();
      scorePanel.setBackground(background);
   
   // Create the label to display "SCOREBOARD" heading
      JLabel scoreLabel = new JLabel ("SCOREBOARD", JLabel.CENTER);
      scoreLabel.setFont(headingFont);
      scoreLabel.setAlignmentX (Component.CENTER_ALIGNMENT);
   //scoreLabel.setForeground(Color.white);
   
   // Create JLabels for players
      JLabel[] playerLabel = new JLabel[NUMPLAYER];
      for (int i = 0; i < NUMPLAYER; i++) {
         playerLabel[i] = new JLabel(playerIcon[i]);
      }
   
   // Create the array of textfield for players' score
      playerScore = new JTextField[NUMPLAYER];
      for (int i = 0; i < NUMPLAYER; i++) {
         playerScore[i] = new JTextField();
         playerScore[i].setFont(regularFont);
         playerScore[i].setText("0");
         playerScore[i].setEditable(false);
         playerScore[i].setHorizontalAlignment (JTextField.CENTER);
         playerScore[i].setPreferredSize (new Dimension (INFOPANEWIDTH - PIECESIZE - 10, 30));
         playerScore[i].setBackground(background);
      }
   
      scorePanel.add(scoreLabel);
      for (int i = 0; i < NUMPLAYER; i++) {
         scorePanel.add(playerLabel[i]);
         scorePanel.add(playerScore[i]);
      }
     
      JPanel nextPanel = new JPanel();
      nextPanel.setBackground(background);
   
   // Create the label to display "NEXT TURN" heading
      JLabel nextLabel = new JLabel ("NEXT TURN", JLabel.CENTER);
      nextLabel.setFont(headingFont);
      nextLabel.setAlignmentX (Component.CENTER_ALIGNMENT);
   //nextLabel.setForeground(Color.white);
   
   // Create the JLabel for the nextPlayer
      nextPlayerIcon = new JLabel();
      System.out.println(nextPlayerIcon.getAlignmentX());
      nextPlayerIcon.setAlignmentX(JLabel.CENTER_ALIGNMENT);
      nextPlayerIcon.setIcon(playerIcon[0]);
   
      nextPanel.add(nextLabel);
      nextPanel.add(nextPlayerIcon);
   
      panel.add(scorePanel);
      panel.add(nextPanel);
   
      return panel;
   }

// createMainFrame
   private void createMainFrame() {
   
   // Create the main Frame
      mainFrame = new JFrame ("Othello");
      JPanel panel = (JPanel)mainFrame.getContentPane();
      panel.setLayout (new BoxLayout(panel,BoxLayout.Y_AXIS));
   
   // Create the panel for the logo
      JPanel logoPane = new JPanel();
      logoPane.setPreferredSize(new Dimension (LOGOWIDTH, LOGOHEIGHT));
      JLabel logo = new JLabel();
      logo.setIcon(new ImageIcon(logoIcon));
      logoPane.add(logo);
   
   // Create the bottom Panel which contains the play panel and info Panel
      JPanel bottomPane = new JPanel();
      bottomPane.setLayout(new BoxLayout(bottomPane,BoxLayout.X_AXIS));
      bottomPane.setPreferredSize(new Dimension(PLAYPANEWIDTH + INFOPANEWIDTH, PLAYPANEHEIGHT));
      bottomPane.add(createPlayPanel());
      bottomPane.add(createInfoPanel());
   
   // Add the logo and bottom panel to the main frame
      panel.add(logoPane);
      panel.add(bottomPane);
   
      mainFrame.setContentPane(panel);
   //   mainFrame.setPreferredSize(new Dimension(FRAMEWIDTH, FRAMEHEIGHT));
      mainFrame.setSize(FRAMEWIDTH, FRAMEHEIGHT);
      mainFrame.setVisible(true);
   }

/**
* Returns the column number of where the given JLabel is on
* 
* @param  label the label whose column number to be requested
* @return the column number
*/
   public int getRow(JLabel label) {
      int result = -1;
      for (int i = 0; i < NUMROW && result == -1; i++) {
         for (int j = 0; j < NUMCOL && result == -1; j++) {
            if (slots[i][j] == label) {
               result = i;
            }
         }
      }
      return result;
   }

/**
* Returns the column number of where the given JLabel is on
* 
* @param  label the label whose column number to be requested
* @return the column number
*/
   public int getColumn(JLabel label) {
      int result = -1;
      for (int i = 0; i < NUMROW && result == -1; i++) {
         for (int j = 0; j < NUMCOL && result == -1; j++) {
            if (slots[i][j] == label) {
               result = j;
            }
         }
      }
      return result;
   }

   public void addListener (OthelloListener listener) {
      for (int i = 0; i < NUMROW; i++) {
         for (int j = 0; j < NUMCOL; j++) {
            slots[i][j].addMouseListener (listener);
         }
      }
   }

/**
* Display the specified player icon on the specified slot
* 
* @param row row of the slot
* @param col column of the slot
* @param player player to be displayed
*/
   public void setPiece(int row, int col, int player) {
      slots[row][col].setIcon(playerIcon[player]);
   }

/**
* Display the score on the textfield of the corresponding player
* 
* @param player the player whose score to be displayed
* @param score the score to be displayed
*/
   public void setPlayerScore(int player, int score) {
      playerScore[player].setText(score+"");
   }

/**
* Display the appropriate player icon under"Next Turn"
* 
* @param player the player number of the next player; its corresponding icon will be displayed under "Next Turn"
*/
   public void setNextPlayer(int player) {
      nextPlayerIcon.setIcon(playerIcon[player]);
   }

/**
* Reset the game board (clear all the pieces on the game board)
* 
*/
   public void resetGameBoard() {
      for (int i = 0; i < NUMROW; i++) {
         for (int j = 0; j < NUMCOL; j++) {
            slots[i][j].setIcon(null);
         }
      }
   }

/**
* Display a pop up window displaying the message about invalid move
* 
*/
   public void showInvalidMoveMessage(){
      JOptionPane.showMessageDialog(null, " This move is invalid", "Invalid Move", JOptionPane.PLAIN_MESSAGE, null); 
   }

/**
* Display a pop up window specifying the number of opponents that was outflanked
* 
* @param player the player number who has outflanked opponents
* @param outflank the number of opponents that were outflanked
*/
   public void showOutflankMessage(int player, int outflank){
      JOptionPane.showMessageDialog(null, " outflanked " + outflank + " opponent(s).", "OutFlank!", JOptionPane.PLAIN_MESSAGE, playerIcon[player]); 
   }
   
/**
* Display a pop up window displaying the message about a tie game
* 
*/
   public void showTieGameMessage(){
      JOptionPane.showMessageDialog(null, " This game is tie.", "Tie Game", JOptionPane.PLAIN_MESSAGE, null); 
   }

/**
* Display a pop up window specifying the winner of this game
* 
* @param player the player number of the winner of the game
*/
   public void showWinnerMessage(int player){
      JOptionPane.showMessageDialog(null, " won this game!", "This game has a winner!", JOptionPane.PLAIN_MESSAGE, playerIcon[player]); 
   }

/**
* Display a pop up window specifying the winner of the match
* 
* @param player the player number of the winner of the match
*/
   public void showFinalWinnerMessage(int player){
      JOptionPane.showMessageDialog(null, " won the match with " + MAXGAME + " wins", "The match is finished", JOptionPane.PLAIN_MESSAGE, playerIcon[player]); 
      System.exit (0);
   }
   
   //i don't know if this is going to work but let's try it!
   public int setGameMode() {
      String[] gameModes = {"Single Player", "Two Players"};
      int userChoice;
      
      userChoice = JOptionPane.showOptionDialog(mainFrame, // parent frame
         "Please select a game mode:", // message in box
         "Set Game Mode", // title of box
         JOptionPane.YES_NO_OPTION, // 
         JOptionPane.INFORMATION_MESSAGE, // icon type
         null,     // no custom icon
         gameModes,  // array containing the options on the buttons
         gameModes[0]); // default selection is "Single Player"
      return userChoice;
   }
	
   public static void main (String[] args) {
      OthelloGUI gui = new OthelloGUI ();
      Othello game = new Othello (gui);
      OthelloListener listener = new OthelloListener (game, gui);
   	
   }

}