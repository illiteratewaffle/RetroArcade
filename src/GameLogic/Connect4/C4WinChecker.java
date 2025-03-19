package GameLogic.Connect4;

public class C4WinChecker {

    private C4WinChecker() {
    }

    /**
     * Checks if the specified move results in a win.
     * @param row the row of the last move.
     * @param col the column of the last move.
     * @param piece the piece placed.
     * @return true if the move results in a win, false otherwise.
     */
    public static boolean isC4Win(int row, int col, C4Piece piece) {
        return false;
    }

    /**
     * Checks if the given position meets a win condition.
     * @param row the row to check.
     * @param col the column to check.
     * @param piece the piece placed.
     * @return true if a win condition is met, false otherwise.
     */
    private static boolean checkC4WinCondition(int row, int col, C4Piece piece) {
        return false;
    }

    /**
     * Checks for a horizontal win condition.
     * @param row the row to check.
     * @param col the column to check.
     * @param piece the piece placed.
     * @return true if a horizontal win is detected, false otherwise.
     */
    private static boolean checkC4Horizontal(int row, int col, C4Piece piece) {
        if (piece == C4Piece.BLANK) return false;           // Ignores if it's a blank piece.

        C4Piece[][] board = C4Board.getC4Board();           // Get the board's 2-d array.
        int pieceCount = 0;                                 // Keeps track of the number of the piece we get in a row.

        for (row = 0; row < board.length; row++) {
            for (col = 0; col < board[0].length; col++) {   // Go through all the rows and columns.
                if (board[row][col] == piece) {
                    pieceCount++;                           // Add to pieceCounter if there is a piece (same) one after the other.
                    if (pieceCount >= 4) return true;       // If there are at least 4 pieces in a row, return true.
                } else {
                    pieceCount = 0;                         // Otherwise reset pieceCount to 0.
                }
            }
        }
        return false;
    }

    /**
     * Checks for a vertical win condition.
     * @param row the row to check.
     * @param col the column to check.
     * @param piece the piece placed.
     * @return true if a vertical win is detected, false otherwise.
     */
    private static boolean checkC4Vertical(int row, int col, C4Piece piece) {
        if (piece == C4Piece.BLANK) return false;   // Ignore blank spaces.

        C4Piece[][] board = C4Board.getC4Board();   // Get board state.
        int count = 0;
        col = 0;

        for (int r = row; r < board.length; r++) {  // Go through the rows in the board.
            if (board[r][col] == piece) {           // Check if there is a piece at the specified column.
                count++;                            // If there is, increment the piece counter.
                if (count >= 4) return true;        // If there is more than 4 consecutive pieces in a column, return true.
            } else {
                col += 1;                           // Increment column number
                count = 0;                          // Otherwise reset the counter to 0 because we don't want to add a counter
                                                    // whenever there is a matching piece in a column; it has to be consecutive.
            }
        }

        return false;
    }

    /**
     * Checks for a forward slash win condition.
     * @param row the row to check.
     * @param col the column to check.
     * @param piece the piece placed.
     * @return true if a forward slash diagonal win is detected, false otherwise.
     */
    private static boolean checkC4ForwardSlash(int row, int col, C4Piece piece) {
        return false;
    }

    /**
     * Checks for a backward slash win condition.
     * @param row the row to check.
     * @param col the column to check.
     * @param piece the piece placed.
     * @return true if a backward slash diagonal win is detected, false otherwise.
     */
    private static boolean checkC4BackwardSlash(int row, int col, C4Piece piece) {
        return false;
    }
}