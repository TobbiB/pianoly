package grg.music.pianoly.data.music.chord;

import grg.music.pianoly.data.music.MusicElement;
import grg.music.pianoly.data.music.note.DeviceNote;
import grg.music.pianoly.data.music.note.ExerciseNote;
import grg.music.pianoly.data.music.note.Note;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Chord implements MusicElement {


    private final ChordMode mode;
    private final ExerciseNote note;

    public Chord(@NotNull ChordMode mode, @NotNull ExerciseNote note) {
        this.mode = mode;
        this.note = note;
    }

    @Override
    public boolean checkCompleted(@NotNull List<DeviceNote> notes) {
        if (notes.size() == 3) {
            List<Integer> semitones = List.of(
                    Math.abs(notes.get(0).getKey() - notes.get(1).getKey()),
                    Math.abs(notes.get(1).getKey() - notes.get(2).getKey()),
                    Math.abs(notes.get(0).getKey() - notes.get(2).getKey()));

            boolean b = false;
            for (Note note : notes) {
                if (note.isKeyEqual(this.note)) {
                    b = true;
                    break;
                }
            }
            for (int i : this.mode.getSemitones()) {
                if (!semitones.contains(i)) {
                    b = false;
                    break;
                }
            }
            return b;
        }
        return false;
    }

    @Override
    public String getDisplay() {
        return this.note.getDisplay() + this.mode.getSymbol();
    }

    public ChordMode getMode() {
        return this.mode;
    }

    public ExerciseNote getNote() {
        return this.note;
    }
}
