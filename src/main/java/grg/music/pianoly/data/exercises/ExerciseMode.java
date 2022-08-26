package grg.music.pianoly.data.exercises;

import grg.music.pianoly.data.music.Chord.Chord;
import grg.music.pianoly.data.music.Chord.ChordMode;
import grg.music.pianoly.data.music.Interval.Interval;
import grg.music.pianoly.data.music.MusicElement;
import grg.music.pianoly.data.music.Note.ExerciseNote;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

public enum ExerciseMode {

    // TODO
    //FREE("Free"),
    NOTE("Note", new Image(new ByteArrayInputStream("♪".getBytes(StandardCharsets.UTF_8))),
            objects -> (objects[0] instanceof String s) ? ExerciseNote.getByName(s) : null),
    INTERVAL("Interval", new Image(new ByteArrayInputStream("♫".getBytes(StandardCharsets.UTF_8))),
            objects -> objects[0] instanceof Interval interval ? interval : null),
    CHORD("Chord", new Image(new ByteArrayInputStream("♬".getBytes(StandardCharsets.UTF_8))), objects -> {
        if (objects.length == 2 && objects[0] instanceof ChordMode mode && objects[1] instanceof String s) {
            ExerciseNote note = ExerciseNote.getByName(s);
            if (note != null)
                return new Chord(mode, note);
        }
        return null;
    });

    ExerciseMode(@NotNull String name, @NotNull Image image, @NotNull Function<Object[], MusicElement> function) {
        this.name = name;
        this.image = image;
        this.function = function;
    }

    private final String name;
    private final Image image;
    private final Function<Object[], MusicElement> function;


    @Nullable
    public MusicElement createMusicElement(@NotNull Object... objects) {
        return (objects.length >= 1) ? function.apply(objects) : null;
    }


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
