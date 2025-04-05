package client_main.java.GameLogic_Client.Checkers;

import GameLogic_Client.Checkers.CheckersBoard;
import GameLogic_Client.Checkers.CheckersMove;
import GameLogic_Client.Checkers.CheckersPiece;
import GameLogic_Client.Ivec2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class CheckersBoardTest {
    private CheckersBoard checkersBoard;

    @BeforeEach
    public void setUp() {
        int rows = 8;
        int cols = 8;
        int[][] board = new int[rows][cols];
        checkersBoard = new CheckersBoard(rows,cols, board);
    }

    @Test
    public void testInitialPieceLocations() {
        HashSet<Ivec2> p1Locations = checkersBoard.getP1PieceLocations();
        HashSet<Ivec2> p2Locations = checkersBoard.getP2PieceLocations();

        assertNotNull(p1Locations);
        assertNotNull(p2Locations);
        assertTrue(p1Locations.isEmpty());
        assertTrue(p2Locations.isEmpty());
    }

    @Test
    public void testSetPieceP1() {
        Ivec2 position = new Ivec2(0, 0);
        int p1Piece = CheckersPiece.P1PAWN.getValue(); // Assume P1 piece has this value.

        checkersBoard.setPiece(position, p1Piece);
        assertTrue(checkersBoard.isP1(position));
        assertTrue(checkersBoard.getP1PieceLocations().contains(position));
    }

    @Test
    public void testSetPieceP2() {
        Ivec2 position = new Ivec2(1, 1);
        int p2Piece = CheckersPiece.P2PAWN.getValue(); // Assume P2 piece has this value.

        checkersBoard.setPiece(position, p2Piece);
        assertTrue(checkersBoard.isP2(position));
        assertTrue(checkersBoard.getP2PieceLocations().contains(position));
    }

    @Test
    public void testMakeMove() {
        // Setup for the test
        Ivec2 start = new Ivec2(3, 3);  // The starting position of the piece
        Ivec2 target = new Ivec2(4, 4); // The target position of the piece
        Ivec2 capture = new Ivec2(5, 5); // The position of the captured piece (can be null if no capture)

        // Assume we have a Player 1 piece at start position
        int p1Piece = CheckersPiece.P1PAWN.getValue();
        checkersBoard.setPiece(start, p1Piece);

        CheckersMove move = new CheckersMove(start, target, capture);
        checkersBoard.makeMove(move);

        assertFalse(checkersBoard.getP1PieceLocations().contains(start), "Start position should no longer have the piece.");
        assertTrue(checkersBoard.getP1PieceLocations().contains(target), "Target position should now have the piece.");
        assertFalse(checkersBoard.getP1PieceLocations().contains(capture), "Captured position should no longer have the piece.");
    }


    @Test
    public void testRemovePiece() {
        Ivec2 position = new Ivec2(2, 2);
        int p1Piece = CheckersPiece.P1PAWN.getValue();
        checkersBoard.setPiece(position, p1Piece);

        checkersBoard.setPiece(position, CheckersPiece.NONE.getValue());
        assertFalse(checkersBoard.getP1PieceLocations().contains(position));
        assertFalse(checkersBoard.isP1(position));
    }

    @Test
    public void testIsValidTile() {
        Ivec2 validTile = new Ivec2(3, 3);
        Ivec2 invalidTile = new Ivec2(8, 8);

        assertTrue(checkersBoard.isValidTile(validTile));
        assertFalse(checkersBoard.isValidTile(invalidTile));
    }

    @Test
    public void testIsPiece() {
        Ivec2 position = new Ivec2(5, 5);
        int p1Piece = CheckersPiece.P1PAWN.getValue();

        checkersBoard.setPiece(position, p1Piece);

        assertTrue(checkersBoard.isPiece(position));
    }

    @Test
    public void testIsP1() {
        Ivec2 position = new Ivec2(6, 6);
        int p1Piece = CheckersPiece.P1PAWN.getValue();

        checkersBoard.setPiece(position, p1Piece);

        assertTrue(checkersBoard.isP1(position));
        assertFalse(checkersBoard.isP2(position));
    }

    @Test
    public void testIsP2() {
        Ivec2 position = new Ivec2(7, 7);
        int p2Piece = CheckersPiece.P2PAWN.getValue();

        checkersBoard.setPiece(position, p2Piece);

        assertTrue(checkersBoard.isP2(position));
        assertFalse(checkersBoard.isP1(position));
    }
}
