/**
 * Root module of Pianoly
 */
module grg.music.pianoly {
    requires java.desktop;
    requires javafx.fxml;
    requires javafx.controls;
    requires org.jetbrains.annotations;
    requires jdk.httpserver;


    exports grg.music.pianoly;
    opens grg.music.pianoly to javafx.fxml;
    exports grg.music.pianoly.gui.views;
    opens grg.music.pianoly.gui.views to javafx.fxml;
    exports grg.music.pianoly.gui;
    opens grg.music.pianoly.gui to javafx.fxml;
}
