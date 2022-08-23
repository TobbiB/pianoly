package grg.music.pianoly.test;

import grg.music.pianoly.model.settings.Settings;
import grg.music.pianoly.model.students.interfaces.IDeviceIn;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class CLIDeviceIn implements IDeviceIn {

    private static CLIDeviceIn[] devices;

    private final int id;
    private final BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();

    public CLIDeviceIn(int id) {
        this.id = id;
    }

    public void addInput(String s) {
        this.blockingQueue.add(s);

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
