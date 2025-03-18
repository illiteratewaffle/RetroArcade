package GameLogic.TicTacToe;


import static org.junit.jupiter.api.Assertions.*;

import GameLogic.ivec2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        assertTrue(board.checkWinner(1), "X should win with a row.");
    }

    @Test
    void testCheckWinner_ColumnWin() {
        board.setPiece(new ivec2(0, 0), 2);
        board.setPiece(new ivec2(1, 0), 2);
        board.setPiece(new ivec2(2, 0), 2);
        assertTrue(board.checkWinner(2), "O should win with a column.");
    }

    @Test
    void testCheckWinner_DiagonalWin() {
        board.setPiece(new ivec2(0, 0), 1);
        board.setPiece(new ivec2(1, 1), 1);
        board.setPiece(new ivec2(2, 2), 1);
        assertTrue(board.checkWinner(1), "X should win with a diagonal.");
    }

    @Test
    void testCheckWinner_NoWin() {
        board.setPiece(new ivec2(0, 0), 1);
        board.setPiece(new ivec2(1, 1), 2);
        board.setPiece(new ivec2(2, 2), 1);
        assertFalse(board.checkWinner(1), "No player should win yet.");
        assertFalse(board.checkWinner(2), "No player should win yet.");
    }
}