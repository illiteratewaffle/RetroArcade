package server.session;

import client_main.java.GameLogic_Client.ivec2;

import java.util.ArrayList;

/**
 * A basic implementation of IBoardGameController.
 * This example simulates a board game with a fixed-size board, a single layer,
 * and simple state changes on input. All “since last command” flags are reset
 * upon querying.
 */
public class BoardGameController implements IBoardGameController {

    // Internal game state variables
    private boolean gameOngoing;
    private int[] winners;
    private ArrayList<int[][]> boardLayers;
    private ivec2 boardSize;
    private int currentPlayer;

    // Change-tracking flags
    private boolean gameOngoingChanged;
    private boolean winnersChanged;
    private boolean currentPlayerChanged;
    private int boardChanged;  // Bit mask representing changed layers

    public BoardGameController() {
        // Initialize game state.
        this.gameOngoing = true;
        this.winners = new int[0];  // No winners initially.
        this.boardSize = new ivec2(3, 3);  // Example board size: 3x3
        // Let's assume we have one board layer for simplicity.
        this.boardLayers = new ArrayList<>();
        int[][] layer = new int[boardSize.x][boardSize.y];
        // Initially, all cells are 0 (empty).
        this.boardLayers.add(layer);
        this.currentPlayer = 0; // Start with player 0

        // Initially, no changes have occurred.
        resetChangeFlags();
    }

    private void resetChangeFlags() {
        gameOngoingChanged = false;
        winnersChanged = false;
        currentPlayerChanged = false;
        boardChanged = 0;
    }

    /**
     * Processes a coordinate input.
     * For this example, the input is used to mark the first board layer with the current player's ID (offset by 1).
     * If the input is out of bounds, nothing is done.
     */
    @Override
    public void receiveInput(ivec2 input) {
        if (input.x < 0 || input.x >= boardSize.x || input.y < 0 || input.y >= boardSize.y) {
            System.err.println("receiveInput: Input out of bounds: " + input);
            return;
        }
        // Mark the cell in layer 0 with current player's id + 1 (so player 0 marks as 1, etc.)
        int[][] layer0 = boardLayers.get(0);
        layer0[input.x][input.y] = currentPlayer + 1;
        // Record that board layer 0 has changed.
        boardChanged |= (1 << 0);
        // For demonstration, simulate a change in current player.
        currentPlayer = (currentPlayer + 1) % 2;  // Toggle between two players.
        currentPlayerChanged = true;
        // Mark game ongoing as changed (for demonstration).
        gameOngoingChanged = true;
    }

    /**
     * Removes the player at the given index.
     * In this simplified implementation, if a valid index is given,
     * we simulate removing a player by marking the game as over.
     */
    @Override
    public void RemovePlayer(int Player) throws IndexOutOfBoundsException {
        // In a real game, you'd have a list of players.
        // Here, we'll just simulate that if Player is not 0 or 1, it's an error.
        if (Player < 0 || Player > 1) {
            throw new IndexOutOfBoundsException("No player exists at index " + Player);
        }
        System.out.println("RemovePlayer: Removing player " + Player);
        // For this demo, removing a player stops the game.
        gameOngoing = false;
        gameOngoingChanged = true;
        // Simulate that the removed player is the winner (or the other player wins).
        winners = new int[] { (Player == 0) ? 1 : 0 };
        winnersChanged = true;
    }

    @Override
    public int[] getWinner() {
        int[] ret = winners;
        winnersChanged = false;  // Reset the flag after reading.
        return ret;
    }

    @Override
    public boolean getGameOngoing() {
        boolean ret = gameOngoing;
        gameOngoingChanged = false;
        return ret;
    }

    /**
     * Returns board layers corresponding to the bits set in LayerMask.
     * For example, if LayerMask is 1, returns the first layer.
     */
    @Override
    public ArrayList<int[][]> getBoardCells(int LayerMask) {
        ArrayList<int[][]> result = new ArrayList<>();
        // For each layer index, if its corresponding bit is set, add that layer.
        for (int i = 0; i < boardLayers.size(); i++) {
            if ((LayerMask & (1 << i)) != 0) {
                result.add(boardLayers.get(i));
            }
        }
        // After returning, reset boardChanged flag if all layers have been reported.
        boardChanged = 0;
        return result;
    }

    @Override
    public ivec2 getBoardSize() {
        return boardSize;
    }

    @Override
    public int getCurrentPlayer() {
        int ret = currentPlayer;
        currentPlayerChanged = false;
        return ret;
    }

    @Override
    public boolean gameOngoingChangedSinceLastCommand() {
        boolean ret = gameOngoingChanged;
        gameOngoingChanged = false;
        return ret;
    }

    @Override
    public boolean winnersChangedSinceLastCommand() {
        boolean ret = winnersChanged;
        winnersChanged = false;
        return ret;
    }

    @Override
    public boolean currentPlayerChangedSinceLastCommand() {
        boolean ret = currentPlayerChanged;
        currentPlayerChanged = false;
        return ret;
    }

    @Override
    public int boardChangedSinceLastCommand() {
        int ret = boardChanged;
        boardChanged = 0;
        return ret;
    }
}

