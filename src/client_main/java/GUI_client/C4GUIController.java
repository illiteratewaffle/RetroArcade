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

    private C4Controller gameLogic;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert c4GUIGrid != null : "🔥 FATAL: c4GUIGrid was NOT injected!";
        System.out.println("✅ c4GUIGrid injected successfully: " + c4GUIGrid);
        gameLogic = new C4Controller();
        gameLogic.start(); // This will print the board and start the game logic
        c4GUIGrid.setGridLinesVisible(true);
    }

    // You can wire this method to buttons later
    @FXML
    private void handleUserClick() {
        gameLogic.ReceiveInput(new ivec2(3, 0)); // for example, drop in column 3
    }

    @FXML
    private void onCol0Click() {
        gameLogic.ReceiveInput(new ivec2(0, 0));
        updateBoard();
    }

    @FXML
    private void onCol1Click() {
        gameLogic.ReceiveInput(new ivec2(1, 0));
        updateBoard();
    }

    @FXML
    private void onCol2Click() {
        gameLogic.ReceiveInput(new ivec2(2, 0));
        updateBoard();
    }

    @FXML
    private void onCol3Click() {
        gameLogic.ReceiveInput(new ivec2(3, 0));
        updateBoard();
    }

    @FXML
    private void onCol4Click() {
        gameLogic.ReceiveInput(new ivec2(4, 0));
        updateBoard();
    }

    @FXML
    private void onCol5Click() {
        gameLogic.ReceiveInput(new ivec2(5, 0));
        updateBoard();
    }

    @FXML
    private void onCol6Click() {
        gameLogic.ReceiveInput(new ivec2(6, 0));
        updateBoard();
    }

    @FXML
    private GridPane c4GUIGrid;

    private void updateBoard() {
//        C4Piece[][] board = gameLogic.getBoard().getC4Board(); // assuming getBoard() returns C4Board
        C4Piece[][] board = gameLogic.getC4Board();;

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

//    private void addPieceToGrid(int row, int col, String imgPath) {
//        ImageView piece = new ImageView(new Image(getClass().getResourceAsStream(imgPath)));
//        piece.setFitWidth(30);
//        piece.setFitHeight(30);
//        piece.setOpacity(1.0);
//
//        Button btn = new Button();
//        btn.setGraphic(piece);
//        btn.setStyle("-fx-background-color: transparent; -fx-alignment: center;");
//        btn.setDisable(true);
//        btn.setPrefSize(30, 30);
//
//
//        c4GUIGrid.add(btn, col, row); // ⚠️ note: GridPane uses (col, row)
//    }

    private void addPieceToGrid(int row, int col, String imgPath) {
        ImageView piece = new ImageView(new Image(getClass().getResourceAsStream(imgPath)));
        piece.setFitWidth(30);
        piece.setFitHeight(30);
        piece.setOpacity(1.0);

        c4GUIGrid.add(piece, col, row); // ✅ Add only the image
    }




}
