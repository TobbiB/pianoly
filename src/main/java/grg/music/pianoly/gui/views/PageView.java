package grg.music.pianoly.gui.views;

import org.jetbrains.annotations.Nullable;

public abstract class PageView {

    protected static PageView instance;

    protected PageView() {
        if (instance != null)
            instance.onClose();
        instance = this;
    }

    protected abstract void initialize();

    protected abstract void onClose();


    @Nullable
    public static PageView getInstance() {
        return instance;
    }
}
