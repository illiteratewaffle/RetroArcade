package GUI_client;

import GameLogic_Client.TicTacToe.TTTGameController;
import GameLogic_Client.ivec2;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TTTController implements Initializable {

    // FXML declarations
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
    public ImageView play_again;
    @FXML
    public ImageView check_circle;
    @FXML
    public ImageView X_circle;
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

    TTTGameController theGame = new TTTGameController();

    ArrayList<Character> EEList = new ArrayList<Character>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Font.loadFont(getClass().getResourceAsStream("Fonts/SilomBol.ttf"), 16);
        Image bg_image = new Image("background_retro.png");
        Image b_image = new Image("foreground.png");
        // set background and foreground images
        background_image.setImage(bg_image);
        board_image.setImage(b_image);
        quit_image.setImage(new Image("quit_x.png"));
        infoButton.setImage(new Image("info_button.png"));

        TTTGameController theGame = new TTTGameController();

        turnBanner.setImage(new Image("XTurn.png"));
        sendButton.setImage(new Image("send_button.png"));
        chat_bg.setImage(new Image("chat_bg.png"));
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
        Tile_0_0.setOnMouseClicked(event -> {TileBorder_0_0.setStyle("-fx-border-color: transparent;");
            setTile(0, 0, Tile_0_0);
        });
        Tile_0_0.setOnMouseEntered(event -> hoverEvent(TileBorder_0_0, 0, 0));
        Tile_0_0.setOnMouseExited(event -> TileBorder_0_0.setStyle("-fx-border-color: transparent;"));


        Tile_0_1.setOnMouseClicked(event -> {TileBorder_0_1.setStyle("-fx-border-color: transparent;");
            setTile(0, 1, Tile_0_1);
        });
        Tile_0_1.setOnMouseEntered(event -> hoverEvent(TileBorder_0_1, 0, 1));
        Tile_0_1.setOnMouseExited(event -> TileBorder_0_1.setStyle("-fx-border-color: transparent;"));


        Tile_0_2.setOnMouseClicked(event -> {TileBorder_0_2.setStyle("-fx-border-color: transparent;");
            setTile(0, 2, Tile_0_2);
        });
        Tile_0_2.setOnMouseEntered(event -> hoverEvent(TileBorder_0_2, 0, 2));
        Tile_0_2.setOnMouseExited(event -> TileBorder_0_2.setStyle("-fx-border-color: transparent;"));


        Tile_1_0.setOnMouseClicked(event -> {TileBorder_1_0.setStyle("-fx-border-color: transparent;");
            setTile(1, 0, Tile_1_0);
        });
        Tile_1_0.setOnMouseEntered(event -> hoverEvent(TileBorder_1_0, 1, 0));
        Tile_1_0.setOnMouseExited(event -> TileBorder_1_0.setStyle("-fx-border-color: transparent;"));


        Tile_1_1.setOnMouseClicked(event -> {TileBorder_1_1.setStyle("-fx-border-color: transparent;");
            setTile(1, 1, Tile_1_1);
        });
        Tile_1_1.setOnMouseEntered(event -> hoverEvent(TileBorder_1_1, 1, 1));
        Tile_1_1.setOnMouseExited(event -> TileBorder_1_1.setStyle("-fx-border-color: transparent;"));


        Tile_1_2.setOnMouseClicked(event -> {TileBorder_1_2.setStyle("-fx-border-color: transparent;");
            setTile(1, 2, Tile_1_2);
        });
        Tile_1_2.setOnMouseEntered(event -> hoverEvent(TileBorder_1_2, 1, 2));
        Tile_1_2.setOnMouseExited(event -> TileBorder_1_2.setStyle("-fx-border-color: transparent;"));


        Tile_2_0.setOnMouseClicked(event -> {TileBorder_2_0.setStyle("-fx-border-color: transparent;");
            setTile(2, 0, Tile_2_0);
        });
        Tile_2_0.setOnMouseEntered(event -> hoverEvent(TileBorder_2_0, 2, 0));
        Tile_2_0.setOnMouseExited(event -> TileBorder_2_0.setStyle("-fx-border-color: transparent;"));


        Tile_2_1.setOnMouseClicked(event -> {TileBorder_2_1.setStyle("-fx-border-color: transparent;");
            setTile(2, 1, Tile_2_1);
        });
        Tile_2_1.setOnMouseEntered(event -> hoverEvent(TileBorder_2_1, 2, 1));
        Tile_2_1.setOnMouseExited(event -> TileBorder_2_1.setStyle("-fx-border-color: transparent;"));


        Tile_2_2.setOnMouseClicked(event -> {TileBorder_2_2.setStyle("-fx-border-color: transparent;");
            setTile(2, 2, Tile_2_2);
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
    private void setTile(int row, int col, ImageView imageView){
        Image X = new Image("X.png");
        Image O = new Image("O.png");
        if (theGame.gameOngoing)
            if (theGame.game.board.isEmpty(new ivec2(row, col))) {
                if (theGame.GetCurrentPlayer() == 1){
                    imageView.setImage(X);
                    turnBanner.setImage(new Image("OTurn.png"));
                    theGame.game.makeMove(row, col);
                    theGame.game.currentPlayer = 2;
                } else {
                    imageView.setImage(O);
                    turnBanner.setImage(new Image("XTurn.png"));
                    theGame.game.makeMove(row, col);
                    theGame.game.currentPlayer = 1;
                }
                checkWin();
            }
    }

    private void hoverEvent(StackPane stackPane, int row, int col){
        if (theGame.gameOngoing) {
            if (theGame.game.board.isEmpty(new ivec2(row, col))) {
                stackPane.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");
            }
        }
    }

    /*
    triggered by x button click
    prompts for confirmation
    returns user to game menu
    should also forfeit active matches
     */
    public void quit_TTT() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType yesButton = new ButtonType("Yes");
        alert.getButtonTypes().set(0, yesButton);
        alert.setTitle("Quit Match");
        alert.setHeaderText("Quit Game?\nYou will forfeit active matches.");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == yesButton){
            Parent root = FXMLLoader.load(getClass().getResource("gameMenu.fxml"));

            Stage stage = (Stage) gameBoard.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();
        }
    }
    public void yellowPress(){
        EEList.add('Y');
    }
    public void bluePress(){
        EEList.add('B');
    }
    public void greenPress(){
        if (EEList.toString().equals("[B, B, Y, Y, B, Y, B, Y]")){
            board_image.setImage(new Image("EE.jpg"));
        }
    }
    public void redPress(){
        EEList.clear();
        board_image.setImage(new Image("foreground.png"));
    }

    public void playAgainYes() {
        if (!theGame.gameOngoing) {
            theGame = new TTTGameController();
            clearBoard();
            turnBanner.setImage(new Image("XTurn.png"));
        }
    }

    public void checkWin(){
        if (theGame.game.checkWin(theGame.game.board)) {
            // if game is over, current player is now the loser
            if (theGame.GetCurrentPlayer() == 2){
                Win_Lose_Banner.setImage(new Image("X_wins.png"));
            } else if (theGame.GetCurrentPlayer() == 1){
                Win_Lose_Banner.setImage(new Image("O_wins.png"));
            }
            theGame.gameOngoing = false;
        } else if (theGame.game.checkDraw(theGame.game.board)){
            Win_Lose_Banner.setImage(new Image("Draw.png"));
            theGame.gameOngoing = false;
        }
        if (!theGame.gameOngoing){
            play_again.setImage(new Image("Play_Again.png"));
            check_circle.setImage(new Image("check_circle.png"));
            X_circle.setImage(new Image("X_circle.png"));
            turnBanner.setImage(null);
        }
    }

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
        play_again.setImage(null);
        check_circle.setImage(null);
        X_circle.setImage(null);
        Win_Lose_Banner.setImage(null);
    }

    public void sendMessage(){
        String message = chat_input_field.getText();
        if (!message.trim().isEmpty()){
            chat_area.appendText("You: " + message + "\n");
            chat_input_field.clear();
        }
    }

    public void getMessage(String message){
        chat_area.appendText(message);
    }

    public void infoButtonPress(){
        info_bg.setImage(new Image("info_image.png"));
        info_bg.setMouseTransparent(false);
        info_ok_button.setMouseTransparent(false);
    }

    public void info_ok_clicked(){
        info_bg.setImage(null);
        info_bg.setMouseTransparent(true);
        info_ok_button.setMouseTransparent(true);
    }
}