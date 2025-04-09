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
     * Receives user input during the game.
     * @param input A 2D-Integer-Coordinate Input that corresponds to a Board Cell.
     */

    /**
     * Receives user input during the game.
     * @param input A 2D-Integer-Coordinate Input that corresponds to a Board Cell.
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

    /**
     * Gets the board for connect-4
     * @return Connect-4 board by calling function in C4GameLogic class.
     */
    @Override
    public C4Piece[][] getC4Board() {
        return c4GameLogic.getC4Board().getC4Board();
    }

    /**
     * Calls C4GameLogic class function to check whether the function
     * @return true or false based on whether the game is over or not.
     */
    public boolean getC4IsGameOver() {
        return c4GameLogic.getC4IsGameOver();
    }

    /**
     * Removes player from game (it is inherited from interface)
     * @param Player The index of the player to remove.
     * @throws IndexOutOfBoundsException if player # is out of bounds.
     */
    @Override
    public void removePlayer(int Player) throws IndexOutOfBoundsException {

    }

    /**
     * Updates the state of the game as it progresses.
     * @return integer of game outcome
     */
    @Override
    public int getWinner() {
        return switch (c4GameLogic.gameState) {
            case TIE -> 0;
            case P1WIN -> 1;
            case P2WIN -> 2; //draw
            default -> 3;
        };
    }

    /**
     * Checks whether the game is ongoing or not.
     * @return true or false based on whether the game is ongoing or not.
     */
    @Override
    public boolean getGameOngoing() {
        return !c4GameLogic.getC4IsGameOver();
    }

    /**
     * Gets board's cells, used in GUI.
     * @param LayerMask A bit-string, where the bits of all the layers to query are set to 1.
     * @return null if it's a blank, else piece's enum value.
     */
    @Override
    public ArrayList<int[][]> getBoardCells(int LayerMask) {
        ArrayList<int[][]> sendBoard = new ArrayList<>();
        C4Piece[][] currentBoardEnum = c4GameLogic.getC4Board().getC4Board();

        int rows = currentBoardEnum.length;
        int cols = currentBoardEnum[0].length;
        int[][] intBoard = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                switch (currentBoardEnum[i][j]) {
                    case BLANK:
                        intBoard[i][j] = 0;
                        break;
                    case RED:
                        intBoard[i][j] = 1;
                        break;
                    case BLUE:
                        intBoard[i][j] = 2;
                        break;
                }
            }
        }
        sendBoard.add(intBoard);
        return sendBoard;
    }


    /**
     * Returns the size of our Connect-4 board.
     * @return null if size is 0, else, returns the dimensions through function in Ivec2 class.
     */
    @Override
    public Ivec2 getBoardSize() {
        return null;
    }

    /**
     * Returns the current player's integer value.
     * @return 0, if no player's turn, else 1 or 2.
     */
    @Override
    public int getCurrentPlayer() {
        int currentPlayer = 0;
        if (c4GameLogic.getC4CurrentPlayer() == C4Piece.BLUE) {
            currentPlayer = 1;
        }
        return currentPlayer;
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

    public C4Piece getC4CurrentPlayer() {return c4GameLogic.getC4CurrentPlayer();}

    /**
     * Prints current state of board (at any point).
     */
    @Override
    public void printBoard() {
        System.out.println(c4GameLogic);
    }


    /**
     * Function to give users hints if needed during the game based on which column is an ideal pick.
     * @return hint to user
     */
    @Override
    public HintResult getC4ColHint() {
        if (c4GameLogic == null) {
            System.out.println("Game not started. No hint available.");
            return new HintResult(-1, "NONE");
        }
        return c4GameLogic.getC4HintColumn();
    }

    @Override
    public boolean isTileEmpty(Ivec2 tile) {
        return false;
    }

    @Override
    public boolean makeMove(int row, int col) {
        return false;
    }

    @Override
    public boolean checkWin() {
        return false;
    }

    @Override
    public boolean checkDraw() {
        return false;
    }

    @Override
    public void updateGameState() {}
}