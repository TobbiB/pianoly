package grg.music.pianoly.controller;

import grg.music.pianoly.model.students.interfaces.IDeviceOut;
import javafx.collections.ObservableList;
import javafx.stage.Screen;

import java.util.LinkedList;
import java.util.List;

public class DeviceOut implements IDeviceOut {

    private final int id;

    public DeviceOut(int id, Screen screen) {
        this.id = id;
    }


    @Override
    public void askForInput() {
    }

    @Override
    public String getName() {
        return "Output Device " + this.id;
    }


    public static IDeviceOut[] getDevices() {
        List<DeviceOut> devices = new LinkedList<>();
        ObservableList<Screen> screens = Screen.getScreens();
        for (int i = 0; i < 4; i++) {
            devices.add(new DeviceOut(i, screens.get(0)));
        }
        return devices.toArray(new DeviceOut[0]);
    }
}
