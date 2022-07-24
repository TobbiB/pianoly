package grg.music.pianoly.model;

import grg.music.pianoly.model.midi.MIDI;

public final class Model {

    private static Model instance;


    private Model() {
        instance = this;
    }

    public void init() {
        // TODO
        System.out.println("model init");
    }

    public void setupMidi() {
        new MIDI();
    }


    public static Model getInstance() {
        return (instance == null) ? new Model() : instance;
    }
}
