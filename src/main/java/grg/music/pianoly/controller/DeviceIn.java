package grg.music.pianoly.controller;

import grg.music.pianoly.data.music.Note.DeviceNote;
import grg.music.pianoly.model.settings.Settings;
import grg.music.pianoly.model.students.interfaces.IDeviceIn;
import org.jetbrains.annotations.NotNull;

import javax.sound.midi.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public record DeviceIn(MidiDevice midiDevice) implements IDeviceIn {

    public DeviceIn(@NotNull MidiDevice midiDevice) {
        this.midiDevice = midiDevice;
    }


    @Override
    public boolean waitForInput() {
        AtomicBoolean b = new AtomicBoolean(false);
        try {
            this.midiDevice.open();
            this.midiDevice.getTransmitter().setReceiver(new Receiver() {
                @Override
                public void send(MidiMessage message, long timeStamp) {
                    b.set(true);
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
                Thread.currentThread().wait(Settings.CONNECT_COOLDOWN.getValue() * 1000);
            } catch (InterruptedException ignored) {
            }
        }
        try {
            this.midiDevice.getTransmitter().setReceiver(null);
            this.midiDevice.close();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        return b.get();
    }

    @Override
    public void loadDevice(@NotNull List<DeviceNote> notes, @NotNull Runnable runnable) {
        try {
            this.midiDevice.open();
            this.midiDevice.getTransmitter().setReceiver(new Receiver() {
                @Override
                public void send(MidiMessage message, long timeStamp) {
                    if (message instanceof ShortMessage sm) {
                        if (sm.getCommand() == ShortMessage.NOTE_ON || sm.getCommand() == ShortMessage.NOTE_OFF) {
                            DeviceNote note = DeviceNote.getNote(sm.getData1());
                            if (!notes.removeIf(n -> n.isKeyEqual(note))) {
                                if (sm.getCommand() == ShortMessage.NOTE_ON)
                                    notes.add(note);
                                else
                                    System.out.println("[WARNING] No note found");
                            }
                            runnable.run();
                        }
                    }
                }

                @Override
                public void close() {
                }
            });
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return this.midiDevice.getDeviceInfo().getName();
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
