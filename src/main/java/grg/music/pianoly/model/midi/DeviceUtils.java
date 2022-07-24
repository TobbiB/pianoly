package grg.music.pianoly.model.midi;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;

public final class DeviceUtils {

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
                Thread.currentThread().wait(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return b[0];
    }
}
