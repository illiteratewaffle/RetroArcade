package client_main.java.client;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Client {

    private Socket clientSocket;
    private BufferedReader reader;
    private static PrintWriter writer;
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
    public static void networkingMethod(HashMap<String, Object> data) {
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
                        case "chat":
                            System.out.println(data.get("sender") + ": " + data.get("message"));
                            String message = (String) data.get("message");
                            String sender = (String) data.get("sender");
                            //updateChatbox(message,sender)
                            break;
                        case "game-move":
                            System.err.println("Error: " + data.get("message"));
                            break;
                        case "profile-info-request":
                            System.err.println("Error: " + data.get("message"));
                            break;
                        case "error":
                            System.err.println("Error: " + data.get("message"));
                            break;
                        case "exit-game":
                            System.out.println("[INFO]: " + data.get("message"));
                            break;
                        case "login":
                            System.out.println("[INFO]: " + data.get("message"));
                            break;
                        case "register":
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
    public static void login(String Username, String Password) {
        String serverAddress = "10.13.157.168";
        int port = 5050;
        Username = "ava";
        Password = "password";
        try {
            Socket socket = new Socket(serverAddress, port);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Send auth request
            HashMap<String, Object> authData = new HashMap<>();
            authData.put("type", "login");
            authData.put("username", Username);
            authData.put("password", Password);
            writer.println(JsonConverter.toJson(authData));

            // Wait for server to assign and respond with PlayerID
            String response;
            // Start client
            Client client = new Client(socket, null);
            client.listenForMessages();

        } catch (IOException e) {
            e.printStackTrace();
        }}
    public static void register(String Username, String Password, String Email) {
        String serverAddress = "localhost";
        int port = 5050;
        try {
            Socket socket = new Socket(serverAddress, port);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Send auth request
            HashMap<String, Object> authData = new HashMap<>();
            authData.put("type", "login");
            authData.put("username", Username);
            authData.put("password", Password);
            authData.put("email", Email);
            writer.println(JsonConverter.toJson(authData));

            // Wait for server to assign and respond with PlayerID
            Client client = new Client(socket, null);
            client.listenForMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }}

}
