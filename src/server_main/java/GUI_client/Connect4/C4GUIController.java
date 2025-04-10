package GUI_client.Connect4;


import GameLogic_Client.*;
import GameLogic_Client.Connect4.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;


import java.net.URL;
import java.util.ResourceBundle;

public class C4GUIController implements Initializable {

    private C4Controller gameLogic;

    @FXML
    private Circle[][] boardCircles = new Circle[6][7];

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameLogic = new C4Controller();
        gameLogic.start(); // This will print the board and start the game logic();
        updateBoardUI();
    }

    // You can wire this method to buttons later
    @FXML
    private void onColClick(int col) {
        gameLogic.receiveInput(new Ivec2(3, 0));
        // for example, drop in column 3if (gameLogic.receiveInput(new Ivec2(0,0))) {
        updateBoardUI();
    }

    @FXML
    private void onCol0Click() {
        gameLogic.receiveInput(new Ivec2(0, 0));
    }

    @FXML
    private void onCol1Click() {
        gameLogic.receiveInput(new Ivec2(1, 0));
    }

    @FXML
    private void onCol2Click() {
        gameLogic.receiveInput(new Ivec2(2, 0));
    }

    @FXML
    private void onCol3Click() {
        gameLogic.receiveInput(new Ivec2(3, 0));
    }

    @FXML
    private void onCol4Click() {
        gameLogic.receiveInput(new Ivec2(4, 0));
    }

    @FXML
    private void onCol5Click() {
        gameLogic.receiveInput(new Ivec2(5, 0));
    }

    @FXML
    private void onCol6Click() {
        gameLogic.receiveInput(new Ivec2(6, 0));
    }

    private void updateBoardUI() {
        int[][] board = gameLogic.getBoardCells(0b01).getFirst();
        for(int row = 0; row < 6; row++){
            for (int col = 0; col<7; col ++){
                Color pieceColor = getColorForPiece(board[row][col]);
               // boardCircles[row][col].setFill(pieceColor);
            }
        }

    }
    private Color getColorForPiece(int pieceID){
        if (pieceID == C4Piece.RED.getValue()) {
            return Color.RED;
        } else if (pieceID == C4Piece.BLUE.getValue()) {
            return Color.BLUE;
        } else {
            return Color.TRANSPARENT;
        }
    }

}
