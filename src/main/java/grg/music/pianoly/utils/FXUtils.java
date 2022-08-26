package grg.music.pianoly.utils;

import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class FXUtils {

    public static RadioButton[] setRadioButtons(@NotNull Pane pane, int selected, @NotNull String... strings) {
        ToggleGroup group = new ToggleGroup();
        RadioButton[] buttons = new RadioButton[strings.length];
        for (int i = 0; i < strings.length; i++) {
            buttons[i] = new RadioButton(strings[i]);
            buttons[i].setToggleGroup(group);
            buttons[i].setSelected(i == selected);
            pane.getChildren().add(buttons[i]);
        }
        return buttons;
    }

    public static void setBorder(@NotNull Region node, @Nullable Color color, @NotNull BorderStrokeStyle style, int radii) {
        node.setBorder((color == null) ? Border.EMPTY : new Border(
                new BorderStroke(color, style, new CornerRadii(radii / 100f, true), BorderWidths.DEFAULT)));
    }

    public static void setBorder(@NotNull Region node, @Nullable Color color, int radii) {
        setBorder(node, color, BorderStrokeStyle.SOLID, radii);
    }

    public static void setBackgroundColor(@NotNull Region node, @NotNull Color color, double radii) {
        node.setBackground(new Background(new BackgroundFill(color, new CornerRadii(radii), new Insets(0))));
    }
}
