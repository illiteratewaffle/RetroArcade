package client_main.java.GameLogic_Client.Connect4;

import client_main.java.GameLogic_Client.ivec2;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class C4GUIController implements Initializable {

    private C4Controller gameLogic;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameLogic = new C4Controller();
        gameLogic.start(); // This will print the board and start the game logic
    }

    // You can wire this method to buttons later
    @FXML
    private void handleUserClick() {
        gameLogic.ReceiveInput(new ivec2(3, 0)); // for example, drop in column 3
    }
}
