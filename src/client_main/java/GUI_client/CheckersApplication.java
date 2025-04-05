package GUI_client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CheckersApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CheckersApplication.class.getResource("/GUI_client/checkers.fxml")); // retrieve the FXML
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);       // Build the scene
        stage.setResizable(false);                                         // background is not resizable (yet)
        stage.setTitle("Checkers");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}