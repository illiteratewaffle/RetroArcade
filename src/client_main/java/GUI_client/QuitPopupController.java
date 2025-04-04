package GUI_client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class QuitPopupController implements Initializable {
    @FXML
    public ImageView popupImage;

    private Stage popupStage = new Stage();
    private Stage ownerStage = new Stage();

    public boolean close = false;
    public boolean closeOld = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        popupImage.setImage(new Image("quitPopup.png"));
    }

    public void quitYes(){
        close = true;
        popupStage = (Stage) popupImage.getScene().getWindow();
        ownerStage = (Stage) popupStage.getOwner();
        if (closeOld) ownerStage.close();
        popupStage.close();
    }
    public void quitNo(){
        popupStage = (Stage) popupImage.getScene().getWindow();
        ownerStage = (Stage) popupStage.getOwner();
        popupStage.close();
    }


}
