package server;

/**
 * This class will be used to hold information about connected players including the Thread they are on
 */
public class Player {
    public enum PlayerStatus {
        PLAYING,
        WAITING
    }

    private final Thread thread;
    private final String playerID;
    private PlayerStatus status;


    /**
     * Constructor for the Player class
     * @param thread the thread that the player is on
     */
    public Player(Thread thread, String playerID) {
        this.thread = thread;
        this.playerID = playerID;
        this.status = PlayerStatus.WAITING;
    }

    /**
     * Gives the Thread object
     * @return the Thread object
     */
    public Thread getThread() {
        return thread;
    }

    public String getPlayerID() {
        return playerID;
    }

    /**
     * Returns the player status (Might change back to a simple boolean?)
     * @return PlayerStatus the enum representing the player's status
     */
    public PlayerStatus getStatus() {
        return status;
    }

    /**
     * Sets the PlayerStatus to WAITING
     */
    public void setWaiting() {
        status = PlayerStatus.WAITING;
    }

    /**
     * Sets the PlayerStatus to PLAYING
     */
    public void setPlaying() {
        status = PlayerStatus.PLAYING;
    }
}
