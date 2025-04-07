module seng300.w25.project_tests {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires seng300.w25.project;
    requires org.junit.jupiter.api;
    requires java.sql;

    //requires seng300.w25.project;


    opens matchmaking_tests;

    opens client_main_tests.java.AuthenticationAndProfile;
    //opens client_main_tests.java.GameLogic_Client.Checkers;
    //opens client_main_tests.java.GameLogic_Client.Connnect4;
    //opens client_main_tests.java.GameLogic_Client.TicTacToe;

    opens client_main_tests.java.GUI_client;
    opens client_main_tests.java.Networking_client;

    opens misc;
    opens networking_main.management;

    opens server_tests to org.junit.platform.commons;
}