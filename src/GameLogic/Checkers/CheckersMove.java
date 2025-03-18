// setting package to checkers
package GameLogic.Checkers;
// importing the ivec2 class from game logic package
import GameLogic.ivec2;

/**
 * The checkers move class allows us to distinguish between a start cord, target cord, and capture cord
 */
public class CheckersMove {
    // private ivec2 fields for start, target, and capture cord
    private ivec2 StartCord;
    private ivec2 TargetCord;
    private ivec2 CaptureCord;

    /**
     * The constructor for the move object takes in three values the start, target, and capture cord values which are ivec2 objects
     * @param startCord the start cord vector
     * @param targetCord the target cord vector
     * @param captureCord the capture cord vector
     */
    public CheckersMove(ivec2 startCord, ivec2 targetCord, ivec2 captureCord) {
        this.StartCord = startCord;
        this.TargetCord = targetCord;
        this.CaptureCord = captureCord;
    }
    /**
     * gets the start cord and returns it
     * @return the start cord ivec2
     */
    public ivec2 getStartCord() {
        return StartCord;
    }

    /**
     * takes in a value for the start cord and sets the start cord for the current move object
     * @param startCord the value that will be set as the new start cord
     */
    public void setStartCord(ivec2 startCord) {
        this.StartCord = startCord;
    }

    /**
     * gets the target cord and returns it
     * @return the target cord ivec2
     */
    public ivec2 getTargetCord() {
        return TargetCord;
    }

    /**
     * takes in a value for the target cord and sets the target cord for the current move object
     * @param targetCord the value that will be set as the new target cord
     */
    public void setTargetCord(ivec2 targetCord) {
        this.TargetCord = targetCord;
    }

    /**
     * gets the capture cord and returns it
     * @return the capture cord ivec2
     */
    public ivec2 getCaptureCord() {
        return CaptureCord;
    }

    /**
     * takes in a value for the capture cord and sets the target cord for the capture move object
     * @param captureCord the value that will be set as the new capture cord
     */
    public void setCaptureCord(ivec2 captureCord) {
        this.CaptureCord = captureCord;
    }
}