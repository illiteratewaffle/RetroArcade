module seng300.w25.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.junit.jupiter.api;
    requires java.sql;


    opens GUI_client to javafx.fxml;
    exports GUI_client;

    exports server.database;
    exports leaderboard;
    exports server.player;
    exports AuthenticationAndProfile;
    exports GameLogic_Client.TicTacToe;
    exports GameLogic_Client;
    exports server.management;
}