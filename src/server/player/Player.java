package server.player;

public class Player {
    public enum PlayerStatus {
        PLAYING,
        WAITING
    }

    private final Thread thread;
    private final PlayerHandler playerHandler;
    // private final PlayerHandler playerHandler;
    private PlayerStatus status;
    private final String username;
    private final int playerId;

    public Player(Thread thread, PlayerHandler playerHandler, int playerId) {
        this.thread = thread;
        this.playerHandler = playerHandler;
        this.playerId = playerId;
        this.username = PlayerManager.getUsername(playerId);
        // this.playerHandler = playerHandler;
        this.status = PlayerStatus.WAITING;
    }

    public Thread getThread() {
        return thread;
    }

    public PlayerHandler getPlayerHandler() {
        return playerHandler;
    }

    public synchronized PlayerStatus getStatus() {
        return status;
    }

    public synchronized String getUsername() {
        return username;
    }

    public synchronized int getPlayerId() {
        return playerId;
    }

    public synchronized void setWaiting() {
        status = PlayerStatus.WAITING;
    }

    public synchronized void setPlaying() {
        status = PlayerStatus.PLAYING;
    }
}
