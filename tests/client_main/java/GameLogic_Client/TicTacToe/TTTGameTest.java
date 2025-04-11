package client_main.java.GameLogic_Client.TicTacToe;

/**
 * TTTGameTest class to test the functions and methods of the TTTGame class.
 * Author: Vicente David, Emma Djukic - Game Logic Team
 */

import GameLogic_Client.GameState;
import GameLogic_Client.Ivec2;
import GameLogic_Client.TicTacToe.TTTGame;
import GameLogic_Client.TicTacToe.TTTPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TTTGameTest {

    private TTTGame game;

    @BeforeEach
    public void setUp() {
        game = new TTTGame();
    }

    @Test
    public void testGameInitialization() {
        assertEquals(1, game.currentPlayer, "The game should start with player 1.");
        assertEquals(GameState.ONGOING, game.gameState, "Game state should be ONGOING at the start.");
        assertNotNull(game.board, "Board should be initialized.");
    }

    @Test
    public void testValidMoveAndSwitchTurn() {
        assertTrue(game.makeMove(0, 0), "Valid move should return true.");
        assertEquals(2, game.currentPlayer, "After player 1's move, it should be player 2's turn.");
    }

    @Test
    public void testInvalidMoveOccupiedCell() {
        game.makeMove(0, 0); // Player 1 moves
        assertFalse(game.makeMove(0, 0), "Move to an occupied cell should return false.");
    }

    @Test
    public void testWinningMoveRow() {
        game.makeMove(0, 0); // X
        game.makeMove(1, 0); // O
        game.makeMove(0, 1); // X
        game.makeMove(1, 1); // O
        game.makeMove(0, 2); // X - win

        assertEquals(GameState.P1WIN, game.gameState, "Player 1 should win.");
        assertEquals(1, game.checkWin(game.board), "Player 1 should be the winner.");
        assertTrue(game.isGameOver(game.board), "Game should be over after a win.");
    }

    @Test
    public void testWinningMoveColumn() {
        game.makeMove(0, 0); // X
        game.makeMove(0, 1); // O
        game.makeMove(1, 0); // X
        game.makeMove(1, 1); // O
        game.makeMove(2, 0); // X - win

        assertEquals(GameState.P1WIN, game.gameState, "Player 1 should win.");
        assertEquals(1, game.checkWin(game.board), "Player 1 should be the winner.");
    }

    @Test
    public void testWinningMoveDiagonal() {
        game.makeMove(0, 0); // X
        game.makeMove(0, 1); // O
        game.makeMove(1, 1); // X
        game.makeMove(2, 1); // O
        game.makeMove(2, 2); // X - win

        assertEquals(GameState.P1WIN, game.gameState, "Player 1 should win.");
        assertEquals(1, game.checkWin(game.board), "Player 1 should be the winner.");
    }

    @Test
    public void testDrawGame() {
        game.makeMove(0, 0); // X
        game.makeMove(0, 1); // O
        game.makeMove(0, 2); // X
        game.makeMove(1, 1); // O
        game.makeMove(1, 0); // X
        game.makeMove(1, 2); // O
        game.makeMove(2, 1); // X
        game.makeMove(2, 0); // O
        game.makeMove(2, 2); // X

        assertEquals(GameState.TIE, game.gameState, "Game should end in a tie.");
        assertTrue(game.checkDraw(game.board), "The game should be a draw as there are no winners.");
        assertTrue(game.isGameOver(game.board), "Game should be over in a draw scenario.");
    }

    @Test
    void getPiece() {
        Ivec2 point = new Ivec2(0, 0);
        assertEquals(TTTPiece.EMPTY, game.getPiece(point), "Board should be empty initially.");

        game.makeMove(0, 0);
        assertEquals(TTTPiece.X, game.getPiece(point), "Should return 'X' for player 1's move at (0,0).");

        Ivec2 point2 = new Ivec2(0, 1);
        game.makeMove(1, 0);
        assertEquals(TTTPiece.O, game.getPiece(point2), "Should return 'O' for player 2's move at (0,1).");
    }

    @Test
    public void testCheckWinNoWinner() {
        game.makeMove(0, 0); // X
        game.makeMove(1, 1); // O
        assertEquals(0, game.checkWin(game.board), "No winner yet.");
    }

    @Test
    public void testCheckDrawFalseIfBoardNotFull() {
        game.makeMove(0, 0); // X
        game.makeMove(0, 1); // O
        assertFalse(game.checkDraw(game.board), "Board is not full, so it shouldn't be a draw.");
    }

    @Test
    void checkDraw() {
        game.makeMove(0, 0); //player 1
        game.makeMove(0,1);  //player 2
        game.makeMove(0,2);  //player 1
        game.makeMove(1,1);  //player 2
        game.makeMove(1,0);  //player 1
        assertFalse(game.checkDraw(game.board), "Should return false as game is still ongoing and no win condition is met.");

        game.makeMove(1,2);  //player 2
        game.makeMove(2,1);  //player 1
        game.makeMove(2,0);  //player 2
        game.makeMove(2,2);  //player 1
        assertTrue(game.checkDraw(game.board), "Should return true as no win condition is met and all tiles are full.");
    }

    @Test
    public void testSwitchTurnTwice() {
        game.makeMove(0, 0);
        assertEquals(2, game.currentPlayer, "After player 1's move, it should be player 2's turn.");
        game.makeMove(1, 1);
        assertEquals(1, game.currentPlayer, "After player 2's move, it should be player 1's turn.");
    }

    @Test
    void isGameOver() {
        game.makeMove(0, 0); //player 1
        game.makeMove(0,1); //player 2
        game.makeMove(0,2); //player 1
        assertFalse(game.isGameOver(game.board), "Game should not be over yet.");

        game.makeMove(1,0); //player 2
        game.makeMove(1,1); //player 1
        game.makeMove(1,2); //player 2
        game.makeMove(2,0); //player 1
        assertTrue(game.isGameOver(game.board), "Game should be over as player 1 wins.");
    }

    @Test
    public void testGameResetAfterWin() {
        game.makeMove(0, 0); // X
        game.makeMove(1, 0); // O
        game.makeMove(0, 1); // X
        game.makeMove(1, 1); // O
        game.makeMove(0, 2); // X - win

        assertEquals(GameState.P1WIN, game.gameState);
        game = new TTTGame(); // Reset game
        assertEquals(GameState.ONGOING, game.gameState, "Game should reset to ongoing after a win.");
        assertEquals(1, game.currentPlayer, "Player 1 should start after reset.");
    }
}
