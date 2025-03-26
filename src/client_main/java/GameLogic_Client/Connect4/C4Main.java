package client_main.java.GameLogic_Client.Connect4;

import client_main.java.GameLogic_Client.ivec2;

public class C4Main {

    /**
     * The main method to start the Connect-4 game.
     */
    public static void main(String[] args) {
        C4Controller c4Controller = new C4Controller();
        c4Controller.start();

        // Testing a full column
        c4Controller.ReceiveInput(new ivec2(0, 0));
        c4Controller.ReceiveInput(new ivec2(0, 0));
        c4Controller.ReceiveInput(new ivec2(0, 0));
        c4Controller.ReceiveInput(new ivec2(0, 0));
        c4Controller.ReceiveInput(new ivec2(0, 0));
        c4Controller.ReceiveInput(new ivec2(0, 0));
        c4Controller.ReceiveInput(new ivec2(0, 0)); //
        c4Controller.ReceiveInput(new ivec2(0, 0));
        c4Controller.ReceiveInput(new ivec2(1, 0));

    }
}
