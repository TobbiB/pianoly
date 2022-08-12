package grg.music.pianoly.data.exercises;

import org.jetbrains.annotations.NotNull;

public record Exercise<T extends ExerciseMode>(T mode, String id) {

    public Exercise(@NotNull T mode, @NotNull String id) {
        this.mode = mode;
        this.id = id;
    }
}
