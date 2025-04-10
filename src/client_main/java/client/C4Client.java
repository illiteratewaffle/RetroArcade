//package client;
//
//import java.util.HashMap;
//
//public class C4Client {
//    public void receiveInput(GameLogic_Client.Ivec2 input){
//    String col = String.valueOf(input.x);
//    HashMap<String, Object> data = new HashMap<>();
//    data.put("type", "game-move");
//    data.put("command", "receiveInput");
//    data.put("game","Connect4");
//    data.put("Column", col);
//    Client.networkingMethod(data);
//    }
//    public void getCurrentPlayer(){
//        HashMap<String, Object> data = new HashMap<>();
//        data.put("type", "game-move");
//        data.put("command", "getCurrentPlayer");
//        data.put("game","Connect4");
//        Client.networkingMethod(data);
//    }
//    public void sendmessage(String message){
//        HashMap<String, Object> data = new HashMap<>();
//        data.put("type", "chat");
//        data.put("message", message);
//        data.put("game","Connect4");
//        Client.networkingMethod(data);
//    }
//}
