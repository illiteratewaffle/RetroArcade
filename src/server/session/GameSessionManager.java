package server.session;

import server.management.ThreadRegistry;
import server.player.Player;
import server.management.ThreadMessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import static server.management.ServerLogger.log;

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
        while(true) {
            try {
//                // This is NASTY
//                while (!queue.containsKey(Thread.currentThread()))
//                    Thread.sleep(10);
                ThreadMessage threadMessage = queue.get(Thread.currentThread()).take();
                Thread sender = threadMessage.getSender();
                if (sender == player1.getThread()) {
                    log("Send message to player2!");
                    queue.get(player2.getThread()).add(new ThreadMessage(Thread.currentThread(), threadMessage.getContent()));
                } else
                    log("This is the thread the server believes the player is on:", player1.getThread().toString(), "\nThis is the thread that it actually is on:", sender.toString());
                if (sender == player2.getThread()) {
                    log("Send message to player1!");
                    queue.get(player1.getThread()).add(new ThreadMessage(Thread.currentThread(), threadMessage.getContent()));
                } else {
                    log("This is the thread the server believes the player is on:", player2.getThread().toString(), "\nThis is the thread that it actually is on:", sender.toString());
                }
            } catch (InterruptedException e) {
                log("Unable to get message for GameSessionManager thread:", e.toString());
            }
        }
    }
}