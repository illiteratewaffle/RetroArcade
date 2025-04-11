package client_main.java.GameLogic_Client.TicTacToe;

/**
 * TTGameTest class to test the functions and methods of the TTTGame class.
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
        assertEquals(1, game.currentPlayer);
        assertEquals(GameState.ONGOING, game.gameState);
        assertNotNull(game.board);
    }

    @Test
    public void testValidMoveAndSwitchTurn() {
        assertTrue(game.makeMove(0, 0));
        assertEquals(2, game.currentPlayer);
    }

    @Test
    public void testInvalidMoveOccupiedCell() {
        assertTrue(game.makeMove(0, 0));
        assertFalse(game.makeMove(0, 0)); // same cell
    }

    @Test
    public void testWinningMoveRow() {
        game.makeMove(0, 0); // X
        game.makeMove(1, 0); // O
        game.makeMove(0, 1); // X
        game.makeMove(1, 1); // O
        game.makeMove(0, 2); // X - win

        assertEquals(GameState.P1WIN, game.gameState);
        assertEquals(1, game.checkWin(game.board));
        assertTrue(game.isGameOver(game.board));
    }

    @Test
    public void testWinningMoveDiagonal() {
        game.makeMove(0, 0); // X
        game.makeMove(0, 1); // O
        game.makeMove(1, 1); // X
        game.makeMove(2, 1); // O
        game.makeMove(2, 2); // X - win

        assertEquals(GameState.P1WIN, game.gameState);
        assertEquals(1, game.checkWin(game.board));
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

        assertEquals(GameState.TIE, game.gameState);
        assertTrue(game.checkDraw(game.board));
        assertTrue(game.isGameOver(game.board));
    }

    @Test
    void getPiece() {
        Ivec2 point = new Ivec2(0, 0);
        assertEquals(TTTPiece.EMPTY, game.getPiece(point), "Should get the empty piece as none have been placed");

        game.makeMove(0, 0);
        assertEquals(TTTPiece.X, game.getPiece(point), "Should get 'X' in top left tile");

        Ivec2 point2 = new Ivec2(0, 1);
        game.makeMove(1, 0);
        assertEquals(TTTPiece.O, game.getPiece(point2), "Should get 'O' in top middle tile");
    }

    @Test
    public void testCheckWinNoWinner() {
        game.makeMove(0, 0); // X
        game.makeMove(1, 1); // O
        assertEquals(0, game.checkWin(game.board));
    }

    @Test
    public void testCheckDrawFalseIfBoardNotFull() {
        game.makeMove(0, 0);
        assertFalse(game.checkDraw(game.board));
    }

    @Test
    void checkDraw() {
        game.makeMove(0, 0); //player 1
        game.makeMove(0,1);  //player 2
        game.makeMove(0,2);  //player 1
        game.makeMove(1,1);  //player 2
        game.makeMove(1,0);  //player 1
        assertFalse(game.checkDraw(game.board), "Should return false as game is still ongoing and no win condition is met");
        game.makeMove(1,2);  //player 2
        game.makeMove(2,1);  //player 1
        game.makeMove(2,0);  //player 2
        game.makeMove(2,2);  //player 1
        assertTrue(game.checkDraw(game.board), "should return true as no win condition is met and all tiles are full");
    }

    @Test
    public void testSwitchTurnTwice() {
        game.makeMove(0, 0);
        assertEquals(2, game.currentPlayer);
        game.makeMove(1, 1);
        assertEquals(1, game.currentPlayer);
    }

    @Test
    void isGameOver() {
        // NOTE: these tests are technically unnecessary and redundant as the isGameOver() function
        // is already being tested by checkDraw() and checkWin() methods.
        game.makeMove(0, 0); //player 1
        game.makeMove(0,1); //player 2
        game.makeMove(0,2); //player 1
        assertFalse(game.isGameOver(game.board), "should return false as no player has achieved a win/draw condition");
        game.makeMove(1,0); //player 2
        game.makeMove(1,1); //player 1
        game.makeMove(1,2); //player 2
        game.makeMove(2,0); //player 1
        assertTrue(game.isGameOver(game.board), "should return true as win condition is met by player 1");
    }
}