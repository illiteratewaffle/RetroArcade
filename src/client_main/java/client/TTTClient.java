//package client;
//
//import java.util.HashMap;
//
//public class TTTClient {
//    public static void receiveInput(GameLogic_Client.Ivec2 input) {
//        // Convert the move to a string format like "x,y"
//        String x = String.valueOf(input.x);
//        String y = String.valueOf(input.y);
//        HashMap<String, Object> data = new HashMap<>();
//        data.put("type", "game-move");
//        data.put("command", "receiveInput");
//        data.put("game","Tik_Tak_Toe");
//        data.put("x", x);
//        data.put("y", y);
//        // Send the move to the server move is column then row
//        Client.networkingMethod(data);
//    }
//    public int getCurrentPlayer() throws InterruptedException {
//        HashMap<String, Object> data = new HashMap<>();
//        data.put("type", "game-move");
//        data.put("command", "getCurrentPlayer");
//        data.put("game","Tik_Tak_Toe");
//        Client.networkingMethod(data);
//        //wait
//        Thread.sleep(300);
//        return Client.currentPlayer;
//    }
//    public boolean getWinner() throws InterruptedException {
//        HashMap<String, Object> data = new HashMap<>();
//        data.put("type", "game-move");
//        data.put("command", "getWinner");
//        data.put("game","Tik_Tak_Toe");
//        Client.networkingMethod(data);
//        //wait
//        Thread.sleep(300);
//        return Client.currentWinner;
//    }
//    public void sendmessage(String message){
//        HashMap<String, Object> data = new HashMap<>();
//        data.put("type", "chat");
//        data.put("message", message);
//        data.put("game","Tik_Tak_Toe");
//        Client.networkingMethod(data);
//    }
//    public boolean getGameOngoing() throws InterruptedException {
//        HashMap<String, Object> data = new HashMap<>();
//        data.put("type", "game-move");
//        data.put("command", "getGameOngoing");
//        data.put("game","Tik_Tak_Toe");
//        Client.networkingMethod(data);
//        //wait
//        Thread.sleep(300);
//        return Client.gameOngoing;
//    }
//    public int[][] getBoardCells() throws InterruptedException {
//        HashMap<String, Object> data = new HashMap<>();
//        data.put("type", "game-move");
//        data.put("command", "getBoardCells");
//        data.put("game","Tik_Tak_Toe");
//        Client.networkingMethod(data);
//        //wait
//        Thread.sleep(300);
//        return Client.boardCells.getFirst();
//    }
//    public boolean checkDraw() throws InterruptedException {
//        HashMap<String, Object> data = new HashMap<>();
//        data.put("type", "game-move");
//        data.put("command", "checkDraw");
//        data.put("game","Tik_Tak_Toe");
//        Client.networkingMethod(data);
//        //wait
//        Thread.sleep(300);
//        return Client.gameOngoing;
//    }
//}
