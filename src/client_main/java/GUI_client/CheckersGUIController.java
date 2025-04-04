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

    private Image winnerBlue;
    private Image winnerPink;
    //private Image tie;

    private StackPane previouslySelectedTile = null;
    private int lastClickedRow = -1;
    private int lastClickedCol = -1;

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
        yourTurn = new Image("checkers_pink_piece.png");
        opponentTurn = new Image("checkers_blue_piece.png");
        winnerBlue = new Image("YOU_WIN_blue.png");
        winnerPink = new Image("YOU_WIN_pink.png");
        //tie = new Image("TIE.png");



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


                border.setOnMouseEntered(event -> {
                    String currentStyle = border.getStyle();
                    if (!currentStyle.contains("#78956f") && !currentStyle.contains("#215b85")
                            && !currentStyle.contains("#81509f") && !currentStyle.contains("#892d37") &&
                            !currentStyle.contains("#e1c50e")) {
                        border.setStyle("-fx-border-color: lightgray; -fx-border-width: 3;");
                    }
                });

                border.setOnMouseExited(event -> {
                    String currentStyle = border.getStyle();
                    if (currentStyle.contains("lightgray")) {
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
    }

    /**
     * Sends mouse click input to game logic and refreshes the board
     * @param row
     * @param col
     */

    @FXML
    private void sendMouseInput(int row, int col) {
        lastClickedRow = row;
        lastClickedCol = col;

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
        int[][] highlightBoard = gameLogic.getBoardCells(0b10).getFirst();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                switch (pieceBoard[i][j]) {
                    case 1 -> tilePiece[i][j].setImage(pinkChecker);
                    case 2 -> tilePiece[i][j].setImage(pinkKingChecker);
                    case 3 -> tilePiece[i][j].setImage(blueChecker);
                    case 4 -> tilePiece[i][j].setImage(blueKingChecker);
                    default -> tilePiece[i][j].setImage(null);

                }
                String highlightStyle = switch (highlightBoard[i][j]) {
                    case 1 -> "-fx-border-color: #78956f; -fx-border-width: 4;"; //selectable pieces
                    case 3 -> "-fx-border-color: #81509f; -fx-border-width: 4;"; //valid move
                    case 4 -> "-fx-border-color: #892d37; -fx-border-width: 4;"; //capture
                    default -> "-fx-border-color: transparent;";
                };
                tileBorderGrid[i][j].setStyle(highlightStyle);
            }
        }
        if (lastClickedRow >= 0 && lastClickedCol >= 0) {
            tileBorderGrid[lastClickedRow][lastClickedCol].setStyle("-fx-border-color: #e1c50e; -fx-border-width: 4;");
        }
        checkWinners();
    }

    /**
     * Displays win message using getWinner method from GL code
     */
    private void checkWinners() {
        // used to determine who won
        int [] winner = gameLogic.getWinner();

        // array of length one means someone one
        // TODO: send to networking
        // TODO: add graphics for win/lose/tie messages
        if (winner.length == 1) {
            if (winner[0] == 0) {
                screen.setImage(winnerPink);
            } else {
                screen.setImage(winnerBlue);
            }
        // array of length two means tie
        } else if (winner.length == 2) {
           // screen.setImage(tie);
        }
        // otherwise do nothing and keep playing
    }
}