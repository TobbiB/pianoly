package grg.music.pianoly.model.midi;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;

public final class DeviceUtils {

    private static final int TIME_OUT_SEC = 5;

    public static boolean verify(MidiDevice device) {
        boolean[] b = {false};
        try {
            device.open();
            device.getTransmitter().setReceiver(new Receiver() {
                @Override
                public void send(MidiMessage message, long timeStamp) {
                    b[0] = true;
                    Thread.currentThread().notify();
                }

                @Override
                public void close() {
                    System.out.println("CLOSE");
                }
            });
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }
        synchronized (Thread.currentThread()) {
            try {
                Thread.currentThread().wait(TIME_OUT_SEC * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            device.getTransmitter().setReceiver(null);
            device.close();
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }
        return b[0];
    }
}
