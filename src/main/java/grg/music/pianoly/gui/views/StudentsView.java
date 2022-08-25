package grg.music.pianoly.gui.views;

import grg.music.pianoly.data.exercises.Exercise;
import grg.music.pianoly.utils.CollectionUtils;
import grg.music.pianoly.utils.FXUtils;
import grg.music.pianoly.utils.ResourceFetcher;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class StudentsView {

    record StudentCell(@NotNull String name, int column, int row) {
    }


    private static final int WIDTH = 150, HEIGHT = 75;

    private static final Set<StudentsView> STUDENTS_VIEWS = new HashSet<>();

    private static final List<StudentCell> STUDENTS = new LinkedList<>();
    private static final List<Exercise<?>> EXERCISES = new LinkedList<>();
    private static int[] EXERCISE_INDICES;
    private static int COLUMNS, ROWS;

    private final List<Label> labels = new ArrayList<>(STUDENTS.size());
    private Exercise<?> exercise;


    @FXML
    private GridPane grid;

    @FXML
    private void initialize() {
        STUDENTS_VIEWS.add(this);
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                Label label = new Label();
                label.setPrefSize(WIDTH, HEIGHT);
                label.setAlignment(Pos.CENTER);
                label.setDisable(true);
                FXUtils.setBorderColor(label, Color.BLACK);
                this.grid.add(label, c, r);
            }
        }

        for (StudentCell student : STUDENTS) {
            this.grid.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == student.column()
                    && GridPane.getRowIndex(node) == student.row());

            Label label = new Label(student.name());
            label.setPrefSize(WIDTH, HEIGHT);
            label.setAlignment(Pos.CENTER);
            FXUtils.setBorderColor(label, Color.BLACK);
            this.grid.add(label, student.column(), student.row());
            this.labels.add(label);

            this.changeLabel(student.name());
        }
    }

    public void onClose() {
        STUDENTS_VIEWS.remove(this);
    }


    private void changeLabel(@NotNull String student) {
        int i = STUDENTS.indexOf(CollectionUtils.find(STUDENTS, info -> student.equals(info.name())));
        if (i >= 0 && i < EXERCISE_INDICES.length && EXERCISE_INDICES[i] <= EXERCISES.size() + 1
                && EXERCISE_INDICES[i] > 0 && !EXERCISES.isEmpty()) {
            Label label = this.labels.get(i);
            Exercise<?> ex = (EXERCISE_INDICES[i] <= EXERCISES.size()) ? EXERCISES.get(EXERCISE_INDICES[i] - 1) : null;
            if (this.exercise == null) {
                Platform.runLater(() -> {
                    label.setText(student + " (" + (EXERCISE_INDICES[i] + ((ex != null) ? 0 : -1) + ")"
                            + "\n" + ((ex != null) ?  ex.id() : "Finished")));
                    FXUtils.setBorderColor(label, ex != null ? ex.color() : Color.GREEN);
                });
            }
            else
                Platform.runLater(() -> {
                    label.setText(student);
                    if (EXERCISES.indexOf(this.exercise) < EXERCISE_INDICES[i] - 1)
                        FXUtils.setBorderColor(label, Color.GREEN);
                    else if (EXERCISES.indexOf(this.exercise) > EXERCISE_INDICES[i] - 1)
                        FXUtils.setBorderColor(label, Color.RED);
                    else
                        FXUtils.setBorderColor(label, Color.BLACK);
                });
        }
    }

    public void updateLabels() {
        StudentsView view = CollectionUtils.find(STUDENTS_VIEWS, studentsView -> studentsView.exercise == null);
        for (StudentCell student : STUDENTS) {
            this.changeLabel(student.name());
            if (view != null)
                view.changeLabel(student.name());
        }
    }

    public GridPane getGrid() {
        return this.grid;
    }


    public static void exerciseCompleted(@NotNull String student) {
        int i = STUDENTS.indexOf(CollectionUtils.find(STUDENTS, info -> student.equals(info.name())));
        if (i >=  0 && i < EXERCISE_INDICES.length && EXERCISE_INDICES[i] <= EXERCISES.size()) {
            EXERCISE_INDICES[i]++;
            for (StudentsView view : STUDENTS_VIEWS)
                view.changeLabel(student);
        }
    }

    public static void close() {
        STUDENTS_VIEWS.removeIf(view -> view.exercise == null);
    }

    public static List<Exercise<?>> getExercises() {
        return EXERCISES;
    }

    public static void setGridData(@NotNull List<StudentCell> students, int columns, int rows) {
        STUDENTS.clear();
        STUDENTS.addAll(students);
        EXERCISE_INDICES = new int[STUDENTS.size()];
        Arrays.fill(EXERCISE_INDICES, 1);
        COLUMNS = columns;
        ROWS = rows;
    }

    @Nullable
    public static StudentsView load(@Nullable Exercise<?> exercise) {
        URL url = ResourceFetcher.getView("students");
        if (url != null && exercise != null) {
            try {
                FXMLLoader loader = new FXMLLoader(url);
                loader.load();
                if (loader.getController() instanceof StudentsView studentsView) {
                    studentsView.exercise = exercise;
                    return studentsView;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
