package GameLogic_Client.Connect4;//package client_main.java.GameLogic_Client.Connect4;

import client_main.java.GameLogic_Client.Connect4.C4Controller;
import client_main.java.GameLogic_Client.Connect4.C4GUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class C4Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(C4Main.class.getResource("connect4.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
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
