package grg.music.pianoly.gui.views;

import grg.music.pianoly.data.Page;
import grg.music.pianoly.gui.GUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public class MenuView extends PageView {

    @FXML private BorderPane root;
    @FXML private FlowPane flowPane;
    @FXML private Button connect, exercise, settings, close;


    private Thread connectThread;


    @Override
    public void onClose() {
        if (this.connectThread != null)
            this.connectThread.interrupt();
    }


    @FXML
    private void initialize() {
    }

    @FXML
    private void onConnect() {
        this.connectThread = new Thread(() -> GUI.getInstance().getOut().connectDevices());
        this.connectThread.start();
        this.root.setCursor(Cursor.WAIT);
        this.flowPane.setDisable(true);
        new Thread(() -> {
            try {
                this.connectThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.flowPane.setDisable(false);
            this.root.setCursor(Cursor.DEFAULT);
        }).start();
    }

    @FXML
    private void onExercises() {
        GUI.getInstance().setPage(Page.EXERCISE);
    }

    @FXML
    private void onSettingsClicked() {
        // TODO
    }

    @FXML
    private void onCloseClicked() {
        Platform.exit();
        System.exit(0);
    }

}