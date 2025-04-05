// setting correct package in tests root
package client_main.java.GameLogic_Client.Checkers;

// importing checkers move and ivec2
import GameLogic_Client.Checkers.CheckersMove;
import GameLogic_Client.Ivec2;

// importing test and assertions
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * checkers move test class which tests the methods inside the checkers move class in source root
 */
public class CheckersMoveTest {
    /**
     * this test checks the return value of the get start cord method
     */
    @Test
    public void testCheckersMoveGetStartCord() {
        // creating start, target, and end cord values
        Ivec2 startCord = new Ivec2(0, 0);
        Ivec2 targetCord = new Ivec2(1, 1);
        Ivec2 captureCord = new Ivec2(1,1);

        // instantiating the move class passing in the values above
        CheckersMove move = new CheckersMove(startCord, targetCord, captureCord);
        // if the start cord from the getter is equal to the initial start cord test passes
        assertEquals(move.getStartCoordinate(), startCord);
    }

    /**
     * this test checks the return value of the get target cord method
     */
    @Test
    public void testCheckersMoveGetTargetCord() {
        // creating start, target, and end cord values
        Ivec2 startCord = new Ivec2(2, 2);
        Ivec2 targetCord = new Ivec2(3, 3);
        Ivec2 captureCord = new Ivec2(6,6);

        // instantiating the move class passing in the values above
        CheckersMove move = new CheckersMove(startCord, targetCord, captureCord);
        // if the target cord from the getter is equal to the initial target cord test passes
        assertEquals(move.getTargetCoordinate(), targetCord);
    }

    /**
     * this test case checks the return value of the get capture cord method
     */
    @Test
    public void testCheckersMoveGetCaptureCord() {
        // creating start, target, and end cord values
        Ivec2 startCord = new Ivec2(4, 4);
        Ivec2 targetCord = new Ivec2(5, 5);
        Ivec2 captureCord = new Ivec2(7,7);

        // instantiating the move class passing in the values above
        CheckersMove move = new CheckersMove(startCord, targetCord, captureCord);
        // if the capture cord from the getter is equal to the initial capture cord test passes
        assertEquals(move.getCaptureCoordinate(), captureCord);
    }
}
