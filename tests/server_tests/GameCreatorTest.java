package server_tests;

import AuthenticationAndProfile.Profile;
import management.ThreadMessage;
import org.junit.jupiter.api.Test;
import session.GameCreator;
import player.PlayerHandler;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class GameCreatorTest {

    @Test
    public void testGameCreatorThreadRunsAndAcceptsPlayers() throws IOException {
        // Start the GameCreator in a virtual thread
        GameCreator gameCreator = new GameCreator();
        Thread.startVirtualThread(gameCreator);

        // Create a dummy BlockingQueue
        BlockingQueue<ThreadMessage> queue = new LinkedBlockingQueue<>();

        // Start a dummy server socket to connect the test client
        ServerSocket serverSocket = new ServerSocket(0); // Use available port
        int port = serverSocket.getLocalPort();

        // Connect the socket (loopback)
        Socket clientSocket = new Socket("localhost", port);
        Socket serverSideSocket = serverSocket.accept(); // Accept connection on server side

        // Create fake profile
        Profile fakeProfile = fakeProfileMaker.createFakeProfile();

        // Create a fake player using the server-side socket
        PlayerHandler fakePlayer = new PlayerHandler(serverSideSocket, queue, fakeProfile);

        // Try to enqueue the player (what we want to test)
        assertDoesNotThrow(() -> gameCreator.enqueuePlayer(fakePlayer),
                "GameCreator should accept a player with a dummy profile");

        // Clean up sockets
        clientSocket.close();
        serverSideSocket.close();
        serverSocket.close();
    }
}


