package grg.music.pianoly.gui.interfaces;

import grg.music.pianoly.data.exercises.Exercise;
import grg.music.pianoly.data.exercises.ExerciseMode;
import org.jetbrains.annotations.NotNull;

public interface IGUIOut {

    boolean isStudentsSetup();

    void connectDevices();

    void startStopWorking();

    void exerciseCreated(@NotNull Exercise<ExerciseMode> exercise);

    void exerciseClosed(int id);

}
