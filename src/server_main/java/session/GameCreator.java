package session;

import management.ThreadRegistry;
import player.Player;

import java.util.concurrent.CopyOnWriteArrayList;

public class GameCreator implements Runnable {
    // private final ConcurrentHashMap<Thread, BlockingQueue<ThreadMessage>> queue;
    private final CopyOnWriteArrayList<Player> playerList;

    /**
     * Constructor for the GameCreator class
     */
    public GameCreator() {
        // this.queue = ThreadRegistry.threadRegistry;
        this.playerList = ThreadRegistry.playerList;
    }

    /**
     * The function that the thread runs
     */
    public void run() {
        // Just run at all times (prolly not a good idea but still at an early state of coding)
        while (true) {
            // Waits until .notify has been called (when playerList is updated)
            synchronized (playerList) {
                try {
                    playerList.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            Player player1 = null;
            Player player2 = null;
            for (Player player : playerList) {
                if (player.getStatus() == Player.PlayerStatus.WAITING) {
                    if (player1 == null) {
                        player1 = player;
                    } else {
                        player2 = player;
                        break;
                    }
                }
            }
            if (player2 != null) {
                // Create the GameSessionManager object
                Thread.ofVirtual().start(new GameSessionManager(player1, player2));
            }
        }
    }
}
