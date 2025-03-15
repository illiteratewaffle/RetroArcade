package gamelogic;

/**
 * AbstractBoard class to be implemented by other GameBoard classes with standard functions that all boards should be able to do.
 *
 * Author: Vicente David - Game Logic Team
 */
public abstract class AbstractBoard {
    private int rows;
    private int cols;
    private int[][] board;

    /**
     * Standard constructor to initialize AbstractBoard class.
     * @param rows the total number of rows in the board
     * @param cols the total number of columns in the board
     * @param board the 2d int array representing the board and its pieces.
     */
    public AbstractBoard(int rows, int cols, int[][] board) {
        this.rows = rows;
        this.cols = cols;
        this.board = board;
    }

    /**
     * setPiece function to set a spot on the board to a user-defined piece.
     * @param row the row which the piece should be set at.
     * @param col the column which the piece should be set at.
     * @param piece the type of piece that should be set at the location.
     */
    public void setPiece(int row, int col, int piece) {
        board[row][col] = piece;
    }

    /**
     * returns the current piece at the designated row/column location.
     * @param row the row of the current piece to be returned.
     * @param col the column of the current piece to be returned.
     * @return piece at board[row][column]
     */
    public int getPiece(int row, int col) {
        return board[row][col];
    }

    /**
     *returns the current board as a 2d int array.
     * @return board
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * initializes a completely blank board.
     */
    private void initBlankBoard() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board[row][col] = 0;
            }
        }
    }
}