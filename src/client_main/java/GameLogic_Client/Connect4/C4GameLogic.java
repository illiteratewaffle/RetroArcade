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
}
