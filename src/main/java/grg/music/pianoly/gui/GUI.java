package grg.music.pianoly.gui;

import grg.music.pianoly.controller.GUIIn;
import grg.music.pianoly.controller.GUIOut;
import grg.music.pianoly.data.Page;
import grg.music.pianoly.gui.interfaces.IGUIIn;
import grg.music.pianoly.gui.interfaces.IGUIOut;
import grg.music.pianoly.gui.window.Window;
import grg.music.pianoly.gui.window.WindowSettings;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public final class GUI {

    private static GUI instance;

    private final IGUIOut out = new GUIOut();
    private final IGUIIn in = new GUIIn();

    private Window window;


    private GUI() {
        instance = this;
    }

    public void createWindow(@NotNull Stage stage) {
        if (this.window == null)
            this.window = new Window(stage);
    }

    public <T> void modifyWindow(@NotNull WindowSettings<T> settings, @NotNull T value) {
        if (this.window != null)
            settings.applyValue(this.window.stage(), value);
    }

    public void setPage(@NotNull Page page) {
        if (this.window != null)
            this.window.setPage(page);
    }

    public void setMainPage() {
        this.setPage(Page.MENU);
    }


    public IGUIOut getOut() {
        return this.out;
    }

    public IGUIIn getIn() {
        return this.in;
    }


    @NotNull
    public static GUI getInstance() {
        return (instance == null) ? new GUI() : instance;
    }
}
