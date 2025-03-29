package server.player;

public class Player {
    public enum PlayerStatus {
        PLAYING,
        WAITING
    }

    private final Thread thread;
    // private final PlayerHandler playerHandler;
    private PlayerStatus status;

    public Player(Thread thread) {
        this.thread = thread;
        // this.playerHandler = playerHandler;
        this.status = PlayerStatus.WAITING;
    }

    public Thread getThread() {
        return thread;
    }

    public synchronized PlayerStatus getStatus() {
        return status;
    }

    public synchronized void setWaiting() {
        status = PlayerStatus.WAITING;
    }

    public synchronized void setPlaying() {
        status = PlayerStatus.PLAYING;
    }
}
