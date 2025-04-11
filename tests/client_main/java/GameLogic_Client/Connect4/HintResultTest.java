package client_main.java.GameLogic_Client.Connect4;

import GameLogic_Client.Connect4.HintResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HintResultTest {
    //only testing that the constructor is instantiating correctly

    @Test
    void testConstructorTypeWin() {
        HintResult hint = new HintResult(3, "WIN");

        assertEquals(3, hint.col);
        assertEquals("WIN", hint.type);
    }

    @Test
    void testConstructorTypeBlock() {
        HintResult hint = new HintResult(5, "BLOCK");

        assertEquals(5, hint.col);
        assertEquals("BLOCK", hint.type);
    }

    @Test
    void testConstructorTypeNone() {
        HintResult hint = new HintResult(-1, "NONE");

        assertEquals(-1, hint.col);
        assertEquals("NONE", hint.type);
    }
}