package grg.music.pianoly.controller;

import grg.music.pianoly.model.students.interfaces.IDeviceOut;
import javafx.stage.Screen;

import java.util.LinkedList;
import java.util.List;

public class DeviceOut implements IDeviceOut {

    public DeviceOut(Screen screen) {
    }


    public static DeviceOut[] getDevices() {
        List<DeviceOut> devices = new LinkedList<>();
        for (Screen screen : Screen.getScreens())
            devices.add(new DeviceOut(screen));
        return devices.toArray(new DeviceOut[0]);
    }
}
