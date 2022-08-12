package grg.music.pianoly.model.students;

import grg.music.pianoly.model.students.interfaces.IDeviceIn;
import grg.music.pianoly.model.students.interfaces.IDeviceOut;

public class Student {

    private final IDeviceOut out;
    private final IDeviceIn in;

    public Student(IDeviceOut out, IDeviceIn in) {
        this.out = out;
        this.in = in;
    }
}
