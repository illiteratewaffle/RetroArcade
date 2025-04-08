//package client_main.java.client;
//import org.junit.jupiter.api.*;
//
//import java.io.*;
//import java.net.Socket;
//import java.util.HashMap;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ClientTest {
//
//    private PipedInputStream serverInput;
//    private PipedOutputStream clientOutput;
//    private PipedInputStream clientInput;
//    private PipedOutputStream serverOutput;
//
//    private Socket fakeSocket;
//    private Client client;
//
//    @BeforeEach
//    void setup() throws IOException {
//        serverInput = new PipedInputStream();
//        clientOutput = new PipedOutputStream(serverInput);
//
//        clientInput = new PipedInputStream();
//        serverOutput = new PipedOutputStream(clientInput);
//
//        fakeSocket = new Socket() {
//            @Override public InputStream getInputStream() { return clientInput; }
//            @Override public OutputStream getOutputStream() { return clientOutput; }
//            @Override public void close() throws IOException {
//                clientInput.close();
//                clientOutput.close();
//                serverInput.close();
//                serverOutput.close();
//            }
//        };
//
//        client = new Client(fakeSocket, "testPlayer");
//
//        // Set static writer for static method
//        try {
//            var writerField = Client.class.getDeclaredField("writer");
//            writerField.setAccessible(true);
//            writerField.set(null, new PrintWriter(clientOutput, true));
//        } catch (ReflectiveOperationException e) {
//            fail("Failed to set static writer");
//        }
//    }
//
//    @Test
//    void testSendMessageToServer_writesCorrectJson() {
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        PrintWriter testWriter = new PrintWriter(output, true);
//
//        try {
//            var writerField = Client.class.getDeclaredField("writer");
//            writerField.setAccessible(true);
//            writerField.set(null, testWriter);
//        } catch (ReflectiveOperationException e) {
//            fail("Reflection error");
//        }
//
//        client.sendMessageToServer("Test Message");
//        String result = output.toString();
//
//        assertTrue(result.contains("\"message\":\"Test Message\""));
//        assertTrue(result.contains("\"type\":\"message\""));
//    }
//
//    @Test
//    void testNetworkingMethod_sendsJson() {
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        PrintWriter testWriter = new PrintWriter(output, true);
//
//        try {
//            var writerField = Client.class.getDeclaredField("writer");
//            writerField.setAccessible(true);
//            writerField.set(null, testWriter);
//        } catch (ReflectiveOperationException e) {
//            fail("Reflection error");
//        }
//
//        HashMap<String, Object> data = new HashMap<>();
//        data.put("type", "example");
//        data.put("message", "hi");
//
//        Client.networkingMethod(data);
//
//        String result = output.toString();
//        assertTrue(result.contains("\"type\":\"example\""));
//        assertTrue(result.contains("\"message\":\"hi\""));
//    }
//
//    @Test
//    void testGetAuthData_loginPath() {
//        String input = "login\nuser\npass\n";
//        System.setIn(new ByteArrayInputStream(input.getBytes()));
//
//        HashMap<String, Object> result = Client.getAuthData();
//
//        assertEquals("login", result.get("type"));
//        assertEquals("user", result.get("username"));
//        assertEquals("pass", result.get("password"));
//        assertFalse(result.containsKey("email"));
//    }
//
//    @Test
//    void testGetAuthData_registerPath() {
//        String input = "register\nnewuser\nnewpass\nnew@email.com\n";
//        System.setIn(new ByteArrayInputStream(input.getBytes()));
//
//        HashMap<String, Object> result = Client.getAuthData();
//
//        assertEquals("register", result.get("type"));
//        assertEquals("newuser", result.get("username"));
//        assertEquals("newpass", result.get("password"));
//        assertEquals("new@email.com", result.get("email"));
//    }
//
//    @Test
//    void testListenForMessages_chatAndErrorMessages() throws Exception {
//        String chatMsg = JsonConverter.toJson(new HashMap<>() {{
//            put("type", "chat");
//            put("sender", "UserA");
//            put("message", "Hello");
//        }}) + "\n";
//
//        String errorMsg = JsonConverter.toJson(new HashMap<>() {{
//            put("type", "error");
//            put("message", "Something went wrong");
//        }}) + "\n";
//
//        serverOutput.write((chatMsg + errorMsg).getBytes());
//        serverOutput.flush();
//
//        ByteArrayOutputStream sysOut = new ByteArrayOutputStream();
//        PrintStream originalOut = System.out;
//        System.setOut(new PrintStream(sysOut));
//
//        client.listenForMessages();
//        Thread.sleep(200); // Give background thread time to run
//
//        System.setOut(originalOut); // restore output
//
//        String output = sysOut.toString();
//        assertTrue(output.contains("UserA: Hello"));
//        assertTrue(output.contains("Error: Something went wrong"));
//    }
//
//    @Test
//    void testCloseEverythingDoesNotThrow() {
//        assertDoesNotThrow(() -> {
//            client = new Client(fakeSocket, "testPlayer");
//            client.sendMessageToServer("closing...");
//            Thread.sleep(50);
//            var method = Client.class.getDeclaredMethod("closeEverything");
//            method.setAccessible(true);
//            method.invoke(client);
//        });
//    }
//
//    @Test
//    void testLoginAndRegisterRunWithoutException() {
//        assertDoesNotThrow(() -> {
//            Thread loginThread = new Thread(() -> Client.login("ava", "password"));
//            Thread registerThread = new Thread(() -> Client.register("ava", "password", "ava@email.com"));
//
//            loginThread.start();
//            registerThread.start();
//
//            loginThread.join(500);
//            registerThread.join(500);
//        });
//    }
//
//    @AfterEach
//    void cleanup() throws IOException {
//        fakeSocket.close();
//        System.setIn(System.in);
//    }
//}
