package grg.music.pianoly.model.midi;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;

public final class DeviceUtils {

    public static boolean verify(MidiDevice device) {
        final boolean[] b = new boolean[1];
        try {
            device.open();
            device.getTransmitter().setReceiver(new Receiver() {
                @Override
                public void send(MidiMessage message, long timeStamp) {
                    Thread.currentThread().notify();
                    b[0] = true;
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
                Thread.currentThread().wait(5000);
                b[0] = false;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return b[0];
    }
}
