package GameLogic_Client.TicTacToe;

import GameLogic_Client.GameState;
import GameLogic_Client.IBoardGameController;
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
    private TTTGame game;

    /**
     * Initializes the game controller by creating a new game instance.
     */
    public TTTGameController() {
        this.game = new TTTGame();
    }

    /**
     * Checks if a given tile on the board is empty.
     * @param tile Ivec2 coordinates of the tile that you want to check if empty.
     * @return returns true if empty, false if not empty.
     */
    public boolean isTileEmpty(Ivec2 tile) {
        return game.board.isEmpty(tile);
    }

    /**
     * Makes the move via the game logic and switches the players turn if the move is valid.
     * @param row the row coordinate of the move.
     * @param col the col coordinate of the move.
     * @return returns true if the move is valid. False if invalid.
     */
    public boolean makeMove(int row, int col) {
        return game.makeMove(row, col);
    }

    /**
     * checks if a win condition has been reached by either player.
     * @return returns true if a player has won, false if nobody has won yet.
     */
    public boolean checkWin() {
        return game.checkWin(game.board);
    }

    /**
     * checks if a draw condition has been reached in-game.
     * @return returns true if there's a draw, false otherwise.
     */
    public boolean checkDraw() {
        return game.checkDraw(game.board);
    }

    /**
     * updates the game state via the game logic.
     */
    public void updateGameState() {
        game.updateGameState();
    }


    @Override
    public void receiveInput(Ivec2 input) {
        if (!game.makeMove(input.y, input.x)) {
            System.out.println("Invalid move, try again!");
        }
    }


    @Override
    public void removePlayer(int player) throws IndexOutOfBoundsException {
        if (player < 0 || player > 1) {
            throw new IndexOutOfBoundsException("Invalid player index.");
        }
    }


    @Override
    public int[] getWinner()
    {
        return switch (game.gameState)
        {
            case P1WIN -> new int[]{0};
            case P2WIN -> new int[]{1};
            case TIE -> new int[]{0, 1};
            // By default, declare nobody as the winner.
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


    @Override
    public ArrayList<int[][]> getBoardCells(int layerMask)
    {
        ArrayList<int[][]> layers = new ArrayList<>();
        // Check the first bit of the mask, which represents the piece layer.
        if ((layerMask & 0b1) != 0)
        {
            layers.add(game.board.getBoard());
        }
        // Check the second bit of the mask, which represents the hint layer.
        if ((layerMask & 0b10) != 0)
        {
            // Construct a 2D int array of hints and add it to the boardCells array list.
            // For Tic-Tac-Toe, there are no hints, so we simply return an empty array.
            Ivec2 boardSize = getBoardSize();
            layers.add(new int[boardSize.y][boardSize.x]);
        }
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
     * @return A bit-field where the bit of the layer that was changed is turned on.<br>
     * Since the Tic-Tac-Toe board changes whenever a valid input is received,
     * the return value is always 0b01 to indicate that the first layer (pieces) has been changed.
     */
    @Override
    public int boardChangedSinceLastCommand() {
        return 0b01;
    }
}