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
    //requires org.junit.jupiter.api;


    opens GUI_client to javafx.fxml;
    exports GUI_client;
}