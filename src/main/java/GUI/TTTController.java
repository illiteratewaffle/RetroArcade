package GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TTTController implements Initializable {
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

    // boolean flag alternating Xs and Os
    boolean flag = true;

    /**
     * gets the row and column index of game tile
     * checks to make sure tile is empty before setting its piece
     * sets array piece char and image of tile imageView
     * @param row int
     * @param col int
     * @param imageView ImageView
     */
    private void setPiece(int row, int col, ImageView imageView){
        Image X = new Image("file:C:\\Users\\Tyler\\IdeaProjects\\demo1\\src\\main\\X.png");
        Image O = new Image("file:C:\\Users\\Tyler\\IdeaProjects\\demo1\\src\\main\\O.png");

        if (board[row][col].getPiece() == 'E') {
            if (flag) {
                board[row][col].setPiece('X');
                imageView.setImage(X);
            } else {
                board[row][col].setPiece('O');
                imageView.setImage(O);
            }
            flag = !flag;
        }
    }

    /**
     * checks for win condition in game board
     * returns true is game has been won
     * @param board Tile[][]
     * @return boolean
     */
    private boolean isWon(Tile[][] board){
        return true;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image bg_image = new Image("file:C:\\Users\\Tyler\\IdeaProjects\\demo1\\src\\main\\background_retro.png");
        Image b_image = new Image("file:C:\\Users\\Tyler\\IdeaProjects\\demo1\\src\\main\\foreground.png");

        // set background and foreground images
        background_image.setImage(bg_image);
        board_image.setImage(b_image);

        // initialize board tile chars to empty
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                board[i][j] = new Tile('E', i, j);
            }
        }

        /*
        mouse-click event: clears border and sets piece
        mouse-entered event: if tile is empty, yellow border appears around hovering imageview
        mouse-exited event: changes border color back to transparent
         */
        Tile_0_0.setOnMouseClicked(event -> {TileBorder_0_0.setStyle("-fx-border-color: transparent;");
            setPiece(0, 0, Tile_0_0);
        });
        Tile_0_0.setOnMouseEntered(event -> {if (board[0][0].getPiece() == 'E') {TileBorder_0_0.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");}});
        Tile_0_0.setOnMouseExited(event -> TileBorder_0_0.setStyle("-fx-border-color: transparent;"));


        Tile_0_1.setOnMouseClicked(event -> {TileBorder_0_1.setStyle("-fx-border-color: transparent;");
            setPiece(0, 1, Tile_0_1);
        });
        Tile_0_1.setOnMouseEntered(event -> {if (board[0][1].getPiece() == 'E') {TileBorder_0_1.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");}});
        Tile_0_1.setOnMouseExited(event -> TileBorder_0_1.setStyle("-fx-border-color: transparent;"));


        Tile_0_2.setOnMouseClicked(event -> {TileBorder_0_2.setStyle("-fx-border-color: transparent;");
            setPiece(0, 2, Tile_0_2);
        });
        Tile_0_2.setOnMouseEntered(event -> {if (board[0][2].getPiece() == 'E') {TileBorder_0_2.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");}});
        Tile_0_2.setOnMouseExited(event -> TileBorder_0_2.setStyle("-fx-border-color: transparent;"));


        Tile_1_0.setOnMouseClicked(event -> {TileBorder_1_0.setStyle("-fx-border-color: transparent;");
            setPiece(1, 0, Tile_1_0);
        });
        Tile_1_0.setOnMouseEntered(event -> {if (board[1][0].getPiece() == 'E') {TileBorder_1_0.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");}});
        Tile_1_0.setOnMouseExited(event -> TileBorder_1_0.setStyle("-fx-border-color: transparent;"));


        Tile_1_1.setOnMouseClicked(event -> {TileBorder_1_1.setStyle("-fx-border-color: transparent;");
            setPiece(1, 1, Tile_1_1);
        });
        Tile_1_1.setOnMouseEntered(event -> {if (board[1][1].getPiece() == 'E') {TileBorder_1_1.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");}});
        Tile_1_1.setOnMouseExited(event -> TileBorder_1_1.setStyle("-fx-border-color: transparent;"));


        Tile_1_2.setOnMouseClicked(event -> {TileBorder_1_2.setStyle("-fx-border-color: transparent;");
            setPiece(1, 2, Tile_1_2);
        });
        Tile_1_2.setOnMouseEntered(event -> {if (board[1][2].getPiece() == 'E') {TileBorder_1_2.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");}});
        Tile_1_2.setOnMouseExited(event -> TileBorder_1_2.setStyle("-fx-border-color: transparent;"));


        Tile_2_0.setOnMouseClicked(event -> {TileBorder_2_0.setStyle("-fx-border-color: transparent;");
            setPiece(2, 0, Tile_2_0);
        });
        Tile_2_0.setOnMouseEntered(event -> {if (board[2][0].getPiece() == 'E') {TileBorder_2_0.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");}});
        Tile_2_0.setOnMouseExited(event -> TileBorder_2_0.setStyle("-fx-border-color: transparent;"));


        Tile_2_1.setOnMouseClicked(event -> {TileBorder_2_1.setStyle("-fx-border-color: transparent;");
            setPiece(2, 1, Tile_2_1);
        });
        Tile_2_1.setOnMouseEntered(event -> {if (board[2][1].getPiece() == 'E') {TileBorder_2_1.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");}});
        Tile_2_1.setOnMouseExited(event -> TileBorder_2_1.setStyle("-fx-border-color: transparent;"));


        Tile_2_2.setOnMouseClicked(event -> {TileBorder_2_2.setStyle("-fx-border-color: transparent;");
            setPiece(2, 2, Tile_2_2);
        });
        Tile_2_2.setOnMouseEntered(event -> {if (board[2][2].getPiece() == 'E') {TileBorder_2_2.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");}});
        Tile_2_2.setOnMouseExited(event -> TileBorder_2_2.setStyle("-fx-border-color: transparent;"));
    }
}