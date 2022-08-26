package grg.music.pianoly.data.music.Note;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class KeyboardNote extends Note {

    private static final KeyboardNote[] NOTES = new KeyboardNote[50];

    private boolean playing = false;

    private KeyboardNote(int key) {
        super(key);
    }

    @NotNull
    public ExerciseNote getAsExerciseNote(boolean up) {
        return Objects.requireNonNull(ExerciseNote.getExerciseNote(super.key % 12, up));
    }

    public boolean isPlaying() {
        return this.playing;
    }
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    @Nullable
    public static KeyboardNote getNote(int key) {
        if (NOTES[0] == null) {
            for (int i=0; i<NOTES.length; i++)
                NOTES[i] = new KeyboardNote(i);
        }
        if (key >= 0 && key < NOTES.length)
            return NOTES[key];
        return null;
    }
}
