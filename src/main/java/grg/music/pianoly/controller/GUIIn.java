package grg.music.pianoly.controller;

import grg.music.pianoly.gui.interfaces.IGUIIn;
import grg.music.pianoly.gui.views.ConnectView;
import grg.music.pianoly.gui.views.PageView;
import grg.music.pianoly.gui.views.StudentsView;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GUIIn implements IGUIIn {

    @Override
    public void setWaitingDeviceOut(@NotNull String name) {
        if (PageView.getInstance() instanceof ConnectView connectView)
            connectView.setWaitingDeviceOut(name);
    }

    @Override
    @Nullable
    public String deviceDetected(@NotNull String name) {
        return PageView.getInstance() instanceof ConnectView connectView ? connectView.deviceDetected(name) : null;
    }

    @Override
    public void setupFinished() {
        if (PageView.getInstance() instanceof ConnectView connectView)
            connectView.setupFinished();
    }

    @Override
    public void studentsDisplayChanged(@NotNull String student, @Nullable String text, @Nullable Color color) {
        //StudentsView.changeLabel(student, text, color);
    }
}
