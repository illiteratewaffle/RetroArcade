package GameLogic_Client.TicTacToe;

import GameLogic_Client.IBoardGameController;
import GameLogic_Client.TicTacToe.TTTGame;
import GameLogic_Client.ivec2;
import java.util.ArrayList;

/**
 * The TicTacToe Game Controller class. Allows Networking and GUI to gather information about
 *  the status of the game and it's players.
 *
 * Author: Emma Djukic ~ Game Logic
 */
public class TTTGameController implements IBoardGameController {
    public TTTGame game;
    public boolean gameOngoing;

    public TTTGameController() {
        this.game = new TTTGame();
        this.gameOngoing = true;
    }

    @Override
    public void ReceiveInput(ivec2 input) {
        // Process the player's move (input is a 2D coordinate)
        if (!game.makeMove(input.y, input.x)) {
            System.out.println("Invalid move, try again!");
        }
    }

    @Override
    public void RemovePlayer(int player) throws IndexOutOfBoundsException {
        // Handling player removal (if needed, can be expanded)
        if (player < 0 || player > 1) {
            throw new IndexOutOfBoundsException("Invalid player index.");
        }
        // For now, we'll assume no functionality is needed
    }

    @Override
    public int[] GetWinner() {
        // Check if there's a winner
        if (game.checkWin(game.board)) {
            return new int[] { game.currentPlayer }; // Return the winner's index (1 for X, 2 for O)
        }
        return new int[0]; // No winner yet
    }

    @Override
    public boolean GetGameOngoing() {
        return gameOngoing;
    }

    @Override
    public ArrayList<int[][]> GetBoardCells(int LayerMask) {
        ArrayList<int[][]> layers = new ArrayList<>();
        layers.add(game.board.getBoard());
        return layers;  // Return the current board's state
    }

    @Override
    public ivec2 GetBoardSize() {
        return new ivec2(3, 3);  // Board size is fixed to 3x3
    }

    @Override
    public int GetCurrentPlayer() {
        return game.currentPlayer;  // Return the current player (1 for X, 2 for O)
    }

    @Override
    public boolean GameOngoingChangedSinceLastCommand() {
        // Assuming no state change tracking, we'll just return true when the game is ongoing
        return gameOngoing;
    }

    @Override
    public boolean WinnersChangedSinceLastCommand() {
        // This could be implemented to track if the winner has changed (based on game state)
        return false;  // For now, always false
    }

    @Override
    public boolean CurrentPlayerChangedSinceLastCommand() {
        // Track if the current player has changed since the last move
        return false;  // For simplicity, return false
    }

    @Override
    public int BoardChangedSinceLastCommand() {
        // Track if the board has changed, for now just return a simple indicator
        return 1;
    }
}