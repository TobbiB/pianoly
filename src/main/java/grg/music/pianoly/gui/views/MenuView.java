package grg.music.pianoly.gui.views;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

public class MenuView extends PageView {

    @FXML private FlowPane root;
    @FXML private Button settings, close;

    @FXML
    private void initialize() {
        instance = this;
    }

    @FXML
    private void onSettingsClicked() {
        // TODO
    }

    @FXML
    private void onCloseClicked() {
        Platform.exit();
    }
}