package GUI_client;

import GameLogic_Client.Checkers.CheckersController;
import GameLogic_Client.Ivec2;

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
    private GridPane checkerBoard;

    @FXML
    private ImageView screen;

    private CheckersController gameLogic;

    private static final int BOARD_SIZE = 8;
    private StackPane[][] tileBorderGrid = new StackPane[BOARD_SIZE][BOARD_SIZE];
    private ImageView[][] tilePiece = new ImageView[BOARD_SIZE][BOARD_SIZE];

    private Image blueChecker;
    private Image pinkChecker;
    private Image blueKingChecker;
    private Image pinkKingChecker;

    private Image yourTurn;
    private Image opponentTurn;

    private StackPane previouslySelectedTile = null;

    /**
    Initializes the GUI board
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameLogic = new CheckersController();
        blueChecker = new Image("checkers_blue_piece.png");
        pinkChecker = new Image("checkers_pink_piece.png");
        blueKingChecker = new Image("checkers_blue_king_piece.png");
        pinkKingChecker = new Image("checkers_pink_king_piece.png");
        yourTurn = new Image("X.png");
        opponentTurn = new Image("O.png");

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                // Create the tile (StackPane)
                tileBorderGrid[i][j] = new StackPane();
                tilePiece[i][j] = new ImageView();
                tilePiece[i][j].setFitWidth(30);
                tilePiece[i][j].setFitHeight(30);
                tilePiece[i][j].setPreserveRatio(true);
                tilePiece[i][j].setMouseTransparent(true);

                StackPane border = tileBorderGrid[i][j];
                ImageView tile = tilePiece[i][j];

                final int row = i;
                final int col = j;


                //hover mouse feature
                border.setOnMouseEntered(event -> {
                    if (border.getStyle().contains("transparent")) {
                        border.setStyle("-fx-border-color: lightgray;");
                    }
                });
                border.setOnMouseExited(event -> {
                    if (border.getStyle().contains("lightgray")) {
                        border.setStyle("-fx-border-color: transparent;");
                    }
                });

                //selected piece highlight
                border.setOnMouseClicked(event -> {

                    if (previouslySelectedTile != null) {
                        previouslySelectedTile.setStyle("-fx-border-color: transparent;");
                    }


                    border.setStyle("-fx-border-color: blue; -fx-border-width: 5;");
                    previouslySelectedTile = border;

                    sendMouseInput(row, col);
                });

                tileBorderGrid[i][j].getChildren().add(tilePiece[i][j]);
                checkerBoard.add(tileBorderGrid[i][j], j, i);
            }
        }
        refreshBoard();
        getTurn();
        //setupInitialPieces();
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

    /**
     * Sends mouse click input to game logic and refreshes the board
     * @param row
     * @param col
     */

    @FXML
    private void sendMouseInput(int row, int col) {
        Ivec2 tileClicked = new Ivec2(col, row);

        gameLogic.receiveInput(tileClicked);

        refreshBoard();
        getTurn();
    }

    @FXML
    private void getTurn() {
        int currentPlayer = gameLogic.getCurrentPlayer();

        if (currentPlayer == 0) {
            screen.setImage(yourTurn);
        }
        else {
            screen.setImage(opponentTurn);
        }

    }



    /**
     * Gets the board cells and updates the pieces on the board based on that
     */

    private void refreshBoard() {
        int[][] pieceBoard = gameLogic.getBoardCells(0b1).getFirst();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                switch (pieceBoard[i][j]) {
                    case 1 -> tilePiece[i][j].setImage(pinkChecker);
                    case 2 -> tilePiece[i][j].setImage(pinkKingChecker);
                    case 3 -> tilePiece[i][j].setImage(blueChecker);
                    case 4 -> tilePiece[i][j].setImage(blueKingChecker);
                    default -> tilePiece[i][j].setImage(null);
                }
            }
        }
    }
}