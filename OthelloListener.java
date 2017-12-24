   import javax.swing.*;
   import java.awt.event.*;
   public class OthelloListener implements MouseListener
   {
      private OthelloGUI gui;
      private Othello game;
      public OthelloListener (Othello game, OthelloGUI gui) {
         this.game = game;
         this.gui = gui;
         gui.addListener (this);
      }
   
   
      public void mouseClicked (MouseEvent event) {
         JLabel label = (JLabel) event.getComponent ();
         int row = gui.getRow(label);
         int column = gui.getColumn (label);
         game.play(row, column);   
      }
   
      public void mousePressed (MouseEvent event) {
      }
   
      public void mouseReleased (MouseEvent event) {
      }
   
   
      public void mouseEntered (MouseEvent event) {
      }
   
      public void mouseExited (MouseEvent event) {
      }
   }
