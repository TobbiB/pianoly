package grg.music.pianoly.data.music.Chord;

import grg.music.pianoly.data.music.MusicElement;
import grg.music.pianoly.data.music.Note.DeviceNote;
import grg.music.pianoly.data.music.Note.ExerciseNote;

import java.util.List;

public class Chord implements MusicElement {


    private final ChordMode mode;
    private final ExerciseNote note;

    public Chord(ChordMode mode, ExerciseNote note) {
        this.mode = mode;
        this.note = note;
    }

    @Override
    public boolean checkCompleted(List<DeviceNote> notes) {
        if (notes.size() == 3) {
            List<Integer> semitones = List.of(
                    Math.abs(notes.get(0).getKey() - notes.get(1).getKey()),
                    Math.abs(notes.get(1).getKey() - notes.get(2).getKey()),
                    Math.abs(notes.get(0).getKey() - notes.get(2).getKey()));

            boolean b = true;
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
