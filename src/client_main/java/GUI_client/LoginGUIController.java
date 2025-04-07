package GUI_client;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.PasswordField;

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

    //login button sends to authentication
    private void checkLogin() throws IOException {
     String username = this.username.getText();
     String password = this.password.getText();


    }
    //for signUp pop
    private void signUp() throws IOException {
     String username = this.username.getText();
     String password = this.password.getText();

    }


}
