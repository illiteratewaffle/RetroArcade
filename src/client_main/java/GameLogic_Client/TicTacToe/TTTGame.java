package client_main.java.GameLogic_Client.TicTacToe;

import  client_main.java.GameLogic_Client.ivec2;

/**
 * Contains the TicTacToe methods called to run the game.
 *
 * Author: Emma Djukic ~ Game Logic
 */
public class TTTGame {
    public TTTBoard board;
    public int currentPlayer; // 1 for X, 2 for O

    public TTTGame() {
        board = new TTTBoard(); // Initialize with a 3x3 board
        currentPlayer = 1; // X starts
    }

    public boolean makeMove(int row, int col) {
        ivec2 point = new ivec2(col, row); // Note: you're passing row/col, but the board uses (x, y)

        // Attempt to place the piece, return false if invalid
        if (!board.placePiece(point, TTTPiece.fromInt(currentPlayer))) {
            return false; // Invalid move (spot already taken)
        }

        // After placing the piece, check if the game is over
        if (isGameOver(board)) {
            printBoard(); // Optionally print the board
            return true;  // Game over (win or draw)
        }

        // Switch turns for the next player
        switchTurn();
        return true;
    }

    public void switchTurn() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1; // Switch between 1 (X) and 2 (O)
    }

    public boolean checkWin(TTTBoard board) {
        return board.checkWinner() == TTTPiece.X || board.checkWinner() == TTTPiece.O;
    }

    public boolean checkDraw(TTTBoard board) {
        return !checkWin(board) && board.isFull(); // No winner and board is full
    }

    public boolean isGameOver(TTTBoard board) {
        return checkWin(board) || checkDraw(board); // Check for win or draw
    }

    public void printBoard() {
        int[][] grid = board.getBoard();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                TTTPiece piece = TTTPiece.fromInt(grid[row][col]);
                System.out.print(piece + " ");
            }
            System.out.println(); // Newline after each row
        }
    }
}