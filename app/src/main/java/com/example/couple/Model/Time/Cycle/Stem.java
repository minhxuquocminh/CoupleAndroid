package com.example.couple.Model.Time.Cycle;

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

    public Stem(int position) {
        this.position = position == Const.EMPTY_VALUE ? position : position % 10;
        this.name = position == Const.EMPTY_VALUE ? "" : TimeInfo.HEAVENLY_STEMS.get(position % 10);
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
        int new_position = (position + 5) % 10;
        return new Stem(new_position);
    }

    public Stem getIncompatibleStems() {
        int new_position = (position + 4) % 10;
        return new Stem(new_position);
    }

}
