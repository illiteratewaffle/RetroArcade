package GUI_client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class leaderboardController {
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
    @FXML
    public TableView TTT_table; //no action created.
    @FXML
    public TableView checkers_table; //no action created.
}



