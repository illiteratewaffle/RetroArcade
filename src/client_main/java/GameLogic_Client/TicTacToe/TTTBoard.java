package client_main.java.GameLogic_Client.TicTacToe;

import client_main.java.GameLogic_Client.AbstractBoard;
import client_main.java.GameLogic_Client.ivec2;

/**
 * Represents a Tic-Tac-Toe board.
 *
 * Author: Emma Djukic ~ Game Logic
 */
public class TTTBoard extends AbstractBoard {

    /**
     * Constructor initializes a 3x3 Tic-Tac-Toe board.
     */
    public TTTBoard() {
        super(3, 3, new int[3][3]);  // Default 3x3 board
    }

    /**
     * Places a piece on the board if the move is valid.
     * @param point The 2D coordinate (column, row) where the piece is placed.
     * @param piece The TTTPiece to place (X or O).
     * @return true if the move was successful, false if the spot was already occupied.
     */
    public boolean placePiece(ivec2 point, TTTPiece piece) {
        if (getPiece(point) == 0) {  // Check if the space is empty
            setPiece(point, piece.getValue());  // Place the piece
            return true;
        }
        return false;  // Spot already taken
    }

    /**
     * Checks if a player has won the game.
     * @return The winning TTTPiece (X or O), or EMPTY if no winner yet.
     */
    public TTTPiece checkWinner() {
        int[][] board = getBoard();

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
     * @return true if the board is full, false otherwise.
     */
    public boolean isFull() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (getPiece(new ivec2(x, y)) == 0) {
                    return false;  // Empty space found
                }
            }
        }
        return true;
    }

    public boolean isEmpty(ivec2 point) {
        return getPiece(point) == 0;
    }
}