package grg.music.pianoly.model;

import grg.music.pianoly.model.midi.MIDI;
import org.jetbrains.annotations.NotNull;

public final class Model {

    private static Model instance;


    private Model() {
        instance = this;
    }

    public void setupMidi() {
        new MIDI();
    }


    @NotNull
    public static Model getInstance() {
        return (instance == null) ? new Model() : instance;
    }
}
