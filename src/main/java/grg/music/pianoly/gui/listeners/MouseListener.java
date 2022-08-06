package grg.music.pianoly.gui.listeners;

import grg.music.pianoly.data.Page;
import grg.music.pianoly.gui.GUI;
import grg.music.pianoly.gui.views.MenuView;
import grg.music.pianoly.gui.views.PageView;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MouseListener extends Listener<MouseEvent> {

    public MouseListener() {
        super(Scene::setOnMouseClicked);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.BACK) && !(PageView.getInstance() instanceof MenuView))
            GUI.getInstance().setPage(Page.MENU);
    }
}
