package grg.music.pianoly.model.midi;

import grg.music.pianoly.data.music.Note;
import org.jetbrains.annotations.NotNull;

import javax.sound.midi.*;

import static javax.sound.midi.ShortMessage.NOTE_OFF;
import static javax.sound.midi.ShortMessage.NOTE_ON;

public class Device {

    private static final int MAX_NOTES = 128;

    private final MidiDevice midi;
    private final int id;
    private final Note[] notes = new Note[MAX_NOTES];


    public Device(@NotNull MidiDevice midiDevice, int id) {
        this.midi = midiDevice;
        this.id = id;

        for (int i = 0; i < this.notes.length; i++)
            this.notes[i] = new Note(i, false);

        try {
            this.midi.getTransmitter().setReceiver(new Receiver() {
                @Override
                public void send(MidiMessage message, long timeStamp) {
                    if (message instanceof ShortMessage sm) {

                        if (sm.getCommand() == NOTE_ON || sm.getCommand() == NOTE_OFF)
                            notes[sm.getData1()].setOn(!notes[sm.getData1()].isOn());
                    }
                }

                @Override
                public void close() {
                    System.out.println("CLOSE");
                }
            });
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    public MidiDevice getMidiDevice() {
        return this.midi;
    }

    public int getId() {
        return this.id;
    }
}
