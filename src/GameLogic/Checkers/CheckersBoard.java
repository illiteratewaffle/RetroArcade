package GameLogic.Checkers;
import GameLogic.AbstractBoard;

/**
 * CheckersBoard state machine implementation
 *
 * Author: Ryan Hashemian ~ Game Logic
 */
public class CheckersBoard extends AbstractBoard {

    /**
     * Constructor to initialize the Checkers board.
     * @param rows the number of rows in the board
     * @param columns the number of columns in the board
     * @param board the 2D integer array representing the board
     */
    public CheckersBoard(int rows, int columns, int[][] board) {
        super(rows, columns, board); // refer to parent class
    }

    /**
     * Sets a piece on the board.
     * @param row the row index
     * @param column the column index
     * @param piece the piece to set
     */
    @Override
    public void setPiece(int row, int column, int piece) {
        int[][] board = this.getBoard();
        board[row][column] = piece;
    }

    /**
     * Gets the piece at the specified position.
     * @param row the row index
     * @param column the column index
     * @return the piece flag at the given location
     */
    @Override
    public int getPiece(int row, int column) {
        int[][] board = this.getBoard();
        int piece = board[row][column];

        return CheckersPiece.P1PAWN.ordinal(); //
    }

    /**
     * Returns the current board state.
     * @return the 2D board array
     */
    @Override
    public int[][] getBoard() {
        return new int[7][7];
    }

    /**
     * Makes a move based on player input
     * @param move
     */
    public void makeMove(CheckersMove move) {
        // for GUI and networking???
    }
}
