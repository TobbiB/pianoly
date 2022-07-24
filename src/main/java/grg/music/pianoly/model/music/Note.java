package grg.music.pianoly.model.music;

import org.jetbrains.annotations.Nullable;

public class Note {

    private static final char[] NAMES = {'C', '?', 'D', '?', 'E', 'F', '?', 'G', '?', 'A', '?', 'B'};

    private final int key;

    private boolean on;

    public Note(int key, boolean on) {
        this.key = key;
        this.on = on;
    }

    public boolean isOn() {
        return this.on;
    }
    public void setOn(boolean on) {
        this.on = on;
    }

    public int getKey() {
        return this.key;
    }
    public int getTone() {
        return this.key % 12;
    }
    public int getOctave() {
        return (this.key / 12) - 1;
    }

    @Nullable
    public String getName(int altered) {
        if (altered < -2 || altered > 2)
            return null;

        int i = this.key + altered;
        char c = NAMES[i % 12];

        if (c == '?')
            return null;

        return c + ((altered < 0) ? "♯" : "♭").repeat(Math.abs(altered)) + ((i / 12) - 1);
    }

}
