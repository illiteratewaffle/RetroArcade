package client;
import server.player.PlayerHandler;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Handles client connections and communication.
 */
public class ClientHandler implements Runnable  {
    private Socket clientSocket;
    private PlayerHandler playerHandler;
    private boolean running;

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    /**
     * Constructor for ClientHandler.
     *
     * @param clientSocket  the Socket that the client is connected on
     * @param playerHandler The associated PlayerHandler for communication.
     */
    public ClientHandler(Socket clientSocket, PlayerHandler playerHandler) {
        try {
            this.clientSocket = clientSocket;
            this.playerHandler = playerHandler;
            this.running = true;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientHandlers.add(this);
        }catch (IOException e) {
            closeEverything(clientSocket, bufferedReader, bufferedWriter);
        }
    }
      /**
     * The function that the thread runs
     */
      @Override
    public void run() {
          String commandFromClient; // placeholder for clientFuntions
          String messageFromServer; // placeholder for server messages
          String message; //
        while (clientSocket.isConnected()) {
            try {
                message = bufferedReader.readLine();
                if (CheckIfJson(message) == true){
                    messageFromServer = message;
                    message = ConvertFromJson(messageFromServer);
                    sendResponse(message);
                }
                else{
                    commandFromClient = message;
                    ConvertToJson(commandFromClient);
                    message = ConvertToJson(commandFromClient);
                    sendToPlayerHandler(message);
                }
            } catch (Exception e) {
                closeEverything(clientSocket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    /**
     * Processes function requests.
     */
    public void processRequest(String request) {
        // Implement request processing
    }

    /**
     * Converts data into a JSON string.
     */
    public String ConvertToJson(Object data) {

        try {
            // Convert recieved Function to JSON
            return "{}";  // Placeholder
        } catch (Exception e) {
            return "{}"; // Return an empty JSON object on error
        }
    }

    /**
     * Converts a JSON string into a return function.
     */
    public String ConvertFromJson(String jsonData) {
        // Convert JSON encoding to Function to send back
        return null;
    }
    public boolean CheckIfJson(String inputData){
        //check if message is from server or client
        return true; //placeholder
    }
    /**
     * Sends JSON string to the associated PlayerHandler.
     */
    public void sendToPlayerHandler(String message) {
        for (ClientHandler clientHandler :clientHandlers){
        try {
            clientHandler.bufferedWriter.write(message);
            clientHandler.bufferedWriter.newLine();
            clientHandler.bufferedWriter.flush();
        } catch (Exception e) {;
            closeEverything(clientSocket, bufferedReader, bufferedWriter);
        }
        }
    }

    /**
     * Sends a response function back to sender.
     */
    public void sendResponse(String response) {
        // Send response to Sender
    }

    /**
     * Closes the client connection.
     */
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        running = false;
        try {
            if (bufferedReader != null){
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
            if (socket != null){
                socket.close();
            }
        }catch (IOException e){

        }
    }
}
