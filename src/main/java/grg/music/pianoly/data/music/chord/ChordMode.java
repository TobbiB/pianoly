package grg.music.pianoly.data.music.chord;

import org.jetbrains.annotations.NotNull;

public enum ChordMode {

    DIMINISHED("Diminished", '°', new int[]{3, 3, 6}),
    MINOR("Minor", 'm', new int[]{3, 4, 7}),
    MAJOR("Major", Character.MIN_VALUE, new int[]{4, 3, 7}),
    AUGMENTED("Augmented", 'ᐩ', new int[]{4, 4, 8});

    ChordMode(@NotNull String name, char symbol, int[] semitones) {
        this.name = name;
        this.symbol = symbol;
        this.semitones = semitones;
    }

    private final String name;
    private final char symbol;
    private final int[] semitones;

    public char getSymbol() {
        return this.symbol;
    }

    public String getDisplay() {
        return this.name;
    }

    public int[] getSemitones() {
        return this.semitones;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
