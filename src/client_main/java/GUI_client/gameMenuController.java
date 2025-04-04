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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class gameMenuController implements Initializable {
    @FXML
    public ImageView quitMenu;
    @FXML
    public ImageView TTT_play_button;
    @FXML
    public ImageView gameMenu_bg_image;

    private Stage quitPopup = new Stage();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quitMenu.setImage(new Image("quit_x.png"));
    }

    public void play_TTT() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TTT.fxml")));

        Stage stage = (Stage) gameMenu_bg_image.getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();
    }
    public void quitMenuClicked() throws IOException {
        if (!quitPopup.isShowing()) {
            quitPopup = new Stage();
            Stage owner = (Stage) gameMenu_bg_image.getScene().getWindow();
            quitPopup.initOwner(owner);

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("quitPopup.fxml")));
            Scene scene = new Scene(root);

            quitPopup.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            quitPopup.setScene(scene);
            quitPopup.show();
        }
    }
    public void XPressed(){
        quitMenu.setImage(new Image("XButtonDown.png"));
    }
    public void XReleased(){
        quitMenu.setImage(new Image("quit_x.png"));
    }
}
