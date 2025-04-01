package server.session;

import server.management.ThreadRegistry;
import server.player.PlayerHandler;

import java.util.concurrent.ConcurrentHashMap;

public class GameCreator {

    //Concurrent hash map that stores a list of all currently active game sessions
    private final ConcurrentHashMap<Thread, GameSessionManager> activeSessions = new ConcurrentHashMap<>();

    public Thread createSession(PlayerHandler player1, PlayerHandler player2) {

        //Create the game session manager for the game session being created
        GameSessionManager session = new GameSessionManager(player1, player2, new BoardGameController());

        //Start the game session manager thread
        Thread sessionThread = Thread.startVirtualThread(session);

        //Assign the game session manager thread to both the game session hashmap list, and also onto the thread registry
        activeSessions.put(sessionThread, session);
        //ThreadRegistry.register(sessionThread, session.getMessageQueue()); This will need to be fixed after talking with martin.

        return sessionThread;
    }

}
