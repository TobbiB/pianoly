package grg.music.pianoly.model.settings;

public class Settings<T> {

    public static final Settings<Integer> CONNECT_COOLDOWN = new Settings<>(5);

    private final T value;

    private Settings(T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }
}
