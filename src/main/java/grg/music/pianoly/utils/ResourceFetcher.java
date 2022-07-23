package grg.music.pianoly.utils;

import grg.music.pianoly.Pianoly;
import org.jetbrains.annotations.Nullable;

import java.net.URL;

public final class ResourceFetcher {

    @Nullable
    public static URL getView(@Nullable String name) {
        return Pianoly.class.getResource("views/" + name + ".fxml");
    }

    @Nullable
    public static URL getImg(@Nullable String name) {
        return Pianoly.class.getResource("img/" + name + ".fxml");
    }
}
