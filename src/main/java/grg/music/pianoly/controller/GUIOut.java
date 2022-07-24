package grg.music.pianoly.controller;

import grg.music.pianoly.gui.interfaces.IGUIOut;
import grg.music.pianoly.model.Model;

public class GUIOut implements IGUIOut {

    private final Model model = Model.getInstance();

    @Override
    public void connectDevices() {
        model.setupMidi();
    }
}
