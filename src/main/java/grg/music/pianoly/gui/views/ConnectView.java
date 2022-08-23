package grg.music.pianoly.gui.views;

import grg.music.pianoly.data.Page;
import grg.music.pianoly.gui.GUI;
import grg.music.pianoly.gui.data.StudentInfo;
import grg.music.pianoly.model.settings.Settings;
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

    private static final int WIDTH = 200, HEIGHT = 75;
    private static final int MAX_COLUMNS = 7, MAX_ROWS = 5;
    private static int COLUMNS = 4, ROWS = 4;

    @FXML private AnchorPane root;
    @FXML private Label label, countdown;
    @FXML private HBox box;
    @FXML private GridPane grid;
    @FXML private Button back, start;

    private final List<StudentInfo> studentInfos = new LinkedList<>();
    private final Button[][] buttons = new Button[MAX_COLUMNS][MAX_ROWS];

    private final BlockingQueue<int[]> cellBlockingQueue = new ArrayBlockingQueue<>(1);
    private final List<Thread> threads = new LinkedList<>();

    private boolean started = false;
    private String outName;
    private boolean countdownActive = false;

    @Override
    @FXML
    protected void initialize() {

        {
            Button[] boxButtons = {new Button("+ Row"), new Button("- Row"),
                    new Button("+ Column"), new Button("- Column")};

            boxButtons[0].setOnAction(actionEvent -> {
                if (ROWS < MAX_ROWS) {
                    for (int i = 0; i < COLUMNS; i++)
                        this.setButton(i, ROWS);
                    ROWS++;
                }
            });
            boxButtons[1].setOnAction(actionEvent -> {
                if (ROWS > 1) {
                    ROWS--;
                    this.grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) == ROWS);
                }
            });
            boxButtons[2].setOnAction(actionEvent -> {
                if (COLUMNS < MAX_COLUMNS) {
                    for (int i = 0; i < ROWS; i++)
                        this.setButton(COLUMNS, i);
                    COLUMNS++;
                }
            });
            boxButtons[3].setOnAction(actionEvent -> {
                if (COLUMNS > 1) {
                    COLUMNS--;
                    grid.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == COLUMNS);
                }
            });

            for (Button button : boxButtons) {
                button.setPrefSize(100, 50);
                this.box.getChildren().add(button);
            }
        }

        for (int column = 0; column < COLUMNS; column++) {
            for (int row = 0; row < ROWS; row++) {
                this.setButton(column, row);
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
    private void onStart() {
        if (!this.started) {
            Thread thread = new Thread(() -> GUI.getInstance().getOut().connectDevices());
            thread.start();
            this.threads.add(thread);

            this.started = true;
            this.start.setText("Retry");
            this.root.getChildren().remove(this.box);
        } else
            GUI.getInstance().setPage(Page.CONNECT);
    }


    public void setWaitingDeviceOut(@NotNull String name) {
        this.outName = name;
        Platform.runLater(() -> this.label.setText("Waiting for device on \"" + name + "\" ..."));
        this.countdownActive = true;
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new Runnable() {
            int i = Settings.CONNECT_COOLDOWN.getValue();

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
                text.setPrefSize(WIDTH, HEIGHT);
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
                label.setPrefSize(WIDTH, HEIGHT);
                label.setAlignment(Pos.CENTER);
                label.setBorder(new Border(new BorderStroke(
                        Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                grid.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == cell.get()[0]
                        && GridPane.getRowIndex(node) == cell.get()[1]);
                grid.add(label, cell.get()[0], cell.get()[1]);
            });
        } catch (InterruptedException ignored) {
        }

        this.studentInfos.add(new StudentInfo(input.get(), cell.get()[0], cell.get()[1]));
        return input.get();
    }

    public void setupFinished() {
        this.countdownActive = false;
        Platform.runLater(() -> {
            this.label.setText("Setup Finished ☺");
            this.back.setDisable(false);
        });
        StudentsView.setGridData(this.studentInfos, this.grid.getColumnCount(), this.grid.getRowCount());
    }


    private void setButton(int row, int column) {
        this.buttons[row][column] = new Button("connect");
        this.buttons[row][column].setPrefSize(WIDTH, HEIGHT);
        this.buttons[row][column].setDisable(true);
        this.buttons[row][column].setOnAction(actionEvent -> this.cellBlockingQueue.add(new int[]{row, column}));
        this.grid.add(buttons[row][column], row, column);
    }

    private void modifyButtons(@NotNull Consumer<Button> consumer) {
        for (int i = 0; i < COLUMNS; i++) {
            for (int k = 0; k < ROWS; k++)
                consumer.accept(this.buttons[i][k]);
        }
    }
}
