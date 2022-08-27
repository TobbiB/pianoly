package grg.music.pianoly.model.students;

import grg.music.pianoly.data.music.Note.DeviceNote;
import grg.music.pianoly.gui.GUI;
import grg.music.pianoly.model.Model;
import grg.music.pianoly.model.students.interfaces.IDeviceIn;
import grg.music.pianoly.model.students.interfaces.IDeviceOut;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class Student {

    private final String name;
    private final IDeviceOut out;
    private final IDeviceIn in;
    private final List<DeviceNote> notes = new LinkedList<>();

    private Thread working;
    private int exercise;


    public Student(@NotNull String name, @NotNull IDeviceOut out, @NotNull IDeviceIn in) {
        this.name = name;
        this.out = out;
        this.in = in;

    }

    public void letWork() {
        if (this.working == null) {
            this.working = new Thread(() -> in.loadDevice(this.notes, this::checkExercise));
            this.working.start();
        }
        /*
        if (this.working.isInterrupted())
            this.working.start();
        else
            this.working.interrupt();

         */
    }

    public void checkExercise() {
        if (this.exercise < Model.getInstance().getExercises().size()
                && Model.getInstance().getExercises().get(this.exercise).musicElement().checkCompleted(this.notes)) {
            GUI.getInstance().getIn().exerciseCompleted(this.getName());
            this.exercise++;
        }
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
