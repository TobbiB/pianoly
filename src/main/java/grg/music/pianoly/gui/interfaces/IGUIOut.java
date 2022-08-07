package grg.music.pianoly.gui.interfaces;

import grg.music.pianoly.data.exercises.Exercise;
import org.jetbrains.annotations.NotNull;

public interface IGUIOut {

    void connectDevices();

    void exerciseCreated(@NotNull Exercise exercise);
}
