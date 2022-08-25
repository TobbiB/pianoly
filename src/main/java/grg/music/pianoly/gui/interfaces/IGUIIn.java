package grg.music.pianoly.gui.interfaces;

import org.jetbrains.annotations.NotNull;

public interface IGUIIn {

    void setWaitingDeviceOut(@NotNull String name);

    String deviceDetected(@NotNull String name);

    void setupFinished();

    void exerciseCompleted(@NotNull String student);
}
