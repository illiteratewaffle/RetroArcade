module com.example.projectdemo3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    //requires org.junit.jupiter.api;


    opens GameLogic_Client.Connect4 to javafx.fxml;
    exports GameLogic_Client.Connect4;
}

