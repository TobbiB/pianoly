package grg.music.pianoly.gui.views;

import grg.music.pianoly.data.exercises.Exercise;
import grg.music.pianoly.data.exercises.ExerciseMode;
import grg.music.pianoly.data.music.chord.ChordMode;
import grg.music.pianoly.data.music.interval.Interval;
import grg.music.pianoly.data.music.MusicElement;
import grg.music.pianoly.data.music.note.Note;
import grg.music.pianoly.gui.GUI;
import grg.music.pianoly.utils.FXUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

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
    @FXML private ColorPicker color;
    @FXML private Label preview;
    @FXML private Button save;

    @FXML private Button back, startStop;

    private final List<ComboBox<?>> specs = new LinkedList<>();


    @Override
    @FXML
    protected void initialize() {
        this.tabPane.getTabs().addAll(TABS);
        this.mode.setItems(FXCollections.observableArrayList(ExerciseMode.values()));
        this.mode.setValue(ExerciseMode.NOTE);
        this.onModeChanged();
        this.preview.setPadding(new Insets(1));
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
                ComboBox<ChordMode> chordMode = new ComboBox<>(FXCollections.observableArrayList(ChordMode.values()));
                chordMode.setValue(ChordMode.MAJOR);
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
        Object[] objects = new Object[this.specs.size()];
        for (int i = 0; i < this.specs.size(); i++)
            objects[i] = this.specs.get(i).getValue();
        MusicElement musicElement = this.mode.getValue().createMusicElement(objects);
        if (musicElement == null)
            return;
        Exercise<?> exercise = new Exercise<>(musicElement, this.color.getValue());
        GUI.getInstance().getOut().exerciseCreated(exercise);
        StudentsView.addExercise(exercise);
        StudentsView students = StudentsView.load(exercise);
        if (students == null)
            return;
        students.updateLabels();

        Label label = new Label("♪");
        label.setGraphic(new ImageView(this.mode.getValue().getImage()));
        FXUtils.setBorder(label, exercise.color(), 20);
        label.setPadding(new Insets(1));

        Tab tab = new Tab(exercise.getDisplay());
        tab.setGraphic(label);
        tab.setContent(students.getGrid());
        tab.setId(String.valueOf(this.tabPane.getTabs().size() - 2));
        tab.setOnSelectionChanged(event -> this.onSelect());

        tab.setClosable(false);
        tab.setOnClosed(event -> students.onClose());
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
        switch (this.mode.getValue()) {
            case NOTE, INTERVAL -> this.preview.setText("" + this.specs.get(0).getValue());
            case CHORD -> this.preview.setText("" + this.specs.get(1).getValue()
                    + ((ChordMode) this.specs.get(0).getValue()).getSymbol());
        }
        boolean savable = true;
        if (this.preview.getText().contains("null")) {
            this.preview.setText("<not specified>");
            savable = false;
        }

        FXUtils.setBorder(this.preview, (savable) ? this.color.getValue() : null, 20);
        this.save.setDisable(!savable || this.color.getValue().equals(Color.WHITE));
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
