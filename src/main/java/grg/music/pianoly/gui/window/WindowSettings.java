package grg.music.pianoly.gui.window;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

public final class WindowSettings<T> {

    private static final List<WindowSettings<?>> windowSettings = new LinkedList<>();


    public static final WindowSettings<String> TITLE = new WindowSettings<>("Pianoly", Stage::setTitle);

    public static final WindowSettings<List<Image>> ICONS = new WindowSettings<>(List.of(), (stage, images) -> stage.getIcons().addAll(images));

    public static final WindowSettings<Boolean> FULLSCREEN = new WindowSettings<>(false, Stage::setFullScreen);

    public static final WindowSettings<Boolean> MAXIMIZED = new WindowSettings<>(true, Stage::setMaximized);


    private final BiConsumer<Stage, T> consumer;
    private final T defaultValue;
    private T value;

    private WindowSettings(@NotNull T defaultValue, @NotNull BiConsumer<Stage, T> consumer) {
        windowSettings.add(this);
        this.consumer = consumer;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    public void applyDefaultValue(@NotNull Stage stage) {
        this.consumer.accept(stage, this.defaultValue);
        this.value = this.defaultValue;
    }

    public void applyValue(@NotNull Stage stage) {
        this.consumer.accept(stage, this.value);
    }

    public void applyValue(@NotNull Stage stage, @Nullable T value) {
        if (value != null) {
            this.consumer.accept(stage, value);
            this.value = value;
        } else {
            this.applyValue(stage);
        }
    }


    public static List<WindowSettings<?>> getAllSettings() {
        return windowSettings;
    }
}
