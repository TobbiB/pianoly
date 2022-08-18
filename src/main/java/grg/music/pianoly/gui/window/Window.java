package grg.music.pianoly.gui.window;

import grg.music.pianoly.data.Page;
import grg.music.pianoly.gui.listeners.Listener;
import grg.music.pianoly.utils.ResourceFetcher;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public record Window(@NotNull Stage stage) {

    public Window(@NotNull Stage stage) {
        this.stage = stage;
        for (WindowSettings<?> settings : WindowSettings.getAllSettings())
            settings.applyDefaultValue(this.stage);
/*
        WindowSettings.TITLE.applyDefaultValue(this.stage);
        WindowSettings.ICONS.applyDefaultValue(this.stage);
        WindowSettings.FULLSCREEN.applyDefaultValue(this.stage);
        WindowSettings.MAXIMIZED.applyDefaultValue(this.stage);
*/
        this.stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        this.stage.setOnCloseRequest(windowEvent -> System.exit(0));

        this.stage.show();
    }

    public void setPage(@NotNull Page page) {

        FXMLLoader fxmlloader = new FXMLLoader(ResourceFetcher.getView(page.getId()));
        try {
            this.stage.setScene(new Scene(fxmlloader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        WindowSettings.FULLSCREEN.applyValue(this.stage);

        Listener.initialize(this.stage.getScene());
    }
}
