package GUI_client;

import GameLogic_Client.Ivec2;
import GameLogic_Client.GameState;
import GameLogic_Client.TicTacToe.TTTBoard;
import GameLogic_Client.TicTacToe.TTTGameController;
import client.Client;
import client.TTTClient;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class TTTController implements Initializable {

    // FXML declarations
    @FXML
    public ImageView muteButton;
    @FXML
    public ImageView turnBanner;
    @FXML
    public ImageView info_bg;
    @FXML
    public ImageView info_ok_button;
    @FXML
    public ImageView infoButton;
    @FXML
    public ImageView chat_bg;
    @FXML
    public ImageView sendButton;
    @FXML
    public ScrollPane chat_pane;
    @FXML
    public TextArea chat_area;
    @FXML
    public TextField chat_input_field;
    @FXML
    public ImageView quit_image;
    @FXML
    public ImageView Win_Lose_Banner;
    @FXML
    public StackPane TileBorder_0_0;
    @FXML
    public StackPane TileBorder_0_1;
    @FXML
    public StackPane TileBorder_0_2;
    @FXML
    public StackPane TileBorder_1_0;
    @FXML
    public StackPane TileBorder_1_1;
    @FXML
    public StackPane TileBorder_1_2;
    @FXML
    public StackPane TileBorder_2_0;
    @FXML
    public StackPane TileBorder_2_1;
    @FXML
    public StackPane TileBorder_2_2;
    @FXML
    public ImageView Tile_0_0;
    @FXML
    public ImageView Tile_1_0;
    @FXML
    public ImageView Tile_2_0;
    @FXML
    public ImageView Tile_0_1;
    @FXML
    public ImageView Tile_1_1;
    @FXML
    public ImageView Tile_2_1;
    @FXML
    public ImageView Tile_0_2;
    @FXML
    public ImageView Tile_1_2;
    @FXML
    public ImageView Tile_2_2;

    @FXML
    public ImageView redButton;
    @FXML
    public ImageView blueButton;
    @FXML
    public ImageView yellowButton;
    @FXML
    public ImageView greenButton;

    @FXML
    public ImageView background_image;
    @FXML
    public ImageView board_image;
    @FXML
    public GridPane gameBoard;

    // game controller for TTT logic
    static TTTGameController theGame = new TTTGameController();

    public static int yourPiece;
    // chat manager
    ChatManager chatManager = new ChatManager();

    public static BooleanProperty isYourTurn = new SimpleBooleanProperty(true);
    public static BooleanProperty msgReceived = new SimpleBooleanProperty(false);
    public static String currentMessage;
    // easter egg
    private final ArrayList<Character> EEList = new ArrayList<Character>();

    // stage for "are you sure?" popup
    private Stage quitPopup = new Stage();

    public TTTClient tttClient;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tttClient = new TTTClient();
        // set background and foreground images
        msgReceived.addListener((observableValue, False, True) -> {
            getMessage(currentMessage);
        });
        isYourTurn.addListener((observable, False, True) -> {
            if (True) {
                // change new int[][]
                int[][] board = Client.getBoardCells().getFirst();
                updateBoard(board);
            }
        });
        Image bg_image = new Image("background_retro.png");
        Image b_image = new Image("foreground.png");
        background_image.setImage(bg_image);
        board_image.setImage(b_image);
        // set button images
        quit_image.setImage(new Image("home_button.png"));
        infoButton.setImage(new Image("info_button.png"));
        turnBanner.setImage(new Image("XTurn.png"));
        sendButton.setImage(new Image("send_button.png"));
        chat_bg.setImage(new Image("chat_bg.png"));

        // setup soundtrack
        String path = Objects.requireNonNull(getClass().getResource("/music/TTTTrack.mp3")).toExternalForm(); // or absolute path
        Media sound = new Media(path);
        AudioManager.mediaPlayer = new MediaPlayer(sound);
        AudioManager.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        AudioManager.mediaPlayer.play();
        // if audioManager is muted, mute soundtrack, update mute button
        if (AudioManager.isMuted()) {
            AudioManager.applyMute();
            muteButton.setImage(new Image("muteButton.png"));
        } else {
            muteButton.setImage(new Image("unmuteButton.png"));
        }

        // styling for chat board
        chat_area.clear();
        chat_area.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-control-inner-background: transparent;" +
                        "-fx-text-fill: white;" + // Optional for readability
                        "-fx-border-color: transparent;" +
                        "-fx-text-fill: yellow;" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-family: 'SilomBol.ttf';"
        );
        chat_pane.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-background: transparent;" +
                        "-fx-border-color: transparent;"
        );

        /*
        mouse-click event: clears border and sets piece
        mouse-entered event: if tile is empty, yellow border appears around hovering imageview
        mouse-exited event: changes border color back to transparent
         */
            Tile_0_0.setOnMouseClicked(event -> {
                TileBorder_0_0.setStyle("-fx-border-color: transparent;");
                try {
                    setTile(0, 0, Tile_0_0);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            Tile_0_0.setOnMouseEntered(event -> hoverEvent(TileBorder_0_0, 0, 0));
            Tile_0_0.setOnMouseExited(event -> TileBorder_0_0.setStyle("-fx-border-color: transparent;"));


            Tile_0_1.setOnMouseClicked(event -> {
                TileBorder_0_1.setStyle("-fx-border-color: transparent;");
                try {
                    setTile(0, 1, Tile_0_1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            Tile_0_1.setOnMouseEntered(event -> hoverEvent(TileBorder_0_1, 0, 1));
            Tile_0_1.setOnMouseExited(event -> TileBorder_0_1.setStyle("-fx-border-color: transparent;"));


            Tile_0_2.setOnMouseClicked(event -> {
                TileBorder_0_2.setStyle("-fx-border-color: transparent;");
                try {
                    setTile(0, 2, Tile_0_2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            Tile_0_2.setOnMouseEntered(event -> hoverEvent(TileBorder_0_2, 0, 2));
            Tile_0_2.setOnMouseExited(event -> TileBorder_0_2.setStyle("-fx-border-color: transparent;"));


            Tile_1_0.setOnMouseClicked(event -> {
                TileBorder_1_0.setStyle("-fx-border-color: transparent;");
                try {
                    setTile(1, 0, Tile_1_0);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            Tile_1_0.setOnMouseEntered(event -> hoverEvent(TileBorder_1_0, 1, 0));
            Tile_1_0.setOnMouseExited(event -> TileBorder_1_0.setStyle("-fx-border-color: transparent;"));


            Tile_1_1.setOnMouseClicked(event -> {
                TileBorder_1_1.setStyle("-fx-border-color: transparent;");
                try {
                    setTile(1, 1, Tile_1_1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            Tile_1_1.setOnMouseEntered(event -> hoverEvent(TileBorder_1_1, 1, 1));
            Tile_1_1.setOnMouseExited(event -> TileBorder_1_1.setStyle("-fx-border-color: transparent;"));


            Tile_1_2.setOnMouseClicked(event -> {
                TileBorder_1_2.setStyle("-fx-border-color: transparent;");
                try {
                    setTile(1, 2, Tile_1_2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            Tile_1_2.setOnMouseEntered(event -> hoverEvent(TileBorder_1_2, 1, 2));
            Tile_1_2.setOnMouseExited(event -> TileBorder_1_2.setStyle("-fx-border-color: transparent;"));


            Tile_2_0.setOnMouseClicked(event -> {
                TileBorder_2_0.setStyle("-fx-border-color: transparent;");
                try {
                    setTile(2, 0, Tile_2_0);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            Tile_2_0.setOnMouseEntered(event -> hoverEvent(TileBorder_2_0, 2, 0));
            Tile_2_0.setOnMouseExited(event -> TileBorder_2_0.setStyle("-fx-border-color: transparent;"));


            Tile_2_1.setOnMouseClicked(event -> {
                TileBorder_2_1.setStyle("-fx-border-color: transparent;");
                try {
                    setTile(2, 1, Tile_2_1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            Tile_2_1.setOnMouseEntered(event -> hoverEvent(TileBorder_2_1, 2, 1));
            Tile_2_1.setOnMouseExited(event -> TileBorder_2_1.setStyle("-fx-border-color: transparent;"));


            Tile_2_2.setOnMouseClicked(event -> {
                TileBorder_2_2.setStyle("-fx-border-color: transparent;");
                try {
                    setTile(2, 2, Tile_2_2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            Tile_2_2.setOnMouseEntered(event -> hoverEvent(TileBorder_2_2, 2, 2));
            Tile_2_2.setOnMouseExited(event -> TileBorder_2_2.setStyle("-fx-border-color: transparent;"));
        }

    /**
     * gets the row and column index of game tile
     * checks to make sure tile is empty before setting its piece
     * sets array piece char and image of tile imageView
     * @param row int
     * @param col int
     * @param imageView ImageView
     */
    private void setTile(int row, int col, ImageView imageView) throws InterruptedException {
        Image X = new Image("X.png");
        Image O = new Image("O.png");
        if (yourPiece == tttClient.getCurrentPlayer()) {
            if (theGame.getGameOngoing()) {
                if (theGame.isTileEmpty(new Ivec2(row, col))) {
                    if (tttClient.getCurrentPlayer() == 1) {
                        imageView.setImage(X);
                        turnBanner.setImage(new Image("OTurn.png"));
                    } else {
                        imageView.setImage(O);
                        turnBanner.setImage(new Image("XTurn.png"));
                    }
                    checkWin();
                    TTTClient.receiveInput(new Ivec2(row, col));
                    isYourTurn.set(false);
                }
            }
        }
    }

    /**
     * shows a yellow border on valid game tiles when hovering over them
     * @param stackPane
     * @param row
     * @param col
     */
    private void hoverEvent(StackPane stackPane, int row, int col){
        if (theGame.getGameOngoing()) {
            if (theGame.isTileEmpty(new Ivec2(row, col))) {
                stackPane.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");
            }
        }
    }

    /**
     * triggered by x button click
     * prompts for confirmation
     * returns user to game menu
     * should also forfeit active matches
     * @throws IOException
     */
    public void quit_TTT() throws IOException {
        // check if popup is already showing
        if (!quitPopup.isShowing()) {
            // if no popup is showing make a new popup
            quitPopup = new Stage();
            // get caller stage, set as popup owner
            Stage owner = (Stage) board_image.getScene().getWindow();
            quitPopup.initOwner(owner);

            // load popup resources
            FXMLLoader loader = new FXMLLoader(getClass().getResource("quitPopup.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            QuitPopupController controller = loader.getController();

            quitPopup.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);

            // set closeOwner false so TTT stage doesn't close
            controller.closeOwner = false;
            quitPopup.setScene(scene);
            // show popup and wait for user input
            quitPopup.showAndWait();

            // check if user wants to close TTT game
            if (controller.closeYes) {
                AudioManager.mediaPlayer.stop();
                Parent newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gameMenu.fxml")));

                Stage stage = (Stage) gameBoard.getScene().getWindow();

                stage.setScene(new Scene(newRoot));
                stage.show();
            }
        }
    }

    // easter egg stuff
    public void yellowPress(){
        EEList.add('Y');
    }

    public void bluePress(){
        EEList.add('B');
    }

    public void greenPress(){
        if (EEList.toString().equals("[B, B, Y, Y, B, Y, B, Y]")){
            board_image.setImage(new Image("EE.jpg"));
            String path = Objects.requireNonNull(getClass().getResource("/music/EESFX.mp3")).toExternalForm(); // or absolute path
            Media sound = new Media(path);
            MediaPlayer EE_SFX = new MediaPlayer(sound);
            EE_SFX.play();
        }
    }

    public void redPress(){
        EEList.clear();
        board_image.setImage(new Image("foreground.png"));
    }

    /**
     * play again function
     * essentially just clears board for now
     * might remove
     */
    public void playAgainYes() {
        if (!theGame.getGameOngoing()) {
            theGame = new TTTGameController();
            clearBoard();
            turnBanner.setImage(new Image("XTurn.png"));
        }
    }

    /**
     * Game logic win checker.
     */
    public void checkWin() throws InterruptedException {
        // check for game win
        if (tttClient.getWinner() == yourPiece) {
            // if game is over, current player is the loser
            if (tttClient.getWinner() == 1){ // if the current player is O
                Win_Lose_Banner.setImage(new Image("X_wins.png"));
            } else if (tttClient.getWinner() == 2){
                Win_Lose_Banner.setImage(new Image("O_wins.png"));
            }
        }
        // check for game draw
        else if (theGame.checkDraw()){
            Win_Lose_Banner.setImage(new Image("Draw.png"));
            theGame.updateGameState();  // Update the game state to TIE
        }

        // if game is over, set play again features
        if (!theGame.getGameOngoing()){ // if the game is not ongoing, i.e. the game is over
            turnBanner.setImage(null);
        }
    }


    /**
     * clears all board images and play again images
     */
    public void clearBoard(){
        Tile_0_0.setImage(null);
        Tile_0_1.setImage(null);
        Tile_0_2.setImage(null);
        Tile_1_0.setImage(null);
        Tile_1_1.setImage(null);
        Tile_1_2.setImage(null);
        Tile_2_0.setImage(null);
        Tile_2_1.setImage(null);
        Tile_2_2.setImage(null);
        Win_Lose_Banner.setImage(null);
    }

    /**
     * gets a string from chat text field and appends it to chat area
     */
    public void sendMessage(){
        String message = chat_input_field.getText();
        if (!message.trim().isEmpty() && chatManager.isAppropriate(message)){
            chat_area.appendText("You: " + message + "\n");
            chat_input_field.clear();
            Client.sendMessageToServer(message);
        }
        else{
            chat_area.appendText("Your message contains\ninappropriate language.\nPlease try again.\n");
            chat_input_field.clear();
        }
    }

    /**
     * function for networking to get string messages from opponents and update chat
     * @param message
     */
    public void getMessage(String message){
        System.out.println(message);
        chat_area.appendText(message);
    }

    // button animations
    public void infoButtonClicked(){
        info_bg.setImage(new Image("info_image.png"));
        info_bg.setMouseTransparent(false);
        info_ok_button.setMouseTransparent(false);
    }
    public void info_ok_clicked(){
        info_bg.setImage(null);
        info_bg.setMouseTransparent(true);
        info_ok_button.setMouseTransparent(true);
    }

    public void XPressed(){
        quit_image.setImage(new Image("home_button_pressed.png"));
    }
    public void XReleased(){
        quit_image.setImage(new Image("home_button.png"));
    }
    public void infoPressed(){
        infoButton.setImage(new Image("infoButtonDown.png"));
    }
    public void infoReleased(){
        infoButton.setImage(new Image("info_button.png"));
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
    public void sendButtonPressed(){
        sendButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/GUI_buttons/pressed/send_button_pressed.png")).toExternalForm()));
    }
    public void sendButtonReleased(){
        sendButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/GUI_buttons/send_button.png")).toExternalForm()));
    }
    public void updateBoard(int[][] board){
        Image x = new Image("X.png");
        Image o = new Image("O.png");
        updateConditional(Tile_0_0, 0, 0, board);
        updateConditional(Tile_0_1, 0, 1, board);
        updateConditional(Tile_0_2, 0, 2, board);
        updateConditional(Tile_1_0, 1, 0, board);
        updateConditional(Tile_1_1, 1, 1, board);
        updateConditional(Tile_1_2, 1, 2, board);
        updateConditional(Tile_2_0, 2, 0, board);
        updateConditional(Tile_2_1, 2, 1, board);
        updateConditional(Tile_2_2, 2, 2, board);



    }
    private void updateConditional(ImageView tile, int row, int col, int[][] board){
        Image x = new Image("X.png");
        Image o = new Image("O.png");
        if (board[row][col] == 1){
            tile.setImage(x);
        } else if (board[row][col] == 2){
            tile.setImage(o);
        }
    }
}