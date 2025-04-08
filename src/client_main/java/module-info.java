module seng300.w25.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires annotations;
    requires javafx.media;
    requires java.sql;
    requires org.postgresql.jdbc;
    //requires org.junit.jupiter.api;


    opens GUI_client to javafx.fxml;
    exports GUI_client;
    /*exports GameLogic_Client;

    //opens GUI_client to javafx.fxml, javafx.graphics;
    exports GameLogic_Client.Connect4;
    opens GameLogic_Client.Connect4 to javafx.fxml, javafx.graphics; */
}