package grg.music.pianoly.model.midi;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import java.util.LinkedList;
import java.util.List;

public final class MIDI {

    private MidiDevice.Info[] available = MidiSystem.getMidiDeviceInfo();
    private final List<MidiDevice> devices = new LinkedList<>();

    public MIDI() {
        this.connectDevices();
    }

    public void updateAvailable() {
        this.available = MidiSystem.getMidiDeviceInfo();
    }

    public void connectDevices() {
        this.updateAvailable();
        for (MidiDevice.Info info : this.available) {
            try {
                MidiDevice device = MidiSystem.getMidiDevice(info);
                if (device.getTransmitter() != null && !this.devices.contains(device) && DeviceUtils.verify(device))
                    this.devices.add(device);
            } catch (MidiUnavailableException ignored) {
            }
        }
    }
}
