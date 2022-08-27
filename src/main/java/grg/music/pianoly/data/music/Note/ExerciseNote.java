package grg.music.pianoly.data.music.Note;

import grg.music.pianoly.data.music.MusicElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExerciseNote extends Note implements MusicElement {

    private final boolean up;

    private ExerciseNote(int key, boolean up) {
        super(key);
        this.up = up;
    }


    @Override
    public boolean checkCompleted(@NotNull List<DeviceNote> notes) {
        return notes.size() == 1 && notes.get(0).isKeyEqual(this);
    }

    @Override
    public String getDisplay() {
        return (this.up) ? NAMES_UP[super.key] : NAMES_DOWN[super.key];
    }


    @Nullable
    public static ExerciseNote getExerciseNote(int key, boolean up) {
        if (key >= 0 && key < 12)
            return new ExerciseNote(key, up);
        return null;
    }

    @Nullable
    public static ExerciseNote getByName(@Nullable String name) {
        for (int i = 0; i < NAMES_UP.length; i++) {
            if (NAMES_UP[i].equals(name))
                return getExerciseNote(i, true);
        }
        for (int i = 0; i < NAMES_DOWN.length; i++) {
            if (NAMES_DOWN[i].equals(name))
                return getExerciseNote(i, false);
        }
        return null;
    }

}
