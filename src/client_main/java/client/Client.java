package client;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import client.JsonConverter;
import GUI_client.LoginGUIController;

public class Client {

    private static boolean loginSuccess = true;
    private static String bio;
    private static String nickname;
    private static String username;
    private static String profilePath;



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
                LoginGUIController.loginSuccess = true;
                while ((msgFromServer = reader.readLine()) != null) {
                    HashMap<String, Object> data = (HashMap<String, Object>) JsonConverter.fromJson(msgFromServer);

                    String type = (String) data.get("type");
                    switch (type) {
                        case "chat":
                            String sender = (String) data.get("sender");
                            String message = (String) data.get("message");
                        case "game-move":
                            handleGameCommand(data);
                            break;
                        case "profile-info-request":

                        case "error":
                            if (data.get("message").equals("java.sql.SQLException: Incorrect Username or Password")){
                                loginSuccess = false;
                                LoginGUIController.loginSuccess = false;
                                break;
                            } else{
                                loginSuccess = true;
                                break;
                            }
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

    public static void closeEverything() {
        try {
                reader = null;
                writer = null;
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
            connect("10.9.125.187", 5050, null);
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
    public static BufferedReader getReader(){
        return reader;
    }
    public static PrintWriter getWriter(){
        return writer;
    }
    private static void handleGameCommand(HashMap<String, Object> data) {
        String command = (String) data.get("command");

        switch (command) {
            case "receiveInput":
                ArrayList<Double> inputParams = (ArrayList<Double>) data.get("parameter");

                int x = inputParams.get(0).intValue();
                int y = inputParams.get(1).intValue();
                //gameLogic.receiveInput(new int[]{x, y});  // assuming you have this
                break;

            case "removePlayer":
                int playerToRemove = ((Double) data.get("parameter")).intValue();
                //gameLogic.removePlayer(playerToRemove);  // assuming you have this
                break;

            case "getWinner":
                ArrayList<Double> winnersRaw = (ArrayList<Double>) data.get("data");
                //List<Integer> winners = winnersRaw.stream().map(Double::intValue).toList();
                //System.out.println("Winner(s): " + winners);
                break;

            case "getGameOngoing":
                boolean ongoing = (Boolean) data.get("data");
                //System.out.println("Game ongoing: " + ongoing);
                break;

            case "getBoardCells":
                int boardId = ((Double) data.get("parameter")).intValue();

                break;

            case "getBoardSize":
                ArrayList<Double> sizeRaw = (ArrayList<Double>) data.get("data");
                int width = sizeRaw.get(0).intValue();
                int height = sizeRaw.get(1).intValue();
                System.out.println("Board size: " + width + "x" + height);
                break;

            case "getCurrentPlayer":
                int currentPlayer = ((Double) data.get("data")).intValue();
                System.out.println("Current player: " + currentPlayer);
                break;

            case "gameOngoingChangedSinceLastCommand":
            case "winnersChangedSinceLastCommand":
            case "currentPlayerChangedSinceLastCommand":
                boolean changed = (Boolean) data.get("data");
                //System.out.println(command + ": " + changed);
                break;

            case "boardChangedSinceLastCommand":
                int changedBoardId = ((Double) data.get("data")).intValue();
                //System.out.println("Board changed: " + changedBoardId);
                break;
            default:
                System.err.println("Unknown game command: " + command);
                break;
        }
    }
    public static boolean getLoginSuccess(){
        return loginSuccess;
    }
}
