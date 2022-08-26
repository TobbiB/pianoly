package grg.music.pianoly.data.music.Note;

import org.jetbrains.annotations.Nullable;

public class Note_OLD {

    private static final Note_OLD[] NOTES = new Note_OLD[50];

    private final int key;

    private boolean on;

    private Note_OLD(int key) {
        this.key = key;
        this.on = false;
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
        char c = Note.NAMES[i % 12];

        if (c == '?')
            return null;

        return c + ((altered < 0) ? "♯" : "♭").repeat(Math.abs(altered)) + ((i / 12) - 1);
    }


    public boolean isKeyEqual(@Nullable Note_OLD note) {
        return note != null && note.key == this.key;
    }


    @Nullable
    public static Note_OLD getNote(int key) {
        if (NOTES[0] == null) {
            for (int i=0; i<NOTES.length; i++)
                NOTES[i] = new Note_OLD(i);
        }
        if (key >= 0 && key < NOTES.length)
            return NOTES[key];
        return null;
    }

}
