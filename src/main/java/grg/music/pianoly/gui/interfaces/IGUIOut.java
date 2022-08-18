package grg.music.pianoly.gui.interfaces;

import grg.music.pianoly.data.exercises.ExerciseMode;

public interface IGUIOut {

    boolean isStudentsSetup();

    void connectDevices();

    void exerciseCreated(ExerciseMode value, String name);

    void exerciseClosed(int id);
}
