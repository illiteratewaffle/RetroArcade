package client_main.java.client;

public class C4Client {
    public void receiveInput(GameLogic_Client.Ivec2 input){
    String moveMessage = String.valueOf(input.x);
    Client.networkingMethod("game-move", moveMessage, "connect4", "null", "null");
    }

}
