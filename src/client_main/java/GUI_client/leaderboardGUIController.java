package GUI_client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import leaderboard.Leaderboard;

public class leaderboardGUIController {

    private static final String GAME_CHECKERS = "CHECKERS";
    private static final String GAME_TTT = "TTT";
    private static final String GAME_C4 = "C4";

    private String selectedSort;
    private String selectedGame;

    //Note that I gave all attributes a fx:id name and on action names. You can change these if you want.
    @FXML
    public ImageView checkers_button; //action: checkers_stats
    @FXML
    public ImageView TTT_button; //action: TTT_stats
    @FXML
    public ImageView C4_button; //action: C4_stats
    @FXML
    public ImageView search_button; //action: send_search
    @FXML
    public SplitMenuButton filter_stats; //action: filter
    @FXML
    public TextField friend_search; //action: search

    //I am not sure if we will need 3 separate tables, or if we can simply refresh the table content based on the game button clicked.
    @FXML
    public TableView C4_table; //no action created.
//    @FXML
//    public TableView TTT_table; //no action created.
//    @FXML
//    public TableView checkers_table; //no action created.

    public void displayLeaderboard(String selectedSort, String selectedGame) {
        Leaderboard leaderboard = new Leaderboard(selectedSort, selectedGame);

    }

    public void C4_stats(javafx.scene.input.MouseEvent mouseEvent) {
        this.selectedGame = GAME_C4;
        displayLeaderboard(this.selectedSort, this.selectedGame);
    }

    public void checkers_stats(ContextMenuEvent contextMenuEvent) {
        this.selectedGame = GAME_CHECKERS;
        displayLeaderboard(this.selectedSort, this.selectedGame);
    }

    public void TTT_stats(MouseEvent mouseEvent) {
        this.selectedGame = GAME_TTT;
        displayLeaderboard(this.selectedSort, this.selectedGame);
    }

    public void filter(ActionEvent actionEvent) {
    }

    public void send_search(MouseEvent mouseEvent) {
    }

    public void search(ActionEvent actionEvent) {
    }
}



