package server.management;

import server.player.Player;
import server.player.PlayerHandler;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AuthenticateClient implements Runnable {
    Socket clientSocket;
    public AuthenticateClient(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * This creates a thread that authenticates the player before creating a PlayerHandler thread.
     */
    public void run() {
//        // Authenticate the player before allowing them to connect
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//            PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
//            // Check their input
//            String auth = bufferedReader.readLine();
//            String[][] thing = Encoder.decodeJSON(auth);
//        } catch (IOException e){
//            log("Failure to initialize AuthenticateClient BufferedReader/BufferedWriter:", e.toString());
//            // TODO: close client's connection
//        }

        //Create a blocking queue and player handler to handle the player connection on the server side.
        BlockingQueue<ThreadMessage> queue = new LinkedBlockingQueue<>();
        PlayerHandler playerHandler = new PlayerHandler(clientSocket, queue);
        //Create a thread for the player and start it.
        Thread playerThread = Thread.ofVirtual().start(playerHandler);
        // Add the new player the ThreadRegistry
        ThreadRegistry.threadRegistry.put(playerThread, queue);
        // Add the new player to the playerList
        synchronized (ThreadRegistry.playerList) {
            ThreadRegistry.playerList.add(new Player(playerThread, playerHandler));
            // log("The new player is on Thread", playerThread.threadId());
            ThreadRegistry.playerList.notifyAll();
        }
    }
}
