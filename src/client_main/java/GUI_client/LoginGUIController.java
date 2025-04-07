package GUI_client;

import AuthenticationAndProfile.Authentication;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginGUIController {

   //buttons on login screen
    @FXML
    public ImageView loginButton;
    @FXML
    public ImageView signUpButton;

    //username and password
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    //declares the label for login when either username or password is wrong
    @FXML
    private Label wrongLogIn;

    public void userLogin() throws IOException {
     checkLogin();
    }

    public void userSignUp() throws IOException {
     signUp();
    }
    //for signUp pop
    private void signUp() throws IOException {
     //load sign up stuff here ltr
     String username = this.username.getText();
     String password = this.password.getText();
     System.out.println("Sign-up button clicked");
     wrongLogIn.setText("Sign-up button clicked");

    }
    //login button sends to authentication
    private void checkLogin() throws IOException {
     String username = this.username.getText();
     String password = this.password.getText();

     if(username.isEmpty() || password.isEmpty()) {
      wrongLogIn.setText("enter username and password");
      return;
     }
     boolean loginSuccess = Authentication.logIn(username, password);
     //add pop up to switch scene if succesful and then if it is not succesful another pop up will show up with ok

     //tyler switch scene plsssssssssssssssssssssssssssssssssssssssssssssss


    }


}
