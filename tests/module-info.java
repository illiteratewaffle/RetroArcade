module seng300.w25.project_tests {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires seng300.w25.project;
    requires org.junit.jupiter.api;
    requires java.sql;
    requires org.checkerframework.checker.qual;
    requires org.junit.platform.commons;

    //requires seng300.w25.project;


    opens matchmaking_tests;
    opens database_tests to org.junit.platform.commons;

    opens client_main.java.AuthenticationAndProfile;
    opens client_main.java.GameLogic_Client.Checkers;
    opens client_main.java.GameLogic_Client.Connnect4;
    opens client_main.java.GameLogic_Client.TicTacToe;

    opens client_main.java.GUI_client;
    opens client_main.java.Networking_client;

    opens misc;
    opens networking_main.management;

    opens server_tests to org.junit.platform.commons;
}