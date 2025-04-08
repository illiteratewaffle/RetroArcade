package GUI_client;
import AuthenticationAndProfile.Authentication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import leaderboard.PlayerRanking;
import AuthenticationAndProfile.Profile;

import java.net.URL;
import java.sql.SQLException;

public class otherPlayerProfileGUIController {
    @FXML
    public ImageView inbox_button;
    @FXML
    public ImageView friends_button;
    @FXML
    public ImageView history_button;
    @FXML
    public ImageView stats_button;
    @FXML
    public ImageView avatar;
    @FXML
    public ImageView edit_profile_button;
    @FXML
    public ImageView done_button;
    @FXML
    public ListView inbox_contents;
    @FXML
    public ListView friends_list;
    @FXML
    public ListView history_list;
    @FXML
    public AnchorPane stats_pane;
    //Checkers labels for stats
    @FXML
    public Label check_wlr_label;
    @FXML
    public Label check_win_label;
    @FXML
    public Label check_rating_label;
    @FXML
    public Label check_rank_label;
    //TTT labels for stats
    @FXML
    public Label TTT_wlr_label;
    @FXML
    public Label TTT_win_label;
    @FXML
    public Label TTT_rating_label;
    @FXML
    public Label TTT_rank_label;
    //C4 labels for stats
    @FXML public Label c4_wlr_label;
    @FXML public Label c4_win_label;
    @FXML public Label c4_rating_label;
    @FXML public Label c4_rank_label;
    @FXML public Label bio_label;
    @FXML public Label name_label;
    @FXML private ScrollPane avatar_pane;
    private Object GUI_avatars;
    @FXML
    public TextField nickname_label;
    @FXML
    private TextArea bio_text_area;
    private Profile profile;
    private String avatarPath;
    private String nickname;
    private String bio;


    public void initialize(Profile loadedProfile) {
        this.profile = loadedProfile;

        avatarPath = profile.getProfilePicFilePath();
        nickname = profile.getNickname();
        bio = profile.getNickname();
        bio_text_area.setEditable(false);

        avatar.setImage(new Image(avatarPath));
        nickname_label.setText(nickname);
        bio_text_area.setText(bio);
    }

    //maybe we can send the information to the server once the home button is clicked?
    public void go_home(MouseEvent mouseEvent) {
    }

    //not sure if we should handle the case that someone is in edit mode.
    //Depending on what we decide we may want to warn them that the changes will not be saved.
    public void close_window(MouseEvent mouseEvent) {
    }
    //**Other profile would need this
    public void open_friends(MouseEvent mouseEvent) {
        //Making sure that all other lists/table is invisible so that you cannot see the other windows, only the one clicked into.
        inbox_contents.setOpacity(0.0);
        history_list.setOpacity(0.0);
        friends_list.setOpacity(1.0);
        stats_pane.setOpacity(0.0);

        // Using an obervable list so that it can be updated as more information is added.
        //Getting the data needed to populate the friends list.
        ObservableList<Integer> friends = FXCollections.observableArrayList(
                profile.getFriendsList().getFriends());
        // Adding the content to the listview
        friends_list.setItems(friends);

    }
    //**Other profile needs this
    public void open_history(MouseEvent mouseEvent) {
        //Making sure that all other lists/table is invisible so that you cannot see the other windows, only the one clicked into.
        history_list.setOpacity(1.0);
        inbox_contents.setOpacity(0.0);
        friends_list.setOpacity(0.0);
        stats_pane.setOpacity(0.0);

        //Using an obervable list so that it can be updated as more information is added.
        //Getting the data needed to populate the game history list.
        ObservableList<String> history = FXCollections.observableArrayList(
                profile.getGameHistory().getGameHistory());

        //Adding the histroy content to the listview
        history_list.setItems(history);
    }

    //**Other profile needs this
    public void open_stats(MouseEvent mouseEvent) {
        //Making sure that all other lists/table is invisible so that you cannot see the other windows, only the one clicked into.
        history_list.setOpacity(0.0);
        inbox_contents.setOpacity(0.0);
        friends_list.setOpacity(0.0);
        stats_pane.setOpacity(1.0);

        int i = PlayerRanking.TTT_INDEX;
        int j = PlayerRanking.CHECKERS_INDEX;
        int k = PlayerRanking.CONNECT4_INDEX;

        System.out.println(i + j + k);

        try {
            //c4 stat labels -> getting the information needed to fill in the stats page
            String c4Rank = profile.getPlayerRanking().getRank(k);
            Double c4Wlr = Double.valueOf(profile.getPlayerRanking().getRating(k));
            int c4Rating = profile.getPlayerRanking().getRating(k);
            int c4Wins = profile.getPlayerRanking().getWins(k);

            System.out.println(c4Rank + c4Wlr + c4Rating + c4Wins);

            //Setting the c4 label to the numbers that retrieved above

            c4_rank_label.setText(String.valueOf(c4Rank));
            c4_wlr_label.setText(String.valueOf(c4Wlr));
            c4_rating_label.setText(String.valueOf(c4Rating));
            c4_win_label.setText(String.valueOf(c4Wins));

            //check stat labels -> getting the information needed to fill in the stats page
            String checkRank = profile.getPlayerRanking().getRank(j);
            Double checkWlr = Double.valueOf(profile.getPlayerRanking().getRating(j));
            int checkRating = profile.getPlayerRanking().getRating(j);
            int checkWins = profile.getPlayerRanking().getWins(j);

            //setting checkers labels to the numbers that retrieved above
            check_rank_label.setText(String.valueOf(checkRank));
            check_wlr_label.setText(String.valueOf(checkWlr));
            check_rating_label.setText(String.valueOf(checkRating));
            check_win_label.setText(String.valueOf(checkWins));

            //TTT stat labels -> getting the information needed to fill in the stats page
            String TTTRank = profile.getPlayerRanking().getRank(i);
            Double TTTWlr = Double.valueOf(profile.getPlayerRanking().getRating(i));
            int TTTRating = profile.getPlayerRanking().getRating(i);
            int TTTWins = profile.getPlayerRanking().getWins(i);

            //setting checkers labels to the numbers that retrieved above
            TTT_rank_label.setText(String.valueOf(TTTRank));
            TTT_wlr_label.setText(String.valueOf(TTTWlr));
            TTT_rating_label.setText(String.valueOf(TTTRating));
            TTT_win_label.setText(String.valueOf(TTTWins));
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }
}