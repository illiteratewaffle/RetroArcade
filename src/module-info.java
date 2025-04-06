//module seng300.w25.project {
//    requires javafx.controls;
//    requires javafx.fxml;
//    requires javafx.web;
//
//    requires org.controlsfx.controls;
//    requires com.dlsc.formsfx;
//    requires net.synedra.validatorfx;
//    requires org.kordamp.ikonli.javafx;
//    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
//    requires com.almasb.fxgl.all;
//    requires java.desktop;
//
//    opens GUI_client to javafx.fxml;
////    exports GUI_client;
//    opens client_main.java.GameLogic_Client.Connect4 to javafx.fxml;
//}

module seng300.w25.project {
    requires javafx.controls;
    requires javafx.fxml;

    opens client_main.java.GameLogic_Client.Connect4 to javafx.fxml;
    opens client_main.java.GUI_client to javafx.fxml;

    exports client_main.java.GameLogic_Client.Connect4;
//    exports client_main.java.GUI_client;
}