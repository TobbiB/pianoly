package grg.music.pianoly.data;

import org.jetbrains.annotations.NotNull;

public enum Page {

    MENU("menu"),
    CONNECT("connect"),
    EXERCISE("exercise");

    Page(@NotNull String id) {
        this.id = id;
    }

    private final String id;

    public String getId() {
        return this.id;
    }
}
