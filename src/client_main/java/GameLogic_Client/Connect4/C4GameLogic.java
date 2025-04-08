package GameLogic_Client.Connect4;

import GameLogic_Client.GameState;
import GameLogic_Client.Ivec2;

public class C4GameLogic {

    private final C4Board c4Board;
    private int c4PiecesPlayed;
    private boolean isC4GameOver;
    private int[] C4lastPlayedPosition = new int[2];
    private C4Piece currentPlayer = C4Piece.RED; //red goes first

    private C4Piece c4winner = C4Piece.BLANK;

    public GameState gameState;

    /**
     * Constructs a new Connect Four game logic instance.
     */
    public C4GameLogic() {
        this.c4Board = new C4Board(6,7);
        gameState = GameState.ONGOING;
        System.out.println("A new connect four board has been created");
    }

    /**
     * Updates the game state based on the current board.
     */
    public void updateGameState() {
        for (int j = 0; j < 7; j++) {
            if (c4winner == C4Piece.RED) {
                gameState = GameState.P1WIN;
            } else if (c4winner == C4Piece.BLUE) {
                gameState = GameState.P2WIN;
            } else if (isC4ColFull(j)) {
                gameState = GameState.TIE;
            } else {
                gameState = GameState.ONGOING;
            }
        }
    }

    /**
     * Check if the game is over.
     * @return true if the game is over, false otherwise.
     */
    public boolean getC4IsGameOver() {
        return isC4GameOver;
    }

    /**
     * Get the current player.
     * @return the current player's piece.
     */
    public C4Piece getC4CurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Get the topmost blank row in a given column.
     * @return the row index of the topmost blank space, or -1 if it's full.
     */
    public int getC4ColTopBlank(int col) {
        C4Piece[][] board = c4Board.getC4Board();

        for (int row = board.length-1; row >= 0; row--) {
            if (board[row][col] == C4Piece.BLANK) {
                return row;
            }
        }
        return -1;
    }

    /**
     * Get the winner of the game.
     * @return the winning piece, or BLANK if no winner.
     */
    public C4Piece getC4Winner() {
        return c4winner;
    }

    /**
     * Get the last played position.
     * @return an array containing the row and column of the last move.
     */
    public int[] getC4lastPlayedPosition() {
        return C4lastPlayedPosition;
    }

    public int getc4PiecesPlayed() {
        return c4PiecesPlayed;
    }

    /**
     * Drops a piece into the specified column.
     * @param col the column to drop the piece into.
     * @param piece the piece to drop.
     * @return true if the piece was successfully placed, false otherwise.
     */
    public boolean c4DropPiece(int col, C4Piece piece) {
        if (isC4ColFull(col)) {
            System.out.println(String.format("Column[%d] is full!", col));
            return false;
        } else {
            int row = getC4ColTopBlank(col);

            // place piece
            c4PlacePiece(row, col, piece);

            // save last played coordinate
            C4lastPlayedPosition[0] = row;
            C4lastPlayedPosition[1] = col;

            // increment piece counter
            c4PiecesPlayed += 1;

            Ivec2 lastPlayed = new Ivec2(col, row);

            // check for win
            if (C4WinCheckerO1.isC4Win(lastPlayed, piece, c4Board.getC4Board())) {
                isC4GameOver = true;
                c4winner = piece;
                System.out.println(c4winner + " has won!");
            } else if (c4PiecesPlayed == c4Board.getC4Board().length * c4Board.getC4Board()[0].length) {
                isC4GameOver = true;
                c4winner = C4Piece.BLANK;
                System.out.println("Game ended in a draw!");
            } else {
                // Swap player turn
                if (currentPlayer == C4Piece.RED) {
                    currentPlayer = C4Piece.BLUE;
                }
                else if (currentPlayer == C4Piece.BLUE) {
                    currentPlayer = C4Piece.RED;
                }
            }
            return true;
        }
    }

    /**
     * Get the total number of pieces played in the game.
     * @return the number of pieces played.
     */
    public int getC4TotalPiecesPlayed() {
        return 0;
    }

    /**
     * Checks if a column is full.
     * @param col the column to check.
     * @return true if the column is full, false otherwise.
     */
    private boolean isC4ColFull(int col) {
        C4Piece[][] board = c4Board.getC4Board();
        return board[0][col] != C4Piece.BLANK; // checks if the top row is BLANK (empty)
    }

    /**
     * Places a piece at the specified row and column.
     * @param row the row to place the piece.
     * @param col the column to place the piece.
     * @param piece the piece to be placed.
     */
    private void c4PlacePiece(int row, int col, C4Piece piece) {
        Ivec2 point = new Ivec2(col, row);
        c4Board.setC4Piece(point, piece);
    }

    /**
     * Returns the 2D array of the board. Required for the controller class to view the board.
     */
    public C4Board getC4Board() {
        return c4Board;
    }

    /**
     * @return the string representation of the board.
     */
    @Override
    public String toString() {
        return c4Board.toString();
    }

    /**
     * Makes a copy of the current board. Used for the hint method
     * @return a copy of a 2D array of the board.
     */
    public C4Piece[][] copyC4Board() {
        C4Piece[][] original = this.getC4Board().getC4Board();
        C4Piece[][] copy = new C4Piece[original.length][original[0].length];

        for (int row = 0; row < original.length; row++) {
            System.arraycopy(original[row], 0, copy[row], 0, original[0].length);
        }
        return copy;
    }

    /**
     * Suggests a column for the current player to play based on immediate win or opportunity to block opponent from winning.
     * @return a HintResult object containing the column index of a winning or blocking move if available, or the first non-full column.
     */
    public HintResult getC4HintColumn () {
        System.out.println("Hint requested");
        C4Piece[][] simulatedBoard = copyC4Board();
        //int colHinted = -1;

        //test for win condition
        for (int col = 0; col < simulatedBoard[0].length; col++) {
            int row = getC4ColTopBlank(col);
            if (row == -1) continue;

            //simulate move
            simulatedBoard[row][col] = currentPlayer;

            if (C4WinCheckerO1.isC4Win(new ivec2(col, row), currentPlayer, simulatedBoard)) {
                simulatedBoard[row][col] = C4Piece.BLANK;
                //colHinted = col;
                System.out.println("Place piece in " + col+1 + " to win");
                return new HintResult(col, "WIN");
            }

            //undo simulated move
            simulatedBoard[row][col] = C4Piece.BLANK;
        }

        C4Piece opponent = currentPlayer == C4Piece.RED ? C4Piece.BLUE : C4Piece.RED;

        for (int col = 0; col < simulatedBoard[0].length; col++) {
            int row = getC4ColTopBlank(col);
            if (row == -1) continue;

            simulatedBoard[row][col] = opponent;

            if (C4WinCheckerO1.isC4Win(new ivec2(col, row), opponent, simulatedBoard)) {
                simulatedBoard[row][col] = C4Piece.BLANK;
                return new HintResult(col, "BLOCK");
            }

            simulatedBoard[row][col] = C4Piece.BLANK;
        }

        System.out.println("No winning or blocking move detected.");
        return new HintResult(-1, "NONE");
    }
}
