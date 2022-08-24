package grg.music.pianoly.gui.views;

import grg.music.pianoly.utils.CollectionUtils;
import grg.music.pianoly.utils.FXUtils;
import grg.music.pianoly.utils.ResourceFetcher;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StudentsView {

    public record StudentInfo(@NotNull String name, int column, int row) {
    }


    private static final int WIDTH = 150, HEIGHT = 75;

    private static int COLUMNS, ROWS;
    private static final List<StudentInfo> STUDENTS = new LinkedList<>();

    private final List<Label> labels = new ArrayList<>(STUDENTS.size());


    @FXML
    private GridPane grid;

    @FXML
    private void initialize() {
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

        for (StudentInfo student : STUDENTS) {
            this.grid.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == student.column()
                    && GridPane.getRowIndex(node) == student.row());

            Label label = new Label(student.name());
            label.setPrefSize(WIDTH, HEIGHT);
            label.setAlignment(Pos.CENTER);
            FXUtils.setBorderColor(label, Color.BLUE);
            this.grid.add(label, student.column(), student.row());
            this.labels.add(label);
        }
    }


    public void changeLabel(@NotNull String student, @Nullable String text, @Nullable Color color) {
        Label label = this.labels.get(STUDENTS.indexOf(CollectionUtils.find(STUDENTS, info -> student.equals(info.name()))));
        if (text != null && !text.isBlank())
            label.setText(text);
        if (color != null)
            FXUtils.setBorderColor(label, color);
    }

    public GridPane getGrid() {
        return this.grid;
    }

    public static void setGridData(@NotNull List<StudentInfo> students, int columns, int rows) {
        STUDENTS.clear();
        STUDENTS.addAll(students);
        COLUMNS = columns;
        ROWS = rows;
    }

    @Nullable
    public static StudentsView load() {
        URL url = ResourceFetcher.getView("students");
        if (url != null) {
            try {
                FXMLLoader loader = new FXMLLoader(url);
                loader.load();
                if (loader.getController() instanceof StudentsView studentsView)
                    return studentsView;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
