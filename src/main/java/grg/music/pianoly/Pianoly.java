package grg.music.pianoly;

import grg.music.pianoly.gui.GUI;
import grg.music.pianoly.model.Model;
import grg.music.pianoly.test.Test;
import javafx.application.Application;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class Pianoly extends Application {

    @Override
    public void start(@NotNull Stage stage) {

        Model.getInstance();

        GUI gui = GUI.getInstance();
        gui.createWindow(stage);
        gui.setMainPage();

        //new Test();
        Locale.setDefault(Locale.ENGLISH);

    }

    public static void main(String[] args) {
        launch();
    }
}