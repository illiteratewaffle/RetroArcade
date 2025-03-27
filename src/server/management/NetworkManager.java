package server.management;

import server.player.PlayerHandler;
import java.util.concurrent.BlockingQueue;

public class NetworkManager {
    /**
     * Sends a message to the specified target thread.
     * Retrieves the target thread's message queue from the centralized ThreadRegistry
     * and puts the message into that queue.
     *
     * @param targetThread The thread to which the message should be sent.
     * @param message The message to send.
     */
    public void sendMessage(Thread targetThread, ThreadMessage message) {
        BlockingQueue<ThreadMessage> queue = ThreadRegistry.threadRegistry.get(targetThread);
        if (queue != null) {
            try {
                queue.put(message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("NetworkManager: Interrupted while sending message.");
            }
        } else {
            System.err.println("NetworkManager: No message queue found for the target thread.");
        }
    }

    /**
     * Returns the player back to the lobby by sending a "RETURN_TO_LOBBY" message.
     *
     * @param handler The PlayerHandler representing the player to return to the lobby.
     */
    public void returnPlayer(PlayerHandler handler) {
        //System.out.println("NetworkManager: Returning player " + handler.getPlayerId() + " to the lobby.");
        //Thread targetThread = handler.getHandlerThread();
        //ThreadMessage message = new ThreadMessage("RETURN_TO_LOBBY", handler.getPlayerId());
        //sendMessage(targetThread, message);
    }
}
