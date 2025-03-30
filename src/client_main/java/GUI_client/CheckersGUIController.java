package client_main.java.GUI_client;

import client_main.java.GameLogic_Client.Checkers.CheckersController;
import client_main.java.GameLogic_Client.Ivec2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

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

    //will use if we can initialize each piece in a for loop rather than explicitly
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

        //per component, have a 2d Array

//        for(Node node : checkerBoard.getChildren()) {
//            Integer col = GridPane.getColumnIndex(node);
//            Integer row = GridPane.getRowIndex(node);
//            tileBorderGrid[row][col] = null;
//            tilePiece[row][col] = null;
//
//            //for every node you want to initilize component
//            node.setOnMouseEntered(event -> hoverevent());
//            //store the component
//
//
//
//        }

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                // Create the tile (StackPane)
                tileBorderGrid[i][j] = new StackPane();
                tilePiece[i][j] = new ImageView();

                StackPane tile = tileBorderGrid[i][j]; // Store reference

                // Store indices for use in the lambda
                int I = i;
                int J = j;

                // Add hover effect directly to the StackPane:
                tile.setOnMouseEntered(event -> {
                    System.out.println("Hovering over square: " + I + ", " + J);
                    // Optionally update the style as well
                    tile.setStyle("-fx-border-color: blue;");
                });
                tile.setOnMouseExited(event -> {
                    tile.setStyle("-fx-border-color: transparent;");
                });

                // Add the StackPane to the GridPane
                checkerBoard.add(tile, j, i);
            }
        }

        blueChecker = new Image("checkers_blue_piece.png");
        orangeChecker = new Image("checkers_orange_piece.png");
        blueKingChecker = new Image("checkers_blue_king_piece.png");
        orangeKingChecker = new Image("checkers_orange_king_piece.png");


    }

    @FXML
    private void handleMouseEntered(MouseEvent event) {
        System.out.println("Hovered over tile: " + tile.getId());
    }
    //will need a methods for handling input, updating board, and checking if a winner has been declared from game logic

}



