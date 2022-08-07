package grg.music.pianoly.data.music;

import org.jetbrains.annotations.NotNull;

public class Chord {

    public enum Mode {

        DIMINISHED("Diminished", '°'),
        MINOR("Minor", 'm'),
        MAJOR("Major", Character.MIN_VALUE),
        AUGMENTED("Augmented", '+');

        Mode(@NotNull String name, char symbol) {
            this.name = name;
            this.symbol = symbol;
        }

        private final String name;
        private final char symbol;

        public char getSymbol() {
            return this.symbol;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
