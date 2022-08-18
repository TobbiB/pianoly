package grg.music.pianoly.model.students;

import grg.music.pianoly.model.students.interfaces.IDeviceIn;
import grg.music.pianoly.model.students.interfaces.IDeviceOut;

public class Student {

    private final String name;
    private final IDeviceOut out;
    private final IDeviceIn in;

    public Student(String name, IDeviceOut out, IDeviceIn in) {
        this.name = name;
        this.out = out;
        this.in = in;
    }

    public IDeviceIn getIn() {
        return this.in;
    }
}
