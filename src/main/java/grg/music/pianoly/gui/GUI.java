package grg.music.pianoly.gui;

import grg.music.pianoly.data.Page;
import grg.music.pianoly.gui.window.Window;
import grg.music.pianoly.gui.window.WindowSettings;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public final class GUI {

    private static GUI instance;

    private Window window;


    private GUI() {
        instance = this;
    }

    public void createWindow(@NotNull Stage stage) {
        if (this.window == null)
            this.window = new Window(stage);
    }

    public <T> void modifyWindow(@NotNull WindowSettings<T> settings, T value) {
        if (this.window != null)
            settings.applyValue(this.window.stage(), value);
    }

    public void setPage(@NotNull Page page) {
        if (this.window != null)
            this.window.setPage(page);
    }


    public static GUI getInstance() {
        return (instance == null) ? new GUI() : instance;
    }
}
