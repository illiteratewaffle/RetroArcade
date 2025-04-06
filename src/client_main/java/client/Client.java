package client_main.java.client;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket clientSocket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private  String PlayerID; // placeholder for the playerID
/**
     * Constructor for ClientHandler.
     *
     * @param socket  the Socket that the client is connected on
     * @param PlayerID The associated PlayerID for of the user.
     */
    public Client(Socket socket, String PlayerID){
        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.PlayerID = PlayerID;
        }catch (IOException e){
            CloseEverything(clientSocket, bufferedReader, bufferedWriter);
        }
    }
    /**
     * Sends the networking info to the associated ClientHandler.
     * @param message the message the function wants to send
     */
    public String SendMessageToHandler(String message){
        try{
        bufferedWriter.write(PlayerID);
        bufferedWriter.newLine();
        bufferedWriter.flush();

        bufferedWriter.write(message);
        bufferedWriter.newLine();
        bufferedWriter.flush();
        return ReturnMessage();
        }catch (IOException e) {
            CloseEverything(clientSocket, bufferedReader, bufferedWriter);
            return null;
        }
    }
    /**
     * Returns the networking response to the function caller.
     */
    public String ReturnMessage(){
        String returnMessage = null;
        while (clientSocket.isConnected()){
            try{
                returnMessage = bufferedReader.readLine();
                break;
            }catch (IOException e){
                returnMessage = null;
                break;
            }}
        return returnMessage;
    }
    /**
     * Closes the client connection.
     */
    public void CloseEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
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
    /**
     * The function that the thread runs
     */
    public static void main(String[] args){
        try{
        Socket socket = new Socket("localHost", 5050);
        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        BufferedReader bufferedReader = new BufferedReader((inputStreamReader));
        String PlayerID = bufferedReader.readLine();
        Client client = new Client(socket, PlayerID);
        String message = bufferedReader.readLine();
        client.SendMessageToHandler(message);
        }catch (IOException e){
        }
    }
}
