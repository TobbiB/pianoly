package grg.music.pianoly.utils;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

public final class FXUtils {

    public static RadioButton[] setRadioButtons(@NotNull Pane pane, int selected, @NotNull String... strings) {
        ToggleGroup group = new ToggleGroup();
        RadioButton[] buttons = new RadioButton[strings.length];
        for (int i = 0; i < strings.length; i++) {
            buttons[i] = new RadioButton(strings[i]);
            buttons[i].setToggleGroup(group);
            if (i == selected)
                buttons[i].setSelected(true);
            pane.getChildren().add(buttons[i]);
        }
        return buttons;
    }
}
