package GUI;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class TTTController implements Initializable {

    // boolean flag alternating Xs and Os
    boolean flag = true;

    // boolean variable that board events must check to be true before executing
    boolean isPlayable = true;

    // booleanProperty to listen for server input by other player
    BooleanProperty isYourTurn = new SimpleBooleanProperty(false);

    // booleanProperty to listen for gameOver message
    BooleanProperty isGameOver = new SimpleBooleanProperty(false);

    // FXML declarations
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
    public ImageView background_image;
    @FXML
    public ImageView board_image;
    @FXML
    public GridPane gameBoard;

    // 2D Array for tracking board status
    Tile[][] board = new Tile[3][3];

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
        if (board[0][0].getPiece() == board[1][1].getPiece() & board[1][1].getPiece() == board[2][2].getPiece() & board[0][0].getPiece() == board[2][2].getPiece()) {
            Win_Lose_Banner.setImage(YOUWIN);

        }
    }

    private void hoverEvent(StackPane stackPane, int row, int col){
        if (board[row][col].getPiece() == '-'){
            stackPane.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image bg_image = new Image("background_retro.png");
        Image b_image = new Image("foreground.png");
        // set background and foreground images
        background_image.setImage(bg_image);
        board_image.setImage(b_image);

        // initialize board tile chars to empty
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                board[i][j] = new Tile('-', i, j);
            }
        }

        /*
         * event listener for boolean value change in isYourTurn
         * when changed, should check if value is now your turn
         * if not, do nothing
         * if isYourTurn, make board playable---
         * *suggestion*
         * may require a new boolean variable--isPlayable--
         * that the board requires be true in order for any event to be executed
         * isPlayable should be set true after isYourTurn is changed to true
         * set to false after a move has been made locally
         */
        isYourTurn.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (isYourTurn.get()) {
                    isPlayable = true;
                }
            }
        });

        isGameOver.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (isGameOver.get()){

                }
            }
        });


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
}