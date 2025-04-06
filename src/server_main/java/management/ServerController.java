package management;

import player.PlayerHandler;
import java.util.concurrent.LinkedBlockingQueue;

import session.GameCreator;

import static management.ServerLogger.*;

public class ServerController {



    public ServerController() {
        ThreadRegistry.threadRegistry.put(Thread.currentThread(), new LinkedBlockingQueue<>());
    }

    /**
     * Registers a new player handler in the list of active players.
     * @param handler The Player Handler to register.
     */
    public void registerPlayerHandler(PlayerHandler handler) {
        // TODO: is creating the BlockingQueue inside of the PlayerHandler, then passing it back through multiple classes the better option in this case?
        // TODO: handler.getHandlerThread() method will not be possible as the thread has not been created yet when calling this function.
        //  The function should instead have it passed in as a parameter as you can get the Thread object when you create the virtual thread.
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
        // TODO: startServer only initiates a ConnectionManager and nothing else? What about ServerLogger or the class that is responsible for creating GameSessions?
        startServerLogger();
        GameCreator gameCreator = new GameCreator();
        Thread.startVirtualThread(gameCreator);
        ConnectionManager connectionManager = new ConnectionManager(this, port);
        Thread.startVirtualThread(connectionManager);

    }
}

