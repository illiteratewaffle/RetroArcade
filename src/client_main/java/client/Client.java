package client;
import java.io.*;
import java.net.Socket;
import java.util.*;

import client.JsonConverter;
import GUI_client.LoginGUIController;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Client {

    private static boolean loginSuccess = true;
    private static String bio;
    private static String nickname;
    private static String username;
    private static String profilePath;
    private static List<String> friends = new ArrayList<>(); // stores list of friends as ids
    private static List<String> friendRequests = new ArrayList<>(); // stores list of friend requests as ids
    private static String friendUsername;
    private static List<String> gameHistory = new ArrayList<>();
    private static double[] winLossRatio = new double[3];
    private static int[] rating = new int[3];
    private static String[] rank = new String[3];
    private static int[] wins = new int[3];
    private static int[][][] boardCells;
    private static boolean gameOngoing;
    private static int[] currentWinner;
    private static int otherPlayerId;
    private static int gameType;


    private static Socket clientSocket;
    private static BufferedReader reader;
    private static PrintWriter writer;
    private static String playerId;
    private static BlockingQueue<HashMap<String, Object>> messageQueue = new LinkedBlockingQueue<>();
    private static volatile boolean running = false;

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
    private static void startNetworkingThread() {
        running = true;
        new Thread(() -> {
            while (running) {
                try {
                    HashMap<String, Object> data = messageQueue.take(); // waits if queue is empty
                    synchronized (writer) {
                        writer.println(JsonConverter.toJson(data));
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "NetworkingThread").start();
    }

    public static void networkingMethod(HashMap<String, Object> data) {
        if (running) {
            messageQueue.offer(data); // adds to the queue
        }
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
                            String sender = (String) data.get("id");
                            String message = (String) data.get("message");
                        case "game-request":
                            otherPlayerId = (int) data.get("sender");
                            gameType = (int) data.get("game-type");
                            break;
                        case "game-move":
                            handleGameCommand(data);
                            break;
                        case "profile-info-request":
                            handleProfileCommand(data);
                            break;
                        case "error":
                            if (data.get("message").equals("java.sql.SQLException: Incorrect Username or Password")){
                                loginSuccess = false;
                                LoginGUIController.loginSuccess = false;
                                break;
                            } else{
                                LoginGUIController.loginSuccess = false;
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
                running = false;
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
        startNetworkingThread();

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
            startNetworkingThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void register(String Username, String Password, String Email) {
        try {
            connect("10.9.125.187", 5050, null);
            HashMap<String, Object> authData = new HashMap<>();
            authData.put("type", "register");
            authData.put("username", Username);
            authData.put("password", Password);
            authData.put("email", Email);
            writer.println(JsonConverter.toJson(authData));
            listenForMessages();
            startNetworkingThread();
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
    public static void enqueue(int gameType) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("type", "enqueue");
        data.put("game-type", gameType);
        networkingMethod(data);
    }
    public static void sendFriendRequest(String username) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("type", "send-friend-request");
        data.put("username", username);
        networkingMethod(data);
    }

    public static void acceptFriendRequest(int username) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("type", "accept-friend-request");
        data.put("username", username);
        networkingMethod(data);
    }

    public static void sendGameRequest(String username, int gameType) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("type", "send-game-request");
        data.put("username", username);
        data.put("game-type", gameType);
        networkingMethod(data);
    }
    public static void acceptGameRequest(String username, int gameType) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("type", "accept-game-request");
        data.put("username", username);
        data.put("game-type", gameType);
        networkingMethod(data);
    }

    private static void handleGameCommand(HashMap<String, Object> data) {
        String command = (String) data.get("command");

        switch (command) {
            case "getWinner":
                List<Integer> winnersRaw = (List<Integer>) data.get("data");
                    currentWinner = JsonConverter.convertToIntArray(winnersRaw);
                break;
            case "getGameOngoing":
                gameOngoing = (Boolean) data.get("data");
                break;
            case "getBoardCells":
                int boardLayer = ((Integer) data.get("parameter")).intValue();
                ArrayList<ArrayList<List<Integer>>> rawBoard = (ArrayList<ArrayList<List<Integer>>>) data.get("data");
               // boardCells = JsonConverter.convertListTo3dArray(rawBoard);

                break;

            default:
                System.err.println("Unknown game command: " + command);
                break;
        }
    }
    private static void handleProfileCommand(HashMap<String, Object> data) {
        String command = (String) data.get("info");

        switch (command) {
            case "bio":
                bio = (String) data.get("message");
                break;
            case "nickname":
                 nickname = (String) data.get("message");
                 break;
            case "username":
                username = (String) data.get("message");
                break;
            case "profilePath":
                profilePath = (String) data.get("message");
                break;

            case "friends":
                friends = (List<String>) data.get("message");
                break;

            case "friendRequests":
                friendRequests = (List<String>) data.get("message");
                break;

            case "friendId":
                friendUsername = (String) data.get("message");
                break;

            case "gameHistory":
                gameHistory = (List<String>) data.get("message");
                break;

            case "winLossRatio":
                winLossRatio = (double[]) data.get("message");
                break;

            case "rating":
                List<Integer> ratingRaw = (List<Integer>) data.get("message");
                rating = JsonConverter.convertToIntArray(ratingRaw);
                break;

            case "rank":
                rank = (String[]) data.get("message");
                break;

            case "wins":
                List<Integer> winsRaw = (List<Integer>) data.get("message");
                wins = JsonConverter.convertToIntArray(winsRaw);
                break;
            default:
                System.err.println("Unknown profile command: " + command);
                break;
        }
    }
    public static boolean getLoginSuccess(){
        return loginSuccess;
    }

    public static boolean getGameOngoing() {
        return gameOngoing;
    }

    public static int[] checkWinners() {
        return currentWinner;
    }

    public static int[][][] getBoardCells() {
        return boardCells;
    }

    public static String getBio() {
        return bio;
    }

    public static String getNickname() {
        return nickname;
    }

    public static String getUsername() {
        return username;
    }

    public static String getProfilePath() {
        return profilePath;
    }

    public static List<String> getFriends() {
        return friends;
    }

    public static List<String> getFriendRequests() {
        return friendRequests;
    }

    public static String getFriendUsername() {
        return friendUsername;
    }

    public static List<String> getGameHistory() {
        return gameHistory;
    }

    public static double getWinLossRatio(int index) {
        return winLossRatio[index];
    }

    public static int getRating(int index) {
        return rating[index];
    }

    public static String getRank(int index) {
        return rank[index];
    }

    public static int getWins(int index) {
        return wins[index];
    }

}
