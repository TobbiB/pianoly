package grg.music.pianoly.gui.listeners;

import grg.music.pianoly.gui.GUI;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MouseListener extends Listener<MouseEvent> {

    public MouseListener() {
        super(Scene::setOnMouseClicked);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.BACK))
            GUI.getInstance().setMainPage();
    }
}
