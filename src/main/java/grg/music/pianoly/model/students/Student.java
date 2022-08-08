package grg.music.pianoly.model.students;

import grg.music.pianoly.controller.DeviceIn;
import grg.music.pianoly.controller.DeviceOut;
import grg.music.pianoly.model.students.interfaces.IDeviceIn;
import grg.music.pianoly.model.students.interfaces.IDeviceOut;

public class Student {

    private final IDeviceOut out = new DeviceOut();
    private final IDeviceIn in = new DeviceIn();

    public Student() {

    }
}
