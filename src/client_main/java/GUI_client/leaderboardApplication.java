package GUI_client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class leaderboardApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TTTApplication.class.getResource("leaderboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 900);
        stage.setResizable(false);
        stage.setTitle("leaderboard");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}