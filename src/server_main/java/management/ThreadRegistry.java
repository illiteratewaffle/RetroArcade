package management;
import player.Player;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ThreadRegistry {
    //A centralized registry of all threads accessible by all network managers and server controller.
    public static final ConcurrentHashMap<Thread, BlockingQueue<ThreadMessage>> threadRegistry = new ConcurrentHashMap<>();

    // A centralized registry of all players accessible by all classes.
    public static final CopyOnWriteArrayList<Player> playerList = new CopyOnWriteArrayList<>();

//    /**
//     * Setter method to register a thread and blocking queue combination (player handler) within the thread registry.
//     * @param thread The thread the player handler is on.
//     * @param queue The blocking queue of the player handler.
//     */
//    public static void register(Thread thread, BlockingQueue<ThreadMessage> queue) {
//        threadRegistry.put(thread, queue);
//    }
//
//    /**
//     * Getter method to obtain the blocking queue matched with a particular thread (Player Handlers blocking queue).
//     * @param thread The thread whose blocking queue we are trying to obtain.
//     * @return The blocking queue paired with the thread.
//     */
//    public static BlockingQueue<ThreadMessage> getQueue(Thread thread) {
//        return threadRegistry.get(thread);
//    }
}
