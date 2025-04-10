// setting package to checkers
package server.GameLogic_Client.Checkers;
// importing the Ivec2 class from game logic package
import server.GameLogic_Client.Ivec2;
import org.jetbrains.annotations.Nullable;

/**
 * The checkers move class allows us to distinguish between a start cord, target cord, and capture cord
 */
public class CheckersMove {
    // private Ivec2 fields for start, target, and capture coordinates
    private Ivec2 startCoordinate;
    private Ivec2 targetCoordinate;
    private Ivec2 captureCoordinate;

    /**
     * The constructor for the move object takes in three values:
     * the start, target, and capture coordinate values which are <code>Ivec2</code> objects
     * @param startCoordinate the start cord vector
     * @param targetCoordinate the target cord vector
     * @param captureCoordinate the capture cord vector
     */
    public CheckersMove(Ivec2 startCoordinate, Ivec2 targetCoordinate, Ivec2 captureCoordinate) {
        this.startCoordinate = startCoordinate;
        this.targetCoordinate = targetCoordinate;
        this.captureCoordinate = captureCoordinate;
    }
    /**
     * @return The start coordinate <code>Ivec2</code> of the movey.<br>
     * (Where the piece will be moved from.)
     */
    public Ivec2 getStartCoordinate() {
        return startCoordinate;
    }

    /**
     * @param startCoordinate The value that will be set as the new start coordinate of this move.
     */
    public void setStartCoordinate(Ivec2 startCoordinate) {
        this.startCoordinate = startCoordinate;
    }

    /**
     * @return the target coordinate <code>Ivec2</code> of the move.<br>
     * (Where the piece will be moved to.)
     */
    public Ivec2 getTargetCoordinate() {
        return targetCoordinate;
    }

    /**
     * @param targetCoordinate The value that will be set as the new target coordinate of this move.
     */
    public void setTargetCoordinate(Ivec2 targetCoordinate) {
        this.targetCoordinate = targetCoordinate;
    }

    /**
     * @return the capture coordinate <code>Ivec2</code> of the move.<br>
     * (The coordinate from where a piece will be removed during the execution of this move.)
     * If this value is <code>null</code>, no pieces will be removed by this move.
     */
    @Nullable
    public Ivec2 getCaptureCoordinate() {
        return captureCoordinate;
    }

    /**
     * @param captureCoordinate The value that will be set as the new capture coordinate for this move.
     */
    public void setCaptureCoordinate(Ivec2 captureCoordinate) {
        this.captureCoordinate = captureCoordinate;
    }

    /**
     * @return <code>True</code> if this move will result in a capture; <code>False</code> otherwise.
     */
    public boolean isCapture() { return captureCoordinate != null; }
}