package GUI_client;

import GameLogic_Client.Connect4.C4Controller;
import GUI_client.C4Application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class C4Application extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI_client/connect4.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        C4GUIController GUIcontroller = fxmlLoader.getController();

        stage.setTitle("Connect 4 - JavaFX UI");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("aaa");//
        C4Controller c4Controller = new C4Controller();
        c4Controller.start();
        launch(args);

    }
}
