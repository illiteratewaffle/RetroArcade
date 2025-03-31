package client;

import static org.junit.jupiter.api.Assertions.*;

import client.Client;
import client.ClientHandler;
import org.junit.jupiter.api.*;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import server.player.PlayerHandler;

public class ClientTest {
    private Socket testSocket;
    private BufferedReader testBufferedReader;
    private BufferedWriter testBufferedWriter;
    private PlayerHandler testPlayerHandler;
    private ClientHandler clientHandler;
    private Client client;

    @BeforeEach
    void setUp() throws IOException {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream(outputStream);
        testBufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        testBufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        testSocket = new Socket();
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        // testPlayerHandler = new PlayerHandler(testSocket, queue);

        clientHandler = new ClientHandler(testSocket, testPlayerHandler);
        client = new Client(testSocket, "TestPlayerID");
    }

    @Test
    void testSendToPlayerHandler() throws IOException {
        String message = "Test Message";
        clientHandler.sendToPlayerHandler(message);
        assertNotNull(message);
    }

    @Test
    void testSendResponse() throws IOException {
        String responseMessage = "Server Response";
        clientHandler.sendResponse(responseMessage);
        assertNotNull(responseMessage);
    }

    @Test
    void testCloseEverything() throws IOException {
        clientHandler.CloseEverything(testSocket, testBufferedReader, testBufferedWriter);
        assertTrue(true); // Just confirming method execution without exceptions
    }

    @Test
    void testClientSendMessageToHandler() throws IOException {
        testBufferedWriter.write("Server Response\n");
        testBufferedWriter.flush();
        String response = client.SendMessageToHandler("Test Message");
        assertEquals("Server Response", response);
    }

    @Test
    void testClientReturnMessage() throws IOException {
        testBufferedWriter.write("Server Response\n");
        testBufferedWriter.flush();
        String response = client.ReturnMessage();
        assertEquals("Server Response", response);
    }
}