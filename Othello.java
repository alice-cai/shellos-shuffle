/**
* Othello.java
*
* This class represents an Othello (TM) game, which allows two players to
* place pieces onto a board. Each move can result in outflanking 0 or more
* of the opponent's pieces.
*/

    public class Othello {
   
      // constants
      final int MAXGAME;            // the number of games a player needs to win the match  
      final int NUMPLAYER;          // number of players in the game
      final int NUMROW;             // number of rows in the game board
      final int NUMCOL;             // number of columns in the game board
      final int NUM_INITIAL = 4;    // number of inital pieces on game board
      final int EMPTY = -1;         // represents an empty square on the game board 	
      final int PLAYER1 = 0;        // identification of player 1
      final int PLAYER2 = 1;        // identification of player 2 
      final int NO_WINNER = -1;
   
      OthelloGUI gui;
      int numMoves;
      int curPlayer;
      int board[][];
      int score[];
      int numOutflanked;
      boolean gameOver;
      int countPlayer1;
      int countPlayer2;
      int winner;
      boolean singlePlayer;
      boolean noOutflank;
      
      /**
      * Constructor:  Othello
      */
      public Othello(OthelloGUI gui) {
         this.gui = gui;
         NUMPLAYER = gui.NUMPLAYER;
         NUMROW = gui.NUMROW;
         NUMCOL = gui.NUMCOL;
         MAXGAME = gui.MAXGAME;
      
         board = new int[NUMROW][NUMCOL];
         score = new int[] {0, 0};
         gameOver = false;
         curPlayer = PLAYER1;
         countPlayer1 = countPlayer2 = 0;
         numMoves = 0;
         noOutflank = false;
      
         initBoard();
      
         int gameMode = gui.setGameMode();
         while(gameMode == -1) {
            gameMode = gui.setGameMode();
         }
         if(gameMode == 0) {
            singlePlayer = true;
         } 
         else {
            singlePlayer = false;
         }
      }
   
      /**
      * This method initializes the four pieces in the centre of the game board and
      * sets the rest of the board as empty.
      */
      void initBoard() {
         for(int i = 0; i < NUMROW; i++) {
            for(int j = 0; j < NUMCOL; j++) {
               board[i][j] = EMPTY;
            }
         }
         
         board[NUMROW/2-1][NUMCOL/2-1] = PLAYER2;
         board[NUMROW/2-1][NUMCOL/2] = PLAYER1;
         board[NUMROW/2][NUMCOL/2-1] = PLAYER1;
         board[NUMROW/2][NUMCOL/2] = PLAYER2;
      
         gui.setPiece(NUMROW/2-1, NUMCOL/2-1, PLAYER2);
         gui.setPiece(NUMROW/2-1, NUMCOL/2, PLAYER1);
         gui.setPiece(NUMROW/2, NUMCOL/2-1, PLAYER1);
         gui.setPiece(NUMROW/2, NUMCOL/2, PLAYER2);
      }
      
      /**
      * Method validMove returns true if the player can place their piece in the
      * specified square and false otherwise.
      */
      boolean validMove(int row, int col) {
         boolean nextToPiece = false;
      
         switch(board[row][col]) {
            // If the specified square is empty, check to see if it is
            // adjacent to an existing piece.
            case EMPTY:
               // Checks checks all squares adjacent to the selected square to
               // see if they are empty. If they are all empty, return false.
               for(int rowShift = -1; rowShift <= 1 && !nextToPiece; rowShift++) {
                  for(int colShift = -1; colShift <= 1 && !nextToPiece; colShift++) {
                     // If the square being checked is within bounds, check if it contains
                     // a piece. If so, nextToPiece = true.
                     if(row+rowShift >= 0 && row+rowShift < board.length && col+colShift >= 0 && col+colShift < board[0].length) {
                        if(board[row+rowShift][col+colShift] != EMPTY) {
                           nextToPiece = true;
                        }
                     }
                  }
               }
               return nextToPiece;
            // If the specified square is not empty, return false (user
            // cannot place piece on occupied square).
            default:
               return false;
         }
      }
   
      /**
      * This method checks for HORIZONTAL outflank.
      * It first checks the squares to the left of the specified piece until it reaches an
      * empty square or one of the player's own pieces. As soon as one of the player's own
      * pieces is found, the program flips all the pieces between that piece and the specified
      * piece. As soon as an empty square is found, the program stops checking (no outflank is
      * possible in this case).
      * This is repeated for the squares to the right of the specified piece.
      */
      int outflankHori(int row, int col, int player) {
         int numOutflank = 0;
         boolean outflankPossible = true;
      	
         // Checks for outflank to the left of the specified piece.
         for(int i = (col-1); i >= 0 && outflankPossible; i--) {
            if(board[row][i] == player) {            	
               for(int j = (i+1); j < col; j++) {
                  if (!noOutflank) {
                     board[row][j] = player;
                     gui.setPiece(row, j, player);
                  }
                  numOutflank++;
               }
               outflankPossible = false;
            } 
            else if (board[row][i] == EMPTY) {
               outflankPossible = false;
            }
         }
      
         // Reset boolean variable.
         outflankPossible = true;
      	
         // Checks for outflank to the right of the specified piece.
         for(int i = (col+1); i < board[row].length && outflankPossible; i++) {
            if(board[row][i] == player) {
               for(int j = (i-1); j > col; j--) {
                  if (!noOutflank) {
                     board[row][j] = player;
                     gui.setPiece(row, j, player);
                  }
                  numOutflank++;
               }
               outflankPossible = false;
            } 
            else if (board[row][i] == EMPTY) {
               outflankPossible = false;
            }
         }
      
         return numOutflank;
      }
   
      /**
      * This method checks for VERTICAL outflank.
      * It first checks the squares above the specified piece until it reaches an empty square
      * or one of the player's own pieces. As soon as one of the player's own pieces is found,
      * the program flips all the pieces between that piece and the specified piece. As soon as
      * an empty square is found, the program stops checking (no outflank is possible in this
      * case).
      * This is repeated for the squares below the specified piece.
      */
      int outflankVert(int row, int col, int player) {
         int numOutflank = 0;
         boolean outflankPossible = true;
         
         // Checks for outflank above the specified piece.
         for(int i = (row-1); i >= 0 && outflankPossible; i--) {
            if (board[i][col] == player) {
               for (int j = (i+1); j < row; j++) {
                  if (!noOutflank) {
                     board[j][col] = player;
                     gui.setPiece(j, col, player);
                  }
                  numOutflank++;
               }
               outflankPossible = false;
            } 
            else if (board[i][col] == EMPTY) {
               outflankPossible = false;
            }
         }
      
         // Reset boolean variable.
         outflankPossible = true;
      
         // Checks for outflank below the specified piece.
         for(int i = (row+1); i < board.length && outflankPossible; i++) {
            if (board[i][col] == player) {
               outflankPossible = false;
            
               for(int j = (i-1); j > row; j--) {
                  if(!noOutflank) {
                     board[j][col] = player;
                     gui.setPiece(j, col, player);
                  }
                  numOutflank++;
               }
            } 
            else if (board[i][col] == EMPTY) {
               outflankPossible = false;
            }
         }
         return numOutflank;
      }
		
      /**
      * This method checks for DIAGONAL outflank.
      */
      int outflankDiag(int row, int col, int player) {
         int numOutflank = 0;
         boolean exit = false;
         int rowShift, colShift;
         int count = 0;
      
         rowShift = row;
         colShift = col;
      
         for(int shiftVert = -1; shiftVert <= 1; shiftVert += 2) {
            for(int shiftHori = -1; shiftHori <= 1; shiftHori += 2) {
               while(!exit) {
                  rowShift += shiftVert;
                  colShift += shiftHori;
                  count++; // counts the amount of shifts
               
                  // If the array indexes are within bounds, continue to check for outflank. If not, exit the loop.
                  if(rowShift >= 0 && rowShift < board.length && colShift >= 0 && colShift < board[0].length) {
                  
                     // If the current index belongs to one of the player's pieces, flip all pieces in between
                     // that piece and the piece placed this turn. Exit the loop.
                     if(board[rowShift][colShift] == player) {						
                        for(int i = 1; i < count; i++) {
                           if(!noOutflank) {
                              board[rowShift - (i * shiftVert)][colShift - (i * shiftHori)] = player;
                              gui.setPiece(rowShift - (i * shiftVert), colShift - (i * shiftHori), player);
                           }
                           numOutflank++;
                        }
                        exit = true;
                     }
                     // If current index belongs to an empty square, exit the loop.
                     else if(board[rowShift][colShift] == EMPTY) {
                        exit = true;
                     }
                  } 
                  else {
                     exit = true;
                  }
               }
            
               // Reset variables.
               count = 0;
               exit = false;
               rowShift = row;
               colShift = col;
            }
         }
      
         return numOutflank;
      }
   
      /**
      * This method compares the players' scores and returns the array
      * index of the winner.
      */
      int checkWinner() {
         if (score[PLAYER1] == MAXGAME) {
            return PLAYER1;
         } 
         else if (score[PLAYER2] == MAXGAME) {
            return PLAYER2;
         }
         return NO_WINNER;
      }
   
      /**
      * This method traverses the game board starting at the top left
      * corner and chooses the move that results in the most outflanks
      * for that turn. If two moves result in the same number of outflanks,
      * the method chooses the first move that it encounters.
      */
      void chooseBestMove() {
         int highestOutflank = -1;
         int rowIdx = -1, colIdx = -1;
         int numOutflank = 0;
      	
         // When true, outflank methods will count the number of outflanks
         // but will not implement the outflanks.
         noOutflank = true;
      	
         for(int row = 0; row < board.length; row++) {
            for(int col = 0; col < board[row].length; col++) {
               if(validMove(row, col)){
                  numOutflank += outflankHori(row, col, PLAYER2);
                  numOutflank += outflankVert(row, col, PLAYER2);
                  numOutflank += outflankDiag(row, col, PLAYER2);
               	
                  if(numOutflank > highestOutflank) {
                     highestOutflank = numOutflank;
                     rowIdx = row;
                     colIdx = col;
                  }
               
                  numOutflank = 0;
               }
            }
         }
      	
         noOutflank = false;
         
         // Makes the move that results in the most outflanks.
         board[rowIdx][colIdx] = PLAYER2;
         gui.setPiece(rowIdx, colIdx, PLAYER2);
         outflankHori(rowIdx, colIdx, PLAYER2);
         outflankVert(rowIdx, colIdx, PLAYER2);
         outflankDiag(rowIdx, colIdx, PLAYER2);
      }
   
      /**
      * play
      * This method will be called when a square is clicked. Parameter "row" and "column" is 
      * the location of the square that is clicked by the user
      */
      public void play (int row, int column) {
         if(validMove(row, column)) {
            numMoves++;
            numOutflanked = 0;

            // sets the specified piece on the board
            board[row][column] = curPlayer;
            gui.setPiece(row, column, curPlayer);
         	
            // calculates and displays the number of pieces outflanked
            // by the current move
            numOutflanked += outflankHori(row, column, curPlayer);
            numOutflanked += outflankVert(row, column, curPlayer);
            numOutflanked += outflankDiag(row, column, curPlayer);
            gui.showOutflankMessage(curPlayer, numOutflanked);
         
            // switch current player
            switch(curPlayer) {
               case PLAYER1:
                  curPlayer = PLAYER2;
                  gui.setNextPlayer(PLAYER2);
                  break;
               case PLAYER2:
                  curPlayer = PLAYER1;
                  gui.setNextPlayer(PLAYER1);
                  break;
            }
         	
            // If the single player option is chosen and the current player
            // is PLAYER2, make the move that results in the most outflanks
            // and switch the current player to PLAYER1.
            if(singlePlayer && curPlayer == PLAYER2) {
               numMoves++;
               chooseBestMove();
               curPlayer = PLAYER1;
               gui.setNextPlayer(PLAYER1);
            }
         	
            if(numMoves == NUMCOL * NUMROW - NUM_INITIAL) { // game must end after 60 valid moves
               countPlayer1 = countPlayer2 = 0;
					
               // counts the number of pieces that each player
               // has on the board
               for (int i = 0; i < board.length; i++) {
                    for(int j = 0; j < board[i].length; j++) {
                         switch(board[i][j]) {
                            case PLAYER1:
                               countPlayer1++;
                               break;
                            default:
                               countPlayer2++;
                               break;
                         }
                    }
               }
			   	
               // Compares the piece count of each player and updates the
               // score accordingly. Displays a window telling the players
               // who won the game.
               if(countPlayer1 == countPlayer2){
                  gui.showTieGameMessage();
               }          
               else {
                  if(countPlayer1 > countPlayer2) {
                     winner = PLAYER1;
                  } 
                  else if(countPlayer2 > countPlayer1) {
                     winner = PLAYER2;
                  }
                  score[winner]++;
               	
                  if(checkWinner() != NO_WINNER) {
                     gui.showFinalWinnerMessage(winner);
                  } 
                  else {
                     gui.showWinnerMessage(winner);
                  }
               	
                  // Resets the game board and updates the scoreboard.
                  gui.resetGameBoard();
                  initBoard();
                  gui.setPlayerScore(winner, score[winner]);
               }
               gameOver = false;
               numMoves = 0;

            }
         } 
         else {
            gui.showInvalidMoveMessage();
         }
      } 
   }
