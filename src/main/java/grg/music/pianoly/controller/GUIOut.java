package grg.music.pianoly.controller;

import grg.music.pianoly.gui.interfaces.IGUIOut;
import grg.music.pianoly.model.Model;

public class GUIOut implements IGUIOut {

    @Override
    public void connectDevices() {
        Model.getInstance().setupMidi();
    }
}
