// setting correct package in tests root
package client_main.java.GameLogic_Client.Checkers;

// importing checkers move and ivec2
import GameLogic_Client.Checkers.CheckersMove;
import GameLogic_Client.Ivec2;

// importing test and assertions
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * checkers move test class which tests the methods inside the checkers move class in source root
 */
public class CheckersMoveTest {
    private CheckersMove checkersMove;
    private Ivec2 startCord;
    private Ivec2 targetCord;
    private Ivec2 captureCord;

    @BeforeEach
    public void setUp() {
        // creating start, target, and end cord values
        startCord = new Ivec2(0, 0);
        targetCord = new Ivec2(1, 1);
        captureCord = new Ivec2(1,1);
        checkersMove = new CheckersMove(startCord, targetCord, captureCord);
    }
    /**
     * this test checks the return value of the get start cord method
     */
    @Test
    public void testCheckersMoveGetStartCord() {
        // if the start cord from the getter is equal to the initial start cord test passes
        assertEquals(checkersMove.getStartCoordinate(), startCord);
    }

    /**
     * this test checks the return value of the get target cord method
     */
    @Test
    public void testCheckersMoveGetTargetCord() {
        // if the target cord from the getter is equal to the initial target cord test passes
        assertEquals(checkersMove.getTargetCoordinate(), targetCord);
    }

    /**
     * this test case checks the return value of the get capture cord method
     */
    @Test
    public void testCheckersMoveGetCaptureCord() {
        // if the capture cord from the getter is equal to the initial capture cord test passes
        assertEquals(checkersMove.getCaptureCoordinate(), captureCord);
    }

    /**
     * this test checks if the set start cord method does the correct operation
     */
    @Test
    public void testCheckersMoveSetStartCoordinate() {
        // creating a set cord vector
        Ivec2 setCord = new Ivec2(1, 1);
        // call the associated set cord method
        checkersMove.setStartCoordinate(setCord);
        // check if the value of set cord equals the return from the associated getter method
        assertEquals(setCord, checkersMove.getStartCoordinate());
    }

    /**
     * this test checks if the set target cord method does the correct operation
     */
    @Test
    public void testCheckersMoveSetTargetCoordinate() {
        // creating a set cord vector
        Ivec2 setCord = new Ivec2(1, 1);
        // call the associated set cord method
        checkersMove.setTargetCoordinate(setCord);
        // check if the value of set cord equals the return from the associated getter method
        assertEquals(setCord, checkersMove.getTargetCoordinate());
    }

    /**
     * this test checks if the set capture cord method does the correct operation
     */
    @Test
    public void testCheckersMoveSetCaptureCoordinate() {
        // creating a set cord vector
        Ivec2 setCord = new Ivec2(1, 1);
        // call the associated set cord method
        checkersMove.setCaptureCoordinate(setCord);
        // check if the value of set cord equals the return from the associated getter method
        assertEquals(setCord, checkersMove.getCaptureCoordinate());
    }

    /**
     * this test checks the return boolean of the is capture method
     */
    @Test
    public void testIsCaptureMethod() {
        // if the method returns true test passes
        assertTrue(checkersMove.isCapture());
    }

    @Test
    public void testIsCaptureMethodNull() {
        // set capture cord to null
        checkersMove.setCaptureCoordinate(null);
        // assert that is capture is false
        assertFalse(checkersMove.isCapture());
    }
}
