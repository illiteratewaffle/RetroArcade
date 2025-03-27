//package client_main.java.GameLogic_Client.TicTacToe;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//class TTTGameTest {
//    private TTTGame game;
//    private TTTBoard board;
//
//    @BeforeEach
//    void setUp() {
//        game = new TTTGame();
//        board = game.board;
//    }
//
//    @Test
//    void makeMove() {
//        assertTrue(game.makeMove(0, 0), "Move should be valid as the board is empty");
//        assertFalse(game.makeMove(0, 0), "Move should be invalid as this tile is not empty");
//
//        assertFalse(game.makeMove(0, 3), "Move should be invalid as the board is empty");
//    }
//
//    @Test
//    void switchTurn() {
//    }
//
//    @Test
//    void checkWin() {
//    }
//
//    @Test
//    void checkDraw() {
//    }
//
//    @Test
//    void isGameOver() {
//    }
//
//    @Test
//    void printBoard() {
//    }
//}