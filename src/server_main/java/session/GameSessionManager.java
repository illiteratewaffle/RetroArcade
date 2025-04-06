package session;

import management.ThreadRegistry;
import player.Player;
import management.ThreadMessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import static management.ServerLogger.log;

/**
 * 
 */
public class GameSessionManager implements Runnable {
    final Player player1;
    final Player player2;
    private final ConcurrentHashMap<Thread, BlockingQueue<ThreadMessage>> queue;

    public GameSessionManager(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.queue = ThreadRegistry.threadRegistry;
        // Set the players as playing
        player1.setPlaying();
        player2.setPlaying();
    }

    @Override
    public void run() {
        queue.put(Thread.currentThread(), new LinkedBlockingQueue<>());
        log("GameSessionManager created");
        // here goes the code for running the game + communicating with the player handlers
        queue.get(player1.getThread()).add(new ThreadMessage(Thread.currentThread(), "This is the GameSessionManager talking to you!"));
        queue.get(player2.getThread()).add(new ThreadMessage(Thread.currentThread(), "This is the GameSessionManager talking to you!"));
        while (true) {
            try {
//                // This is NASTY
//                while (!queue.containsKey(Thread.currentThread()))
//                    Thread.sleep(10);
                ThreadMessage threadMessage = queue.get(Thread.currentThread()).take();
                Thread sender = threadMessage.getSender();
                if (sender == player1.getThread()) {
                    queue.get(player2.getThread()).add(new ThreadMessage(Thread.currentThread(), threadMessage.getContent()));
                } else if (sender == player2.getThread()) {
                    queue.get(player1.getThread()).add(new ThreadMessage(Thread.currentThread(), threadMessage.getContent()));
                } else {
                    log("The player's message failed to route correctly.");
                }
            } catch (InterruptedException e) {
                log("Unable to get message for GameSessionManager thread:", e.toString());
            }
        }
    }
}