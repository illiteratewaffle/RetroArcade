package GameLogic_Client;
/**
 * AbstractBoard class to be implemented by other GameBoard classes with standard functions that all boards should be able to do.
 *
 * Author: Vicente David - Game Logic Team
 */
public abstract class AbstractBoard {
    private int rows;
    private int cols;
    public int[][] board;

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
     * sets a spot on the board represented by an xy-coordinate to a user-defined piece.
     * x represents the horizontal component (column index) of the location.<br>
     * y represents the vertical component (row index) of the location.
     * @param point the coordinate point which the piece should be set at.
     * @param piece the type of piece that should be set at the location.
     */
    public void setPiece(Ivec2 point, int piece) {
        board[point.y][point.x] = piece;
    }

    /**
     * gets the current piece at the designated xy-coordinate.<br>
     * x represents the horizontal component (column index) of the location.<br>
     * y represents the vertical component (row index) of the location.
     * @param point the coordinate point of the current piece to be returned.
     * @return piece at board[y][x]
     */
    public int getPiece(Ivec2 point) {
        return board[point.y][point.x];
    }

    /**
     * @return the current board as a 2d int array.
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * initializes a completely blank board.
     */
    protected void initBlankBoard() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board[row][col] = 0;
            }
        }
    }

    /**
     * @return an <code>Ivec2</code> containing the (width, height) of the board.
     */
    public Ivec2 getSize() { return new Ivec2(rows, cols); }
}
