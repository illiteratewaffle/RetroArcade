package GameLogic_Client.TicTacToe;

import GameLogic_Client.IBoardGameController;
import GameLogic_Client.TicTacToe.TTTGame;
import GameLogic_Client.Ivec2;
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
     * Indicates whether the game is still ongoing.
     */
    public boolean gameOngoing;

    /**
     * Initializes the game controller, setting up a new game instance and marking the game as ongoing.
     */
    public TTTGameController() {
        this.game = new TTTGame();
        this.gameOngoing = true;
    }

    /**
     * Processes the player's move input.
     *
     * @param input A 2D coordinate representing the player's move.
     */
    @Override
    public void ReceiveInput(Ivec2 input) {
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
    public void RemovePlayer(int player) throws IndexOutOfBoundsException {
        if (player < 0 || player > 1) {
            throw new IndexOutOfBoundsException("Invalid player index.");
        }
    }

    /**
     * Determines if there is a winner.
     *
     * @return An array containing the winner's index (1 for X, 2 for O), or an empty array if no winner.
     */
    @Override
    public int[] GetWinner() {
        if (game.checkWin(game.board)) {
            return new int[] { game.currentPlayer };
        }
        return new int[0];
    }

    /**
     * Checks if the game is still ongoing.
     *
     * @return true if the game is ongoing, false otherwise.
     */
    @Override
    public boolean GetGameOngoing() {
        return gameOngoing;
    }

    /**
     * Retrieves the current state of the game board.
     *
     * @param LayerMask A layer mask (not used in the current implementation).
     * @return A list containing the board's current state.
     */
    @Override
    public ArrayList<int[][]> GetBoardCells(int LayerMask) {
        ArrayList<int[][]> layers = new ArrayList<>();
        layers.add(game.board.getBoard());
        return layers;
    }

    /**
     * Retrieves the size of the game board.
     *
     * @return A 2D vector representing the board size (3x3).
     */
    @Override
    public Ivec2 GetBoardSize() {
        return new Ivec2(3, 3);
    }

    /**
     * Retrieves the current player.
     *
     * @return The current player (1 for X, 2 for O).
     */
    @Override
    public int GetCurrentPlayer() {
        return game.currentPlayer;
    }

    /**
     * Checks if the game state has changed since the last command.
     *
     * @return true if the game is still ongoing.
     */
    @Override
    public boolean GameOngoingChangedSinceLastCommand() {
        return gameOngoing;
    }

    /**
     * Determines if the winner status has changed since the last command.
     *
     * @return false, as winner tracking is not currently implemented.
     */
    @Override
    public boolean WinnersChangedSinceLastCommand() {
        return false;
    }

    /**
     * Determines if the current player has changed since the last command.
     *
     * @return false, as player change tracking is not currently implemented.
     */
    @Override
    public boolean CurrentPlayerChangedSinceLastCommand() {
        return false;
    }

    /**
     * Indicates whether the board state has changed since the last command.
     *
     * @return 1 as a simple indicator of change.
     */
    @Override
    public int BoardChangedSinceLastCommand() {
        return 1;
    }
}
