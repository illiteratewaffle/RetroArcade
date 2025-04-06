package client_main.java.client;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Client {

    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String playerId;

    public Client(Socket socket, String playerId) {
        try {
            this.clientSocket = socket;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(socket.getOutputStream(), true);
            this.playerId = playerId;
        } catch (IOException e) {
            closeEverything();
        }
    }
    /**
     * Sends the networking info to the associated ClientHandler.
     */
    public void sendMessageToServer(String message) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("type", "message");
        data.put("message", message);
        writer.println(JsonConverter.toJson(data));
    }
    public void sendMessageFromMethod(String type, String message, String extra, String extraObject) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("type", type);
        data.put("message", message);
        if (extra != "null"){
            data.put(extra, extraObject);}
        writer.println(JsonConverter.toJson(data));
    }
    /**
     * Returns the networking response to the function caller.
     */
    public void listenForMessages() {
        new Thread(() -> {
            try {
                String msgFromServer;
                while ((msgFromServer = reader.readLine()) != null) {
                    HashMap<String, Object> data = (HashMap<String, Object>) JsonConverter.fromJson(msgFromServer);

                    String type = (String) data.get("type");
                    switch (type) {
                        case "message":
                            System.out.println(data.get("sender") + ": " + data.get("message"));
                            break;
                        case "error":
                            System.err.println("Error: " + data.get("message"));
                            break;
                        case "info":
                            System.out.println("[INFO]: " + data.get("message"));
                            break;
                        default:
                            System.out.println(msgFromServer);
                            break;
                    }
                }
            } catch (IOException e) {
                closeEverything();
            }
        }).start();
    }
    /**
     * Closes the client connection.
     */
    private void closeEverything() {
        try {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, Object> getAuthData() {
        Scanner scanner = new Scanner(System.in);
        HashMap<String, Object> authData = new HashMap<>();

        String type;
        while (true) {
            System.out.print("Are you logging in or registering? (login/register): ");
            type = scanner.nextLine().trim().toLowerCase();

            if (!type.equals("login") && !type.equals("register")) {
                System.out.println("Invalid input. Must be 'login' or 'register'.");
            } else {
                break;
            }
        }

        authData.put("type", type);

        System.out.print("Enter username: ");
        authData.put("username", scanner.nextLine().trim());

        System.out.print("Enter password: ");
        authData.put("password", scanner.nextLine().trim());

        if (type.equals("register")) {
            System.out.print("Enter email: ");
            authData.put("email", scanner.nextLine().trim());
        }

        return authData;
    }
    /**
     * The function that the thread runs
     */
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 5050;

        if (args.length == 2) {
            serverAddress = args[0];
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port number. Using default 5050.");
            }
        }

        try {
            Socket socket = new Socket(serverAddress, port);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Send auth request
            HashMap<String, Object> authData = getAuthData();
            writer.println(JsonConverter.toJson(authData));

            // Wait for server to assign and respond with PlayerID
            String response;
            String playerId = null;
            if (playerId == null) {
                // Start client
                Client client = new Client(socket, null);
                client.listenForMessages();

                // Send messages to server
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    String message = scanner.nextLine();
                    client.sendMessageToServer(message);
                }
            }
            else {

                // Start client
                Client client = new Client(socket, playerId);
                client.listenForMessages();

                // Send messages to server
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    String message = scanner.nextLine();
                    client.sendMessageToServer(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }}
}
