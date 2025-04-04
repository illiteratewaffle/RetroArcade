package GUI_client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class gameMenuController implements Initializable {
    @FXML
    public ImageView quitMenu;
    @FXML
    public ImageView TTT_play_button;
    @FXML
    public ImageView gameMenu_bg_image;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quitMenu.setImage(new Image("quit_x.png"));
    }

    public void play_TTT() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TTT.fxml"));

        Stage stage = (Stage) gameMenu_bg_image.getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();
    }
    public void quitMenuClicked(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType yesButton = new ButtonType("Yes");
        alert.getButtonTypes().set(0, yesButton);
        alert.setTitle("Quit Application");
        alert.setHeaderText("Are you sure you want to quit?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == yesButton){
            Platform.exit();
        }
    };
}
