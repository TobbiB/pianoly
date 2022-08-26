package grg.music.pianoly.data.music.Interval;

import grg.music.pianoly.data.music.MusicElement;
import grg.music.pianoly.data.music.Note.DeviceNote;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
    public boolean checkCompleted(List<DeviceNote> notes) {
        return notes.size() == 2 && Math.abs(notes.get(0).getKey() - notes.get(1).getKey()) == this.getIndex();
    }

    @Override
    public String getDisplay() {
        //return this.name.replace("Minor", "Min").replace("Major", "Maj");
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    private int getIndex() {
        int index = -1;
        for (int i = 0; i < Interval.values().length; i++) {
            if (Interval.values()[i].equals(this))
                index = i;
        }
        return index;
    }
}
