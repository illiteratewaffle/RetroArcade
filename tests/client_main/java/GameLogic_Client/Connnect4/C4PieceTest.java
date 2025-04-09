package client_main.java.GameLogic_Client.Connnect4;

import static org.junit.jupiter.api.Assertions.*;

import GameLogic_Client.Connect4.C4Piece;
import org.junit.jupiter.api.Test;

class C4PieceTest {

    @Test
    void testGetValue() {
        assertEquals(1, C4Piece.RED.getValue());
        assertEquals(2, C4Piece.BLUE.getValue());
        assertEquals(0, C4Piece.BLANK.getValue());
    }

    @Test
    void testFromIntValidInputs() {
        assertEquals(C4Piece.RED, C4Piece.fromInt(1));
        assertEquals(C4Piece.BLUE, C4Piece.fromInt(2));
        assertEquals(C4Piece.BLANK, C4Piece.fromInt(0));
    }

    @Test
    void testFromIntInvalidInput() {
        assertEquals(C4Piece.BLANK, C4Piece.fromInt(-1));
        assertEquals(C4Piece.BLANK, C4Piece.fromInt(99));
    }
}
