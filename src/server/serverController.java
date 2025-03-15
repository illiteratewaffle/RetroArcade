package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerController {
    private static final int PORT = 8080;  //Change if needed
    private final ConcurrentHashMap<String, PlayerHandler> activePlayers = new ConcurrentHashMap<>();
    private final ExecutorService connectionPool = Executors.newVirtualThreadPerTaskExecutor();

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                connectionPool.execute(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            PlayerHandler playerHandler = new PlayerHandler(clientSocket, this);
            String playerId = playerHandler.getPlayerId();
            activePlayers.put(playerId, playerHandler);

            System.out.println("New player connected: " + playerId);
            playerHandler.listenForMessages(); //Handle communication
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removePlayer(String playerId) {
        activePlayers.remove(playerId);
        System.out.println("Player " + playerId + " disconnected.");
    }

    public void processMessage(String playerId, String message) {
        System.out.println("Message from " + playerId + ": " + message);
        //This will later send the message to NetworkManager
    }

    public static void main(String[] args) {
        ServerController server = new ServerController();
        server.startServer();
    }
}

