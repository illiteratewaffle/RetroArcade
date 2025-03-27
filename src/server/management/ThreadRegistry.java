package server.management;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadRegistry {
    // TODO: We should discuss whether we want to use a static class to contain the message queue for all threads instead of an instance of the class
    //A centralized registry of all threads accessible by all network managers and server controller.
    public static final ConcurrentHashMap<Thread, BlockingQueue<ThreadMessage>> threadRegistry = new ConcurrentHashMap<>();
}
