package GUI_client;
import AuthenticationAndProfile.Profile;
import client.Client2;
import GUI_client.AudioManager;
import GUI_client.otherPlayerProfileGUIController;
import javafx.application.Platform;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import GUI_client.otherPlayerProfileGUIController;

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
    public ImageView visit_button;
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
    private String avatarPath;
    private String nickname;
    private String bio;
    private String username;
    private String selectedRequest;
    private String selectedFriend;
    static String friend;

    //**NOTE TO MARKER: Any instance of a hardcoded input is simply because the server was not working for that specific part:
    //but I still wanted to show the functionality of the GUI.
    @FXML
    public void initialize() {
        Client2.getProfileInfo();
        if (Client2.getProfilePath() == null) {
            String avatarPath = "/GUI_avatars/Invader_green.PNG";
            updateProfilePicture(avatarPath);
            System.out.println(avatarPath);
        } else {
            Platform.runLater(() -> {
                avatarPath = Client2.getProfilePath();
                updateProfilePicture(avatarPath);
            });
        }
        System.out.println(avatarPath);


        if (Client2.getUsername() == null) {
            username = "";
            name_label.setText("@" + username);
        } else {
            username = Client2.getUsername();
            nickname_label.setText("@" + username);
        }

        if (Client2.getNickname() == null) {
            nickname = "Set your nickname!";
            nickname_label.setText(nickname);
        } else {
            nickname = Client2.getNickname();
            nickname_label.setText(nickname);
        }
        if (Client2.getBio() == null) {
            bio = "Set your Bio";
            bio_text_area.setText(bio);
        } else {
            bio = Client2.getBio();
            bio_text_area.setText(bio);
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
        inbox_contents.getItems().clear();
        //Making sure that all other lists/table is invisible so that you cannot see the other windows, only the one clicked into.
        inbox_contents.setOpacity(1.0);
        history_list.setOpacity(0.0);
        friends_list.setOpacity(0.0);
        stats_pane.setOpacity(0.0);
        List<String> requestList = Client2.getFriendRequests();
        inbox_contents.getItems().addAll(requestList);
        //Adding the request content to the listview
        inbox_contents.setItems((ObservableList) requestList);
    }

    //The friends button is pressed
    public void open_friends(MouseEvent mouseEvent) {
        friends_list.getItems().clear();
        //Making sure that all other lists/table is invisible so that you cannot see the other windows, only the one clicked into.
        inbox_contents.setOpacity(0.0);
        history_list.setOpacity(0.0);
        friends_list.setOpacity(1.0);
        stats_pane.setOpacity(0.0);
        edit_profile_button.setDisable(false);
        edit_profile_button.toFront();
        edit_profile_button.setOpacity(1.0);
        List<String> friendsList = Client2.getFriends();
        friends_list.getItems().addAll(friendsList);
        // Adding the content to the listview
        friends_list.setItems((ObservableList) friendsList);
    }

    //The history button is pressed.
    public void open_history(MouseEvent mouseEvent) {
        history_list.getItems().clear();
        //Making sure that all other lists/table is invisible so that you cannot see the other windows, only the one clicked into.
        history_list.setOpacity(1.0);
        inbox_contents.setOpacity(0.0);
        friends_list.setOpacity(0.0);
        stats_pane.setOpacity(0.0);
        edit_profile_button.setDisable(false);
        edit_profile_button.toFront();
        edit_profile_button.setOpacity(1.0);
        add_friend.setOpacity(0.0);
        add_friend.setDisable(true);
        List<String> historyList = Client2.getGameHistory();
        history_list.getItems().addAll(historyList);
        //Adding the history content to the listview
        history_list.setItems((ObservableList) historyList);
    }


    //If a friend request is clicked (inbox ListView) into this will happen so they can press "add friend" or "Decline"
    public void click_friend_request(MouseEvent mouseEvent) {
        add_friend.setOpacity(1.0);
        add_friend.setDisable(false);
        add_friend.toFront();
        decline_friend.setOpacity(1.0);
        decline_friend.setDisable(false);
        decline_friend.toFront();
        edit_profile_button.setOpacity(0.0);
        edit_profile_button.setDisable(true);

        //This is to capture the name of the friend that was clicked from the request list (inbox list)
        //The selectedRequest will then be sent as an accepted request or a declined request depending on what button
        //The user clicked (The next 2 methods below)
        String selected = inbox_contents.getSelectionModel().getSelectedItem().toString();
        if (selectedRequest != null) {
            selectedRequest = selected;
            System.out.println("you clicked on" + selectedRequest);
        } else {
            System.out.println("Nothing to Click!");
        }
    }

    //After a request is clicked, the user will have the option to accept the request. The username of the person
    //Will be sent to Client2 through the acceptRequest method (name may have changed IDK)
    public void accept_request(MouseEvent mouseEvent) {
        if (selectedRequest == null) return;
        add_friend.setOpacity(0.0);
        add_friend.setDisable(true);
        decline_friend.setOpacity(0.0);
        decline_friend.setDisable(true);
        edit_profile_button.setOpacity(0.0);
        edit_profile_button.setDisable(true);
        //**add call to send to Client2 here**
        //taken from chat gtp. prompt: "how do I set a label to show for only a certain amount of time?"
        notification.toFront();
        notification.setText("Friend Added");
        notification.setOpacity(1.0);
        PauseTransition pause = new PauseTransition(Duration.seconds(10));
        pause.setOnFinished(e -> notification.setVisible(false)); // hide after delay
        pause.play();
        notification.toBack();

        //Not sure how this will be sent to server.
        Client2.acceptRequest(selectedRequest); //selected request = the username of the friend
        inbox_contents.getItems().remove(selectedRequest);
        selectedRequest = null;
    }

    //After a request is clicked, the user will have the option to decline the request. The username of the person
    //Will be sent to Client2 through the acceptRequest method (name may have changed IDK)
    public void decline_request(MouseEvent mouseEvent) {
        if (selectedRequest == null) return;
        add_friend.setOpacity(0.0);
        add_friend.setDisable(true);
        decline_friend.setOpacity(0.0);
        decline_friend.setDisable(true);
        edit_profile_button.setOpacity(0.0);
        edit_profile_button.setDisable(true);
        //Show "request declined" to user
        notification.toFront();
        notification.setText("Request Declined");
        notification.setOpacity(1.0);
        PauseTransition pause = new PauseTransition(Duration.seconds(10));
        pause.setOnFinished(e -> notification.setVisible(false)); // hide after delay
        pause.play();
        notification.toBack();
        //Not sure how the declined request will be sent to the server.
        Client2.declineRequest(selectedRequest);
        inbox_contents.getItems().remove(selectedRequest);
        selectedRequest = null;
    }

    //the stats button is pressed.
    public void open_stats(MouseEvent mouseEvent) {
        //Making sure that all other lists/table is invisible so that you cannot see the other windows, only the one clicked into.
        history_list.setOpacity(0.0);
        inbox_contents.setOpacity(0.0);
        friends_list.setOpacity(0.0);
        stats_pane.setOpacity(1.0);
        edit_profile_button.setDisable(false);
        edit_profile_button.toFront();
        edit_profile_button.setOpacity(1.0);
        getStats();
    }

    public void getStats() {
        int i = PlayerRanking.TTT_INDEX;
        int j = PlayerRanking.CHECKERS_INDEX;
        int k = PlayerRanking.CONNECT4_INDEX;

        System.out.println(i + j + k);

        //c4 stat labels -> getting the information needed to fill in the stats page
        String c4Rank = Client2.getRank(k);
        Double c4Wlr = Double.valueOf(Client2.getWinLossRatio(k));
        int c4Rating = Client2.getRating(k);
        int c4Wins = Client2.getWins(k);

        System.out.println(c4Rank + c4Wlr + c4Rating + c4Wins);

        //Setting the c4 label to the numbers that retrieved above
        c4_rank_label.setText(String.valueOf(c4Rank));
        c4_wlr_label.setText(String.valueOf(c4Wlr));
        c4_rating_label.setText(String.valueOf(c4Rating));
        c4_win_label.setText(String.valueOf(c4Wins));

        //check stat labels -> getting the information needed to fill in the stats page
        String checkRank = Client2.getRank(j);
        Double checkWlr = Double.valueOf(Client2.getWinLossRatio(j));
        int checkRating = Client2.getRating(j);
        int checkWins = Client2.getWins(j);

        //setting checkers labels to the numbers that retrieved above
        check_rank_label.setText(String.valueOf(checkRank));
        check_wlr_label.setText(String.valueOf(checkWlr));
        check_rating_label.setText(String.valueOf(checkRating));
        check_win_label.setText(String.valueOf(checkWins));

        //TTT stat labels -> getting the information needed to fill in the stats page
        String TTTRank = Client2.getRank(i);
        Double TTTWlr = Double.valueOf(Client2.getWinLossRatio(i));
        int TTTRating = Client2.getRating(i);
        int TTTWins = Client2.getWins(i);

        //setting checkers labels to the numbers that retrieved above
        TTT_rank_label.setText(String.valueOf(TTTRank));
        TTT_wlr_label.setText(String.valueOf(TTTWlr));
        TTT_rating_label.setText(String.valueOf(TTTRating));
        TTT_win_label.setText(String.valueOf(TTTWins));
    }

    //edit button is pressed.
    public void open_edit_profile(MouseEvent mouseEvent) {
        //Making sure that all other lists/table is invisible while editing profile.
        history_list.setOpacity(0.0);
        history_button.setDisable(false);
        inbox_contents.setOpacity(0.0);
        inbox_button.setDisable(false);
        friends_list.setOpacity(0.0);
        friends_button.setDisable(false);
        stats_pane.setOpacity(0.0);
        stats_button.setDisable(false);
        edit_profile_button.setOpacity(0.0);
        edit_profile_button.setDisable(false);
        done_button.setOpacity(1.0);
        done_button.setDisable(false);
        done_button.toFront();
        avatar_pane.setOpacity(1.0);
        avatar_pane.setDisable(false);
        nickname_label.setEditable(true);
        bio_text_area.setEditable(true);
        bio_text_area.setOpacity(1.0);
        nickname_label.setStyle("-fx-background-color: white;");
        confirm_search.setOpacity(0.0);
        confirm_search.setDisable(true);
        search_friend.setOpacity(0.0);
        search_friend.setDisable(true);
        home_button.setOpacity(0.0);
        home_button.setDisable(true); //force person to finish editing before exiting the page

    }

    //The once the done button is pressed, the editing will no longer occur.
    //my idea is to have the done button click be what will initiate sending to the server.
    public void apply_changes(MouseEvent mouseEvent) {
        history_list.setOpacity(0.0);
        history_button.setDisable(false);
        inbox_contents.setOpacity(0.0);
        inbox_button.setDisable(false);
        friends_list.setOpacity(0.0);
        friends_button.setDisable(false);
        stats_pane.setOpacity(1.0);
        stats_button.setDisable(false);
        edit_profile_button.setOpacity(1.0);
        edit_profile_button.setDisable(false);
        done_button.setOpacity(0.0);
        done_button.setDisable(true);
        avatar_pane.setOpacity(0.0);
        avatar_pane.setDisable(true);
        nickname_label.setEditable(false);
        bio_text_area.setEditable(false);
        bio_text_area.setOpacity(1.0);
        nickname_label.setStyle("-fx-background-color: transparent;");
        confirm_search.setOpacity(1.0);
        confirm_search.setDisable(false);
        confirm_search.toFront();
        search_friend.setOpacity(1.0);
        search_friend.setDisable(false);
        search_friend.toFront();
        home_button.setOpacity(1.0);
        home_button.setDisable(false);

        String bio = bio_text_area.getText(); // gets user input
        bio_label.setText(bio);
        bio_label.setVisible(true);

        String nickname = nickname_label.getText(); // gets user input
        nickname_label.setText(nickname);

        //When the apply button is pressed I want to send these to client2 (all strings)
        Platform.runLater(() -> {
            Client2.setBio(bio);
        });
        Platform.runLater(() -> {
            Client2.setNickname(nickname);
        });
        Platform.runLater(() -> {
            Client2.setProfilePath(avatarPath);
        });

        //Notify that the profile was updated
        notification.toFront();
        notification.setText("Profile updated!");
        notification.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(e -> notification.setVisible(false));
        pause.play();
        notification.toBack();


    }

    //Any click on a picture will call to this to set the picture.
    public void updateProfilePicture(String path) {
        URL url = getClass().getResource(path);

        if (url != null) {
            System.out.println("Image URL: " + url);
            Image img = new Image(url.toExternalForm(), false);
            avatar.setImage(img);
            avatarPath = path;
        } else {
            System.out.println("Failed to load image from path: " + path);
        }
    }

    //For the search button, it will get the text from the search bar next to it.
    public void username_search(MouseEvent mouseEvent) throws InterruptedException {
        //send username to Client2 to verify it exists, and if it does then that page will be visited
        //Open new profile if username exists
        String friend = search_friend.getText();
        Client2.getOtherProfileInfo(friend);
        PauseTransition wait = new PauseTransition(Duration.seconds(3));
        username = Client2.getUsername();
        if (username == null) {
            notification.toFront();
            notification.setText("username not found!");
            notification.setVisible(true);
            System.out.println("username not found!");
            PauseTransition pause = new PauseTransition(Duration.seconds(10));
            pause.setOnFinished(e -> notification.setVisible(false)); // hide after delay
            pause.play();
            notification.toBack();
            // Now wait for the response asynchronously

            //Alternative way to try to load:
            System.out.println("loading profile");
        }
        else{
                try {
                    System.out.println("Loading other Profile");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("otherPlayerProfile.fxml"));
                    Parent root = loader.load();
                    otherPlayerProfileGUIController controller = loader.getController();
                    controller.setFriendUsername(friend); // assuming you're parsing the JSON/dictionary
                    Stage stage = (Stage) search_friend.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    System.out.println("Unable to load profile");
                    e.printStackTrace(); // this would tell you why it's not loading
                }
        }
    }



    //clicking on a friend from the friends list should take the person to their profile.
    public void click_friend_list(MouseEvent mouseEvent) {
        add_friend.setOpacity(0.0);
        add_friend.setDisable(true);
        decline_friend.setOpacity(0.0);
        decline_friend.setDisable(true);
        edit_profile_button.setOpacity(0.0);
        edit_profile_button.setDisable(true);
        visit_button.setOpacity(1.0);
        visit_button.setDisable(false);
        visit_button.toFront();
        String selected = friends_list.getSelectionModel().getSelectedItem().toString();
        if (selectedFriend != null) {
            selectedFriend = selected;
            System.out.println("you clicked on" + selectedFriend);
        } else {
            System.out.println("Nothing to Click!");
        }
    }

    //Once you click on a friend in your list you will be able to do to that friend's profile.
    //Clicking visit will take you to the profile.
    public void visit_friend(MouseEvent mouseEvent) throws InterruptedException {
        Client2.getOtherProfileInfo(selectedFriend);
        Thread.sleep(300);
        if (Client2.getOtherUsername() == null) {
            notification.toFront();
            notification.setText("username not found!");
            notification.setVisible(true);
            System.out.println("username not found!");
            PauseTransition pause = new PauseTransition(Duration.seconds(10));
            pause.setOnFinished(e -> notification.setVisible(false)); // hide after delay
            pause.play();
            notification.toBack();
        } else {
            try {
                //Not sure if I am loading this properly.
                System.out.println("loading profile");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("otherPlayerProfile.fxml"));
                Parent root = loader.load(); // Load FXML
                otherPlayerProfileGUIController controller = loader.getController(); // Get controller instance
                controller.setFriendUsername(selectedFriend);
                Stage stage = (Stage) visit_button.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("error in loading new profile");
            }
        }
    }


    //All methods below are used to change the profile picture based on what picture is clicked
    //during editing mode
    //------------------------------------------------------------------------------
    public void choose_poop(MouseEvent mouseEvent) {
        String path = "/GUI_avatars/poop.PNG";
        URL url = getClass().getResource(path);

        if (url != null) {
            System.out.println("Image URL: " + url);
            Image img = new Image(url.toExternalForm(), false);
            avatar.setImage(img);
            avatarPath = path;
        } else {
            System.out.println("Failed to load image from path: " + path);
        }
    }


    public void choose_goomba(MouseEvent mouseEvent) {
        String path = "/GUI_avatars/mario_goomba.PNG";
        URL url = getClass().getResource(path);

        if (url != null) {
            System.out.println("Image URL: " + url);
            Image img = new Image(url.toExternalForm(), false);
            avatar.setImage(img);
            avatarPath = path;
        } else {
            System.out.println("Failed to load image from path: " + path);
        }
    }

    public void choose_purple_alien(MouseEvent mouseEvent) {
        String path = "/GUI_avatars/invader_purple.PNG";
        URL url = getClass().getResource(path);

        if (url != null) {
            System.out.println("Image URL: " + url);
            Image img = new Image(url.toExternalForm(), false);
            avatar.setImage(img);
            avatarPath = path;
        } else {
            System.out.println("Failed to load image from path: " + path);
        }
    }


    public void choose_pink_alien(MouseEvent mouseEvent) {
        String path = "/GUI_avatars/Invader_pink.PNG";
        URL url = getClass().getResource(path);

        if (url != null) {
            System.out.println("Image URL: " + url);
            Image img = new Image(url.toExternalForm(), false);
            avatar.setImage(img);
            avatarPath = path;
        } else {
            System.out.println("Failed to load image from path: " + path);
        }
    }

    public void choose_green_alien(MouseEvent mouseEvent) {
        String path = "/GUI_avatars/Invader_green.PNG";
        URL url = getClass().getResource(path);

        if (url != null) {
            System.out.println("Image URL: " + url);
            Image img = new Image(url.toExternalForm(), false);
            avatar.setImage(img);
            avatarPath = path;
        } else {
            System.out.println("Failed to load image from path: " + path);
        }
    }

    public void choose_cyan_alien(MouseEvent mouseEvent) {
        String path = "/GUI_avatars/Invader_cyan.PNG";
        URL url = getClass().getResource(path);

        if (url != null) {
            System.out.println("Image URL: " + url);
            Image img = new Image(url.toExternalForm(), false);
            avatar.setImage(img);
            avatarPath = path;
        } else {
            System.out.println("Failed to load image from path: " + path);
        }
    }

    public void choose_toad(MouseEvent mouseEvent) {
        String path = "/GUI_avatars/mario_toad.PNG";
        URL url = getClass().getResource(path);

        if (url != null) {
            System.out.println("Image URL: " + url);
            Image img = new Image(url.toExternalForm(), false);
            avatar.setImage(img);
            avatarPath = path;
        } else {
            System.out.println("Failed to load image from path: " + path);
        }
    }

    public void choose_blue_ghost(MouseEvent mouseEvent) {
        String path = "/GUI_avatars/pacman_blue.PNG";
        URL url = getClass().getResource(path);

        if (url != null) {
            System.out.println("Image URL: " + url);
            Image img = new Image(url.toExternalForm(), false);
            avatar.setImage(img);
            avatarPath = path;
        } else {
            System.out.println("Failed to load image from path: " + path);
        }
    }

    public void choose_pink_ghost(MouseEvent mouseEvent) {
        String path = "/GUI_avatars/pacman_pink.PNG";
        URL url = getClass().getResource(path);

        if (url != null) {
            System.out.println("Image URL: " + url);
            Image img = new Image(url.toExternalForm(), false);
            avatar.setImage(img);
            avatarPath = path;
        } else {
            System.out.println("Failed to load image from path: " + path);
        }
    }

    public void choose_red_ghost(MouseEvent mouseEvent) {
        String path = "/GUI_avatars/pacman_red.PNG";
        URL url = getClass().getResource(path);

        if (url != null) {
            System.out.println("Image URL: " + url);
            Image img = new Image(url.toExternalForm(), false);
            avatar.setImage(img);
            avatarPath = path;
        } else {
            System.out.println("Failed to load image from path: " + path);
        }
    }

    public void choose_yellow_ghost(MouseEvent mouseEvent) {
        String path = "/GUI_avatars/pacman_yellow.PNG";
        URL url = getClass().getResource(path);

        if (url != null) {
            System.out.println("Image URL: " + url);
            Image img = new Image(url.toExternalForm(), false);
            avatar.setImage(img);
            avatarPath = path;
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
    public void searchPressed() {
        confirm_search.setImage(new Image("search_button_pressed.png"));
    }

    public void searchReleased() {
        confirm_search.setImage(new Image("search_button.png"));
    }

    public void send_request(MouseEvent mouseEvent) {
    }

}



