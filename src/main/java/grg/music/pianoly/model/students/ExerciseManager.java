package grg.music.pianoly.model.students;

import grg.music.pianoly.data.music.Note.DeviceNote;
import grg.music.pianoly.gui.GUI;
import grg.music.pianoly.model.Model;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExerciseManager {

    private final String student;
    private final List<DeviceNote> notes;

    private int exercise = 0;

    public ExerciseManager(@NotNull String student, @NotNull List<DeviceNote> notes) {
        this.student = student;
        this.notes = notes;
    }

    public void checkExercise() {
        if (this.exercise < Model.getInstance().getExercises().size()
                && Model.getInstance().getExercises().get(this.exercise).musicElement().checkCompleted(this.notes)) {
            GUI.getInstance().getIn().exerciseCompleted(this.student);
            this.exercise++;
        }
    }
}
