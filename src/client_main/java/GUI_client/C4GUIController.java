package GUI_client;

import GameLogic_Client.Connect4.C4Controller;
import GameLogic_Client.Connect4.C4Piece;
import GameLogic_Client.ivec2;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class C4GUIController implements Initializable {

    private C4Controller c4Controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert c4GUIGrid != null : "🔥 FATAL: c4GUIGrid was NOT injected!";
        System.out.println("✅ c4GUIGrid injected successfully: " + c4GUIGrid);
        c4Controller = new C4Controller();
        c4Controller.start(); // This will print the board and start the game logic
        c4GUIGrid.setGridLinesVisible(true);
    }

    // You can wire this method to buttons later
    @FXML
    private void handleUserClick() {
        c4Controller.ReceiveInput(new ivec2(3, 0)); // for example, drop in column 3
    }

    @FXML
    private void onCol0Click() {
        handleColumnClick(0);
    }

    @FXML
    private void onCol1Click() {
        handleColumnClick(1);
    }

    @FXML
    private void onCol2Click() {
        handleColumnClick(2);
    }

    @FXML
    private void onCol3Click() {
        handleColumnClick(3);
    }

    @FXML
    private void onCol4Click() {
        handleColumnClick(4);
    }

    @FXML
    private void onCol5Click() {
        handleColumnClick(5);
    }

    @FXML
    private void onCol6Click() {
        handleColumnClick(6);
    }

    @FXML
    private GridPane c4GUIGrid;

    private void updateBoard() {
//        C4Piece[][] board = gameLogic.getBoard().getC4Board(); // assuming getBoard() returns C4Board
        C4Piece[][] board = c4Controller.getC4Board();;

        c4GUIGrid.getChildren().clear();

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                C4Piece piece = board[row][col];
                if (piece != C4Piece.BLANK) {
                    String imgPath = piece == C4Piece.RED
                            ? "/connect_4_assets/c4_pink_piece.png"
                            : "/connect_4_assets/c4_blue_piece.png";
                    addPieceToGrid(row, col, imgPath);
                }
            }
        }
    }

    private void addPieceToGrid(int row, int col, String imgPath) {
        ImageView piece = new ImageView(new Image(getClass().getResourceAsStream(imgPath)));
        piece.setFitWidth(30);
        piece.setFitHeight(30);
        piece.setOpacity(1.0);

        c4GUIGrid.add(piece, col, row); // ✅ Add only the image
    }

    private void handleColumnClick(int col) {
        if (!c4Controller.getC4IsGameOver()) {
            c4Controller.ReceiveInput(new ivec2(col, 0));
            updateBoard();

            if (c4Controller.getC4IsGameOver()) {
                C4Piece winner = c4Controller.getC4WinnerAsEnum();
                if (winner == C4Piece.BLANK) {
                    System.out.println("It's a draw!");
                } else {
                    System.out.println("Player " + winner + " wins! 🎉");
                }
                disableAllColumnButtons();
            }
        }
    }

    @FXML private Button col0Button, col1Button, col2Button, col3Button, col4Button, col5Button, col6Button;
    private void disableAllColumnButtons() {
        col0Button.setDisable(true);
        col1Button.setDisable(true);
        col2Button.setDisable(true);
        col3Button.setDisable(true);
        col4Button.setDisable(true);
        col5Button.setDisable(true);
        col6Button.setDisable(true);
    }

}
