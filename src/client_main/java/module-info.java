module seng300.w25.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.junit.jupiter.api;
    requires java.sql;
    //requires org.junit.jupiter.api;


    opens  GUI_client to javafx.fxml;
    exports GUI_client;
}