package grg.music.pianoly.gui.views;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

public class ExerciseView extends PageView {

    @FXML private AnchorPane root;
    @FXML private TabPane tabPane;


    @FXML
    private void initialize() {
        instance = this;
    }
}
