package grg.music.pianoly.data.exercises;

import org.jetbrains.annotations.NotNull;

public record Exercise(String id) {

    public Exercise(@NotNull String id) {
        this.id = id;
    }
}
