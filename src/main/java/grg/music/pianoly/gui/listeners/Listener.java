package grg.music.pianoly.gui.listeners;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class Listener<T extends Event> implements EventHandler<T> {

    private static final List<Listener<?>> listeners = new LinkedList<>();


    private final BiConsumer<Scene, Listener<T>> consumer;

    public Listener(@NotNull BiConsumer<Scene, Listener<T>> consumer) {
        listeners.add(this);
        this.consumer = consumer;
    }

    public void setHandler(@NotNull Scene scene) {
        this.consumer.accept(scene, this);
    }

    public static void initialize(@NotNull Scene scene) {
        if (listeners.isEmpty()) {
            new MouseListener();
            new KeyListener();
        }

        for (Listener<?> listener : listeners)
            listener.setHandler(scene);
    }
}
