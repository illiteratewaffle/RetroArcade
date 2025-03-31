package client_main.java.GameLogic_Client.TicTacToe;

import GameLogic_Client.TicTacToe.TTTBoard;
import GameLogic_Client.TicTacToe.TTTGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class TTTGameTest {
    private TTTGame game;
    private TTTBoard board;

    @BeforeEach
    void setUp() {
        game = new TTTGame();
        board = game.board;
    }

    @Test
    void makeMove() {
        assertTrue(game.makeMove(0, 0), "Move should be valid as the board is empty");
        assertFalse(game.makeMove(0, 0), "Move should be invalid as this tile is not empty");

        assertFalse(game.makeMove(0, 3), "Move should be invalid as the board is empty");
    }

    @Test
    void switchTurn() {
        int currentPlayer = game.currentPlayer;
        assertEquals(1, currentPlayer, "Current player should be 1 at the start of the game");
        game.switchTurn();
        assertEquals(2, currentPlayer, "Current player should be 2 after first turn ends");
        game.switchTurn();
        assertEquals(1, currentPlayer, "Current player should be 1 after player 2 ends turn");
    }

    @Test
    void checkWin() {
        game.makeMove(0, 0);
        game.switchTurn();
        game.makeMove(1,0);
        game.switchTurn();
        game.makeMove(0,1);
        assertFalse(game.checkWin(board), "should return false as no player has achieved a win condition");
        game.switchTurn();
        game.makeMove(1,1);
        game.switchTurn();
        game.makeMove(0,2);
        assertTrue(game.checkWin(board), "should return true as player 3 has a tile in the first row");
    }

    @Test
    void checkDraw() {
        game.makeMove(0, 0); //player 1
        game.switchTurn();
        game.makeMove(0,2);  //player 2
        game.switchTurn();
        game.makeMove(0,1);  //player 1
        game.switchTurn();
        game.makeMove(2,0);  //player 2
        game.switchTurn();
        game.makeMove(1,0);  //player 1
        assertFalse(game.checkDraw(board), "Should return false as game is still ongoing and no win condition is met");
        game.switchTurn();
        game.makeMove(1,1);  //player 2
        game.switchTurn();
        game.makeMove(1,2);  //player 1
        game.switchTurn();
        game.makeMove(2,1);  //player 2
        game.switchTurn();
        game.makeMove(2,2);  //player 1
        assertTrue(game.checkDraw(board), "should return true as no win condition is met and all tiles are full");

    }

    @Test
    void isGameOver() {
    }

    @Test
    void printBoard() {
    }
}