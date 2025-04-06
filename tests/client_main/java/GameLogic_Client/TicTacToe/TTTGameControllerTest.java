package client_main.java.GameLogic_Client.TicTacToe;

import GameLogic_Client.TicTacToe.TTTGameController;
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
        assertEquals(1, gameController.GetCurrentPlayer(), "Current player should now be O after first turn");
    }
}