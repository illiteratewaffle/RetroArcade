package client_main.java.GameLogic_Client.Connect4;

import GameLogic_Client.Connect4.C4Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class C4PieceTest {

    @Test
    void testGetValue() {
        assertEquals(0, C4Piece.BLANK.getValue());
        assertEquals(1, C4Piece.RED.getValue());
        assertEquals(2, C4Piece.BLUE.getValue());    }

    @Test
    void fromIntInvalid() {
        assertEquals(C4Piece.BLANK, C4Piece.fromInt(3));  //invalid values should return blank
        assertEquals(C4Piece.BLANK, C4Piece.fromInt(-1));  //invalid values should return blank
    }

    @Test
    void testEnumValues() { //test that ensures enum constants are not changed
        C4Piece[] values = C4Piece.values();
        assertArrayEquals(new C4Piece[]{C4Piece.RED, C4Piece.BLUE, C4Piece.BLANK}, values);
    }
}