package client_main.java.client;

import java.util.HashMap;

public class C4Client {
    public void receiveInput(GameLogic_Client.Ivec2 input){
    String col = String.valueOf(input.x);
    HashMap<String, Object> data = new HashMap<>();
    data.put("type", "game-move");
    data.put("message", "null");
    data.put("game","Connect4");
    data.put("Column", col);
    Client.networkingMethod(data);
    }

}
