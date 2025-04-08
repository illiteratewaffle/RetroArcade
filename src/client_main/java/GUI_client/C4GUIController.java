package GUI_client;

import GameLogic_Client.Connect4.*;
import GameLogic_Client.Connect4.C4Controller;
import GameLogic_Client.Connect4.C4Piece;
import GameLogic_Client.Ivec2;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class C4GUIController implements Initializable {

    private C4Controller c4Controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert c4GUIGrid != null : "🔥 FATAL: c4GUIGrid was NOT injected!";
        assert winImage != null : "🔥 winImageView was NOT injected! Check your FXML fx:id";
        System.out.println("✅ c4GUIGrid injected successfully: " + c4GUIGrid);
        c4Controller = new C4Controller();
        c4Controller.start(); // This will print the board and start the game logic
        c4GUIGrid.setGridLinesVisible(true);
        //infoButton.setImage(new Image("C4Info.jpg"));

        setupHoverEffect(col0Button, 0);
        setupHoverEffect(col1Button, 1);
        setupHoverEffect(col2Button, 2);
        setupHoverEffect(col3Button, 3);
        setupHoverEffect(col4Button, 4);
        setupHoverEffect(col5Button, 5);
        setupHoverEffect(col6Button, 6);

        String path = Objects.requireNonNull(getClass().getResource("/music/C4Track.mp4")).toExternalForm(); // or absolute path
        Media sound = new Media(path);
        AudioManager.mediaPlayer = new MediaPlayer(sound);
        AudioManager.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        AudioManager.mediaPlayer.play();
//        if (AudioManager.isMuted()){
//            AudioManager.applyMute();
//            muteButton.setImage(new Image("muteButton.png"));
//        } else {
//            muteButton.setImage(new Image("unmuteButton.png"));
//        }
//
//        homeButton.setImage(new Image("home_button.png"));

    }
    @FXML
    public ImageView muteButton;

    @FXML
    private ImageView winImage;

    @FXML
    public ImageView infoButton;

    @FXML
    public ImageView infoImage;

    @FXML
    public Button info_ok_button;

    @FXML
    public ImageView homeButton;

    @FXML
    private ImageView hintButton;

    @FXML
    private ImageView hintMessageImage;

    @FXML
    public Button hint_ok_button;

    @FXML
    private ImageView noHintMessageImage;

    @FXML
    private ImageView turnIndicatorImage;

    @FXML
    public ImageView sendButton;

    @FXML
    public TextArea chatArea;

    @FXML
    public TextField chatField;

    @FXML
    public ScrollPane chatPane;

    @FXML
    private void handleUserClick() {
        c4Controller.receiveInput(new Ivec2(3, 0)); // for example, drop in column 3
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

    private Stage quitPopup = new Stage();

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
        ImageView piece = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imgPath))));
        piece.setFitWidth(30);
        piece.setFitHeight(30);
        piece.setOpacity(1.0);

        c4GUIGrid.add(piece, col, row); // ✅ Add only the image
    }
    /*private void handleColumnClick(int col) {
        if (!c4Controller.getC4IsGameOver()) {
            c4Controller.receiveInput(new Ivec2(col, 0));
            updateBoard();

            if (c4Controller.getC4IsGameOver()) {
                C4Piece winner = c4Controller.getC4WinnerAsEnum();
                if (winner == C4Piece.BLANK) {
                    c4Controller.c4GameLogic.updateGameState();
                    System.out.println("It's a draw!");
                } else {
                    System.out.println("Player " + winner + " wins! 🎉");
                    showWinImage();
                    c4Controller.c4GameLogic.updateGameState();
                }
                disableAllColumnButtons();
            }
        }
    }*/

    private void handleColumnClick(int col) {
        if (!c4Controller.getC4IsGameOver()) {
            c4Controller.receiveInput(new Ivec2(col, 0));
            updateBoard();
            updateTurnIndicator();

            if (c4Controller.getC4IsGameOver()) {
                C4Piece winner = c4Controller.getC4WinnerAsEnum();
                if (winner == C4Piece.BLANK) {
                   // C4Piece winner = c4Controller.getC4WinnerAsEnum();
                    c4Controller.c4GameLogic.updateGameState();
                    System.out.println("It's a draw!");
                } else {
                    System.out.println("Player " + winner + " wins! 🎉");
                    showWinImage();
                    c4Controller.c4GameLogic.updateGameState();
                }
                disableAllColumnButtons();
                c4GUIGrid.getChildren().removeIf(node ->
                        node instanceof Rectangle && "HINT".equals(node.getId())
                );
            }
        }
    }

    private void setupHoverEffect(Button button, int col) {
        button.setOnMouseEntered(e -> highlightColumnOnHover(col, true));
        button.setOnMouseExited(e -> highlightColumnOnHover(col, false));
    }

    private void highlightColumnOnHover(int col, boolean isHovering) {
        if (isHovering) {
            // Get current player and assign appropriate color (for pink or blue's turn)
            C4Piece currentPlayer = c4Controller.getC4CurrentPlayer();
            Color hoverColor = currentPlayer == C4Piece.RED ? Color.HOTPINK : Color.LIGHTBLUE;

            for (int row = 0; row < 6; row++) {
                Rectangle highlight = new Rectangle(38, 36);
                highlight.setFill(hoverColor);
                highlight.setOpacity(0.4);
                highlight.setMouseTransparent(true);
                highlight.setId("HOVER");
                c4GUIGrid.add(highlight, col, row);
            }
        } else {
            c4GUIGrid.getChildren().removeIf(node ->
                    node instanceof Rectangle &&
                            "HOVER".equals(node.getId()) &&
                            GridPane.getColumnIndex(node) != null &&
                            GridPane.getColumnIndex(node) == col
            );
        }
    }

    private void updateTurnIndicator() {
        turnIndicatorImage.setImage(new Image(
                c4Controller.getC4CurrentPlayer() == C4Piece.RED
                        ? "/pink_turn.png"
                        : "/blue_turn.png"
        ));
    }

    public void sendMessage(){
        String message = chatField.getText();
        if (!message.trim().isEmpty()){
            chatArea.appendText("You: " + message + "\n");
            chatField.clear();
        }
    }

    public void getMessage(String message){
        chatArea.appendText(message);
    }


    public void clickInfoButton(){
        infoImage.setImage(new Image("C4info.jpg"));
        infoImage.setVisible(true);
        infoImage.setMouseTransparent(false);
        info_ok_button.setMouseTransparent(false);
    }

    public void clickInfoOkButton(){
        infoImage.setVisible(false);
        infoImage.setMouseTransparent(true);
        info_ok_button.setMouseTransparent(true);
    }


    public void showWinImage() {
        if (c4Controller.getC4IsGameOver()) {
            winImage.setImage(new Image("win_image.png"));
            winImage.setVisible(true);
            hintButton.setDisable(true);
        }
    }

    @FXML
    private void clickHintButton() {
        HintResult hint = c4Controller.getC4ColHint();
        hintMessageImage.setVisible(false);
        noHintMessageImage.setVisible(false);
        if (hint.col != -1) {
            highlightHintColumn(hint.col);
            if ("WIN".equals(hint.type)){
                hintMessageImage.setImage(new Image("C4hint_win_image.png"));
            }else if ("BLOCK".equals(hint.type)){
                hintMessageImage.setImage(new Image("C4hint_block_image.png"));
            }
            hintMessageImage.setVisible(true);
            hintMessageImage.setMouseTransparent(false);
        }else{
            noHintMessageImage.setImage(new Image("C4no_hint_image.png"));
            noHintMessageImage.setVisible(true);
            noHintMessageImage.setMouseTransparent(false);
        }
        hint_ok_button.setVisible(true);
        hint_ok_button.setMouseTransparent(false);
    }

    private void highlightHintColumn(int col) {
        c4GUIGrid.getChildren().removeIf(node ->
                node instanceof Rectangle &&
                        "HINT".equals(node.getId())
        );

        for (int row = 0; row < 6; row++) {
            Rectangle highlight = new Rectangle(38, 36);
            highlight.setFill(Color.YELLOW);
            highlight.setOpacity(0.4);
            highlight.setMouseTransparent(true);
            c4GUIGrid.add(highlight, col, row);

        }
    }

    public Node getNodeFromGridPane(GridPane grid, int col, int row) {
        for (Node node : grid.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    public void clickHintOkButton(){
        hintMessageImage.setVisible(false);
        hintMessageImage.setMouseTransparent(true);
        noHintMessageImage.setVisible(false);
        noHintMessageImage.setMouseTransparent(true);
        hint_ok_button.setVisible(false);
        hint_ok_button.setMouseTransparent(true);
        resetColumnHighlights();
    }

    public void resetColumnHighlights() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                Node cell = getNodeFromGridPane(c4GUIGrid, col, row);
                if (cell instanceof Rectangle) {
                    Rectangle rect = (Rectangle) cell;
                    rect.setStroke(Color.TRANSPARENT);  // Remove the highlight (clear border)
                }
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

    public void homeButtonClicked() throws IOException {
        if (!quitPopup.isShowing()) {
            quitPopup = new Stage();
            Stage owner = (Stage) homeButton.getScene().getWindow();
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

                Stage stage = (Stage) homeButton.getScene().getWindow();

                stage.setScene(new Scene(newRoot));
                stage.show();
            }
        }
    }
    public void homeButtonPressed(){
        homeButton.setImage(new Image("home_button_pressed.png"));
    }
    public void homeButtonReleased(){ homeButton.setImage(new Image("home_button.png"));}
    public void infoButtonPressed(){
        infoButton.setImage(new Image("infoButtonDown.png"));
    }
    public void infoButtonReleased(){
        infoButton.setImage(new Image("info_button.png"));
    }
    public void muteButtonClicked(){
        if(!AudioManager.isMuted()) {
            muteButton.setImage(new Image("muteButton.png"));
            AudioManager.toggleMute();
        } else {
            muteButton.setImage(new Image("unmuteButton.png"));
            AudioManager.toggleMute();
        }
    }
}
