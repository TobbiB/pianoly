package grg.music.pianoly.model.students;

import grg.music.pianoly.data.music.Note.DeviceNote;
import grg.music.pianoly.model.students.interfaces.IDeviceIn;
import grg.music.pianoly.model.students.interfaces.IDeviceOut;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class Student {

    private final String name;
    private final IDeviceOut out;
    private final IDeviceIn in;
    private final ExerciseManager exerciseManager;
    private final List<DeviceNote> notes = new LinkedList<>();

    private Thread working;


    public Student(@NotNull String name, @NotNull IDeviceOut out, @NotNull IDeviceIn in) {
        this.name = name;
        this.out = out;
        this.in = in;
        this.exerciseManager = new ExerciseManager(this.name, this.notes);

    }

    public void letWork() {
        if (this.working == null) {
            this.working = new Thread(() -> in.loadDevice(this.notes, this.exerciseManager::checkExercise));
            this.working.start();
        }
        /*
        if (this.working.isInterrupted())
            this.working.start();
        else
            this.working.interrupt();

         */
    }


    public String getName() {
        return this.name;
    }

    public IDeviceOut getOut() {
        return this.out;
    }

    public IDeviceIn getIn() {
        return this.in;
    }
}
