package client_main.java.GameLogic_Client.TicTacToe;

import GameLogic_Client.TicTacToe.TTTGameController;
import GameLogic_Client.Ivec2;
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
        assertEquals(1, gameController.getCurrentPlayer(), "Current player should be X at start of the game");
        gameController.game.makeMove(0, 0);
        assertEquals(2, gameController.getCurrentPlayer(), "Current player should now be O after first turn");
    }

    @Test
    void removePlayerOutOfBounds() {
        Exception ex = assertThrows(IndexOutOfBoundsException.class, () -> {
            gameController.removePlayer(2); //should throw index out of bounds exception as 2 > 1
        });
        assertEquals("Invalid player index.", ex.getMessage());
    }

    @Test
    void removePlayerSuccess() {
        assertDoesNotThrow(() -> {
            gameController.removePlayer(0); //should not throw exception as index is in bounds.
        });
    }

    @Test
    void getWinnerNone() {
        assertArrayEquals(new int[0], gameController.getWinner()); //should return the empty array as no winning conditions are met
    }

    @Test
    void getWinnerX() {
        gameController.game.makeMove(0, 0);
        gameController.game.makeMove(0, 1);
        gameController.game.makeMove(1, 0);
        gameController.game.makeMove(1, 1);
        gameController.game.makeMove(2, 0); // player 1 should win here
        assertArrayEquals(new int[] {0}, gameController.getWinner());
    }

    @Test
    void getWinnerO() {
        gameController.game.makeMove(0, 0);
        gameController.game.makeMove(0, 1);
        gameController.game.makeMove(1, 0);
        gameController.game.makeMove(1, 1);
        gameController.game.makeMove(2, 2);
        gameController.game.makeMove(2, 1); // Player 2 should win here
        assertArrayEquals(new int[] {1}, gameController.getWinner());
    }

    @Test
    void getWinnerDraw() {
        gameController.game.makeMove(0, 0); //player 1
        gameController.game.makeMove(0,1);  //player 2
        gameController.game.makeMove(0,2);  //player 1
        gameController.game.makeMove(1,1);  //player 2
        gameController.game.makeMove(1,0);  //player 1
        gameController.game.makeMove(1,2);  //player 2
        gameController.game.makeMove(2,1);  //player 1
        gameController.game.makeMove(2,0);  //player 2
        gameController.game.makeMove(2,2);  //player 1
        // this should result in a draw
        assertArrayEquals(new int[] {0, 1}, gameController.getWinner());
    }

    @Test
    void getGameOngoingTrue() {
        assertTrue(gameController.getGameOngoing(), "Game should be ongoing from the beginning");
    }

    @Test
    void getGameOngoingFalse() {
        gameController.game.makeMove(0, 0);
        gameController.game.makeMove(0, 1);
        gameController.game.makeMove(1, 0);
        gameController.game.makeMove(1, 1);
        gameController.game.makeMove(2, 0); // X should win here, thus ending the game
        gameController.getWinner();
        assertFalse(gameController.getGameOngoing(), "Game should now be completed");
    }

    @Test
    void getBoardSize() {
        Ivec2 boardSize = new Ivec2(3, 3);
        assertEquals(boardSize, gameController.getBoardSize()); // tic-tac-toe board size is always 3x3
    }

    @Test
    void receiveInputValidMove() {
        Ivec2 input = new Ivec2(0, 0);
        gameController.receiveInput(input);
        assertEquals(2, gameController.getCurrentPlayer(), "Player should switch after valid move");
    }

    @Test
    void receiveInputInvalidMove() {
        Ivec2 input = new Ivec2(0, 0);
        gameController.receiveInput(input); // valid
        gameController.receiveInput(input); // invalid
        assertEquals(2, gameController.getCurrentPlayer(), "Player should not switch after invalid move");
    }

    @Test
    void getBoardCellsAfterMove() {
        gameController.game.makeMove(0, 0);
        int[][] board = gameController.getBoardCells(0).get(0);
        assertEquals(1, board[0][0], "Top-left cell should be marked by Player 1");
    }

}