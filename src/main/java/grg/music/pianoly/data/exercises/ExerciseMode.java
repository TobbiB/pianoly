package grg.music.pianoly.data.exercises;

import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public enum ExerciseMode {

    //FREE("Free"),
    NOTE("Note", new Image(new ByteArrayInputStream("♪".getBytes(StandardCharsets.UTF_8)))),
    INTERVAL("Interval", new Image(new ByteArrayInputStream("♫".getBytes(StandardCharsets.UTF_8)))),
    CHORD("Chord", new Image(new ByteArrayInputStream("♬".getBytes(StandardCharsets.UTF_8))));

    ExerciseMode(@NotNull String name, @NotNull Image image) {
        this.name = name;
        this.image = image;
    }

    private final String name;
    private final Image image;

    public String getName() {
        return this.name;
    }

    public Image getImage() {
        return this.image;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
