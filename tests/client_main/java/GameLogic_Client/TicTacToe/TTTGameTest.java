package client_main.java.GameLogic_Client.TicTacToe;
/**
 * TTGameTest class to test the functions and methods of the TTTGame class.
 * Author: Vicente David - Game Logic Team
 */

import GameLogic_Client.TicTacToe.TTTBoard;
import GameLogic_Client.TicTacToe.TTTGame;
import GameLogic_Client.TicTacToe.TTTPiece;
import GameLogic_Client.Ivec2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class TTTGameTest {
    private TTTGame game;

    @BeforeEach
    void setUp() {
        game = new TTTGame();
    }

    @Test
    void makeMove() {
        assertTrue(game.makeMove(0, 0), "Move should be valid as the board is empty");
        assertFalse(game.makeMove(0, 0), "Move should be invalid as this tile is not empty");
    }

    @Test
    void switchTurn() {
        assertEquals(1, game.currentPlayer, "Current player should be 1 at the start of the game");
        game.switchTurn();
        assertEquals(2, game.currentPlayer, "Current player should be 2 after first turn ends");
        game.switchTurn();
        assertEquals(1, game.currentPlayer, "Current player should be 1 after player 2 ends turn");
    }

    @Test
    void checkWin() {
        game.makeMove(0, 0);
        game.makeMove(1,0);
        game.makeMove(0,1);
        assertFalse(game.checkWin(game.board), "should return false as no player has achieved a win condition");
        game.makeMove(1,1);
        game.makeMove(0,2);
        assertTrue(game.checkWin(game.board), "should return true as player 3 has a tile in the first row");
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