package grg.music.pianoly.model.students;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import grg.music.pianoly.controller.DeviceIn;
import grg.music.pianoly.controller.DeviceOut;
import grg.music.pianoly.gui.GUI;
import grg.music.pianoly.model.Model;
import grg.music.pianoly.model.students.interfaces.IDeviceIn;
import grg.music.pianoly.model.students.interfaces.IDeviceOut;
import grg.music.pianoly.model.web.Web;
import grg.music.pianoly.test.CLIDeviceIn;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

public class StudentsManager {

    private List<Student> students;

    private boolean started = false;

    public StudentsManager() {
    }

    public void setupStudents() {
        List<Student> students = new LinkedList<>();
        for (IDeviceOut out : DeviceOut.getDevices()) {
            out.askForInput();
            GUI.getInstance().getIn().setWaitingDeviceOut(out.getName());
            // TODO
            IDeviceIn[] deviceIns = DeviceIn.getDevices();
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
                int finalI1 = i;
                threads[i] = new Thread(() -> {
                    if (deviceIns[finalI].waitForInput()) {
                        String name = GUI.getInstance().getIn().deviceDetected(deviceIns[finalI].getName());
                        if (name != null)
                            students.add(new Student(name, out, deviceIns[finalI], finalI1));
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
        if (!this.started) {
            for (Student student : this.students) {
                student.letWork();
            }
            this.started = true;
            for (int i=0; i<4; i++) {
                Web.setContext(i, new HttpHandler() {
                    @Override
                    public void handle(HttpExchange exchange) throws IOException {
                        exchange.sendResponseHeaders(200, 0);
                        OutputStream os = exchange.getResponseBody();
                        String s = "Neue Aufgabe: " + Model.getInstance().getExercises().get(0).getDisplay();
                        os.write(s.getBytes(), 0, s.length());
                        os.close();
                    }
                });
            }
        }
    }

    public List<Student> getStudents() {
        return this.students;
    }
}
