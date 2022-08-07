package grg.music.pianoly.controller;

import grg.music.pianoly.data.exercises.Exercise;
import grg.music.pianoly.gui.interfaces.IGUIOut;
import grg.music.pianoly.model.Model;
import org.jetbrains.annotations.NotNull;

public class GUIOut implements IGUIOut {

    private final Model model = Model.getInstance();

    @Override
    public void connectDevices() {
        model.setupMidi();
    }

    @Override
    public void exerciseCreated(@NotNull Exercise exercise) {
        System.out.println(exercise.id());
    }
}
