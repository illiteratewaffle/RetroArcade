package GUI_client;

import AuthenticationAndProfile.Authentication;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginGUIController {

   //buttons on login screen
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
     signUpButton.setImage(new Image ("signup_button.png"));
    }

     public void userLogin() throws IOException {
     checkLogin();
    }

    //for signUp pop up
    @FXML
    private void signUpButtonPress() throws IOException {
     //load sign up stuff here ltr
     //signUpPage.setImage(new Image("sign_up_page.png"));
     signUpPage.setVisible(true);
     signUpUserField.setVisible(true);
     signUpUserField.setDisable(false);
     signUpPasswordField.setVisible(true);
     signUpPasswordField.setDisable(false);
     signUpPage.setMouseTransparent(false);
     signUpButton.setMouseTransparent(false);
     backButton.setVisible(true);


    }
    @FXML
    private void backButtonPress() throws IOException {
     signUpPage.setVisible(false);
     signUpUserField.setVisible(false);
     signUpUserField.setDisable(true);
     signUpPasswordField.setVisible(false);
     signUpPasswordField.setDisable(true);
     signUpPage.setMouseTransparent(true);
     signUpButton.setMouseTransparent(true);
     backButton.setVisible(false);

    }
    public void signup_ok_clicked() throws IOException {
     signUpPage.setImage(null);
     signUpPage.setMouseTransparent(true);
     signUpButton.setMouseTransparent(true);

    }
    //login button sends to authentication
    private void checkLogin() throws IOException {
     String username = this.username.getText();
     String password = this.password.getText();

     if(username.isEmpty() || password.isEmpty()) {
      wrongLogIn.setText("enter username and password");
      return;
     }
     boolean loginSuccess = Authentication.logIn(username, password); //send boolean to
     //add pop up to switch scene if succesful and then if it is not succesful another pop up will show up with ok

     //tyler switch scene plsssssssssssssssssssssssssssssssssssssssssssssss


    }


}
