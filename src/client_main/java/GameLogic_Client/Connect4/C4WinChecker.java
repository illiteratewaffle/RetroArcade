package client_main.java.GameLogic_Client.Connect4;

public class C4WinChecker {
    private C4WinChecker() {
    }

    /**
     * Checks if the specified move results in a win.
     *
     * @param row     the row of the last move.
     * @param col     the column of the last move.
     * @param piece   the piece placed.
     * @param c4Board the 2-d representation of the board.
     * @return true if the move results in a win, false otherwise.
     */
    public static boolean isC4Win(int row, int col, C4Piece piece, C4Piece[][] c4Board) {
        return checkC4WinCondition(row, col, piece, c4Board);
    }

    /**
     * Checks if the given position meets a win condition.
     * @param row the row to check.
     * @param col the column to check.
     * @param piece the piece placed.
     * @param c4Board the 2-d representation of the board.
     * @return true if a win condition is met, false otherwise.
     */
    private static boolean checkC4WinCondition(int row, int col, C4Piece piece, C4Piece[][] c4Board) {
        return checkC4Horizontal(row, col, piece, c4Board) || checkC4Vertical(row, col, piece, c4Board) ||
                checkC4ForwardSlash(row, col, piece, c4Board) || checkC4BackwardSlash(row, col, piece, c4Board);
    }

    /**
     * Checks for a horizontal win condition.
     *
     * @param row     the row to check.
     * @param col     the column to check.
     * @param piece   the piece placed.
     * @param c4Board the 2-d representation of the board.
     * @return true if a horizontal win is detected, false otherwise.
     */
    private static boolean checkC4Horizontal(int row, int col, C4Piece piece, C4Piece[][] c4Board) {
        int pieceCounter = 1;

        // Go left of piece placed till the start of board.
        for(int i = col-1; i >= 0; i--){
            if(c4Board[row][i] == piece){
                pieceCounter += 1;              // Increment piece counter if piece found.
            } else {
                pieceCounter = 0;               // Else reset it to just 0, so it only takes consecutive pieces into account for win.
            }
            if(pieceCounter >= 4) {
                return true;                    // Returns true if there is 4 in a row consecutively.
            }
        }

        // Go right of piece placed till the end of the board.
        // Same process as above otherwise.
        for(int i = col+1; i < c4Board[0].length; i++){
            if(c4Board[row][i] == piece){
                pieceCounter += 1;
            } else {
                pieceCounter = 0;
            }
            if(pieceCounter >= 4) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks for a vertical win condition.
     *
     * @param row     the row to check.
     * @param col     the column to check.
     * @param piece   the piece placed.
     * @param c4Board the 2-d representation of the board.
     * @return true if a vertical win is detected, false otherwise.
     */
    private static boolean checkC4Vertical(int row, int col, C4Piece piece, C4Piece[][] c4Board) {
        int pieceCounter = 1;

        // Go down of piece placed till the end of the board.
        // Does not check for pieces above, as the pieces are stacked, so we are currently looking at the top-most piece in a column.
        for(int i = row+1; i < c4Board.length; i++){
            if(c4Board[i][col] == piece){
                pieceCounter += 1;              // Increment piece counter if piece found.
            } else {
                pieceCounter = 0;               // Else reset it to just 0, so it only takes consecutive pieces into account for win.
            }
            if(pieceCounter >= 4) {
                return true;                    // Returns true if there is 4 in a row consecutively.
            }
        }

        return false;
    }

    /**
     * Checks for a forward slash win condition.
     *
     * @param row     the row to check.
     * @param col     the column to check.
     * @param piece   the piece placed.
     * @param c4Board the 2-d representation of the board.
     * @return true if a forward slash diagonal win is detected, false otherwise.
     */
    private static boolean checkC4ForwardSlash(int row, int col, C4Piece piece, C4Piece[][] c4Board) {
        int pieceCounter = 1;

        // Loop to check coordinates to the piece's top-right.
        for (int i = row-1, j = col+1; i >= 0 && j < c4Board[0].length; i--, j++) {
            if(c4Board[i][j] == piece) {
                pieceCounter += 1;
            } else {
                pieceCounter = 0;
            }
            if(pieceCounter >= 4) {
                return true;
            }
        }

        // Loop to check coordinates to the piece's bottom-left.
        for (int i = row+1, j = col-1; i < c4Board.length && j >=0; i++, j--) {
            if(c4Board[i][j] == piece) {
                pieceCounter += 1;
            } else {
                pieceCounter = 0;
            }
            if(pieceCounter >= 4) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks for a backward slash win condition.
     *
     * @param row     the row to check.
     * @param col     the column to check.
     * @param piece   the piece placed.
     * @param c4Board the 2-d representation of the board.
     * @return true if a backward slash diagonal win is detected, false otherwise.
     */
    private static boolean checkC4BackwardSlash(int row, int col, C4Piece piece, C4Piece[][] c4Board) {
        int pieceCounter = 1;

        // Loop to check coordinates to the piece's top-left.
        for (int i = row-1, j = col-1; i >= 0 && j >= 0; i--, j--) {
            if(c4Board[i][j] == piece) {
                pieceCounter += 1;
            } else {
                pieceCounter = 0;
            }
            if(pieceCounter >= 4) {
                return true;
            }
        }

        // Loop to check coordinates to the piece's bottom-right.
        for (int i = row+1, j = col+1; i < c4Board.length && j < c4Board[0].length; i++, j++) {
            if(c4Board[i][j] == piece) {
                pieceCounter += 1;
            } else {
                pieceCounter = 0;
            }
            if(pieceCounter >= 4) {
                return true;
            }
        }

        return false;
    }
}