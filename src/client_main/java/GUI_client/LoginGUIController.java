package GUI_client;

import AuthenticationAndProfile.Authentication;
import javafx.fxml.FXML;
import client.*;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.PasswordField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class LoginGUIController implements Initializable {

    private Stage quitPopup = new Stage();
    //buttons on login screen
    @FXML
    public ImageView createProfileButton;
    @FXML
    public ImageView muteButton;
    @FXML
    public ImageView loginButton;
    @FXML
    public ImageView signUpButton;
    @FXML
    public ImageView exitButton;
    @FXML
    public ImageView backButton;

    //sign up user and password
    @FXML
    public ImageView signUpPage;
    @FXML
    private TextField signUpUserField;
    @FXML
    private PasswordField signUpPasswordField;

    //username and password
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    //declares the label for login when either username or password is wrong
    @FXML
    private Label wrongLogIn;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        //setup mute status and soundtrack for main menu
        String path = Objects.requireNonNull(getClass().getResource("/music/loginTrack.mp3")).toExternalForm();
        Media sound = new Media(path);
        AudioManager.mediaPlayer = new MediaPlayer(sound);
        AudioManager.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        AudioManager.mediaPlayer.play();
        if (AudioManager.isMuted()){
            AudioManager.applyMute();
            muteButton.setImage(new Image("muteButton.png"));
        } else{
            muteButton.setImage(new Image("unmuteButton.png"));
        }
    }

    //for signUp pop up
    @FXML
    private void signUpButtonClicked() throws IOException {
        //load sign up stuff here ltr
        //signUpPage.setImage(new Image("sign_up_page.png"));
        signUpPage.setVisible(true);
        signUpUserField.setVisible(true);
        signUpUserField.setDisable(false);
        signUpPasswordField.setVisible(true);
        signUpPasswordField.setDisable(false);
        wrongLogIn.setVisible(false);
        createProfileButton.setMouseTransparent(false);
        createProfileButton.setVisible(true);
        createProfileButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/GUI_buttons/createProfile.png")).toExternalForm()));
        backButton.setVisible(true);

    }

    @FXML
    private void backButtonClicked() throws IOException {
        signUpPage.setVisible(false);
        signUpUserField.setVisible(false);
        signUpUserField.setDisable(true);
        signUpPasswordField.setVisible(false);
        signUpPasswordField.setDisable(true);
        //signUpPage.setMouseTransparent(true);
        //signUpButton.setMouseTransparent(true);
        backButton.setVisible(false);
        createProfileButton.setVisible(false);
        createProfileButton.setMouseTransparent(true);
    }

    public void signup_ok_clicked() throws IOException {
        signUpPage.setImage(null);
        signUpPage.setMouseTransparent(true);
        signUpButton.setMouseTransparent(true);
    }

    //login button sends to authentication
    @FXML
    private void checkLogin() throws IOException {
        wrongLogIn.setVisible(true);
        String username = this.username.getText();
        String password = this.password.getText();
        if (username.equals("admin") & password.equals("admin")){

        } else {
            Client.login(username, password);
        }

        boolean loginSuccess = true;
        if(username.isEmpty() || password.isEmpty()) {
            wrongLogIn.setText("Enter a username and password!");
            return;
        } else if (loginSuccess){
            // stop current soundtrack
            AudioManager.mediaPlayer.stop();
            // load TTT resources
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gameMenu.fxml")));

            // get the current gameMenu stage
            Stage stage = (Stage) exitButton.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();
        }

        //need to send over username and password
        //boolean loginSuccess = Authentication.logIn(username, password); // where networking function should be called

//  if(loginSuccess) {
//   //tyler switch scene plsssssssssssssssssssssssssssssssssssssssssssssss
//  }
//  else{
//   wrongLogIn.setText("Wrong username or password. Try again.");
//  }
    }

    public void exitButtonClicked() throws IOException {
        // only show new popup if no popup is showing
        if (!quitPopup.isShowing()) {
            quitPopup = new Stage();
            Stage owner = (Stage) exitButton.getScene().getWindow();
            quitPopup.initOwner(owner);

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("quitPopup.fxml")));
            Scene scene = new Scene(root);

            quitPopup.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            quitPopup.setScene(scene);
            quitPopup.show();
        }
    }

    public void exitButtonPressed(){
        exitButton.setImage(new Image("XButtonDown.png"));
    }

    public void exitButtonReleased(){
        exitButton.setImage(new Image("quit_x.png"));
    }

    public void muteButtonClicked(){
        if(!AudioManager.isMuted()) {
            muteButton.setImage(new Image("muteButton.png"));
            AudioManager.toggleMute();
        } else {
            muteButton.setImage(new Image("unmuteButton.png"));
            AudioManager.toggleMute();
        }
    }

    public void createProfileClicked(){

    }

    public void createProfilePressed(){
        createProfileButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/GUI_buttons/pressed/createProfile_pressed.png")).toExternalForm()));

    }

    public void createProfileReleased(){
        createProfileButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/GUI_buttons/createProfile.png")).toExternalForm()));
    }

    public void backButtonPressed(){
        backButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/GUI_buttons/pressed/back_button_pressed.png")).toExternalForm()));
    }

    public void backButtonReleased(){
        backButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/GUI_buttons/back_button.png")).toExternalForm()));
    }

    public void signupButtonPressed(){
        signUpButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/GUI_buttons/pressed/signup_button_pressed.png")).toExternalForm()));
    }

    public void signupButtonReleased(){
        signUpButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/GUI_buttons/signup_button.png")).toExternalForm()));
    }
}