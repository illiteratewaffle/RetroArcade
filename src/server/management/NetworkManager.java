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

        //Obtain the queue of the thread we are trying to message.
        BlockingQueue<ThreadMessage> queue = ThreadRegistry.getQueue(targetThread);

        //Check if the queue actually exists, and if it doesn't throw some errors.
        if (queue != null) {
            try {

                //If the queue exists, put the message in it.
                queue.put(message);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("NetworkManager: Interrupted while sending message.");
            }

        } else {
            System.err.println("NetworkManager: No message queue found for the target thread.");
        }
    }
}
