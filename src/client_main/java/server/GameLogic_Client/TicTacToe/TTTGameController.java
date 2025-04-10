package server.GameLogic_Client.TicTacToe;

import server.GameLogic_Client.GameState;
import server.GameLogic_Client.IBoardGameController;
import server.GameLogic_Client.Ivec2;
import server.GameLogic_Client.TicTacToe.TTTGame;

import java.util.ArrayList;

/**
 * Controls the Tic-Tac-Toe game, facilitating interactions between the networking layer, GUI,
 * and the game logic.
 *
 * Author: Emma Djukic ~ Game Logic
 */
public class TTTGameController implements IBoardGameController {
    /**
     * The Tic-Tac-Toe game instance.
     */
    public TTTGame game;

    /**
     * Initializes the game controller by creating a new game instance.
     */
    public TTTGameController() {
        this.game = new TTTGame();
    }

    /**
     * Processes the player's move input.
     *
     * @param input A 2D coordinate representing the player's move.
     */
    @Override
    public void receiveInput(Ivec2 input) {
        if (!game.makeMove(input.y, input.x)) {
            System.out.println("Invalid move, try again!");
        }
    }

    /**
     * Handles player removal (currently not implemented with functionality).
     *
     * @param player The index of the player to be removed (0 or 1).
     * @throws IndexOutOfBoundsException If an invalid player index is provided.
     */
    @Override
    public void removePlayer(int player) throws IndexOutOfBoundsException {
        if (player < 0 || player > 1) {
            throw new IndexOutOfBoundsException("Invalid player index.");
        }
    }

    /**
     * Determines if there is a winner.
     *
     * @return An array containing the winner's index (0 for X, 1 for O), both for tie, or empty if no winner.
     */
    @Override
    public int[] getWinner() {
        return switch (game.gameState) {
            case GameState.P1WIN -> new int[]{0};
            case GameState.P2WIN -> new int[]{1};
            case GameState.TIE -> new int[]{0, 1};
            default -> new int[]{};
        };
    }

    /**
     * Checks if the game is still ongoing.
     *
     * @return true if the game is ongoing, false otherwise.
     */
    @Override
    public boolean getGameOngoing() {
        return game.gameState == GameState.ONGOING;
    }

    /**
     * Retrieves the current state of the game board.
     *
     * @param LayerMask A layer mask (not used in the current implementation).
     * @return A list containing the board's current state.
     */
    @Override
    public ArrayList<int[][]> getBoardCells(int LayerMask) {
        ArrayList<int[][]> layers = new ArrayList<>();
        if (LayerMask & 0b01 > 0) {}
        layers.add(game.board.getBoard());
        return layers;
    }

    /**
     * Retrieves the size of the game board.
     *
     * @return A 2D vector representing the board size (3x3).
     */
    @Override
    public Ivec2 getBoardSize() {
        return new Ivec2(3, 3);
    }

    /**
     * Retrieves the current player.
     *
     * @return The current player (1 for X, 2 for O).
     */
    @Override
    public int getCurrentPlayer() {
        return game.currentPlayer;
    }

    /**
     * Checks if the game state has changed since the last command.
     *
     * @return false, as state change tracking is not currently implemented.
     */
    @Override
    public boolean gameOngoingChangedSinceLastCommand() {
        return false;
    }

    /**
     * Determines if the winner status has changed since the last command.
     *
     * @return false, as winner tracking is not currently implemented.
     */
    @Override
    public boolean winnersChangedSinceLastCommand() {
        return false;
    }

    /**
     * Determines if the current player has changed since the last command.
     *
     * @return false, as player change tracking is not currently implemented.
     */
    @Override
    public boolean currentPlayerChangedSinceLastCommand() {
        return false;
    }

    /**
     * Indicates whether the board state has changed since the last command.
     *
     * @return 1 as a simple indicator of change.
     */
    @Override
    public int boardChangedSinceLastCommand() {
        return 1;
    }

    /**
     * Ignored
     */
    public C4Piece[][] getC4Board() {
        return null;
    }

    /**
     * Ignored
     */
    public boolean getC4IsGameOver() {
        return false;
    }


    /**
     * Ignored.
     */
    public C4Piece getC4WinnerAsEnum() {
        return null;
    }

    /**
     * Ignored.
     */
    public C4Piece getC4CurrentPlayer() {
        return null;
    }

    public void printBoard() {

    }

    public HintResult getC4ColHint() {
        return null;
    }
}