package grg.music.pianoly.controller;

import grg.music.pianoly.data.exercises.Exercise;
import grg.music.pianoly.data.exercises.ExerciseMode;
import grg.music.pianoly.gui.interfaces.IGUIOut;
import grg.music.pianoly.model.Model;

public class GUIOut implements IGUIOut {

    private final Model model = Model.getInstance();

    @Override
    public void connectDevices() {
        model.getStudentsManager().setupStudents();
    }

    @Override
    public void exerciseCreated(ExerciseMode value, String name) {
        Model.getInstance().getExercises().add(new Exercise<>(value, name));
    }

    @Override
    public void exerciseClosed(int id) {
        Model.getInstance().getExercises().remove(id);
    }
}
