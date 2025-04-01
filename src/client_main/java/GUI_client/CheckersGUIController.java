package GUI_client;

import GameLogic_Client.Checkers.CheckersController;
import GameLogic_Client.Ivec2;

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
    private Image pinkChecker;
    private Image blueKingChecker;
    private Image pinkKingChecker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameLogic = new CheckersController();
        blueChecker = new Image("checkers_blue_piece.png");
        pinkChecker = new Image("checkers_pink_piece.png");
        blueKingChecker = new Image("checkers_blue_king_piece.png");
        pinkKingChecker = new Image("checkers_pink_king_piece.png");

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                // Create the tile (StackPane)
                tileBorderGrid[i][j] = new StackPane();
                tilePiece[i][j] = new ImageView();
                tilePiece[i][j].setFitWidth(30);
                tilePiece[i][j].setFitHeight(30);
                tilePiece[i][j].setPreserveRatio(true);

                StackPane tile = tileBorderGrid[i][j]; // Store reference

                tile.setOnMouseEntered(event -> {
                    tile.setStyle("-fx-border-color: #ffcc00; -fx-border-width: 5;");
                });
                tile.setOnMouseExited(event -> {
                    tile.setStyle("-fx-border-color: transparent;");
                });

                tileBorderGrid[i][j].getChildren().add(tilePiece[i][j]);
                checkerBoard.add(tileBorderGrid[i][j], j, i);
            }
        }

        setupInitialPieces();
    }

    /**
    Sets up the initial pieces for game board
     */

    private void setupInitialPieces() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if ((i + j) % 2 != 0) {
                    if (i < 3) {
                        tilePiece[i][j].setImage(pinkChecker);
                    } else if (i > 4) {
                        tilePiece[i][j].setImage(blueChecker);
                    }
                }
            }
        }
    }

    @FXML
    private void handleMouseEntered(javafx.scene.input.MouseEvent event) {
        System.out.println("Hovered over tile: " + event.getTarget());
    }
    //will need a methods for handling input, updating board, and checking if a winner has been declared from game logic

}



