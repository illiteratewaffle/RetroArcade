package GameLogic_Client.TicTacToe;

import GameLogic_Client.GameState;
import GameLogic_Client.Ivec2;

/**
 * Represents the Tic-Tac-Toe game logic, including game state management and move handling.
 *
 * Author: Emma Djukic ~ Game Logic
 */
public class TTTGame {
    /**
     * The game board instance.
     */
    public TTTBoard board;

    /**
     * The current player (1 for X, 2 for O).
     */
    public int currentPlayer;

    /**
     * Tracks the current game state (ongoing, win, or tie).
     */
    public GameState gameState;

    /**
     * Initializes a new Tic-Tac-Toe game with an empty board.
     * X (player 1) starts first.
     */
    public TTTGame() {
        board = new TTTBoard();
        currentPlayer = 1;
        gameState = GameState.ONGOING;
    }

    /**
     * Attempts to make a move on the board at the specified row and column.
     * If the move is valid, the turn is switched and the game state is checked.
     *
     * @param row The row index of the move.
     * @param col The column index of the move.
     * @return true if the move was successful, false if the move was invalid.
     */
    public boolean makeMove(int row, int col) {
        Ivec2 point = new Ivec2(col, row);

        if (!board.placePiece(point, TTTPiece.fromInt(currentPlayer))) {
            return false;
        }

        updateGameState();

        if (gameState != GameState.ONGOING) {
            printBoard();
            return true;
        }

        switchTurn();
        return true;
    }

    /**
     * Switches the turn between players X (1) and O (2).
     */
    public void switchTurn() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    /**
     * Updates the game state based on the current board.
     */
    public void updateGameState() {
        TTTPiece winner = board.checkWinner();

        if (winner == TTTPiece.X) {
            gameState = GameState.P1WIN;
        } else if (winner == TTTPiece.O) {
            gameState = GameState.P2WIN;
        } else if (board.isFull()) {
            gameState = GameState.TIE;
        } else {
            gameState = GameState.ONGOING;
        }
    }

    /**
     * Checks if there is a winner on the given board.
     *
     * @param board The current game board.
     * @return true if there is a winner, false otherwise.
     */
    public boolean checkWin(TTTBoard board) {
        return ((board.checkWinner() == TTTPiece.O) || (board.checkWinner() == TTTPiece.X));
    }

    /**
     * Checks if the game has ended in a draw.
     *
     * @param board The current game board.
     * @return true if the board is full and there is no winner, false otherwise.
     */
    public boolean checkDraw(TTTBoard board) {
        return !checkWin(board) && board.isFull();
    }

    /**
     * Retrieves the piece located at the specified position on the board.
     *
     * @param point The position on the board.
     * @return The piece at the specified position.
     */
    public TTTPiece getPiece(Ivec2 point) {
        return TTTPiece.fromInt(this.board.getPiece(point));
    }

    /**
     * Determines if the game is over by checking for a win or a draw.
     *
     * @param board The current game board.
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver(TTTBoard board) {
        return checkWin(board) || checkDraw(board);
    }

    /**
     * Prints the current state of the board to the console.
     */
    public void printBoard() {
        int[][] grid = board.getBoard();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                TTTPiece piece = TTTPiece.fromInt(grid[row][col]);
                System.out.print(piece + " ");
            }
            System.out.println();
        }
    }
}