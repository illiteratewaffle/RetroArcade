package GUI_client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class gameMenuController implements Initializable {
    @FXML
    public ImageView TTT_play_button;
    @FXML
    public ImageView gameMenu_bg_image;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void play_TTT() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TTT.fxml"));

        Stage stage = (Stage) gameMenu_bg_image.getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();
    }
    public void play_C4() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("connect4.fxml"));

        Stage stage = (Stage) gameMenu_bg_image.getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();

}
