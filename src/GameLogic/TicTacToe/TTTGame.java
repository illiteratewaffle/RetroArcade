package GameLogic.TicTacToe;


public class TTTGame {
    private TTTBoard board;
    private int currentPlayer; // X is 1, 2 O is 2

    public TTTGame() {
        board = new TTTBoard(3, 3);
        currentPlayer = 1; // X plays first
    }

    public void startGame() {
        // Main game loop

    }

    public boolean makeMove(int row, int col) {
        // Place piece if valid, return true if successful
        return false; // just a placeholder for now
    }

    private void switchTurn() {
        // Change currentPlayer from X to O and vice versa
    }

    private boolean isGameOver() {
        // Check if there's a winner or if it's a draw
        return false; // placeholder for now
    }

    private void printBoard() {
        // Print the board for debugging things
    }
}