package grg.music.pianoly.data.music;

import grg.music.pianoly.data.music.Note.DeviceNote;

import java.util.List;

public interface MusicElement {

    boolean checkCompleted(List<DeviceNote> notes);

    String getDisplay();

}
