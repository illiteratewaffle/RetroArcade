package client_main.java.GameLogic_Client.Connect4;

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
        C4Board c4Board = C4GameLogic.getC4Board();
        return checkC4WinCondition(c4Board, piece, row, col);
    }

    /**
     * Checks if the given position meets a win condition.
     * @param piece the piece placed.
     * @param row the row of the last move.
     * @param col the column of the last move.
     * @return true if a win condition is met, false otherwise.
     */
    private static boolean checkC4WinCondition(C4Board c4Board, C4Piece piece, int row, int col) {
        return checkC4Horizontal(c4Board, piece, row) ||
                checkC4Vertical(c4Board, piece, col) ||
                checkC4ForwardSlash(c4Board, piece, row, col) ||
                checkC4BackwardSlash(c4Board, piece, row, col);
    }

    /**
     * Checks for a horizontal win condition.
     * @param c4Board the connect-4 board being checked.
     * @param piece the piece placed.
     * @param row the row of the last move.
     * @return true if a horizontal win is detected, false otherwise.
     */
    private static boolean checkC4Horizontal(C4Board c4Board, C4Piece piece, int row) {

        return false;
    }

    /**
     * Checks for a vertical win condition.
     * @param c4Board the connect-4 board being checked.
     * @param piece the piece placed.
     * @param col the column of the last move.
     * @return true if a vertical win is detected, false otherwise.
     */
    private static boolean checkC4Vertical(C4Board c4Board, C4Piece piece, int col) {

        return false;
    }

    /**
     * Checks for a forward slash win condition.
     * @param c4Board the connect-4 board being checked.
     * @param piece the piece placed.
     * @param row the row of the last move.
     * @param col the column of the last move.
     * @return true if a forward slash win is detected, false otherwise.
     */
    private static boolean checkC4ForwardSlash(C4Board c4Board, C4Piece piece, int row, int col) {

        return false;
    }

    /**
     * Checks for a backward slash win condition.
     * @param c4Board the connect-4 board being checked.
     * @param piece the piece placed.
     * @param row the row of the last move.
     * @param col the column of the last move.
     * @return true if a backward slash is detected, false otherwise.
     */
    private static boolean checkC4BackwardSlash(C4Board c4Board, C4Piece piece, int row, int col) {
        int count = 0;
        C4Piece[][] board = c4Board.getC4Board();
        int rows = board.length;
        int cols = board[0].length;

        for (int i = -3; i <= 3; i++) {
            int r = row + i;
            int c = col + i;

            if (r >= 0 && r < rows && c >= 0 && c < cols) {
                if (board[r][c] == piece) {
                    count++;
                    if (count == 4) return true;
                } else {
                    count = 0;
                }
            }
        }
        return false;
    }
}
