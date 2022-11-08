package grg.music.pianoly.model;

import grg.music.pianoly.data.exercises.Exercise;
import grg.music.pianoly.model.students.StudentsManager;
import grg.music.pianoly.model.web.Web;

import java.util.LinkedList;
import java.util.List;

public final class Model {

    private static Model INSTANCE;

    private final List<Exercise<?>> exercises = new LinkedList<>();
    private final StudentsManager studentsManager = new StudentsManager();


    private Model() {
        INSTANCE = this;

        Web.initialize();
    }


    public List<Exercise<?>> getExercises() {
        return this.exercises;
    }

    public StudentsManager getStudentsManager() {
        return this.studentsManager;
    }


    public static Model getInstance() {
        return (INSTANCE == null) ? new Model() : INSTANCE;
    }
}
