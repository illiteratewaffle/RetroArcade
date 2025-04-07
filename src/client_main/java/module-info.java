module seng300.w25.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media; // if using media like sounds or video
    requires java.sql;     // optional

    exports GUI_client;
    exports GameLogic_Client;

    opens GUI_client to javafx.fxml, javafx.graphics;
    exports GameLogic_Client.Connect4;
    opens GameLogic_Client.Connect4 to javafx.fxml, javafx.graphics;
}