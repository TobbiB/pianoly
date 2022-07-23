package grg.music.pianoly;

import grg.music.pianoly.data.Page;
import grg.music.pianoly.gui.GUI;
import grg.music.pianoly.model.Model;
import javafx.application.Application;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class Pianoly extends Application {

    @Override
    public void start(@NotNull Stage stage) {

        Model model = Model.getInstance();
        model.init();

        GUI gui = GUI.getInstance();
        gui.createWindow(stage);
        gui.setPage(Page.MENU);
    }

    public static void main(String[] args) {
        launch();
    }
}