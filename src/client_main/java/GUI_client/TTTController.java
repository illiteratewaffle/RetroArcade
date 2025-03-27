package client_main.java.GUI_client;

import client_main.java.GameLogic_Client.TicTacToe.TTTGameController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import javax.swing.*;
import java.awt.*;

import javafx.geometry.Rectangle2D;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class TTTController implements Initializable {

    // FXML declarations
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

    // boolean flag alternating Xs and Os
    // Tyler, the TTTGame class has implementation for this, now.
    boolean flag = true;

    // boolean variable that board events must check to be true before executing
    boolean isPlayable = true;

    // booleanProperty to listen for server input by other player
    BooleanProperty isYourTurn = new SimpleBooleanProperty(false);

    // booleanProperty to listen for gameOver message
    BooleanProperty isGameOver = new SimpleBooleanProperty(false);

    // 2D Array for tracking board status
    Tile[][] board = new Tile[3][3];

    ArrayList<Character> EEList = new ArrayList<Character>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image bg_image = new Image("background_retro.png");
        Image b_image = new Image("foreground.png");
        // set background and foreground images
        background_image.setImage(bg_image);
        board_image.setImage(b_image);
        quit_image.setImage(new Image("quit_x.png"));

        // initialize board tile chars to empty
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                board[i][j] = new Tile('-', i, j);
            }
        }

        // initialize a TTTGameController class, this is where the game actually happens and
        // everything is kept track of
        //TTTGameController theGame = new TTTGameController();
        // to access the board, you go theGame.board
        // to access the current plater, you go theGame.currentPlayer, 1 for X, 2 for O


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
        Image YOUWIN = new Image("YOU_WIN.png");
        Image play_again_image = new Image("Play_Again.png");
        Image check_image = new Image("check_circle.png");
        Image X_image = new Image("X_circle.png");

        if (isPlayable) {
            if (board[row][col].getPiece() == '-') {
                if (flag) {
                    board[row][col].setPiece('X');
                    imageView.setImage(X);
                } else {
                    board[row][col].setPiece('O');
                    imageView.setImage(O);
                }
                flag = !flag;
            }
            for (Tile[] tiles: board){
                for (Tile tile: tiles){
                    System.out.print(tile.getPiece());
                }
                System.out.println();
            }
            System.out.println();
        }
        if (isWon(board)) {
            Win_Lose_Banner.setImage(YOUWIN);
            isGameOver.set(true);
            play_again.setImage(play_again_image);
            check_circle.setImage(check_image);
            X_circle.setImage(X_image);
        }
    }

    private void hoverEvent(StackPane stackPane, int row, int col){
        if (board[row][col].getPiece() == '-'){
            stackPane.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");
        }
    }

    private  boolean isWon(Tile[][] board){
        return true;
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
        alert.setHeaderText("Quit Game?\nYou will forfeit active matches.");
        alert.setContentText("Click YES to quit");
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
    }
}