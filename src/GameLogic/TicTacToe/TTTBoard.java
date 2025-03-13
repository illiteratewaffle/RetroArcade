package GameLogic;

/**
 * Represents a Tic-Tac-Toe board.
 *
 * Author: Emma Djukic ~ Game Logic
 */
public class TTTBoard extends AbstractBoard {

    /**
     * The constructor initializes a 3x3 Tic-Tac-Toe board.
     */
    public TTTBoard() {
        super(3, 3, new int[3][3]);  // Passes a blank 3x3 int array to AbstractBoard
    }

    /**
     * Places a piece on the board if the move is valid.
     * @param row the row where the piece is placed
     * @param col the column where the piece is placed
     * @param piece the TTTPiece to place (X or O)
     * @return true if the move was successful, false if the spot was already occupied
     */
    public boolean placePiece(int row, int col, TTTPiece piece) {
        if (getPiece(row, col) == 0) {  // Check if the space is empty
            setPiece(row, col, piece.getValue());  // Convert TTTPiece to int
            return true;
        }
        return false;  // Spot not available
    }

    /**
     * Checks if the board is full, or no moves possible.
     * @return true if the board is full, false if not
     */
    public boolean isFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (getPiece(row, col) == 0) {  // If any cell is empty, board is not full
                    return false;
                }
            }
        }
        return true;
    }
}