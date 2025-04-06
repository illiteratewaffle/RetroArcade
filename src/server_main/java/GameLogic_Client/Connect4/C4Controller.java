package GameLogic_Client.Connect4;

import GameLogic_Client.IBoardGameController;
import GameLogic_Client.Ivec2;

import java.util.ArrayList;

public class C4Controller implements IBoardGameController {

    private GameLogic_Client.Connect4.C4GameLogic c4GameLogic;

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
        int col = input.x;
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

    @Override
    public void removePlayer(int player) throws IndexOutOfBoundsException {

    }

    @Override
    public int[] getWinner() {
        return new int[0];
    }

    @Override
    public boolean getGameOngoing() {
        return false;
    }

    @Override
    public ArrayList<int[][]> getBoardCells(int layerMask) {
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

    /**
     * Prints current state of board (at any point).
     */
    void printBoard() {
        System.out.println(c4GameLogic);
    }
}