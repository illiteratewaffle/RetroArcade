package GUI_client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("/GUI_client/login.fxml")); // retrieve the FXML
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);       // Build the scene
        stage.setResizable(false);                                         // background is not resizable (yet)
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}