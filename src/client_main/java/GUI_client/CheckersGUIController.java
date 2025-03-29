package client_main.java.GUI_client;

import client_main.java.GameLogic_Client.Checkers.CheckersController;
import client_main.java.GameLogic_Client.ivec2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class CheckersGUIController implements Initializable {

    @FXML
    private ImageView carpetBackground;
    @FXML
    private ImageView danceBoard;
    @FXML
    private GridPane checkerBoard;

    @FXML private StackPane tileBorder_5_6;

    @FXML private ImageView tile_5_6;

    private CheckersController gameLogic;

    private static final int BOARD_SIZE = 8;
    private StackPane[][] tileBorderGrid = new StackPane[BOARD_SIZE][BOARD_SIZE];
    private ImageView[][] tilePiece = new ImageView[BOARD_SIZE][BOARD_SIZE];

    private Image blueChecker;
    private Image orangeChecker;
    private Image blueKingChecker;
    private Image orangeKingChecker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameLogic = new CheckersController();

        blueChecker = new Image("checkers_blue_piece.png");
        orangeChecker = new Image("checkers_orange_piece.png");
        blueKingChecker = new Image("checkers_blue_king_piece.png");
        orangeKingChecker = new Image("checkers_orange_king_piece.png");


    }
}



