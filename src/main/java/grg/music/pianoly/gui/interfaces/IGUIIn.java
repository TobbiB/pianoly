package grg.music.pianoly.gui.interfaces;

import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IGUIIn {

    void setWaitingDeviceOut(@NotNull String name);

    String deviceDetected(@NotNull String name);

    void setupFinished();

    void studentsDisplayChanged(@NotNull String student, @Nullable String text, @Nullable Color color);
}
