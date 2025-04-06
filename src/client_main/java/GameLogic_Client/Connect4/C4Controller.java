package GameLogic_Client.Connect4;

import GameLogic_Client.IBoardGameController;
import GameLogic_Client.Ivec2;

import java.util.ArrayList;

public class C4Controller implements IBoardGameController {

    public GameLogic_Client.Connect4.C4GameLogic c4GameLogic;

    /**
     * Starts a new game of Connect Four.
     */
    public void start() {
        System.out.println("A new game of connect four has started");
        c4GameLogic = new GameLogic_Client.Connect4.C4GameLogic();
        printBoard();
    }

    /**
     * Receives yser input during the game.
     */
    @Override
    public void receiveInput(Ivec2 input) {

        //this section gets the dimensions of the board to check against user inputs

        C4Piece[][] board = c4GameLogic.getC4Board().getC4Board();
        int colDimension = board[0].length;

        int col = input.x;

        if (col < 0 || col >= colDimension) {
            System.out.println("Invalid column, out of bounds");
            return;
        }

        if (!c4GameLogic.getC4IsGameOver()) {
            C4Piece currentPlayer = c4GameLogic.getC4CurrentPlayer();
            boolean successfulPlay = c4GameLogic.c4DropPiece(col, currentPlayer);
            if (!successfulPlay) {
                System.out.println("Invalid move. Try again");
            } else {
                printBoard();
            }
        }
    }

    public C4Piece[][] getC4Board() {
        return c4GameLogic.getC4Board().getC4Board();
    }

    public boolean getC4IsGameOver() {
        return c4GameLogic.getC4IsGameOver();
    }

    @Override
    public void removePlayer(int Player) throws IndexOutOfBoundsException {

    }

    @Override
    public int[] getWinner() {
        C4Piece winner = c4GameLogic.getC4Winner();
        return switch (winner) {
            case RED -> new int[]{0};
            case BLUE -> new int[]{1};
            default -> new int[]{0, 1}; //draw
        };
    }

    @Override
    public boolean getGameOngoing() {
        return !c4GameLogic.getC4IsGameOver();
    }

    @Override
    public ArrayList<int[][]> getBoardCells(int LayerMask) {
        return null;
    }

    @Override
    public Ivec2 getBoardSize() {
        return null;
    }

    @Override
    public int getCurrentPlayer() {
        return 0;
    }

    @Override
    public boolean gameOngoingChangedSinceLastCommand() {
        return false;
    }

    @Override
    public boolean winnersChangedSinceLastCommand() {
        return false;
    }

    @Override
    public boolean currentPlayerChangedSinceLastCommand() {
        return false;
    }

    @Override
    public int boardChangedSinceLastCommand() {
        return 0;
    }

    public C4Piece getC4WinnerAsEnum() {
        return c4GameLogic.getC4Winner();
    }

    /**
     * Prints current state of board (at any point).
     */
    void printBoard() {
        System.out.println(c4GameLogic);
    }
}