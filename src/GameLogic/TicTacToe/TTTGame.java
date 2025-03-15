package gamelogic.tictactoe;

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

    private boolean checkWin(TTTBoard board) {
        // if winner is X or O, return true. Else, return false.
        return board.checkWinner() == TTTPiece.X || board.checkWinner() == TTTPiece.O;
    }

    private boolean checkDraw(TTTBoard board) {
        // if there is no winner, AND the board is full, then there is a draw.
        return !checkWin(board) && board.isFull();
    }

    private boolean isGameOver(TTTBoard board) {
        // Check if there's a winner or if it's a draw
        return checkWin(board) || checkDraw(board);
    }

    private void printBoard() {
        // Print the board for debugging things
    }
}