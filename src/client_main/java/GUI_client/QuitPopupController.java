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

    // closeYes used by caller to see if popup is closed
    public boolean closeYes = false;
    // closeOwner checks if the popup caller wants to close the caller stage or not
    public boolean closeOwner = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        popupImage.setImage(new Image("quitPopup.png"));
    }

    /**
     * yes button was pressed
     */
    public void quitYes(){
        // update that popup is closed
        closeYes = true;
        popupStage = (Stage) popupImage.getScene().getWindow();
        Stage ownerStage = (Stage) popupStage.getOwner();
        if (closeOwner) ownerStage.close();
        popupStage.close();
    }

    /**
     * no button was pressed
     */
    public void quitNo(){
        popupStage = (Stage) popupImage.getScene().getWindow();
        popupStage.close();
    }


}
