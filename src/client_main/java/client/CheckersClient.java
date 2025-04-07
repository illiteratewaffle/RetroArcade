package client_main.java.client;
public class CheckersClient {
    public void receiveInput(GameLogic_Client.Ivec2 input) {
        // Convert the move to a string format like "x,y"
        String move = input.x + "," + input.y;

        // Send the move to the server move is column then row
        Client.networkingMethod("game-move", move, "checkers", "null", "null");
    }

}
