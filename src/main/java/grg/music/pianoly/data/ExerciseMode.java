package grg.music.pianoly.data;

import org.jetbrains.annotations.NotNull;

public enum ExerciseMode {

    FREE("Free"),
    NOTE("Note"),
    INTERVAL("Interval"),
    CHORD("Chord");

    ExerciseMode(@NotNull String name) {
        this.name = name;
    }

    private final String name;

    @Override
    public String toString() {
        return this.name;
    }
}
