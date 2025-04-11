package client_main.java.GameLogic_Client.TicTacToe;

import GameLogic_Client.TicTacToe.TTTGameController;
import GameLogic_Client.Ivec2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TTTGameControllerTest class to test the functions and methods of the TTTGameController class.
 * Author: Emma Djukic - Game Logic Team
 */
class TTTGameControllerTest {
    TTTGameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new TTTGameController();
    }

    @Test
    void getCurrentPlayer() {
        assertEquals(1, gameController.getCurrentPlayer(), "Current player should be X at start of the game");
        gameController.makeMove(0, 0);
        assertEquals(2, gameController.getCurrentPlayer(), "Current player should now be O after first turn");
        gameController.makeMove(1, 1);
        assertEquals(1, gameController.getCurrentPlayer(), "Current player should switch back to X after second turn");
    }

    @Test
    void removePlayerOutOfBounds() {
        Exception ex = assertThrows(IndexOutOfBoundsException.class, () -> {
            gameController.removePlayer(2); // should throw index out of bounds exception as 2 > 1
        });
        assertEquals("Invalid player index.", ex.getMessage());
    }

    @Test
    void removePlayerSuccess() {
        assertDoesNotThrow(() -> {
            gameController.removePlayer(0); // should not throw exception as index is in bounds
        });
        assertDoesNotThrow(() -> {
            gameController.removePlayer(1); // should not throw exception as index is in bounds
        });
    }

    @Test
    void getWinnerNone() {
        assertEquals(3, gameController.getWinner(), "No winner at the start of the game");
    }

    @Test
    void getWinnerX() {
        gameController.makeMove(0, 0);
        gameController.makeMove(0, 1);
        gameController.makeMove(1, 0);
        gameController.makeMove(1, 1);
        gameController.makeMove(2, 0); // player 1 (X) should win here
        assertEquals(0, gameController.getWinner(), "Player 1 (X) should win after this move");
    }

    @Test
    void getWinnerO() {
        gameController.makeMove(0, 0);
        gameController.makeMove(0, 1);
        gameController.makeMove(1, 0);
        gameController.makeMove(1, 1);
        gameController.makeMove(2, 2);
        gameController.makeMove(2, 1); // player 2 (O) should win here
        assertEquals(1, gameController.getWinner(), "Player 2 (O) should win after this move");
    }

    @Test
    void getWinnerDraw() {
        gameController.makeMove(0, 0); //player 1
        gameController.makeMove(0,1);  //player 2
        gameController.makeMove(0,2);  //player 1
        gameController.makeMove(1,1);  //player 2
        gameController.makeMove(1,0);  //player 1
        gameController.makeMove(1,2);  //player 2
        gameController.makeMove(2,1);  //player 1
        gameController.makeMove(2,0);  //player 2
        gameController.makeMove(2,2);  //player 1
        // this should result in a draw
        assertEquals(2, gameController.getWinner(), "The game should end in a draw");
    }

    @Test
    void getGameOngoingTrue() {
        assertTrue(gameController.getGameOngoing(), "Game should be ongoing at the start");
    }

    @Test
    void getGameOngoingFalse() {
        gameController.makeMove(0, 0);
        gameController.makeMove(0, 1);
        gameController.makeMove(1, 0);
        gameController.makeMove(1, 1);
        gameController.makeMove(2, 0); // X should win here, thus ending the game
        assertFalse(gameController.getGameOngoing(), "Game should now be completed after a win");
    }

    @Test
    void getBoardSize() {
        Ivec2 boardSize = new Ivec2(3, 3);
        assertEquals(boardSize, gameController.getBoardSize(), "Tic-Tac-Toe board size should always be 3x3");
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
        gameController.receiveInput(input); // invalid, same spot
        assertEquals(2, gameController.getCurrentPlayer(), "Player should not switch after invalid move");
    }

    @Test
    void getBoardCellsAfterMove() {
        gameController.makeMove(0, 0);
        int[][] board = gameController.getBoardCells(0).get(0);
        assertEquals(1, board[0][0], "Top-left cell should be marked by Player 1");
    }

    @Test
    void changeTrackersDefaultValues() {
        assertFalse(gameController.gameOngoingChangedSinceLastCommand(), "Game ongoing status should not have changed yet");
        assertFalse(gameController.winnersChangedSinceLastCommand(), "Winner status should not have changed yet");
        assertFalse(gameController.currentPlayerChangedSinceLastCommand(), "Current player should not have changed yet");
        assertEquals(1, gameController.boardChangedSinceLastCommand(), "Board state should have changed at least once after starting");
    }

    @Test
    void testMultipleMovesAndWinner() {
        gameController.makeMove(0, 0);
        gameController.makeMove(1, 0);
        gameController.makeMove(0, 1);
        gameController.makeMove(1, 1);
        gameController.makeMove(0, 2); // player 1 should win with a row of X's
        assertEquals(0, gameController.getWinner(), "Player 1 should win with a horizontal row");
    }

    @Test
    void testInvalidMoveOnOccupiedTile() {
        gameController.makeMove(0, 0);
        gameController.makeMove(0, 0); // invalid move on the same tile
        assertEquals(2, gameController.getCurrentPlayer(), "Player should not switch after an invalid move");
    }

    @Test
    void testFullBoardNoWinner() {
        // Fill up the board with a situation where there is no winner (a tie).
        gameController.makeMove(0, 0); // X
        gameController.makeMove(0, 1); // O
        gameController.makeMove(0, 2); // X
        gameController.makeMove(1, 1);
        gameController.makeMove(1, 0);
        gameController.makeMove(1, 2);
        gameController.makeMove(2, 1);
        gameController.makeMove(2, 0);
        gameController.makeMove(2, 2);
        assertEquals(2, gameController.getWinner(), "The game should end in a draw (tie)");
    }
}
