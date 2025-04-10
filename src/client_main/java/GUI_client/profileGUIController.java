package GUI_client;

import client.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import leaderboard.PlayerRanking;
import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;

public class profileGUIController {
    @FXML
    public ImageView muteButton;
    @FXML
    public ImageView home_button;
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
    public ImageView add_friend;
    @FXML
    public ImageView decline_friend;
    @FXML
    public ImageView edit_profile_button;
    @FXML
    public ImageView done_button;
    @FXML
    public ImageView confirm_search;
    @FXML
    public ListView inbox_contents;
    @FXML
    public ListView friends_list;
    @FXML
    public ListView history_list;
    @FXML
    public AnchorPane stats_pane;
    @FXML
    public Circle game_status;
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
    public Label name_label;
    @FXML
    private ScrollPane avatar_pane;
    @FXML
    public TextField nickname_label;
    @FXML
    public TextField search_friend;
    @FXML
    public Label notification;
    @FXML
    private TextArea bio_text_area;
    @FXML
    private ImageView image;
    public static String passFriend;
    private String avatarPath;
    private String nickname;
    private String bio;
    private String username;

    @FXML
    public void initialize() {
        Client.getProfileInfo();
        if (Client.getProfilePath() == null) {
            avatarPath = "/GUI_avatars/Invader_green.PNG";
            URL url = getClass().getResource(avatarPath);
            Image image = new Image(url.toExternalForm(), false);
            updateProfilePicture(image);
            System.out.println(avatarPath);
        } else {
            avatarPath = Client.getProfilePath();
        }
        System.out.println(avatarPath);


        if (Client.getUsername() == null)
            username = "";
        else {
            username = Client.getUsername();
        }

        if (Client.getNickname() == null)
            nickname = "Set your nickname!";
        else {
            nickname = Client.getNickname();
        }
        if (Client.getBio() == null)
            bio = "Set your Bio";
        else {
            bio = Client.getBio();
        }

        System.out.println(avatarPath);

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

    //The inbox button is pressed, the friend requests should be displayed.
    public void open_inbox(MouseEvent mouseEvent) {
        //Making sure that all other lists/table is invisible so that you cannot see the other windows, only the one clicked into.
        inbox_contents.setOpacity(1.0);
        history_list.setOpacity(0.0);
        friends_list.setOpacity(0.0);
        stats_pane.setOpacity(0.0);


        // Using an obervable list so that it can be updated as more information is added.
        //Getting the data needed to populate the inbox (friend requests) list.
        ObservableList<String> requests = FXCollections.observableArrayList(
                Client.getFriendRequests());
        // Adding the content to the listview
        inbox_contents.setItems(requests);
    }

    //The friends button is pressed
    public void open_friends(MouseEvent mouseEvent) {
        //Making sure that all other lists/table is invisible so that you cannot see the other windows, only the one clicked into.
        inbox_contents.setOpacity(0.0);
        history_list.setOpacity(0.0);
        friends_list.setOpacity(1.0);
        stats_pane.setOpacity(0.0);

        // Using an obervable list so that it can be updated as more information is added.
        //Getting the data needed to populate the friends list.
        ObservableList<String> friends = FXCollections.observableArrayList(
                Client.getFriends());
        // Adding the content to the listview
        friends_list.setItems(friends);
    }

    //click into a request in the inbox
    public void select_request(MouseEvent mouseEvent) {
        add_friend.setOpacity(0.0);
        add_friend.setDisable(false);
        add_friend.toFront();
        decline_friend.setOpacity(0.0);
        decline_friend.setDisable(true);
        decline_friend.toFront();
        edit_profile_button.setOpacity(0.0);
        edit_profile_button.setDisable(true);

    }

    public void accept_request(MouseEvent mouseEvent) {
        add_friend.setOpacity(0.0);
        add_friend.setDisable(true);
        decline_friend.setOpacity(0.0);
        decline_friend.setDisable(true);
        //**add call to send to client here**

        //taken from chat gtp. prompt: "how do I set a label to show for only a certain amount of time?"
        notification.setText("Friend Added");
        PauseTransition pause = new PauseTransition(Duration.seconds(10));
        pause.setOnFinished(e -> notification.setVisible(false)); // hide after delay
        pause.play();

    }

    public void decline_request(MouseEvent mouseEvent) {
        add_friend.setOpacity(0.0);
        add_friend.setDisable(true);
        decline_friend.setOpacity(0.0);
        decline_friend.setDisable(true);
        //** send request decline to client
        notification.setText("Request Declined");
        PauseTransition pause = new PauseTransition(Duration.seconds(10));
        pause.setOnFinished(e -> notification.setVisible(false)); // hide after delay
        pause.play();
    }


    //The history button is pressed.
    public void open_history(MouseEvent mouseEvent) {
        //Making sure that all other lists/table is invisible so that you cannot see the other windows, only the one clicked into.
        history_list.setOpacity(1.0);
        inbox_contents.setOpacity(0.0);
        friends_list.setOpacity(0.0);
        stats_pane.setOpacity(0.0);
        edit_profile_button.setDisable(false);
        edit_profile_button.toFront();
        add_friend.setOpacity(0.0);
        add_friend.setDisable(true);

        //Using an obervable list so that it can be updated as more information is added.
        //Getting the data needed to populate the game history list.
        ObservableList<String> history = FXCollections.observableArrayList(
                Client.getGameHistory());

        //Adding the histroy content to the listview
        history_list.setItems(history);
    }


    public void click_friend_request(MouseEvent mouseEvent) {
        add_friend.setOpacity(1.0);
        add_friend.setDisable(false);
        decline_friend.setOpacity(1.0);
        decline_friend.setDisable(false);
        edit_profile_button.setOpacity(0.0);
        edit_profile_button.setDisable(true);

    }

    //the stats button is pressed.
    public void open_stats(MouseEvent mouseEvent) {
        //Making sure that all other lists/table is invisible so that you cannot see the other windows, only the one clicked into.
        history_list.setOpacity(0.0);
        inbox_contents.setOpacity(0.0);
        friends_list.setOpacity(0.0);
        stats_pane.setOpacity(1.0);
        getStats();


    }

    public void getStats() {
        int i = PlayerRanking.TTT_INDEX;
        int j = PlayerRanking.CHECKERS_INDEX;
        int k = PlayerRanking.CONNECT4_INDEX;

        System.out.println(i + j + k);

        //c4 stat labels -> getting the information needed to fill in the stats page
        String c4Rank = Client.getRank(k);
        Double c4Wlr = Double.valueOf(Client.getWinLossRatio(k));
        int c4Rating = Client.getRating(k);
        int c4Wins = Client.getWins(k);

        System.out.println(c4Rank + c4Wlr + c4Rating + c4Wins);

        //Setting the c4 label to the numbers that retrieved above
        c4_rank_label.setText(String.valueOf(c4Rank));
        c4_wlr_label.setText(String.valueOf(c4Wlr));
        c4_rating_label.setText(String.valueOf(c4Rating));
        c4_win_label.setText(String.valueOf(c4Wins));

        //check stat labels -> getting the information needed to fill in the stats page
        String checkRank = Client.getRank(j);
        Double checkWlr = Double.valueOf(Client.getWinLossRatio(j));
        int checkRating = Client.getRating(j);
        int checkWins = Client.getWins(j);

        //setting checkers labels to the numbers that retrieved above
        check_rank_label.setText(String.valueOf(checkRank));
        check_wlr_label.setText(String.valueOf(checkWlr));
        check_rating_label.setText(String.valueOf(checkRating));
        check_win_label.setText(String.valueOf(checkWins));

        //TTT stat labels -> getting the information needed to fill in the stats page
        String TTTRank = Client.getRank(i);
        Double TTTWlr = Double.valueOf(Client.getWinLossRatio(i));
        int TTTRating = Client.getRating(i);
        int TTTWins = Client.getWins(i);

        //setting checkers labels to the numbers that retrieved above
        TTT_rank_label.setText(String.valueOf(TTTRank));
        TTT_wlr_label.setText(String.valueOf(TTTWlr));
        TTT_rating_label.setText(String.valueOf(TTTRating));
        TTT_win_label.setText(String.valueOf(TTTWins));
    }

    //edit button is pressed.
    public void open_edit_profile(MouseEvent mouseEvent) {
        //Making sure that all other lists/table is invisible while editing profile.
        history_list.setOpacity(0.0);history_button.setDisable(false);
        inbox_contents.setOpacity(0.0);inbox_button.setDisable(false);
        friends_list.setOpacity(0.0);friends_button.setDisable(false);
        stats_pane.setOpacity(0.0);stats_button.setDisable(false);
        edit_profile_button.setOpacity(0.0);edit_profile_button.setDisable(false);
        done_button.setOpacity(1.0);done_button.setDisable(false);
        avatar_pane.setOpacity(1.0);avatar_pane.setDisable(false);
        nickname_label.setEditable(true);
        bio_text_area.setEditable(true);bio_text_area.setOpacity(1.0);
        nickname_label.setStyle("-fx-background-color: white;");
        confirm_search.setOpacity(0.0);confirm_search.setDisable(true);
        search_friend.setOpacity(0.0);search_friend.setDisable(true);
        home_button.setOpacity(0.0); home_button.setDisable(true); //force person to finish editing before exiting the page

    }

    //The once the done button is pressed, the editing will no longer occur.
    //my idea is to have the done button click be what will initiate sending to the server.
    public void apply_changes(MouseEvent mouseEvent) {
        history_list.setOpacity(0.0);history_button.setDisable(false);
        inbox_contents.setOpacity(0.0);inbox_button.setDisable(false);
        friends_list.setOpacity(0.0);friends_button.setDisable(false);
        stats_pane.setOpacity(1.0);stats_button.setDisable(false);
        edit_profile_button.setOpacity(1.0);edit_profile_button.setDisable(false);
        done_button.setOpacity(0.0);done_button.setDisable(true);
        avatar_pane.setOpacity(0.0);avatar_pane.setDisable(true);
        nickname_label.setEditable(false);
        bio_text_area.setEditable(false);bio_text_area.setOpacity(1.0);
        nickname_label.setStyle("-fx-background-color: transparent;");
        confirm_search.setOpacity(1.0);confirm_search.setDisable(false);
        search_friend.setOpacity(1.0);search_friend.setDisable(false);
        home_button.setOpacity(1.0); home_button.setDisable(false);

        String bio = bio_text_area.getText(); // gets user input
        bio_label.setText(bio);
        bio_label.setVisible(true);
        getBio(bio);

        String nickname = nickname_label.getText(); // gets user input
        nickname_label.setText(nickname);
        getNickname(nickname);
        getNickname(nickname);

    }

    public void updateProfilePicture(Image image) {
        avatar.setImage(image);
    }

    //For the search button, it will get the text from the search bar next to it.
    public void username_search(MouseEvent mouseEvent) throws InterruptedException {
        //send search to client and open new page
        //Open new profile if username exists
        String friend = search_friend.getText();
        Client.getOtherProfileInfo(friend);
        Thread.sleep(300);
        if (Client.getOtherUsername() == null){notification.setText("username not found!");
            System.out.println("username not found!");
            PauseTransition pause = new PauseTransition(Duration.seconds(10));
            pause.setOnFinished(e -> notification.setVisible(false)); // hide after delay
            pause.play();}
            else{
            try {
                System.out.println("loading profile");
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("otherPlayerProfile.fxml")));
                Stage stage = (Stage) confirm_search.getScene().getWindow();
                otherPlayerProfileGUIController.setFriend(friend);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("error in loading new profile");
            }
        }
    }

    //For the "add friend" button that will become visible when
    public void send_request(MouseEvent mouseEvent) {

        //need code here for sending the request to the server once the "add friend" button
        //is clicked
    }

    //clicking on a friend from the friends list should take the person to their profile.
    public void select_friend(MouseEvent mouseEvent) {
        //I am not entirely sure if we can retrieve the information about which friend was clicked
        //from a ListView.
        //we will need to get the id of the friend that is clicked to send to the server,
        //then we can put another fxml loader to load their profile.
    }

    //This will change the circle in avatar
    public void onlineStatus() {
         //boolean status = Client.getOnlineStatus(); //need this method made.
//        if status {
//            game_status.setOpacity(1.0);
//        }
//        else{
//            game_status.setOpacity(0.0);
//        }
    }

    //All methods below are used to change the profile picture based on what picture is clicked
    //during editing mode
    //------------------------------------------------------------------------------
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

    }

    public void homeButtonClicked() throws IOException {
        AudioManager.mediaPlayer.stop();
        Parent newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gameMenu.fxml")));

        Stage stage = (Stage) home_button.getScene().getWindow();

        stage.setScene(new Scene(newRoot));
        stage.show();
    }

    public void homeButtonPressed() {
        home_button.setImage(new Image("home_button_pressed.png"));
    }

    public void homeButtonReleased() {
        home_button.setImage(new Image("home_button.png"));
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

    //------------------------------------------------------------------------------

    //So that it can be sent to the server? idk how this works man
    public String getAvatarPath(String path) {
        return avatarPath;
    }

    public String getBio(String bio) {
        return bio;
    }

    public String getNickname(String nickname) {
        return nickname;
    }

    public String getFriendSearch(String friend) {
        return friend;
    }

    public void visit_friend(MouseEvent mouseEvent) {
    }
}


