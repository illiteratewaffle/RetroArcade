package server.management;
import server.player.PlayerHandler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadRegistry {
    //A centralized registry of all threads accessible by all network managers and server controller.
    private static final ConcurrentHashMap<Thread, BlockingQueue<ThreadMessage>> threadRegistry = new ConcurrentHashMap<>();

    //A centralized registry of all players active containing their player ids, and the thread their player handler is on.
    private static final ConcurrentHashMap<Integer, PlayerHandler> playerList = new ConcurrentHashMap<>();

    /**
     * Setter method to register a thread and blocking queue combination (player handler) within the thread registry.
     * @param thread The thread the player handler is on.
     * @param queue The blocking queue of the player handler.
     */
    public static void register(Thread thread, BlockingQueue<ThreadMessage> queue) {
        threadRegistry.put(thread, queue);
    }

    /**
     * Getter method to obtain the blocking queue matched with a particular thread (Player Handlers blocking queue).
     * @param thread The thread whose blocking queue we are trying to obtain.
     * @return The blocking queue paired with the thread.
     */
    public static BlockingQueue<ThreadMessage> getQueue(Thread thread) {
        return threadRegistry.get(thread);
    }

    /**
     * Getter method to obtain the player handler matched with a particular player id
     * @param id The id the player whose thread we are trying to find.
     * @return The player handler.
     */
    public static PlayerHandler getHandler(int id) {
        return playerList.get(id);
    }

    /**
     * Method to register a player within the player list
     * @param ID The ID of the player
     * @param handler The player handler for the player.
     */
    public static void registerPlayer(int ID, PlayerHandler handler) {
        playerList.put(ID, handler);
    }

}
