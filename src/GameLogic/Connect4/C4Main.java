package GameLogic.Connect4;

import GameLogic.ivec2;

public class C4Main {

    /**
     * The main method to start the Connect-4 game.
     */
    public static void main(String[] args) {
        C4Controller c4Controller = new C4Controller();
        c4Controller.start();

//        c4Controller.ReceiveInput(new ivec2(0, 0));
//        c4Controller.ReceiveInput(new ivec2(0, 0));
    }
}
