package grg.music.pianoly.gui.views;

import grg.music.pianoly.data.exercises.Exercise;
import grg.music.pianoly.data.exercises.ExerciseMode;
import grg.music.pianoly.data.music.Chord;
import grg.music.pianoly.data.music.Interval;
import grg.music.pianoly.data.music.Note;
import grg.music.pianoly.gui.GUI;
import grg.music.pianoly.utils.FXUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ExerciseView extends PageView {

    private static List<Tab> TABS = Collections.emptyList();


    @FXML private AnchorPane root;
    @FXML private TabPane tabPane;
    @FXML private ComboBox<ExerciseMode> mode;
    @FXML private HBox specsBox;
    @FXML private Label preview;
    @FXML private ColorPicker color;
    @FXML private Button save;

    @FXML private Button back, startStop;

    private final List<ComboBox<?>> specs = new LinkedList<>();
    private String name;


    @Override
    @FXML
    protected void initialize() {
        this.tabPane.getTabs().addAll(TABS);
        this.mode.setItems(FXCollections.observableArrayList(ExerciseMode.values()));
        this.mode.setValue(ExerciseMode.NOTE);
        this.onModeChanged();
    }

    @Override
    protected void onClose() {
        this.tabPane.getSelectionModel().selectFirst();
        TABS = this.tabPane.getTabs().stream().skip(2).toList();
        StudentsView.close();
    }

    @FXML
    private void onSelect() {
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
        Exercise<ExerciseMode> exercise = new Exercise<>(this.mode.getValue(), this.color.getValue(), this.name);
        GUI.getInstance().getOut().exerciseCreated(exercise);
        StudentsView.getExercises().add(exercise);
        StudentsView students = StudentsView.load(exercise);
        if (students == null)
            return;
        students.updateLabels();
        Tab tab = new Tab(this.name);
        tab.setContent(students.getGrid());
        tab.setId(String.valueOf(this.tabPane.getTabs().size() - 2));
        tab.setOnSelectionChanged(event -> this.onSelect());
        tab.setOnClosed(event -> {
            GUI.getInstance().getOut().exerciseClosed(Integer.parseInt(tab.getId()));
            StudentsView.getExercises().remove(exercise);
            students.onClose();
        });
        this.tabPane.getTabs().add(tab);
        this.tabPane.getSelectionModel().select(tab);
    }

    @FXML
    private void onBack() {
        GUI.getInstance().setMainPage();
    }

    @FXML
    private void onStart() {
        GUI.getInstance().getOut().startStopWorking();
        if (this.startStop.getText().equals("Start"))
            this.startStop.setText("Stop");
        else if (this.startStop.getText().equals("Stop"))
            this.startStop.setText("Start");
    }

    @FXML
    private void update() {
        String base = "To Play: ";
        switch (this.mode.getValue()) {
            //case FREE -> this.preview.setText(base + "Free");
            case NOTE, INTERVAL -> this.preview.setText(base + this.specs.get(0).getValue());
            case CHORD -> this.preview.setText(base + this.specs.get(1).getValue()
                    + ((Chord.Mode) this.specs.get(0).getValue()).getSymbol());
        }
        if (this.preview.getText().contains("null"))
            this.preview.setText(base + "<not specified>");
        else
            this.name = this.mode.getValue() + ": " + this.preview.getText().replaceAll(base, "");

        FXUtils.setBorderColor(this.preview, this.color.getValue());
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
