package grg.music.pianoly.gui.data;

import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

public final class StudentInfo {

    private final String name;
    private final int column, row;
    private Color color;
    private String display;

    public StudentInfo(@NotNull String name, int column, int row) {
        this.name = name;
        this.color = Color.ORANGE;
        this.display = name;
        this.column = column;
        this.row = row;
    }

    public Color getColor() {
        return this.color;
    }
    public void setColor(@NotNull Color color) {
        this.color = color;
    }

    public String getDisplay() {
        return display;
    }
    public void setDisplay(@NotNull String display) {
        this.display = display;
    }

    public String getName() {
        return this.name;
    }

    public int getColumnIndex() {
        return this.column;
    }
    public int getRowIndex() {
        return this.row;
    }

}
