package grg.music.pianoly.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.Predicate;

public final class CollectionUtils {

    @Nullable
    public static <T> T find(@NotNull Collection<T> collection, @NotNull Predicate<? super T> predicate) {
        return collection.stream().filter(predicate).findFirst().orElse(null);
    }
}
