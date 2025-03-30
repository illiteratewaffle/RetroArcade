package GameLogic_Client.Connect4;

import GameLogic_Client.IBoardGameController;
import GameLogic_Client.ivec2;

import java.util.ArrayList;

public class C4Controller implements IBoardGameController {

    private C4GameLogic c4GameLogic;

    /**
     * Starts a new game of Connect Four.
     */
    void start() {
        System.out.println("A new game of connect four has started");
        c4GameLogic = new C4GameLogic();
        printBoard();
    }

    /**
     * Receives yser input during the game.
     */
    @Override
    public void ReceiveInput(ivec2 input) {
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
    public void RemovePlayer(int Player) throws IndexOutOfBoundsException {

    }

    @Override
    public int[] GetWinner() {
        return new int[0];
    }

    @Override
    public boolean GetGameOngoing() {
        return false;
    }

    @Override
    public ArrayList<int[][]> GetBoardCells(int LayerMask) {
        return null;
    }

    @Override
    public ivec2 GetBoardSize() {
        return null;
    }

    @Override
    public int GetCurrentPlayer() {
        return 0;
    }

    @Override
    public boolean GameOngoingChangedSinceLastCommand() {
        return false;
    }

    @Override
    public boolean WinnersChangedSinceLastCommand() {
        return false;
    }

    @Override
    public boolean CurrentPlayerChangedSinceLastCommand() {
        return false;
    }

    @Override
    public int BoardChangedSinceLastCommand() {
        return 0;
    }

    /**
     * Prints current state of board (at any point).
     */
    void printBoard() {
        System.out.println(c4GameLogic);
    }
}