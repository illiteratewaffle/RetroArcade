package GUI_client;

import client.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class gameMenuController implements Initializable {
    @FXML
    public ImageView profileButton;
    @FXML
    public ImageView leaderboardsButton;
    @FXML
    public ImageView muteButton;
    @FXML
    public ImageView C4_play_button;
    @FXML
    ImageView checkers_play_button;
    @FXML
    public ImageView quitMenu;
    @FXML
    public ImageView TTT_play_button;
    @FXML
    public ImageView gameMenu_bg_image;

    private Stage quitPopup = new Stage();
    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quitMenu.setImage(new Image("quit_x.png"));

        //setup mute status and soundtrack for main menu
        String path = Objects.requireNonNull(getClass().getResource("/music/mainMenuTrack.mp3")).toExternalForm(); // or absolute path
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

    /**
     * switches scene to TTT game
     *
     * @throws IOException
     */
    public void play_TTT() throws IOException {
        Client.enqueue(0);
        // stop current soundtrack
        AudioManager.mediaPlayer.stop();
        // load TTT resources
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TTT.fxml")));

        // get the current gameMenu stage
        Stage stage = (Stage) gameMenu_bg_image.getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * switches scene to checkers game
     *
     * @throws IOException
     */
    public void play_checkers() throws IOException {
        // stop current soundtrack
        AudioManager.mediaPlayer.stop();
        // get checkers fxml resources
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("checkers.fxml")));

        // get current gameMenu stage
        Stage stage = (Stage) gameMenu_bg_image.getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();
    }

    public void play_C4() throws IOException {
        // stop current soundtrack
        AudioManager.mediaPlayer.stop();
        // get connect4 fxml resources
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("connect4.fxml")));

        // get current gameMenu stage
        Stage stage = (Stage) gameMenu_bg_image.getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * quits gameMenu, closing application
     *
     * @throws IOException
     */
    public void quitMenuClicked() throws IOException {
        // check if popup is already showing
        if (!quitPopup.isShowing()) {
            // if no popup is showing make a new popup
            quitPopup = new Stage();
            // get caller stage, set as popup owner
            Stage owner = (Stage) quitMenu.getScene().getWindow();
            quitPopup.initOwner(owner);

            // load popup resources
            FXMLLoader loader = new FXMLLoader(getClass().getResource("quitPopup.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            QuitPopupController controller = loader.getController();

            quitPopup.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);

            // set closeOwner false so TTT stage doesn't close
            controller.closeOwner = false;
            quitPopup.setScene(scene);
            // show popup and wait for user input
            quitPopup.showAndWait();

            // check if user wants to close TTT game
            if (controller.closeYes) {
                Client.closeEverything();

                AudioManager.mediaPlayer.stop();
                Parent newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));

                Stage stage = (Stage) quitMenu.getScene().getWindow();

                stage.setScene(new Scene(newRoot));
                stage.show();
            }
        }
    }

    public void XPressed() {
        quitMenu.setImage(new Image("XButtonDown.png"));
    }

    public void XReleased() {
        quitMenu.setImage(new Image("quit_x.png"));
    }

    public void TTTStartEntered() {
        TTT_play_button.setImage(new Image("TTT_start_button_inverted.png"));
    }

    public void TTTStartExited() {
        TTT_play_button.setImage(new Image("TTT_start_button.png"));
    }

    public void checkersStartEntered() {
        checkers_play_button.setImage(new Image("checkers_start_button_inverted.png"));
    }

    public void checkersStartExited() {
        checkers_play_button.setImage(new Image("checkers_start_button.png"));
    }

    public void C4StartEntered() {
        C4_play_button.setImage(new Image("C4_start_button_inverted.png"));
    }

    public void C4StartExited() {
        C4_play_button.setImage(new Image("C4_start_button.png"));
    }

    public void muteButtonClick() {
        if (!AudioManager.isMuted()) {
            muteButton.setImage(new Image("muteButton.png"));
            AudioManager.toggleMute();
        } else {
            muteButton.setImage(new Image("unmuteButton.png"));
            AudioManager.toggleMute();
        }
    }

    public void leaderboardsClicked() throws IOException {
        // stop current soundtrack
        AudioManager.mediaPlayer.stop();
        // get connect4 fxml resources
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("leaderboard.fxml")));

        // get current gameMenu stage
        Stage stage = (Stage) gameMenu_bg_image.getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();
    }

    public void leaderboardsEntered() {
        leaderboardsButton.setImage(new Image("leaderboardsButton_inverted.png"));
    }

    public void leaderboardsExited() {
        leaderboardsButton.setImage(new Image("leaderboardsButton.png"));
    }

    public void profileButtonClicked() throws IOException {
        // stop current soundtrack
        AudioManager.mediaPlayer.stop();
        // get connect4 fxml resources
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("profile.fxml")));

        // get current gameMenu stage
        Stage stage = (Stage) gameMenu_bg_image.getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();
    }

    public void profileButtonPressed() {
        profileButton.setImage(new Image("profile_button_pressed.png"));
    }

    public void profileButtonReleased() {
        profileButton.setImage(new Image("profile_button.png"));
    }
}
