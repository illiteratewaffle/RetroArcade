package client_main.java.GameLogic_Client.TicTacToe;

import GameLogic_Client.TicTacToe.TTTGameController;
import GameLogic_Client.ivec2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TTTGameControllerTest {
    TTTGameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new TTTGameController();
    }

    @Test
    void getCurrentPlayer() {
        assertEquals(1, gameController.GetCurrentPlayer(), "Current player should be X at start of the game");
        gameController.game.makeMove(0, 0);
        assertEquals(2, gameController.GetCurrentPlayer(), "Current player should now be O after first turn");
    }

    @Test
    void removePlayerOutOfBounds() {
        Exception ex = assertThrows(IndexOutOfBoundsException.class, () -> {
            gameController.RemovePlayer(2); //should throw index out of bounds exception as 2 > 1
        });
        assertEquals("Invalid player index.", ex.getMessage());
    }

    @Test
    void removePlayerSuccess() {
        assertDoesNotThrow(() -> {
            gameController.RemovePlayer(0); //should not throw exception as index is in bounds.
        });
    }

    @Test
    void getWinnerNone() {
        assertArrayEquals(new int[0], gameController.GetWinner()); //should return the empty array as no winning conditions are met
    }

    @Test
    void getWinnerX() {
        gameController.game.makeMove(0, 0);
        gameController.game.makeMove(0, 1);
        gameController.game.makeMove(1, 0);
        gameController.game.makeMove(1, 1);
        gameController.game.makeMove(2, 0); // X should win here
        assertArrayEquals(new int[] {1}, gameController.GetWinner());
    }

    @Test
    void getWinnerO() {
        gameController.game.makeMove(0, 0);
        gameController.game.makeMove(0, 1);
        gameController.game.makeMove(1, 0);
        gameController.game.makeMove(1, 1);
        gameController.game.makeMove(2, 2);
        gameController.game.makeMove(2, 1); // O should win here
        assertArrayEquals(new int[] {2}, gameController.GetWinner());
    }

    @Test
    void getGameOngoingTrue() {
        assertTrue(gameController.GetGameOngoing(), "Game should be ongoing from the beginning");
    }

    @Test
    void getGameOngoingFalse() {
        gameController.game.makeMove(0, 0);
        gameController.game.makeMove(0, 1);
        gameController.game.makeMove(1, 0);
        gameController.game.makeMove(1, 1);
        gameController.game.makeMove(2, 0); // X should win here, thus ending the game
        gameController.GetWinner();
        assertFalse(gameController.GetGameOngoing(), "Game should now be completed");
    }

    @Test
    void getBoardSize() {
        ivec2 boardSize = new ivec2(3, 3);
        assertEquals(boardSize, gameController.GetBoardSize()); // tic-tac-toe board size is always 3x3
    }
}