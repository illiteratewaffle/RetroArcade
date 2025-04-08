package client_main.java.client;

import client.Client;
import org.junit.jupiter.api.*;

import java.io.*;
import java.net.*;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    static ServerSocket serverSocket;
    static int port = 5051;

    @BeforeAll
    static void startServer() throws IOException {
        serverSocket = new ServerSocket(port);
        new Thread(() -> {
            try {
                while (true) {
                    Socket client = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);

                    String input;
                    while ((input = in.readLine()) != null) {
                        out.println(input);  // echo
                    }

                    client.close();
                }
            } catch (IOException e) {
                // Do nothing, server closed
            }
        }).start();
    }

    @AfterAll
    static void stopServer() throws IOException {
        serverSocket.close();
    }

    @Test
    public void testConnect() {
        assertDoesNotThrow(() -> {
            Client.connect("localhost", port, "testPlayer");
        });
    }

    @Test
    public void testSendMessageToServer() throws IOException {
        Client.connect("localhost", port, "testPlayer");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Client.sendMessageToServer("Hello World");

        BufferedReader reader = new BufferedReader(new InputStreamReader(ClientTest.serverSocket.accept().getInputStream()));
        String msg = reader.readLine();

        assertTrue(msg.contains("Hello World"));
    }

    @Test
    public void testNetworkingMethod() throws IOException {
        Client.connect("localhost", port, "testPlayer");

        HashMap<String, Object> data = new HashMap<>();
        data.put("type", "chat");
        data.put("message", "Test Message");

        Client.networkingMethod(data);

        BufferedReader reader = new BufferedReader(new InputStreamReader(ClientTest.serverSocket.accept().getInputStream()));
        String msg = reader.readLine();

        assertTrue(msg.contains("Test Message"));
        assertTrue(msg.contains("\"type\":\"chat\""));
    }

    @Test
    public void testGetAuthData_login() {
        String input = "login\nuser1\npass1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        HashMap<String, Object> authData = Client.getAuthData();

        assertEquals("login", authData.get("type"));
        assertEquals("user1", authData.get("username"));
        assertEquals("pass1", authData.get("password"));
        assertNull(authData.get("email"));
    }

    @Test
    public void testGetAuthData_register() {
        String input = "register\nuser2\npass2\nuser2@email.com\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        HashMap<String, Object> authData = Client.getAuthData();

        assertEquals("register", authData.get("type"));
        assertEquals("user2", authData.get("username"));
        assertEquals("pass2", authData.get("password"));
        assertEquals("user2@email.com", authData.get("email"));
    }

    @Test
    public void testLogin() {
        assertDoesNotThrow(() -> {
            Client.login("testUser", "testPass");
            // Since we can't read server output without mocks, just ensure no exceptions thrown
        });
    }

    @Test
    public void testRegister() {
        assertDoesNotThrow(() -> {
            Client.register("newUser", "newPass", "email@domain.com");
        });
    }
}
