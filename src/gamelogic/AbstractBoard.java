import package.gamelogic.*;

abstract class AbstractBoard {

    /**
     * Basic variables needed for game implementation (boards).
     */
    private Piece[][] board;
    private int rows;
    private int cols;

    /**
     * Basic abstract board initialization.
     * @param rows
     * @param cols
     */
    public AbstractBoard(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.board = new Piece[rows][cols];
        initializeBlankBoard();
    }

    /**
     * Places a piece to the board
     * @param row
     * @param col
     * @param piece
     */
    public void setPiece(int row, int col, Piece piece) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            board[row][col] = piece;
        } else {
            throw new IllegalArgumentException("Invalid board position");
        }
    }

    /**
     * Gets a piece from the board at specified row and column.
     * @param row
     * @param col
     * @return a piece
     */
    public Piece getPiece(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            return board[row][col];
        }
        throw new IllegalArgumentException("Invalid board position");
    }

    /**
     * @return board for the game.
     */
    public Piece[][] getBoard() {
        return board;
    }

    /**
     * This function initializes blank boards for new games.
     * Setting each value in the board to null.
     */
    private void initBlankBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = null;
            }
        }
    }
}
