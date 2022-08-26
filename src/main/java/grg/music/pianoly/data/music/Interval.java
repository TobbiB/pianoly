package grg.music.pianoly.data.music;

import org.jetbrains.annotations.NotNull;

public enum Interval implements MusicElement {

    UNISONE("Unisone"),
    MIN_SECOND("Minor Second"),
    MAJ_SECOND("Major Second"),
    MIN_THIRD("Minor Third"),
    MAJ_THIRD("Major Third"),
    FOURTH("Fourth"),
    TRITONE("Tritone"),
    FIFTH("Fifth"),
    MIN_SIXTH("Minor Sixth"),
    MAJ_SIXTH("Major Sixth"),
    MIN_SEVENTH("Minor Seventh"),
    MAJ_SEVENTH("Major Seventh"),
    OCTAVE("Octave");

    Interval(@NotNull String name) {
        this.name = name;
    }

    private final String name;

    @Override
    public String getDisplay() {
        //return this.name.replace("Minor", "Min").replace("Major", "Maj");
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
