package GUI_client;
import AuthenticationAndProfile.Authentication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import leaderboard.PlayerRanking;

import java.io.IOException;
import java.net.URL;

public class profileGUIController {
    @FXML public ImageView inbox_button;
    @FXML public ImageView friends_button;
    @FXML public ImageView history_button;
    @FXML public ImageView stats_button;
    @FXML public ImageView avatar;
    @FXML public ImageView edit_profile_button;
    @FXML public ImageView apply_button;
    @FXML public ImageView discard_button;
    @FXML public ListView inbox_contents;
    @FXML public ListView friends_list;
    @FXML public ListView history_list;
    @FXML public AnchorPane stats_pane;
    //Checkers labels for stats
    @FXML public Label check_wlr_label;
    @FXML public Label check_win_label;
    @FXML public Label check_rating_label;
    @FXML public Label check_rank_label;
    //TTT labels for stats
    @FXML public Label TTT_wlr_label;
    @FXML public Label TTT_win_label;
    @FXML public Label TTT_rating_label;
    @FXML public Label TTT_rank_label;
    //C4 labels for stats
    @FXML public Label c4_wlr_label;
    @FXML public Label c4_win_label;
    @FXML public Label c4_rating_label;
    @FXML public Label c4_rank_label;
    @FXML public Label bio_label;
    @FXML public Label name_label;
    @FXML private ScrollPane avatar_pane;
    private Object GUI_avatars;
    private AuthenticationAndProfile.Profile Profile;

    public profileGUIController() {
        System.out.println("profileGUIController loaded");
    }
    //**Other profile would need this
    public void initializeProfile(){
        String bio = Authentication.getProfileLoggedIn().getBio();
        String userName = Profile.getUsername();
        String nickName = Profile.getNickname();
        String getAvatarPath = Profile.getProfilePicFilePath();
        setConponents(bio, userName, nickName, getAvatarPath);
    }
    //**Other profile would need this
    public void setConponents(String bio, String userName, String nickName, String getAvatarPath) {
        bio_label.setText(bio);
        name_label.setText(nickName + "(" + userName + ")");
        if (getAvatarPath != null) {
            URL url = getClass().getResource(getAvatarPath);
            if (url != null) {
                Image profileImage = new Image(url.toExternalForm(), false);
                avatar.setImage(profileImage);
            } else {
                System.out.println("No Avatar found!");
            }
        }
    }

    public void go_home(MouseEvent mouseEvent) {
    }

    public void close_window(MouseEvent mouseEvent) {
    }

    public void open_inbox(MouseEvent mouseEvent) {
        //Making sure that all other lists/table is invisible so that you cannot see the other windows, only the one clicked into.
        inbox_contents.setOpacity(1.0);
        history_list.setOpacity(0.0);
        friends_list.setOpacity(0.0);
        stats_pane.setOpacity(0.0);


       // Using an obervable list so that it can be updated as more information is added.
        //Getting the data needed to populate the inbox (friend requests) list.
        ObservableList<Integer> requests = FXCollections.observableArrayList(
                Authentication.getProfileLoggedIn().getFriendsList().getFriendRequests());
       // Adding the content to the listview
        inbox_contents.setItems(requests);
    }
    //**Other profile would need this
    public void open_friends(MouseEvent mouseEvent){
        //Making sure that all other lists/table is invisible so that you cannot see the other windows, only the one clicked into.
        inbox_contents.setOpacity(0.0);
        history_list.setOpacity(0.0);
        friends_list.setOpacity(1.0);
        stats_pane.setOpacity(0.0);

        // Using an obervable list so that it can be updated as more information is added.
        //Getting the data needed to populate the friends list.
        ObservableList<Integer> friends = FXCollections.observableArrayList(
                Authentication.getProfileLoggedIn().getFriendsList().getFriends());
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
                Authentication.getProfileLoggedIn().getGameHistory().getGameHistory());

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

        //c4 stat labels -> getting the information needed to fill in the stats page
        String c4Rank = Authentication.getProfileLoggedIn().getPlayerRanking().getRank(k);
        Double c4Wlr = Double.valueOf(Authentication.getProfileLoggedIn().getPlayerRanking().getRating(k));
        int c4Rating = Authentication.getProfileLoggedIn().getPlayerRanking().getRating(k);
        int c4Wins = Authentication.getProfileLoggedIn().getPlayerRanking().getWins(k);

        System.out.println(c4Rank + c4Wlr + c4Rating + c4Wins);

        //Setting the c4 label to the numbers that retrieved above

        c4_rank_label.setText(String.valueOf(c4Rank));
        c4_wlr_label.setText(String.valueOf(c4Wlr));
        c4_rating_label.setText(String.valueOf(c4Rating));
        c4_win_label.setText(String.valueOf(c4Wins));

        //check stat labels -> getting the information needed to fill in the stats page
        String checkRank = Authentication.getProfileLoggedIn().getPlayerRanking().getRank(j);
        Double checkWlr = Double.valueOf(Authentication.getProfileLoggedIn().getPlayerRanking().getRating(j));
        int checkRating = Authentication.getProfileLoggedIn().getPlayerRanking().getRating(j);
        int checkWins = Authentication.getProfileLoggedIn().getPlayerRanking().getWins(j);

        //setting checkers labels to the numbers that retrieved above
        check_rank_label.setText(String.valueOf(checkRank));
        check_wlr_label.setText(String.valueOf(checkWlr));
        check_rating_label.setText(String.valueOf(checkRating));
        check_win_label.setText(String.valueOf(checkWins));

        //TTT stat labels -> getting the information needed to fill in the stats page
        String TTTRank = Authentication.getProfileLoggedIn().getPlayerRanking().getRank(i);
        Double TTTWlr = Double.valueOf(Authentication.getProfileLoggedIn().getPlayerRanking().getRating(i));
        int TTTRating = Authentication.getProfileLoggedIn().getPlayerRanking().getRating(i);
        int TTTWins = Authentication.getProfileLoggedIn().getPlayerRanking().getWins(i);

        //setting checkers labels to the numbers that retrieved above
        TTT_rank_label.setText(String.valueOf(TTTRank));
        TTT_wlr_label.setText(String.valueOf(TTTWlr));
        TTT_rating_label.setText(String.valueOf(TTTRating));
        TTT_win_label.setText(String.valueOf(TTTWins));
    }


    public void open_edit_profile(MouseEvent mouseEvent) {
        //Making sure that all other lists/table is invisible while editing profile.

        apply_button.setOpacity(1.0); apply_button.setDisable(false);
        discard_button.setOpacity(1.0); discard_button.setDisable(false);
        history_list.setOpacity(0.0);
        inbox_contents.setOpacity(0.0);
        friends_list.setOpacity(0.0);
        stats_pane.setOpacity(0.0);
        avatar_pane.setOpacity(1.0);
        edit_profile_button.setOpacity(0.0);
        friends_button.setDisable(true);
        stats_button.setDisable(true);
        inbox_button.setDisable(true);
        history_button.setDisable(true);
        edit_profile_button.setDisable(true);
    }
    public void updateProfilePicture(Image image){
        avatar.setImage(image);
    }

    public void discard_changes(MouseEvent mouseEvent) throws IOException {
        history_list.setOpacity(0.0);
        inbox_contents.setOpacity(0.0);
        friends_list.setOpacity(0.0);
        stats_pane.setOpacity(1.0);
        avatar_pane.setOpacity(0.0);
        edit_profile_button.setOpacity(1.0);
        apply_button.setOpacity(0.0);
        discard_button.setOpacity(0.0);
        friends_button.setDisable(false);
        stats_button.setDisable(false);
        inbox_button.setDisable(false);
        history_button.setDisable(false);
        edit_profile_button.setDisable(false);
        apply_button.setDisable(true);
        discard_button.setDisable(true);
        initializeProfile();
    }

    public void apply_changes(MouseEvent mouseEvent) {
        history_list.setOpacity(0.0);
        inbox_contents.setOpacity(0.0);
        friends_list.setOpacity(0.0);
        stats_pane.setOpacity(1.0);
        avatar_pane.setOpacity(0.0);
        edit_profile_button.setOpacity(1.0);
        apply_button.setOpacity(0.0);
        discard_button.setOpacity(0.0);
        friends_button.setDisable(false);
        stats_button.setDisable(false);
        inbox_button.setDisable(false);
        history_button.setDisable(false);
        edit_profile_button.setDisable(false);
        apply_button.setDisable(true);
        discard_button.setDisable(true);
    }

    //All methods below are used to change the profile picture based on
    public void choose_poop(MouseEvent mouseEvent) {
        String path = "/GUI_avatars/poop.PNG";
        URL url = getClass().getResource(path);
        if (url != null) {
            System.out.println("Image URL: " + url);
            Image image = new Image(url.toExternalForm(), false);
            updateProfilePicture(image);
        } else {
            System.out.println("Failed to load image from path: " + path);
        }

    }

    public void choose_goomba(MouseEvent mouseEvent) {
        String path = "/GUI_avatars/mario_goomba.PNG";
        URL url = getClass().getResource(path);
        if (url != null) {
            System.out.println("Image URL: " + url);
            Image image = new Image(url.toExternalForm(), false);
            updateProfilePicture(image);
        } else {
            System.out.println("Failed to load image from path: " + path);
        }
        getAvatarPath(path);
    }

    public void choose_purple_alien(MouseEvent mouseEvent) {
        String path = "/GUI_avatars/invader_purple.PNG";
        URL url = getClass().getResource(path);
        if (url != null) {
            System.out.println("Image URL: " + url);
            Image image = new Image(url.toExternalForm(), false);
            updateProfilePicture(image);
        } else {
            System.out.println("Failed to load image from path: " + path);
        }
        getAvatarPath(path);
    }


public void choose_pink_alien(MouseEvent mouseEvent) {
        String path = "/GUI_avatars/Invader_pink.PNG";
        URL url = getClass().getResource(path);
        if (url != null) {
            System.out.println("Image URL: " + url);
            Image image = new Image(url.toExternalForm(), false);
            updateProfilePicture(image);
        } else {
            System.out.println("Failed to load image from path: " + path);
        }
        getAvatarPath(path);
    }

    public void choose_green_alien(MouseEvent mouseEvent) {
        String path = "/GUI_avatars/Invader_green.PNG";
        URL url = getClass().getResource(path);
        if (url != null) {
            System.out.println("Image URL: " + url);
            Image image = new Image(url.toExternalForm(), false);
            updateProfilePicture(image);
        } else {
            System.out.println("Failed to load image from path: " + path);
        }
        getAvatarPath(path);
    }

    public void choose_cyan_alien(MouseEvent mouseEvent) {
        String path = "/GUI_avatars/Invader_cyan.PNG";
        URL url = getClass().getResource(path);
        if (url != null) {
            System.out.println("Image URL: " + url);
            Image image = new Image(url.toExternalForm(), false);
            updateProfilePicture(image);
        } else {
            System.out.println("Failed to load image from path: " + path);
        }
        getAvatarPath(path);
    }

    public void choose_toad(MouseEvent mouseEvent) {
        String path = "/GUI_avatars/mario_toad.PNG";
        URL url = getClass().getResource(path);
        if (url != null) {
            System.out.println("Image URL: " + url);
            Image image = new Image(url.toExternalForm(), false);
            updateProfilePicture(image);
        } else {
            System.out.println("Failed to load image from path: " + path);
        }
        getAvatarPath(path);
    }

    public void choose_blue_ghost(MouseEvent mouseEvent) {
        String path = "/GUI_avatars/pacman_blue.PNG";
        URL url = getClass().getResource(path);
        if (url != null) {
            System.out.println("Image URL: " + url);
            Image image = new Image(url.toExternalForm(), false);
            updateProfilePicture(image);
        } else {
            System.out.println("Failed to load image from path: " + path);
        }
        getAvatarPath(path);
    }

    public void choose_pink_ghost(MouseEvent mouseEvent) {
        String path = "/GUI_avatars/pacman_pink.PNG";
        URL url = getClass().getResource(path);
        if (url != null) {
            System.out.println("Image URL: " + url);
            Image image = new Image(url.toExternalForm(), false);
            updateProfilePicture(image);
        } else {
            System.out.println("Failed to load image from path: " + path);
        }
        getAvatarPath(path);
    }

    public void choose_red_ghost(MouseEvent mouseEvent) {
        String path = "/GUI_avatars/pacman_red.PNG";
        URL url = getClass().getResource(path);
        if (url != null) {
            System.out.println("Image URL: " + url);
            Image image = new Image(url.toExternalForm(), false);
            updateProfilePicture(image);
        } else {
            System.out.println("Failed to load image from path: " + path);
        }
        getAvatarPath(path);
    }

    public void choose_yellow_ghost(MouseEvent mouseEvent) {
        String path = "/GUI_avatars/pacman_yellow.PNG";
        URL url = getClass().getResource(path);
        if (url != null) {
            System.out.println("Image URL: " + url);
            Image image = new Image(url.toExternalForm(), false);
            updateProfilePicture(image);
        } else {
            System.out.println("Failed to load image from path: " + path);
        }
        getAvatarPath(path);
    }
    public String getAvatarPath(String path){
        return (path);
    }
}


