package grg.music.pianoly.controller;

import grg.music.pianoly.gui.interfaces.IGUIIn;
import grg.music.pianoly.gui.views.ConnectView;
import grg.music.pianoly.gui.views.PageView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GUIIn implements IGUIIn {

    @Override
    public void setWaitingDeviceOut(String name) {
        if (PageView.getInstance() instanceof ConnectView connectView)
            connectView.setWaitingDeviceOut(name);
    }

    @Override
    @Nullable
    public String deviceDetected(String name) {
        if (PageView.getInstance() instanceof ConnectView connectView)
            return connectView.deviceDetected(name);
        return null;
    }

    @Override
    public void setupFinished() {
        if (PageView.getInstance() instanceof ConnectView connectView)
            connectView.setupFinished();
    }
}
