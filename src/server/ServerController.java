package server;
import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerController {

    // List to maintain active player handlers
    private final List<PlayerHandler> activePlayers = new CopyOnWriteArrayList<>();

    // List to maintain active game sessions
    private final List<GameSession> gameSessions = new CopyOnWriteArrayList<>();

    // Reference to the NetworkManager (message routing is delegated there)
    private final NetworkManager networkManager;

    // Constructor
    public ServerController(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    /**
     * Initializes the server and listens for new client connections.
     * For every accepted connection, a Virtual Thread is created.
     *
     * @param port the port number to listen on.
     */
    public void startServer(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                // Each client connection is handled on its own virtual thread.
                Thread.startVirtualThread(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles an individual client connection by creating a PlayerHandler
     * and starting to listen for client messages.
     *
     * @param clientSocket the socket corresponding to the client connection.
     */
    private void handleClient(Socket clientSocket) {
        try {
            PlayerHandler handler = new PlayerHandler(clientSocket, this);
            activePlayers.add(handler);
            // Begin listening for messages on the current virtual thread.
            handler.listen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new game session with two players. The players are assigned to
     * the session, and the game session is started on its own virtual thread.
     *
     * @param player1 first player.
     * @param player2 second player.
     * @return the created GameSession.
     */
    public synchronized GameSession createGameSession(Player player1, Player player2) {
        GameSession session = new GameSession(player1, player2);
        gameSessions.add(session);

        // Assign both players to the session.
        assignToSession(player1, session);
        assignToSession(player2, session);

        // Start the game session on a separate virtual thread.
        Thread.startVirtualThread(session::startSession);
        return session;
    }

    /**
     * Assigns a player to a game session and updates the player's handler to
     * forward further messages to the session.
     *
     * @param player  the player to assign.
     * @param session the game session.
     */
    public synchronized void assignToSession(Player player, GameSession session) {
        player.setGameSession(session);
        PlayerHandler handler = player.getHandler();
        handler.setCurrentSession(session);
    }

    /**
     * Removes a player from their game session. If the session is complete, it
     * is removed from the active sessions list and the database is updated.
     *
     * @param player the player to remove.
     */
    public synchronized void removeFromSession(Player player) {
        GameSession session = player.getGameSession();
        if (session != null) {
            session.removePlayer(player);
            player.setGameSession(null);
            if (session.isComplete()) {
                gameSessions.remove(session);
                updateDatabaseAfterGame(session);
            }
            returnToNetworkManager(player);
        }
    }

    /**
     * Returns the player back to the Network Manager after a game session ends.
     *
     * @param player the player to return.
     */
    public void returnToNetworkManager(Player player) {
        networkManager.returnPlayer(player);
    }

    /**
     * Handles player disconnection. If the player is part of an active game session,
     * they are removed from that session.
     *
     * @param player the disconnected player.
     */
    public synchronized void handleDisconnection(Player player) {
        if (player.getGameSession() != null) {
            removeFromSession(player);
        }
        activePlayers.remove(player.getHandler());
    }

    /**
     * Updates the database with match history and leaderboard information after
     * a game session completes.
     *
     * @param session the completed game session.
     */
    public void updateDatabaseAfterGame(GameSession session) {
        DatabaseConnector.getInstance().updateMatchHistory(session);
    }

    // --- Main method to start the server ---
    public static void main(String[] args) {
        // Create an instance of NetworkManager.
        NetworkManager networkManager = new NetworkManager();
        ServerController controller = new ServerController(networkManager);
        int port = 12345; // Set your desired port
        controller.startServer(port);
    }

    // --- Dummy Classes for Demonstration Purposes ---

    /**
     * Represents a player in the system.
     */
    public static class Player {
        private final String id;
        private final PlayerHandler handler;
        private GameSession gameSession;

        public Player(String id, PlayerHandler handler) {
            this.id = id;
            this.handler = handler;
        }

        public String getId() {
            return id;
        }

        public PlayerHandler getHandler() {
            return handler;
        }

        public GameSession getGameSession() {
            return gameSession;
        }

        public void setGameSession(GameSession session) {
            this.gameSession = session;
        }
    }

    /**
     * Manages a game session between two players.
     * Runs on its own virtual thread to enforce game rules, turn order, etc.
     */
    public static class GameSession {
        private final Player player1;
        private final Player player2;
        private boolean complete = false;

        public GameSession(Player player1, Player player2) {
            this.player1 = player1;
            this.player2 = player2;
        }

        /**
         * Starts the game session.
         * Here, you would include the actual game loop and validation logic.
         */
        public void startSession() {
            System.out.println("Game session started for players: " +
                    player1.getId() + " and " + player2.getId());
            // Simulate game play
            try {
                Thread.sleep(5000); // Simulated game duration
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            complete = true;
            System.out.println("Game session completed for players: " +
                    player1.getId() + " and " + player2.getId());
        }

        /**
         * Removes a player from the session.
         *
         * @param player the player to remove.
         */
        public void removePlayer(Player player) {
            System.out.println("Removing player " + player.getId() + " from session.");
            // Insert additional removal logic if needed.
        }

        /**
         * Indicates whether the session has completed.
         *
         * @return true if the game is complete.
         */
        public boolean isComplete() {
            return complete;
        }
    }

    /**
     * Handles communication with an individual client.
     * Uses the provided socket for I/O and forwards messages to the ServerController.
     */
    public static class PlayerHandler {
        private final Socket socket;
        private final ServerController serverController;
        private GameSession currentSession;
        private final Player player;

        public PlayerHandler(Socket socket, ServerController serverController) {
            this.socket = socket;
            this.serverController = serverController;
            // Create a new player with a unique identifier (using socket info for simplicity)
            this.player = new Player(socket.getRemoteSocketAddress().toString(), this);
        }

        /**
         * Listens for incoming messages from the client.
         * If an error occurs (e.g. disconnection), it notifies the ServerController.
         */
        public void listen() {
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                String message;
                while ((message = in.readLine()) != null) {
                    processMessage(message);
                }
            } catch (IOException e) {
                System.err.println("Connection error with player " + player.getId());
                serverController.handleDisconnection(player);
            }
        }

        /**
         * Processes a received message.
         * For example, a "JOIN_GAME" command could trigger session creation.
         *
         * @param message the incoming message.
         */
        private void processMessage(String message) {
            if (message.startsWith("JOIN_GAME")) {
                System.out.println("Player " + player.getId() + " requested to join a game.");
                // Here, matchmaking logic would determine an opponent.
                // For demonstration, you might simulate pairing with another waiting player.
            } else {
                System.out.println("Received message from player " + player.getId() + ": " + message);
                // Other message types could be handled or forwarded as needed.
            }
        }

        public void setCurrentSession(GameSession session) {
            this.currentSession = session;
        }

        public Player getPlayer() {
            return player;
        }
    }

    /**
     * Represents the Network Manager.
     * This component is responsible for routing messages and, for instance,
     * returning players back to the lobby after their game session ends.
     */
    public static class NetworkManager {
        public void returnPlayer(Player player) {
            System.out.println("Returning player " + player.getId() + " to Network Manager.");
            // Update the player's state or routing table as necessary.
        }
    }

    /**
     * A singleton Database Connector for handling PostgreSQL operations.
     * It loads configurations from a file and reuses a single connection.
     */
    public static class DatabaseConnector {
        private static DatabaseConnector instance;

        private DatabaseConnector() {
            // Load configuration and establish database connection here.
        }

        public static synchronized DatabaseConnector getInstance() {
            if (instance == null) {
                instance = new DatabaseConnector();
            }
            return instance;
        }

        /**
         * Updates the match history and leaderboards based on a completed session.
         *
         * @param session the completed game session.
         */
        public void updateMatchHistory(GameSession session) {
            System.out.println("Updating match history in the database for the completed session.");
            // Insert actual database update logic (using JDBC, prepared statements, etc.)
        }
    }
}

