package grg.music.pianoly.model.students;

import grg.music.pianoly.data.music.Note;
import grg.music.pianoly.model.students.interfaces.IDeviceIn;
import grg.music.pianoly.model.students.interfaces.IDeviceOut;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class Student {

    private final String name;
    private final IDeviceOut out;
    private final IDeviceIn in;

    private final List<Note> notes = new LinkedList<>();

    public Student(@NotNull String name, @NotNull IDeviceOut out, @NotNull IDeviceIn in) {
        this.name = name;
        this.out = out;
        this.in = in;

    }

    public void letWork() {
         new Thread(() -> in.loadDevice(this.notes,
                 () -> System.out.println(this.name + ": " + this.notes.size()))).start();
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
