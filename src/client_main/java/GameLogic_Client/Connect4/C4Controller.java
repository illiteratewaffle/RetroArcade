package GameLogic_Client.Connect4;

import GameLogic_Client.IBoardGameController;
import GameLogic_Client.ivec2;

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
     * Receives user input during the game.
     * @param input A 2D-Integer-Coordinate Input that corresponds to a Board Cell.
     */
    @Override
    public void ReceiveInput(ivec2 input) {

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
    public void RemovePlayer(int Player) throws IndexOutOfBoundsException {

    }

    @Override
    public int[] GetWinner() {
        C4Piece winner = c4GameLogic.getC4Winner();
        return switch (winner) {
            case RED -> new int[]{0};
            case BLUE -> new int[]{1};
            default -> new int[]{0, 1}; //draw
        };
    }

    @Override
    public boolean GetGameOngoing() {
        return !c4GameLogic.getC4IsGameOver();
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

    public C4Piece getC4WinnerAsEnum() {
        return c4GameLogic.getC4Winner();
    }

    /**
     * Prints current state of board (at any point).
     */
    void printBoard() {
        System.out.println(c4GameLogic);
    }


    /**
     * Function to give users hints if needed during the game based on which column is an ideal pick.
     * @return hint to user
     */
    public HintResult getC4ColHint() {
        if (c4GameLogic == null) {
            System.out.println("Game not started. No hint available.");
            return new HintResult(-1, "NONE");
        }
        return c4GameLogic.getC4HintColumn();
    }
}