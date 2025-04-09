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
    requires org.junit.jupiter.api;
    requires javafx.media;
    requires java.sql;
    requires org.postgresql.jdbc;
    //requires org.junit.jupiter.api;


    //opens GUI_client to javafx.fxml;

    exports database;
    exports player;
    exports AuthenticationAndProfile;
    exports GameLogic_Client.Checkers;
    exports GameLogic_Client.TicTacToe;
    exports GameLogic_Client;
    exports management;
    exports launcher;
    exports session;
    exports matchmaking;
    exports GameLogic_Client.Connect4;

}