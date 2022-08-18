package grg.music.pianoly.gui.views;

import grg.music.pianoly.data.Page;
import grg.music.pianoly.gui.GUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class ConnectView extends PageView {

    private static final int columns = 4, lines = 4, width = 200, height = 100;

    @FXML private AnchorPane root;
    @FXML private Label label, countdown;
    @FXML private GridPane grid;
    @FXML private Button back, retry;

    private final Button[][] buttons = new Button[lines][columns];
    private final BlockingQueue<int[]> cellBlockingQueue = new ArrayBlockingQueue<>(1);
    private final List<Thread> threads = new LinkedList<>();

    private String outName;
    private boolean countdownActive = false;

    @Override
    @FXML
    protected void initialize() {
        Thread thread = new Thread(() -> GUI.getInstance().getOut().connectDevices());
        thread.start();
        this.threads.add(thread);
        for (int i = 0; i < lines; i++) {
            for (int k = 0; k < columns; k++) {
                int[] cell = new int[]{i, k};
                buttons[i][k] = new Button("connect");
                buttons[i][k].setPrefSize(width, height);
                buttons[i][k].setDisable(true);
                buttons[i][k].setOnAction(actionEvent -> cellBlockingQueue.add(cell));
                this.grid.add(buttons[i][k], i, k);
            }
        }
    }

    @Override
    protected void onClose() {
        for (Thread thread : this.threads)
            thread.interrupt();
    }

    @FXML
    private void onBack() {
        GUI.getInstance().setMainPage();
    }

    @FXML
    private void onRetry() {
        GUI.getInstance().setPage(Page.CONNECT);
    }


    public void setWaitingDeviceOut(@NotNull String name) {
        this.outName = name;
        Platform.runLater(() -> this.label.setText("Waiting for device on \"" + name + "\" ..."));
        this.countdownActive = true;
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new Runnable() {
            int i = 10;

            @Override
            public void run() {
                Platform.runLater(() -> {
                    countdown.setText("Countdown: " + i);
                    if (i < 0 || !countdownActive) {
                        executor.shutdown();
                        countdown.setText("");
                    }
                    i--;
                });
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public String deviceDetected(@NotNull String name) {
        countdownActive = false;

        AtomicReference<String> input = new AtomicReference<>(name);
        AtomicReference<int[]> cell = new AtomicReference<>(new int[2]);
        BlockingQueue<String> nameBlockingQueue = new ArrayBlockingQueue<>(1);

        try {
            Platform.runLater(() -> label.setText("New Device on \"" + this.outName + "\": \"" + name + "\""));
            this.modifyButtons(button -> button.setDisable(false));

            cell.set(cellBlockingQueue.take());
            this.modifyButtons(button -> button.setDisable(true));
            Platform.runLater(() -> {
                TextField text = new TextField();
                text.setPrefSize(width, height);
                text.setAlignment(Pos.CENTER);
                text.setOnAction(actionEvent -> {
                    if (text.getText() != null && !text.getText().isBlank())
                        nameBlockingQueue.add(text.getText());
                });
                grid.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == cell.get()[0]
                        && GridPane.getRowIndex(node) == cell.get()[1]);
                grid.add(text, cell.get()[0], cell.get()[1]);
            });

            input.set(nameBlockingQueue.take());
            Platform.runLater(() -> {
                Label label = new Label(input.get());
                label.setPrefSize(width, height);
                label.setAlignment(Pos.CENTER);
                label.setBorder(new Border(new BorderStroke(
                        Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                grid.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == cell.get()[0]
                        && GridPane.getRowIndex(node) == cell.get()[1]);
                grid.add(label, cell.get()[0], cell.get()[1]);
            });
        } catch (InterruptedException ignored) {
        }
        return input.get();
    }

    public void setupFinished() {
        this.countdownActive = false;
        Platform.runLater(() -> {
            this.label.setText("Setup Finished ☺");
            this.back.setDisable(false);
        });
    }


    private void modifyButtons(@NotNull Consumer<Button> consumer) {
        for (Button[] button : buttons) {
            for (Button value : button)
                consumer.accept(value);
        }
    }
}
