// setting package to checkers
package client_main.java.GameLogic_Client.Checkers;
// importing the ivec2 class from game logic package
import client_main.java.GameLogic_Client.Ivec2;

/**
 * The checkers move class allows us to distinguish between a start cord, target cord, and capture cord
 */
public class CheckersMove {
    // private ivec2 fields for start, target, and capture cord
    private Ivec2 startCord;
    private Ivec2 targetCord;
    private Ivec2 captureCord;

    /**
     * The constructor for the move object takes in three values the start, target, and capture cord values which are ivec2 objects
     * @param startCord the start cord vector
     * @param targetCord the target cord vector
     * @param captureCord the capture cord vector
     */
    public CheckersMove(Ivec2 startCord, Ivec2 targetCord, Ivec2 captureCord) {
        this.startCord = startCord;
        this.targetCord = targetCord;
        this.captureCord = captureCord;
    }
    /**
     * gets the start cord and returns it
     * @return the start cord ivec2
     */
    public Ivec2 getStartCord() {
        return startCord;
    }

    /**
     * takes in a value for the start cord and sets the start cord for the current move object
     * @param startCord the value that will be set as the new start cord
     */
    public void setStartCord(Ivec2 startCord) {
        this.startCord = startCord;
    }

    /**
     * gets the target cord and returns it
     * @return the target cord ivec2
     */
    public Ivec2 getTargetCord() {
        return targetCord;
    }

    /**
     * takes in a value for the target cord and sets the target cord for the current move object
     * @param targetCord the value that will be set as the new target cord
     */
    public void setTargetCord(Ivec2 targetCord) {
        this.targetCord = targetCord;
    }

    /**
     * gets the capture cord and returns it
     * @return the capture cord ivec2
     */
    public Ivec2 getCaptureCord() {
        return captureCord;
    }

    /**
     * takes in a value for the capture cord and sets the target cord for the capture move object
     * @param captureCord the value that will be set as the new capture cord
     */
    public void setCaptureCord(Ivec2 captureCord) {
        this.captureCord = captureCord;
    }
}