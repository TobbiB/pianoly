package grg.music.pianoly.model.students;

import grg.music.pianoly.controller.DeviceIn;
import grg.music.pianoly.controller.DeviceOut;
import grg.music.pianoly.model.students.interfaces.IDeviceIn;
import grg.music.pianoly.model.students.interfaces.IDeviceOut;

import java.util.LinkedList;
import java.util.List;

public class StudentsManager {

    private final List<Student> students = new LinkedList<>();

    public StudentsManager() {
    }

    public void setupStudents() {
        for (IDeviceOut out : DeviceOut.getDevices()) {
            for (IDeviceIn in : DeviceIn.getDevices()) {
                if (in.waitForInput()) {
                    this.students.add(new Student(out, in));
                    break;
                }
            }
        }
    }

    public List<Student> getStudents() {
        return this.students;
    }
}
