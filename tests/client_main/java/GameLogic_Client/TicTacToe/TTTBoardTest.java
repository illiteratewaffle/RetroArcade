package client_main.java.GameLogic_Client.TicTacToe;

import static org.junit.jupiter.api.Assertions.*;

import GameLogic_Client.TicTacToe.TTTBoard;
import GameLogic_Client.TicTacToe.TTTPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GameLogic_Client.Ivec2;

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
        board.setPiece(new Ivec2(1, 1), 1);
        assertEquals(1, board.getPiece(new Ivec2(1, 1)), "Piece should be set correctly.");
    }

    @Test
    void testPlacePiece_ValidMove() {
        boolean result = board.placePiece(new Ivec2(0, 0), TTTPiece.X);
        assertTrue(result, "Valid move should return true.");
        assertEquals(TTTPiece.X.getValue(), board.getPiece(new Ivec2(0, 0)), "Piece should be placed correctly.");
    }

    @Test
    void testPlacePiece_AlreadyOccupied() {
        board.placePiece(new Ivec2(0, 0), TTTPiece.X);
        boolean result = board.placePiece(new Ivec2(0, 0), TTTPiece.O);
        assertFalse(result, "Move on an already occupied space should return false.");
    }

    @Test
    void testCheckWinner_RowWin() {
        board.setPiece(new Ivec2(0, 0), 1);
        board.setPiece(new Ivec2(0, 1), 1);
        board.setPiece(new Ivec2(0, 2), 1);
        assertEquals(TTTPiece.X, board.checkWinner(), "X should win with a row.");
    }

    @Test
    void testCheckWinner_ColumnWin() {
        board.setPiece(new Ivec2(0, 0), 2);
        board.setPiece(new Ivec2(1, 0), 2);
        board.setPiece(new Ivec2(2, 0), 2);
        assertEquals(TTTPiece.O, board.checkWinner(), "O should win with a column.");
    }

    @Test
    void testCheckWinner_DiagonalWin() {
        board.setPiece(new Ivec2(0, 0), 1);
        board.setPiece(new Ivec2(1, 1), 1);
        board.setPiece(new Ivec2(2, 2), 1);
        assertEquals(TTTPiece.X, board.checkWinner(), "X should win with a diagonal.");
    }

    @Test
    void testCheckWinner_AntiDiagonalWin() {
        board.setPiece(new Ivec2(0, 2), 2);
        board.setPiece(new Ivec2(1, 1), 2);
        board.setPiece(new Ivec2(2, 0), 2);
        assertEquals(TTTPiece.O, board.checkWinner(), "O should win with an anti-diagonal.");
    }

    @Test
    void testCheckWinner_NoWin() {
        board.setPiece(new Ivec2(0, 0), 1);
        board.setPiece(new Ivec2(1, 1), 2);
        board.setPiece(new Ivec2(2, 2), 1);
        assertEquals(TTTPiece.EMPTY, board.checkWinner(), "No winner yet.");
    }

    @Test
    void testIsFull_BoardFull() {
        board.setPiece(new Ivec2(0, 0), 1);
        board.setPiece(new Ivec2(0, 1), 2);
        board.setPiece(new Ivec2(0, 2), 1);
        board.setPiece(new Ivec2(1, 0), 2);
        board.setPiece(new Ivec2(1, 1), 1);
        board.setPiece(new Ivec2(1, 2), 2);
        board.setPiece(new Ivec2(2, 0), 1);
        board.setPiece(new Ivec2(2, 1), 2);
        board.setPiece(new Ivec2(2, 2), 1);
        assertTrue(board.isFull(), "Board should be full after all pieces are placed.");
    }

    @Test
    void testIsFull_BoardNotFull() {
        board.setPiece(new Ivec2(0, 0), 1);
        board.setPiece(new Ivec2(0, 1), 2);
        assertFalse(board.isFull(), "Board should not be full with empty spots.");
    }

    @Test
    void testIsEmpty_EmptySpot() {
        assertTrue(board.isEmpty(new Ivec2(1, 1)), "Spot should be empty initially.");
    }

    @Test
    void testIsEmpty_OccupiedSpot() {
        board.setPiece(new Ivec2(1, 1), 1);
        assertFalse(board.isEmpty(new Ivec2(1, 1)), "Spot should be occupied after placing a piece.");
    }
}
