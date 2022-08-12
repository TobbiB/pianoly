package grg.music.pianoly.controller;

import grg.music.pianoly.model.students.interfaces.IDeviceIn;

import javax.sound.midi.*;
import java.util.LinkedList;
import java.util.List;

public class DeviceIn implements IDeviceIn {

    private static final int TIME_OUT_SEC = 5;


    private final MidiDevice midiDevice;

    public DeviceIn(MidiDevice midiDevice) {
        this.midiDevice = midiDevice;
    }


    @Override
    public boolean waitForInput() {
        boolean[] b = {false};
        try {
            this.midiDevice.open();
            this.midiDevice.getTransmitter().setReceiver(new Receiver() {
                @Override
                public void send(MidiMessage message, long timeStamp) {
                    b[0] = true;
                    Thread.currentThread().notify();
                }

                @Override
                public void close() {
                }
            });
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        synchronized (Thread.currentThread()) {
            try {
                Thread.currentThread().wait(TIME_OUT_SEC * 1000);
            } catch (InterruptedException ignored) {
            }
        }
        try {
            this.midiDevice.getTransmitter().setReceiver(null);
            this.midiDevice.close();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        return b[0];
    }


    public MidiDevice getMidiDevice() {
        return this.midiDevice;
    }


    public static DeviceIn[] getDevices() {
        List<DeviceIn> devices = new LinkedList<>();
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : infos) {
            try {
                if (MidiSystem.getMidiDevice(info).getTransmitter() != null)
                    devices.add(new DeviceIn(MidiSystem.getMidiDevice(info)));
            } catch (MidiUnavailableException ignored) {
            }
        }
        return devices.toArray(new DeviceIn[0]);
    }
}
