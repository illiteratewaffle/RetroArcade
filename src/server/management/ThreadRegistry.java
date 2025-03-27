package server.management;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadRegistry {

    //A centralized registry of all threads accessible by all network managers and server controller.
    private static final ConcurrentHashMap<Thread, BlockingQueue<ThreadMessage>> threadRegistry = new ConcurrentHashMap<>();


}
