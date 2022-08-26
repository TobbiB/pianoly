package grg.music.pianoly.model.students.interfaces;

import grg.music.pianoly.data.music.Note.DeviceNote;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IDeviceIn {

    boolean waitForInput();

    void loadDevice(@NotNull List<DeviceNote> notes, @NotNull Runnable runnable);

    String getName();
}
