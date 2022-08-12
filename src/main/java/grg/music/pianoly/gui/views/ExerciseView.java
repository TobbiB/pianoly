package grg.music.pianoly.gui.views;

import grg.music.pianoly.data.exercises.Exercise;
import grg.music.pianoly.data.exercises.ExerciseMode;
import grg.music.pianoly.data.music.Chord;
import grg.music.pianoly.data.music.Interval;
import grg.music.pianoly.data.music.Note;
import grg.music.pianoly.gui.GUI;
import grg.music.pianoly.utils.FXUtils;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ExerciseView extends PageView {

    private static List<Tab> tabs = Collections.emptyList();


    @FXML private AnchorPane root;
    @FXML private TabPane tabPane;
    @FXML private ComboBox<ExerciseMode> mode;
    @FXML private HBox specsBox;
    @FXML private Label preview;
    @FXML private Button save;

    private final List<ComboBox<?>> specs = new LinkedList<>();
    private String name;


    @Override
    public void onClose() {
        this.tabPane.getSelectionModel().selectFirst();
        tabs = this.tabPane.getTabs().stream().skip(1).toList();
    }


    @FXML
    private void initialize() {
        this.tabPane.getTabs().addAll(tabs);
        this.mode.setItems(FXCollections.observableArrayList(ExerciseMode.values()));
        this.mode.setValue(ExerciseMode.NOTE);
        this.onModeChanged();
    }

    @FXML
    private void onModeChanged() {
        this.specs.clear();
        this.specsBox.getChildren().clear();
        switch (this.mode.getValue()) {
            case NOTE -> {
                this.specsBox.getChildren().add(new Label("Note:"));
                this.setUpDownSelection(1);
            }
            case INTERVAL -> {
                this.specsBox.getChildren().add(new Label("Interval:"));
                ComboBox<Interval> intervals = new ComboBox<>(FXCollections.observableArrayList(Interval.values()));
                this.specs.add(intervals);
                this.specsBox.getChildren().add(intervals);
            }
            case CHORD -> {
                this.specsBox.getChildren().add(new Label("Chord:"));
                ComboBox<Chord.Mode> chordMode = new ComboBox<>(FXCollections.observableArrayList(Chord.Mode.values()));
                chordMode.setValue(Chord.Mode.MAJOR);
                this.specs.add(chordMode);
                this.specsBox.getChildren().add(chordMode);
                this.setUpDownSelection(2);
            }
        }
        for (ComboBox<?> box : this.specs)
            box.setOnAction(actionEvent -> update());
        this.update();
    }

    @FXML
    private void onSave() {
        this.update();
        GUI.getInstance().getOut().exerciseCreated(this.mode.getValue(), this.name);
        Tab tab = new Tab(this.name);
        tab.setId(String.valueOf(this.tabPane.getTabs().size() - 1));
        tab.setOnClosed(event -> GUI.getInstance().getOut().exerciseClosed(Integer.parseInt(tab.getId())));
        this.tabPane.getTabs().add(tab);
        this.tabPane.getSelectionModel().select(tab);
    }


    private void update() {
        String base = "To Play: ";
        switch (this.mode.getValue()) {
            case NOTE, INTERVAL -> this.preview.setText(base + this.specs.get(0).getValue());
            case CHORD -> this.preview.setText(base + this.specs.get(1).getValue() + ((Chord.Mode) this.specs.get(0).getValue()).getSymbol());
        }
        if (this.preview.getText().contains("null"))
            this.preview.setText(base + "<not specified>");
        else
            this.name = this.mode.getValue() + ": " + this.preview.getText().replaceAll(base, "");

        this.save.setDisable(this.preview.getText().equals(base + "<not specified>"));
    }

    private void setUpDownSelection(int index) {
        RadioButton[] buttons = FXUtils.setRadioButtons(this.specsBox, 0, "Up", "Down");
        ComboBox<String> notes = new ComboBox<>();
        this.specs.add(notes);
        this.specsBox.getChildren().add(index, notes);

        notes.setItems(FXCollections.observableArrayList((buttons[0].isSelected()) ? Note.NAMES_UP : Note.NAMES_DOWN));

        AtomicInteger selected = new AtomicInteger(-1);
        for (RadioButton button : buttons) {
            button.setOnAction(actionEvent -> {
                for (int i = 0; i < notes.getItems().size(); i++) {
                    if (notes.getValue() != null && notes.getValue().equals(notes.getItems().get(i)))
                        selected.set(i);
                }
                notes.setItems(FXCollections.observableArrayList((buttons[0].isSelected()) ? Note.NAMES_UP : Note.NAMES_DOWN));
                if (selected.get() >= 0)
                    notes.setValue((buttons[0].isSelected()) ? Note.NAMES_UP[selected.get()] : Note.NAMES_DOWN[selected.get()]);
                this.update();
            });
        }
    }

}
