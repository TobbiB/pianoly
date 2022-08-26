package grg.music.pianoly.controller;

import grg.music.pianoly.data.exercises.Exercise;
import grg.music.pianoly.data.exercises.ExerciseMode;
import grg.music.pianoly.gui.interfaces.IGUIOut;
import grg.music.pianoly.model.Model;
import org.jetbrains.annotations.NotNull;

public class GUIOut implements IGUIOut {

    private final Model model = Model.getInstance();

    @Override
    public boolean isStudentsSetup() {
        return this.model.getStudentsManager().getStudents() != null
                && !this.model.getStudentsManager().getStudents().isEmpty();
    }

    @Override
    public void connectDevices() {
        this.model.getStudentsManager().setupStudents();
    }

    @Override
    public void startStopWorking() {
        this.model.getStudentsManager().startStopWorking();
    }

    @Override
    public void exerciseCreated(@NotNull Exercise<?> exercise) {
        Model.getInstance().getExercises().add(exercise);
    }
}
