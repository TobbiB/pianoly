package grg.music.pianoly.data.exercises;

import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

public record Exercise<T extends ExerciseMode>(@NotNull T mode, @NotNull Color color, @NotNull Object... objects) {

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Object obj : objects)
            s.append(obj.toString());
        return s.toString();
    }
}
