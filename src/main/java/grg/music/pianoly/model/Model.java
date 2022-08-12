package grg.music.pianoly.model;

import grg.music.pianoly.data.exercises.Exercise;
import grg.music.pianoly.model.students.StudentsManager;

import java.util.LinkedList;
import java.util.List;

public final class Model {

    private static Model instance;

    private final List<Exercise<?>> exercises = new LinkedList<>();
    private final StudentsManager studentsManager = new StudentsManager();


    private Model() {
        instance = this;
    }


    public List<Exercise<?>> getExercises() {
        return this.exercises;
    }

    public StudentsManager getStudentsManager() {
        return this.studentsManager;
    }


    public static Model getInstance() {
        return (instance == null) ? new Model() : instance;
    }
}
