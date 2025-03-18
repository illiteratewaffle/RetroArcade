package GameLogic.Connect4;

public class C4GameLogic {

    private final C4Board c4Board;
    private int c4PiecesPlayed;
    private boolean isC4GameOver;
    private int[] C4lastPlayedPosition = new int[2];

    /**
     * Constructs a new Connect Four game logic instance.
     */
    public C4GameLogic() {
        this.c4Board = new C4Board(6,7);
        System.out.println("A new connect four board has been created");
    }

    /**
     * Check if the game is over.
     * @return true if the game is over, false otherwise.
     */
    public boolean getC4IsGameOver() {
        return false;
    }

    /**
     * Get the current player.
     * @return the current player's piece.
     */
    public C4Piece getC4CurrentPlayer() {
        return C4Piece.BLANK;
    }

    /**
     * Get the topmost blank row in a given column.
     * @return the row index of the topmost blank space, or -1 if it's full.
     */
    public int getC4ColTopBlank() {
        return -1;
    }

    /**
     * Get the winner of the game.
     * @return the winning piece, or BLANK if no winner.
     */
    public C4Piece getC4Winner() {
        return C4Piece.BLANK;
    }

    /**
     * Get the last played position.
     * @return an array containing the row and column of the last move.
     */
    public int[] getC4lastPlayedPosition() {
        return C4lastPlayedPosition;
    }

    /**
     * Drops a piece into the specified column.
     * @param col the column to drop the piece into.
     * @param piece the piece to drop.
     * @return true if the piece was successfully placed, false otherwise.
     */
    public boolean c4DropPiece(int col, C4Piece piece) {
        return false;
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
        return false;
    }

    /**
     * Places a piece at the specified row and column.
     * @param row the row to place the piece.
     * @param col the column to place the piece.
     * @param piece the piece to be placed.
     */
    private void c4PlacePiece(int row, int col, C4Piece piece) {
    }

    /**
     * @return the string representation of the board.
     */
    @Override
    public String toString() {
        return c4Board.toString();
    }
}