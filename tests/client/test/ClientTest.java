package client.test;

import client.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import launcher.ServerLauncher;

class ClientTest {
    private static Thread serverThread;

    String serverAddress = "localhost";
    int serverPort = 5050;
    String username1 = "evan";
    String password1 = "password";
    String username2 = "colby";
    String password2 = "password";

    Client client1;
    Client client2;

    @BeforeEach
    void setUp() {
        // Start the dummy server instance in a separate thread.
        serverThread = new Thread(() -> {
            // The DummyServer main method will block listening to incoming connections.
            ServerLauncher.main(new String[]{});
        });
        serverThread.setDaemon(true);
        serverThread.start();
        // Give the server some time to bind to port 5050.
        synchronized (this) {
            try {
                wait(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @AfterEach
    void tearDown() {
        serverThread.interrupt();
    }

    @Test
    void login() {
        boolean returned = client1.login(serverAddress, serverPort, username1, password1);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertTrue(returned);
    }

    @Test
    void register() {

    }

    @Test
    void logout() {
        client1.login(serverAddress, serverPort, username1, password1);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        client1.logout();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    void sendChatMessage() {
    }

    @Test
    void enqueue() {
        //enqueue both clients
        this.client1.login(serverAddress, serverPort, username1, password1);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.client1.enqueue(2);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.client2.login(serverAddress, serverPort, username2, password2);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }




        this.client2.enqueue(2);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    void receiveInput() {
    }

    @Test
    void removePlayer() {
    }

    @Test
    void getWinner() {
    }

    @Test
    void getGameOngoing() {
    }

    @Test
    void getBoardCells() {
    }

    @Test
    void getBoardSize() {
    }

    @Test
    void getCurrentPlayer() {
    }

    @Test
    void gameOngoingChangedSinceLastCommand() {
    }

    @Test
    void winnersChangedSinceLastCommand() {
    }

    @Test
    void currentPlayerChangedSinceLastCommand() {
    }

    @Test
    void boardChangedSinceLastCommand() {
    }

    @Test
    void checkDraw() {
    }
}