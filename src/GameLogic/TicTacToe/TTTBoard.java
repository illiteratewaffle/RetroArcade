package GameLogic.TicTacToe;

import GameLogic.*;

/**
 * Represents a Tic-Tac-Toe board.
 *
 * Author: Emma Djukic ~ Game Logic
 */
public class TTTBoard extends AbstractBoard {

    /**
     * Constructor initializes a 3x3 Tic-Tac-Toe board.
     */
    public TTTBoard(int rows, int cols) {
        super(rows, cols, new int[rows][cols]);  // Passes a blank 3x3 int array to AbstractBoard
    }

    /**
     * Places a piece on the board if the move is valid.
     * @param point the 2D coordinate (x, y) (or (column, row)) where the piece is placed.
     * @param piece the TTTPiece to place (X or O)
     * @return true if the move was successful, false if the spot was already occupied
     */
    public boolean placePiece(ivec2 point, TTTPiece piece) {
        if (getPiece(point) == 0) {  // Check if the space is empty
            setPiece(point, piece.getValue());  // Convert TTTPiece to int
            return true;
        }
        return false;  // Spot already taken
    }

    /**
     * Checks if a player has won the game.
     * @return the winning TTTPiece (X or O), or EMPTY if no winner yet
     */
    public TTTPiece checkWinner() {
        int[][] board = getBoard();  // Get the board state

        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return TTTPiece.fromInt(board[i][0]);  // Row match
            }
            if (board[0][i] != 0 && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return TTTPiece.fromInt(board[0][i]);  // Column match
            }
        }

        // Check diagonals
        if (board[0][0] != 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return TTTPiece.fromInt(board[0][0]);  // Main diagonal
        }
        if (board[0][2] != 0 && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return TTTPiece.fromInt(board[0][2]);  // Anti-diagonal
        }

        return TTTPiece.EMPTY;  // No winner yet
    }

    /**
     * Checks if the board is full (no more moves possible).
     * @return true if the board is full, false otherwise
     */
    public boolean isFull() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (getPiece(new ivec2(x, y)) == 0) {  // If any cell is empty, board is not full
                    return false;
                }
            }
        }
        return true;
    }
}