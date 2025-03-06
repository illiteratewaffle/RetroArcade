package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class guiController extends Application {

    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file (Ensure the correct path)
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/FXML/guiController.fxml"));

            // Set the title of the primary stage (window)
            primaryStage.setTitle("My Application");

            // Create a scene, set its size, and set it to the stage
            primaryStage.setScene(new Scene(root, 800, 600));

            // Show the primary stage (window)
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}