package client_main.java.client;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import client_main.java.client.JsonConverter;

public class Client {

    private static Socket clientSocket;
    private static BufferedReader reader;
    private static PrintWriter writer;
    private static String playerId;

    public static void connect(String serverAddress, int port, String id) {
        try {
            clientSocket = new Socket(serverAddress, port);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            playerId = id;
        } catch (IOException e) {
            closeEverything();
        }
    }

    public static void sendMessageToServer(String message) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("type", "message");
        data.put("message", message);
        writer.println(JsonConverter.toJson(data));
    }

    public static void networkingMethod(HashMap<String, Object> data) {
        writer.println(JsonConverter.toJson(data));
    }

    public static void listenForMessages() {
        new Thread(() -> {
            try {
                String msgFromServer;
                while ((msgFromServer = reader.readLine()) != null) {
                    HashMap<String, Object> data = (HashMap<String, Object>) JsonConverter.fromJson(msgFromServer);

                    String type = (String) data.get("type");
                    switch (type) {
                        case "chat":
                        case "game-move":
                        case "profile-info-request":
                        case "error":
                        case "exit-game":
                        case "login":
                        case "register":
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

    private static void closeEverything() {
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

        connect(serverAddress, port, null);

        HashMap<String, Object> authData = getAuthData();
        writer.println(JsonConverter.toJson(authData));

        listenForMessages();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String message = scanner.nextLine();
            sendMessageToServer(message);
        }
    }

    public static void login(String Username, String Password) {
        try {
            connect("10.13.157.168", 5050, null);
            HashMap<String, Object> authData = new HashMap<>();
            authData.put("type", "login");
            authData.put("username", Username);
            authData.put("password", Password);
            writer.println(JsonConverter.toJson(authData));
            listenForMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void register(String Username, String Password, String Email) {
        try {
            connect("localhost", 5050, null);
            HashMap<String, Object> authData = new HashMap<>();
            authData.put("type", "register");
            authData.put("username", Username);
            authData.put("password", Password);
            authData.put("email", Email);
            writer.println(JsonConverter.toJson(authData));
            listenForMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
