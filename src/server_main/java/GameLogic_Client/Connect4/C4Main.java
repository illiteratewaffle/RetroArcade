package GameLogic_Client.Connect4;
import GameLogic_Client.Ivec2;

public class C4Main {

    /**
     * The main method to start the Connect-4 game.
     */
    public static void main(String[] args) {
        C4Controller c4Controller = new C4Controller();
        c4Controller.start();
    }
}
