package com.example.couple.Model.DateTime.Date.Cycle;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;

import lombok.Getter;

/**
 * class heavenly stems
 */

@Getter
public class Stem {
    int position;
    String name;

    private Stem() {
        this.position = Const.EMPTY_VALUE;
        this.name = "";
    }

    public Stem(int position) {
        if (position == Const.EMPTY_VALUE) {
            new Stem();
            return;
        }

        this.position = position % 10;
        this.name = TimeInfo.HEAVENLY_STEMS.get(position % 10);
    }

    public static Stem getByName(String stemName) {
        int position = TimeInfo.HEAVENLY_STEMS.indexOf(stemName);
        if (position < 0) return Stem.getEmpty();
        return new Stem(position);
    }

    public static Stem getEmpty() {
        return new Stem(Const.EMPTY_VALUE);
    }

    public boolean isEmpty() {
        return position == Const.EMPTY_VALUE || name.isEmpty();
    }

    public Stem getCompatibleStems() {
        if (this.isEmpty()) return Stem.getEmpty();
        int new_position = (position + 5) % 10;
        return new Stem(new_position);
    }

    public Stem getIncompatibleStems() {
        if (this.isEmpty()) return Stem.getEmpty();
        int new_position = (position + 4) % 10;
        return new Stem(new_position);
    }

}
