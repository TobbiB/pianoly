package grg.music.pianoly.gui.listeners;

import grg.music.pianoly.data.Page;
import grg.music.pianoly.gui.GUI;
import grg.music.pianoly.gui.views.MenuView;
import grg.music.pianoly.gui.views.PageView;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyListener extends Listener<KeyEvent> {

    public KeyListener() {
        super(Scene::setOnKeyPressed);
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE && !(PageView.getInstance() instanceof MenuView))
            GUI.getInstance().setPage(Page.MENU);
    }
}
