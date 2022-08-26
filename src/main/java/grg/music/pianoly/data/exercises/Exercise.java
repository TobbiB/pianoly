package grg.music.pianoly.data.exercises;

import grg.music.pianoly.data.music.MusicElement;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

public record Exercise<T extends MusicElement>(@NotNull T musicElement, @NotNull Color color) {

    public String getDisplay() {
        return musicElement.getDisplay();
    }
}
