package grg.music.pianoly.data.music;

import grg.music.pianoly.data.music.note.DeviceNote;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface MusicElement {

    boolean checkCompleted(@NotNull List<DeviceNote> notes);

    String getDisplay();

}
