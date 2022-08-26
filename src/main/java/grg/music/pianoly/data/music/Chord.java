package grg.music.pianoly.data.music;

import grg.music.pianoly.data.exercises.ExerciseMode;
import grg.music.pianoly.data.music.Note.ExerciseNote;
import org.jetbrains.annotations.NotNull;

public class Chord implements MusicElement {

    public enum Mode {

        DIMINISHED("Diminished", '°'),
        MINOR("Minor", 'm'),
        MAJOR("Major", Character.MIN_VALUE),
        AUGMENTED("Augmented", 'ᐩ');

        Mode(@NotNull String name, char symbol) {
            this.name = name;
            this.symbol = symbol;
        }

        private final String name;
        private final char symbol;

        public char getSymbol() {
            return this.symbol;
        }

        public String getDisplay() {
            return this.name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }


    private final Mode mode;
    private final ExerciseNote note;

    public Chord(Mode mode, ExerciseNote note) {
        this.mode = mode;
        this.note = note;
    }

    @Override
    public String getDisplay() {
        return this.note.getDisplay() + this.mode.getSymbol();
    }

    public Mode getMode() {
        return this.mode;
    }

    public ExerciseNote getNote() {
        return this.note;
    }
}
