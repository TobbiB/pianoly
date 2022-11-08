package grg.music.pianoly.model.students;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import grg.music.pianoly.data.music.note.DeviceNote;
import grg.music.pianoly.gui.GUI;
import grg.music.pianoly.model.Model;
import grg.music.pianoly.model.students.interfaces.IDeviceIn;
import grg.music.pianoly.model.students.interfaces.IDeviceOut;
import grg.music.pianoly.model.web.Web;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

public class Student {

    private final String name;
    private final IDeviceOut out;
    private final IDeviceIn in;
    private final List<DeviceNote> notes = new LinkedList<>();
    private final int id;

    private Thread working;
    private int exercise;


    public Student(@NotNull String name, @NotNull IDeviceOut out, @NotNull IDeviceIn in, int id) {
        this.name = name;
        this.out = out;
        this.in = in;
        this.id = id;
    }

    public void checkExercise() {
        if (this.exercise < Model.getInstance().getExercises().size()
                && Model.getInstance().getExercises().get(this.exercise).musicElement().checkCompleted(this.notes)) {
            GUI.getInstance().getIn().exerciseCompleted(this.getName());
            this.exercise++;
            Web.setContext(this.id, new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    exchange.sendResponseHeaders(200, 0);
                    OutputStream os = exchange.getResponseBody();
                    String s = "Neue Aufgabe: " + Model.getInstance().getExercises().get(exercise).getDisplay();
                    os.write(s.getBytes(), 0, s.length());
                    os.close();
                }
            });
        }
    }

    public void letWork() {
        this.working = new Thread(() -> in.loadDevice(this.notes, this::checkExercise));
        this.working.start();
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
