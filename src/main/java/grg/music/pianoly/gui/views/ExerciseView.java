package grg.music.pianoly.gui.views;

import grg.music.pianoly.data.ExerciseMode;
import grg.music.pianoly.data.music.Chord;
import grg.music.pianoly.data.music.Interval;
import grg.music.pianoly.data.music.Note;
import grg.music.pianoly.utils.FXUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.util.concurrent.atomic.AtomicInteger;

public class ExerciseView extends PageView {

    @FXML private AnchorPane root;
    @FXML private TabPane tabPane;
    @FXML private TextField name;
    @FXML private ComboBox<ExerciseMode> mode;
    @FXML private HBox specs;


    @FXML
    private void initialize() {
        this.mode.setItems(FXCollections.observableArrayList(ExerciseMode.values()));
        this.mode.setValue(ExerciseMode.CHORD);
        this.onModeChanged();
    }

    @FXML
    private void onModeChanged() {
        this.specs.getChildren().clear();
        switch (this.mode.getValue()) {
            case NOTE -> {
                this.specs.getChildren().add(new Label("Note:"));
                this.setUpDownSelection(1);
            }
            case INTERVAL -> {
                this.specs.getChildren().add(new Label("Interval:"));
                this.specs.getChildren().add(new ComboBox<>(FXCollections.observableArrayList(Interval.values())));
            }
            case CHORD -> {
                this.specs.getChildren().add(new Label("Chord:"));
                ComboBox<Chord.Mode> chordMode = new ComboBox<>(FXCollections.observableArrayList(Chord.Mode.values()));
                chordMode.setValue(Chord.Mode.MAJOR);
                this.specs.getChildren().add(chordMode);
                this.setUpDownSelection(2);
            }
        }
    }


    private void setUpDownSelection(int index) {
        RadioButton[] buttons = FXUtils.setRadioButtons(this.specs, 0, "Up", "Down");
        ComboBox<String> boxUp = new ComboBox<>(FXCollections.observableArrayList((Note.NAMES_UP)));
        ComboBox<String> boxDown = new ComboBox<>(FXCollections.observableArrayList((Note.NAMES_DOWN)));
        this.specs.getChildren().add(index, (buttons[0].isSelected()) ? boxUp : boxDown);

        AtomicInteger selected = new AtomicInteger(-1);
        for (RadioButton button : buttons)
            button.setOnAction(actionEvent -> {
                ComboBox<String> box = (this.specs.getChildren().get(index).equals(boxUp)) ? boxUp : boxDown;
                for (int i = 0; i < box.getItems().size(); i++)
                    if (box.getValue() != null && box.getValue().equals(box.getItems().get(i)))
                        selected.set(i);
                this.specs.getChildren().set(index, (buttons[0].isSelected()) ? boxUp : boxDown);
                box = (buttons[0].isSelected()) ? boxUp : boxDown;
                if (selected.get() >= 0)
                    box.setValue((buttons[0].isSelected()) ? Note.NAMES_UP[selected.get()] : Note.NAMES_DOWN[selected.get()]);
            });
    }

}
