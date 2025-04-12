package GUI_client;
import AuthenticationAndProfile.Authentication;

import AuthenticationAndProfile.ProfileDatabaseAccess;
import GUI_client.AudioManager;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import leaderboard.PlayerRanking;
import AuthenticationAndProfile.Profile;
import client.Client2;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;

import static GUI_client.profileGUIController.friend;
import static java.awt.SystemColor.info;

public class otherPlayerProfileGUIController {
    @FXML
    public ImageView muteButton;
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
    public ImageView add_friend;
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
    @FXML
    public Label c4_wlr_label;
    @FXML
    public Label c4_win_label;
    @FXML
    public Label c4_rating_label;
    @FXML
    public Label c4_rank_label;
    @FXML
    public Label bio_label;
    @FXML
    public Circle game_status;
    @FXML
    public Label name_label;
    @FXML Label notification;
    @FXML
    private ScrollPane avatar_pane;
    private Object GUI_avatars;
    @FXML
    public TextField nickname_label;
    @FXML
    private TextArea bio_text_area;
    private Profile profile;
    private String avatarPath;
    private String nickname;
    private String bio;
    private String username;
    static String selectedFriend;

    public static String setFriendUsername(String friend) {
        return friend;
    }

    @FXML
    public void initialize() {
        System.out.println("You made it to the other profile!");
        Client2.getOtherProfileInfo(friend);
        if (Client2.getOtherProfilePath() == null) {
            avatarPath = "/GUI_avatars/Invader_green.PNG";
            URL url = getClass().getResource(avatarPath);
            Image image = new Image(url.toExternalForm(), false);
            avatar.setImage(image);
            System.out.println(avatarPath);
        } else {
            avatarPath = Client2.getProfilePath();
        }
        System.out.println(avatarPath);
        if (Client2.getUsername() == null)
            username = "";
        else {
            username = Client2.getUsername();
        }

        if (Client2.getNickname() == null)
            nickname = "Set your nickname!";
        else {
            nickname = Client2.getNickname();
        }
        if (Client2.getBio() == null)
            bio = "Set your Bio";
        else {
            bio = Client2.getBio();
        }
        getStats();

        // setup soundtrack and mute status
        String path = Objects.requireNonNull(getClass().getResource("/music/profileTrack.mp3")).toExternalForm(); // or absolute path
        Media sound = new Media(path);
        AudioManager.mediaPlayer = new MediaPlayer(sound);
        AudioManager.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        AudioManager.mediaPlayer.play();
        if (AudioManager.isMuted()) {
            AudioManager.applyMute();
            muteButton.setImage(new Image("muteButton.png"));
        } else {
            muteButton.setImage(new Image("unmuteButton.png"));
        }
    }

    public void muteButtonClicked() {
        if (!AudioManager.isMuted()) {
            muteButton.setImage(new Image("muteButton.png"));
            AudioManager.toggleMute();
        } else {
            muteButton.setImage(new Image("unmuteButton.png"));
            AudioManager.toggleMute();
        }
    }

    public void send_request(MouseEvent mouseEvent){
        notification.setOpacity(1.0);
        add_friend.setOpacity(0.0); add_friend.setDisable(true);

        Client2.sendOtherRequest(selectedFriend); //Sends the name of the friend whose profile your on to the server.

    }


    //the stats button is pressed.
    public void open_stats(MouseEvent mouseEvent) {
        getStats();
    }

    public void getStats() {
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
        String c4Rank = Client2.getOtherRank(k);
        Double c4Wlr = Double.valueOf(Client2.getOtherWinLossRatio(k));
        int c4Rating = Client2.getOtherRating(k);
        int c4Wins = Client2.getOtherWins(k);

        System.out.println(c4Rank + c4Wlr + c4Rating + c4Wins);

        //Setting the c4 label to the numbers that retrieved above
        c4_rank_label.setText(String.valueOf(c4Rank));
        c4_wlr_label.setText(String.valueOf(c4Wlr));
        c4_rating_label.setText(String.valueOf(c4Rating));
        c4_win_label.setText(String.valueOf(c4Wins));

        //check stat labels -> getting the information needed to fill in the stats page
        String checkRank = Client2.getOtherRank(j);
        Double checkWlr = Double.valueOf(Client2.getOtherWinLossRatio(j));
        int checkRating = Client2.getOtherRating(j);
        int checkWins = Client2.getOtherWins(j);

        //setting checkers labels to the numbers that retrieved above
        check_rank_label.setText(String.valueOf(checkRank));
        check_wlr_label.setText(String.valueOf(checkWlr));
        check_rating_label.setText(String.valueOf(checkRating));
        check_win_label.setText(String.valueOf(checkWins));

        //TTT stat labels -> getting the information needed to fill in the stats page
        String TTTRank = Client2.getOtherRank(i);
        Double TTTWlr = Double.valueOf(Client2.getOtherWinLossRatio(i));
        int TTTRating = Client2.getOtherRating(i);
        int TTTWins = Client2.getOtherWins(i);

        //setting checkers labels to the numbers that retrieved above
        TTT_rank_label.setText(String.valueOf(TTTRank));
        TTT_wlr_label.setText(String.valueOf(TTTWlr));
        TTT_rating_label.setText(String.valueOf(TTTRating));
        TTT_win_label.setText(String.valueOf(TTTWins));
    }

    public void open_friends(MouseEvent mouseEvent) {
        //Making sure that all other lists/table is invisible so that you cannot see the other windows, only the one clicked into.
        inbox_contents.setOpacity(0.0);
        history_list.setOpacity(0.0);
        friends_list.setOpacity(1.0);
        stats_pane.setOpacity(0.0);

        // Using an obervable list so that it can be updated as more information is added.
        //Getting the data needed to populate the friends list.
        ObservableList<String> friends = FXCollections.observableArrayList(
                Client2.getOtherFriends());
        // Adding the content to the listview
        friends_list.setItems(friends);
    }

    public void open_history(MouseEvent mouseEvent) {
        //Making sure that all other lists/table is invisible so that you cannot see the other windows, only the one clicked into.
        history_list.setOpacity(1.0);
        inbox_contents.setOpacity(0.0);
        friends_list.setOpacity(0.0);
        stats_pane.setOpacity(0.0);
        edit_profile_button.setDisable(false);
        edit_profile_button.toFront();

        //Using an obervable list so that it can be updated as more information is added.
        //Getting the data needed to populate the game history list.
        ObservableList<String> history = FXCollections.observableArrayList(
                Client2.getOtherGameHistory());

        //Adding the histroy content to the listview
        history_list.setItems(history);
    }

//    //This will just set the little green circle to 100% opacity if the person is online.
//    //I am expecting that you will send a boolean, but I am not 100% sure.
    public void onlineStatus() {
        boolean status = Client2.getOtherOnlineStatus(); //need this method made.
        if (status) {
            game_status.setOpacity(1.0);
        }
        else{
            game_status.setOpacity(0.0);
        }
    }
}