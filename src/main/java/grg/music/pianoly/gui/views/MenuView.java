package grg.music.pianoly.gui.views;

import grg.music.pianoly.data.Page;
import grg.music.pianoly.gui.GUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public class MenuView extends PageView {

    @FXML private BorderPane root;
    @FXML private FlowPane flowPane;
    @FXML private Button connect, exercise, settings, close;


    @Override
    @FXML
    protected void initialize() {
        this.exercise.setDisable(!GUI.getInstance().getOut().isStudentsSetup());

    }

    @Override
    public void onClose() {
    }

    @FXML
    private void onConnect() {
        GUI.getInstance().setPage(Page.CONNECT);
    }

    @FXML
    private void onExercises() {
        GUI.getInstance().setPage(Page.EXERCISE);
    }

    @FXML
    private void onSettings() {
        // TODO
    }

    @FXML
    private void onCloseClicked() {
        Platform.exit();
        System.exit(0);
    }

    public void onTest() {
        GUI.getInstance().setPage(Page.STUDENTS);
    }
}