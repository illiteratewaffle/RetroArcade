package server.management;

import server.player.PlayerHandler;
import java.util.concurrent.LinkedBlockingQueue;
import server.management.ThreadMessage;

public class ServerController {

    public ServerController() {
        ThreadRegistry.threadRegistry.put(Thread.currentThread(), new LinkedBlockingQueue<>());
    }

    /**
     * Registers a new player handler in the list of active players.
     * @param handler The Player Handler to register.
     */
    public void registerPlayerHandler(PlayerHandler handler) {
        //Thread playerThread = handler.getHandlerThread();
        //ThreadRegistry.threadRegistry.put(playerThread, handler.getMessages());

    }

    /**
     * Handles player disconnection by removing the player's thread from the thread registry.
     * @param handler The player handler of the player to be disconnected.
     */
    public synchronized void handleDisconnection(PlayerHandler handler) {
        //Thread playerThread = handler.getHandlerThread();
        //ThreadRegistry.threadRegistry.remove(playerThread);
    }

    /**
     * Starts the server by instantiating a ConnectionManager.
     */
    public void startServer(int port) {
        ConnectionManager connectionManager = new ConnectionManager(this, port);
        Thread.startVirtualThread(connectionManager);
    }

    /**
     * Main Method to start the server
     */
    public static void main(String[] args) {
        ServerController controller = new ServerController();
        int port = 5050;
        controller.startServer(port);
    }
}

/*
notes (for colby):
 - handler.getHandlerThread() will not be possible as you have not created the thread yet when calling this function
    - you should probably just pass it in as a variable as you can extract it when you create the virtual thread
 - is creating the blocking queue inside the playerHandler then passing it back through multiple classes the better option?
    - would it not be cleaner to simply create the blocking queue then passing it into the constructor for the PlayerHandler, keeping it all contained in ConnectionManager?
    - alternatively, since the ThreadRegistry is a static variable, the PlayerHandler could even create it.
 */
