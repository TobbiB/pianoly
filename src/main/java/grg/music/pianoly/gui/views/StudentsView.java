package grg.music.pianoly.gui.views;

import grg.music.pianoly.gui.data.StudentInfo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentsView {

    private static final int WIDTH = 150, HEIGHT = 75;

    private static StudentsView INSTANCE;

    private static int COLUMNS, ROWS;
    private static final Map<StudentInfo, Label> STUDENTS = new HashMap<>();


    @FXML
    private GridPane root;

    @FXML
    private void initialize() {
        INSTANCE = this;
        if (STUDENTS.isEmpty()) {
            return;
        }

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                Label label = new Label();
                label.setPrefSize(WIDTH, HEIGHT);
                label.setAlignment(Pos.CENTER);
                label.setBorder(new Border(new BorderStroke(
                        Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                label.setDisable(true);
                this.root.add(label, c, r);
            }
        }

        for (Map.Entry<StudentInfo, Label> entry : STUDENTS.entrySet()) {
            this.root.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == entry.getKey().getColumnIndex()
                    && GridPane.getRowIndex(node) == entry.getKey().getRowIndex());
            this.root.add(entry.getValue(), entry.getKey().getColumnIndex(), entry.getKey().getRowIndex());
        }
        this.update();
    }


    private void update() {
        Platform.runLater(() -> {
            for (Map.Entry<StudentInfo, Label> entry : STUDENTS.entrySet()) {
                Label label = entry.getValue();
                label.setText(entry.getKey().getDisplay());
                label.setBorder(new Border(new BorderStroke(entry.getKey().getColor(),
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }
        });
    }


    public static void changeLabel(@NotNull String student, @Nullable String text, @Nullable Color color) {
        for (StudentInfo info : STUDENTS.keySet()) {
            if (info.getName().equals(student)) {
                if (text != null && !text.isBlank())
                    info.setDisplay(text);
                if (color != null)
                    info.setColor(color);
            }
        }

        if (INSTANCE != null)
            INSTANCE.update();
    }

    public static void setGridData(@NotNull List<StudentInfo> students, int columns, int rows) {
        for (StudentInfo info : students) {
            Label label = new Label(info.getDisplay());
            label.setPrefSize(WIDTH, HEIGHT);
            label.setAlignment(Pos.CENTER);
            label.setBorder(new Border(new BorderStroke(info.getColor(),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            STUDENTS.put(info, label);
        }
        COLUMNS = columns;
        ROWS = rows;
    }
}
