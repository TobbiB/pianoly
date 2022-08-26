package grg.music.pianoly.data.music.Note;

import org.jetbrains.annotations.Nullable;

public abstract class Note {

    public static final char[] NAMES = {'C', '?', 'D', '?', 'E', 'F', '?', 'G', '?', 'A', '?', 'B'};
    public static final String[] NAMES_UP = {"C", "C♯", "D", "D♯", "E", "F", "F♯", "G", "G♯", "A", "A♯", "B"};
    public static final String[] NAMES_DOWN = {"C", "D♭", "D", "E♭", "E", "F", "G♭", "G", "A♭", "A", "B♭", "B"};

    protected final int key;

    protected Note(int key) {
        this.key = key;
    }

    public boolean isKeyEqual(@Nullable Note note) {
        return note != null && this.key % 12 == note.key % 12;
    }

    public int getKey() {
        return this.key;
    }
}
