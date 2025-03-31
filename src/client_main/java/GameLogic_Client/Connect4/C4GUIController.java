package GameLogic_Client.Connect4;

import GameLogic_Client.ivec2;
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

    @FXML
    private void onCol0Click() {
        gameLogic.ReceiveInput(new ivec2(0, 0));
    }

    @FXML
    private void onCol1Click() {
        gameLogic.ReceiveInput(new ivec2(1, 0));
    }

    @FXML
    private void onCol2Click() {
        gameLogic.ReceiveInput(new ivec2(2, 0));
    }

    @FXML
    private void onCol3Click() {
        gameLogic.ReceiveInput(new ivec2(3, 0));
    }

    @FXML
    private void onCol4Click() {
        gameLogic.ReceiveInput(new ivec2(4, 0));
    }

    @FXML
    private void onCol5Click() {
        gameLogic.ReceiveInput(new ivec2(5, 0));
    }

    @FXML
    private void onCol6Click() {
        gameLogic.ReceiveInput(new ivec2(6, 0));
    }

}
