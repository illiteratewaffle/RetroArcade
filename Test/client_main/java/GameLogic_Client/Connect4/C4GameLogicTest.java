package client_main.java.GameLogic_Client.Connect4;

import GameLogic_Client.Connect4.*;
import GameLogic_Client.ivec2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class C4GameLogicTest {

    private C4GameLogic game;

    @BeforeEach
    void setUp() {
        game = new C4GameLogic();
    }

    @Test
    void testInitialPlayerIsRed() {
        assertEquals(C4Piece.RED, game.getC4CurrentPlayer());
    }

    @Test
    void testGetC4ColTopBlankInitially() {
        assertEquals(5, game.getC4ColTopBlank(0));
    }

    @Test
    void testDropPieceSuccessfully() {
        boolean result = game.c4DropPiece(0, C4Piece.RED);
        assertTrue(result);
        assertEquals(C4Piece.RED, game.getC4Board().getC4Board()[5][0]);
        assertArrayEquals(new int[]{5, 0}, game.getC4lastPlayedPosition());
        assertEquals(1, game.getc4PiecesPlayed());
    }

    @Test
    void testSwitchPlayerAfterMove() {
        game.c4DropPiece(0, C4Piece.RED);
        assertEquals(C4Piece.BLUE, game.getC4CurrentPlayer());
    }

    @Test
    void testIsGameOverInitiallyFalse() {
        assertFalse(game.getC4IsGameOver());
    }

    @Test
    void testDropPieceInFullColumn() {
        for (int i = 0; i < 6; i++) {
            assertTrue(game.c4DropPiece(0, game.getC4CurrentPlayer()));
        }
        assertFalse(game.c4DropPiece(0, game.getC4CurrentPlayer()));
    }

    @Test
    void testGetC4WinnerInitiallyBlank() {
        assertEquals(C4Piece.BLANK, game.getC4Winner());
    }

    @Test
    void testWinDetection() {
        game.c4DropPiece(0, C4Piece.RED); // row 5
        game.c4DropPiece(1, C4Piece.BLUE);
        game.c4DropPiece(0, C4Piece.RED); // row 4
        game.c4DropPiece(1, C4Piece.BLUE);
        game.c4DropPiece(0, C4Piece.RED); // row 3
        game.c4DropPiece(1, C4Piece.BLUE);
        game.c4DropPiece(0, C4Piece.RED); // row 2 - win

        assertTrue(game.getC4IsGameOver());
        assertEquals(C4Piece.RED, game.getC4Winner());
    }

    @Test
    void testDrawCondition() {
        C4Piece current = C4Piece.RED;
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                game.c4DropPiece(col, current);
                current = (current == C4Piece.RED) ? C4Piece.BLUE : C4Piece.RED;
            }
        }
        assertTrue(game.getC4IsGameOver());
        assertEquals(C4Piece.BLANK, game.getC4Winner());
    }

    @Test
    void testColTopBlankAfterDrop() {
        game.c4DropPiece(3, C4Piece.RED);
        assertEquals(4, game.getC4ColTopBlank(3));
    }

    @Test
    void testToStringNotNull() {
        assertNotNull(game.toString());
    }

    @Test
    void testGetBoardReturnsBoard() {
        assertNotNull(game.getC4Board());
    }
}
