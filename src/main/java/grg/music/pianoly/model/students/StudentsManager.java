package grg.music.pianoly.model.students;

import grg.music.pianoly.controller.DeviceOut;
import grg.music.pianoly.gui.GUI;
import grg.music.pianoly.gui.views.StudentsView;
import grg.music.pianoly.model.students.interfaces.IDeviceIn;
import grg.music.pianoly.model.students.interfaces.IDeviceOut;
import grg.music.pianoly.test.CLIDeviceIn;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StudentsManager {

    private List<Student> students;

    public StudentsManager() {
        // TODO
        this.students = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            this.students.add(new Student("test" + i, DeviceOut.getDevices()[i], CLIDeviceIn.getDevices()[i]));
    }

    public void setupStudents() {
        List<Student> students = new LinkedList<>();
        for (IDeviceOut out : DeviceOut.getDevices()) {
            out.askForInput();
            GUI.getInstance().getIn().setWaitingDeviceOut(out.getName());
            // TODO
            IDeviceIn[] deviceIns = CLIDeviceIn.getDevices();
            Thread[] threads = new Thread[deviceIns.length];
            for (int i = 0; i < deviceIns.length; i++) {
                boolean skip = false;
                for (Student student : students) {
                    if (student.getIn().equals(deviceIns[i])) {
                        skip = true;
                        break;
                    }
                }
                if (skip)
                    continue;

                int finalI = i;
                threads[i] = new Thread(() -> {
                    if (deviceIns[finalI].waitForInput()) {
                        String name = GUI.getInstance().getIn().deviceDetected(deviceIns[finalI].getName());
                        if (name != null)
                            students.add(new Student(name, out, deviceIns[finalI]));
                        for (int k = 0; k < deviceIns.length; k++) {
                            if (finalI != k && threads[k] != null)
                                threads[k].interrupt();
                        }
                    }
                });
                threads[i].start();
            }
            for (Thread thread : threads) {
                try {
                    if (thread != null)
                        thread.join();
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
        GUI.getInstance().getIn().setupFinished();
        this.students = students;
    }

    public void startStopWorking() {
        for (Student student : this.students)
            student.letWork();
    }

    public List<Student> getStudents() {
        return this.students;
    }
}
