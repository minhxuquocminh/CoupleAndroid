package com.example.couple.Model.Time.Cycle;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;

import lombok.Getter;

/**
 * class heavenly stems
 */

@Getter
public class Stems {
    int position;
    String name;

    public Stems(int position) {
        this.position = position == Const.EMPTY_VALUE ? position : position % 10;
        this.name = position == Const.EMPTY_VALUE ? "" : TimeInfo.HEAVENLY_STEMS.get(position % 10);
    }

    public static Stems getEmpty() {
        return new Stems(Const.EMPTY_VALUE);
    }

    public boolean isEmpty() {
        return position == Const.EMPTY_VALUE || name.equals("");
    }

    public Stems getCompatibleStems() {
        int new_position = (position + 5) % 10;
        return new Stems(new_position);
    }

    public Stems getIncompatibleStems() {
        int new_position = (position + 4) % 10;
        return new Stems(new_position);
    }

    public String showCompatibleStems() {
        Stems compatible = getCompatibleStems();
        return "" + compatible.getPosition();
    }

    public String showIncompatibleStems() {
        Stems incompatible = getIncompatibleStems();
        return "" + incompatible.getPosition();
    }

}
