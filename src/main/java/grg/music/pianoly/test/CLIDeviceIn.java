package grg.music.pianoly.test;

import grg.music.pianoly.data.music.Note;
import grg.music.pianoly.model.settings.Settings;
import grg.music.pianoly.model.students.interfaces.IDeviceIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class CLIDeviceIn implements IDeviceIn {

    private static CLIDeviceIn[] devices;

    private final int id;
    private final BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1);

    public CLIDeviceIn(int id) {
        this.id = id;
    }

    public void addInput(String s) {
        try {
            this.blockingQueue.put(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean waitForInput() {
        String input = null;
        try {
            input = blockingQueue.poll(Settings.CONNECT_COOLDOWN.getValue(), TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {
        }
        return input != null;
    }

    @Override
    public void loadDevice(@NotNull List<Note> notes, @NotNull Runnable runnable) {
        //noinspection InfiniteLoopStatement
        while (true) {
            String input = null;
            try {
                input = blockingQueue.take();
            } catch (InterruptedException ignored) {
            }
            if (input != null && input.length() == 2 && Character.isDigit(input.charAt(0))
                    && Character.isDigit(input.charAt(1)) && Integer.parseInt(input) < 50) {
                Note note = Note.getNote(Integer.parseInt(input));
                if (!notes.removeIf(n -> n.isKeyEqual(note)))
                    notes.add(note);
                runnable.run();
            }
        }
    }

    @Override
    public String getName() {
        return "Device Nr " + this.id;
    }

    public static CLIDeviceIn[] getDevices() {
        if (devices == null) {
            CLIDeviceIn[] devices = new CLIDeviceIn[4];
            for (int i = 0; i < devices.length; i++)
                devices[i] = new CLIDeviceIn(i);
            CLIDeviceIn.devices = devices;
        }
        return devices;
    }
}
