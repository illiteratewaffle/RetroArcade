package client_main_tests.java.GameLogic_Client.TicTacToe;



import static org.junit.jupiter.api.Assertions.*;

import GameLogic_Client.TicTacToe.TTTBoard;
import GameLogic_Client.TicTacToe.TTTPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GameLogic_Client.ivec2;


class TTTBoardTest {
    private TTTBoard board;

    @BeforeEach
    void setUp() {
        board = new TTTBoard();
    }

    @Test
    void testBoardInitialization() {
        int[][] expected = { {0, 0, 0}, {0, 0, 0}, {0, 0, 0} };
        assertArrayEquals(expected, board.getBoard(), "Board should initialize to all zeros.");
    }

    @Test
    void testSetPiece() {
        board.setPiece(new ivec2(1, 1), 1);
        assertEquals(1, board.getPiece(new ivec2(1, 1)), "Piece should be set correctly.");
    }

    @Test
    void testCheckWinner_RowWin() {
        board.setPiece(new ivec2(0, 0), 1);
        board.setPiece(new ivec2(0, 1), 1);
        board.setPiece(new ivec2(0, 2), 1);
        assertEquals(TTTPiece.X, board.checkWinner(), "X should win with a row.");
    }

    @Test
    void testCheckWinner_ColumnWin() {
        board.setPiece(new ivec2(0, 0), 2);
        board.setPiece(new ivec2(1, 0), 2);
        board.setPiece(new ivec2(2, 0), 2);
        assertEquals(TTTPiece.O, board.checkWinner(), "O should win with a column.");
    }

    @Test
    void testCheckWinner_DiagonalWin() {
        board.setPiece(new ivec2(0, 0), 1);
        board.setPiece(new ivec2(1, 1), 1);
        board.setPiece(new ivec2(2, 2), 1);
        assertEquals(TTTPiece.X, board.checkWinner(), "X should win with a diagonal.");
    }

    @Test
    void testCheckWinner_NoWin() {
        board.setPiece(new ivec2(0, 0), 1);
        board.setPiece(new ivec2(1, 1), 2);
        board.setPiece(new ivec2(2, 2), 1);
        assertEquals(TTTPiece.EMPTY, board.checkWinner(), "No player should win yet.");
        assertEquals(TTTPiece.EMPTY, board.checkWinner(), "No player should win yet.");
    }
}