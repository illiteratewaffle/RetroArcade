package client.test;

import GameLogic_Client.Ivec2;
import client.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.TimeoutException;

import launcher.ServerLauncher;
import org.junit.jupiter.api.Timeout;

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

    int gametype = 0;

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
    @Timeout(5)
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
    @Timeout(5)
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
    @Timeout(5)
    void enqueue() {
        //enqueue both clients
        this.client1.login(serverAddress, serverPort, username1, password1);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.client1.enqueue(gametype);

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




        this.client2.enqueue(gametype);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    @Timeout(5)
    void receiveInput() {
        this.client1.login(serverAddress, serverPort, username1, password1);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.client1.enqueue(gametype);

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

        this.client2.enqueue(gametype);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Ivec2 ivec = new Ivec2(0, 0);
        this.client1.receiveInput(ivec);

        try {
            Thread.sleep(5000);
            this.client2.getBoardCells(0);
            Thread.sleep(5000);
            this.client2.getCurrentPlayer();
        } catch (TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void removePlayer() {
    }

    @Test
    void getWinner() {
    }

    @Test
    @Timeout(5)
    void getGameOngoing() {
        //enqueue both clients
        this.client1.login(serverAddress, serverPort, username1, password1);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.client1.enqueue(gametype);

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




        this.client2.enqueue(gametype);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            boolean returns = this.client2.getGameOngoing();
            System.out.println(returns);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Timeout(5)
    void getBoardCells() {
        this.client1.login(serverAddress, serverPort, username1, password1);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.client1.enqueue(gametype);

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




        this.client2.enqueue(gametype);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        try {
            this.client2.getBoardCells(0);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getBoardSize() {
    }

    @Test
    @Timeout(5)
    void getCurrentPlayer() {
        this.client1.login(serverAddress, serverPort, username1, password1);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.client1.enqueue(gametype);

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




        this.client2.enqueue(gametype);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try{
            int curr = client1.getCurrentPlayer();
            System.out.println(curr);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
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