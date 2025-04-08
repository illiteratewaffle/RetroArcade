package GUI_client;

import GameLogic_Client.Checkers.CheckersController;

import GameLogic_Client.Ivec2;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ScrollPane;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CheckersGUIController implements Initializable {
    @FXML
    public ImageView sendButton;
    @FXML
    public ImageView quitButton;
    @FXML
    public ImageView infoButton;
    @FXML
    public ImageView infoOkButton;
    @FXML
    public ImageView infoBg;
    @FXML
    public ImageView muteButton;
    @FXML
    private GridPane checkerBoard;
    @FXML
    private ScrollPane checkerChatPane;
    @FXML
    private TextField checkerChatInput;
    @FXML
    private TextArea checkerChatArea;

    @FXML
    private ImageView screen;
    @FXML
    private ImageView checkerChatBg;

    private CheckersController gameLogic;
    private Stage quitPopup = new Stage();

    private static final int BOARD_SIZE = 8;
    private StackPane[][] tileBorderGrid = new StackPane[BOARD_SIZE][BOARD_SIZE];
    private ImageView[][] tilePiece = new ImageView[BOARD_SIZE][BOARD_SIZE];

    // pieces
    private Image blueChecker;
    private Image pinkChecker;
    private Image blueKingChecker;
    private Image pinkKingChecker;


    // Turn images
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
        // relative addresses
        gameLogic = new CheckersController();
        blueChecker = new Image("checkers_blue_piece.png");
        pinkChecker = new Image("checkers_pink_piece.png");
        blueKingChecker = new Image("checkers_blue_king_piece.png");
        pinkKingChecker = new Image("checkers_pink_king_piece.png");
        yourTurn = new Image("checkers_pink_piece.png");
        opponentTurn = new Image("checkers_blue_piece.png");
        winnerBlue = new Image("YOU_WIN_blue.png");
        winnerPink = new Image("YOU_WIN_pink.png");
        quitButton.setImage(new Image("home_button.png"));
        infoButton.setImage(new Image("info_button.png"));
        checkerChatBg.setImage(new Image("chat_bg.png"));

        // setup audio and mute status
        String path = Objects.requireNonNull(getClass().getResource("/music/checkersTrack.mp3")).toExternalForm(); // or absolute path
        Media sound = new Media(path);
        AudioManager.mediaPlayer = new MediaPlayer(sound);
        AudioManager.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        AudioManager.mediaPlayer.play();
        if (AudioManager.isMuted()){
            AudioManager.applyMute();
            muteButton.setImage(new Image("muteButton.png"));
        } else {
            muteButton.setImage(new Image("unmuteButton.png"));
        }

        checkerChatArea.clear();
        checkerChatArea.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-control-inner-background: transparent;" +
                        "-fx-text-fill: white;" +
                        "-fx-border-color: transparent;" +
                        "-fx-text-fill: yellow;" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-family: 'SilomBol.ttf';"
        );
        checkerChatPane.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-background: transparent;" +
                        "-fx-border-color: transparent;"
        );
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


                    border.setStyle("-fx-border-color: #e1c50e; -fx-border-width: 5;");
                    previouslySelectedTile = border;

                    sendMouseInput(row, col);
                });

                tileBorderGrid[i][j].getChildren().add(tilePiece[i][j]);
                checkerBoard.add(tileBorderGrid[i][j], j, i);
            }
        }

        // update the board and turns
        refreshBoard();
        getTurn();
    }

    /**
     * Sends mouse click input to game logic and refreshes the board
     * @param row of the clicked piece
     * @param col of the clicked piece
     */

    private void sendMouseInput(int row, int col) {
        lastClickedRow = row;
        lastClickedCol = col;

        Ivec2 tileClicked = new Ivec2(col, row);

        gameLogic.receiveInput(tileClicked);

        refreshBoard();
        getTurn();
        checkWinners();
    }

    /**
    Sets the screen to show which players turn it is
     */

    private void getTurn() {
        int currentPlayer = gameLogic.getCurrentPlayer();
        int winner = gameLogic.getWinner();

        // Check if the game is ongoing before checking the turns
        if (winner == 3) {
            // Check turns
            if (currentPlayer == 0) {
                screen.setImage(yourTurn);
            }
            else {
                screen.setImage(opponentTurn);
            }
        }
    }

    

    /**
     * Gets the board cells and updates the pieces and highlights on the board based on that
     */

    private void refreshBoard() {
        int[][] pieceBoard = gameLogic.getBoardCells(0b1).getFirst();
        int[][] highlightBoard = gameLogic.getBoardCells(0b10).getFirst();
        // itterate through the board and update one by one
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
        // Check if either player has won
        checkWinners();
    }

    /**
     * Displays win message using getWinner method from GL code
     */
    private void checkWinners() {
        // used to determine who won
        int winner = gameLogic.getWinner();

        // array of length one means someone one
        // TODO: send to networking
        // TODO: add graphics for win/lose/tie messages
        if (winner == 0) {
            screen.setImage(winnerPink);
        } else if (winner == 1) {
            screen.setImage(winnerBlue);

        // array of length two means tie
        } else if (winner == 2) {
            // screen.setImage(tie);
        }
       /* if (!gameLogic.getGameOngoing()){
            play_again.setImage(new Image("Play_Again.png"));
            check_circle.setImage(new Image("check_circle.png"));
            X_circle.setImage(new Image("X_circle.png"));
        }*/
    }
    public void XPressed(){
        quitButton.setImage(new Image("home_button_pressed.png"));
    }
    public void XReleased(){
        quitButton.setImage(new Image("home_button.png"));
    }
    public void infoPressed(){
        infoButton.setImage(new Image("infoButtonDown.png"));
    }
    public void infoReleased(){
        infoButton.setImage(new Image("info_button.png"));
    }

    public void infoButtonPress(){
        infoBg.setImage(new Image("checkers_info.png"));
        infoBg.setMouseTransparent(false);
        infoOkButton.setMouseTransparent(false);
    }
    public void info_ok_clicked(){
        infoBg.setImage(null);
        infoBg.setMouseTransparent(true);
        infoOkButton.setMouseTransparent(true);
    }

    public void muteButtonClick(){
        if(!AudioManager.isMuted()) {
            muteButton.setImage(new Image("muteButton.png"));
            AudioManager.toggleMute();
        } else {
            muteButton.setImage(new Image("unmuteButton.png"));
            AudioManager.toggleMute();
        }
    }
    public void quitCheckers() throws IOException {
        if (!quitPopup.isShowing()) {
            quitPopup = new Stage();
            Stage owner = (Stage) checkerBoard.getScene().getWindow();
            quitPopup.initOwner(owner);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("quitPopup.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            QuitPopupController controller = loader.getController();

            quitPopup.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);

            controller.closeOwner = false;
            quitPopup.setScene(scene);
            quitPopup.showAndWait();

            if (controller.closeYes) {
                AudioManager.mediaPlayer.stop();
                Parent newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gameMenu.fxml")));

                Stage stage = (Stage) checkerBoard.getScene().getWindow();

                stage.setScene(new Scene(newRoot));
                stage.show();
            }
        }
    }

    /**
     * gets a string from chat text field and appends it to chat area
     */
    public void sendMessage(){
        String message = checkerChatInput.getText();
        if (!message.trim().isEmpty()){
            checkerChatArea.appendText("You: " + message + "\n");
            checkerChatInput.clear();
        }
    }

    /**
     * function for networking to get string messages from opponents and update chat
     * @param message
     */
    public void getMessage(String message){
        checkerChatArea.appendText(message);
    }
}