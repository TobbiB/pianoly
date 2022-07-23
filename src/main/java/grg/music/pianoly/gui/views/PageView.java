package grg.music.pianoly.gui.views;

import org.jetbrains.annotations.Nullable;

public abstract class PageView {

    protected static PageView instance;

    protected PageView() {
    }


    @Nullable
    public static PageView getInstance() {
        return instance;
    }
}
