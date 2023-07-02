package com.example.couple.Model.Cycle;

import com.example.couple.Custom.Const.TimeInfo;

import lombok.Getter;

/**
 * class heavenly stems
 */

@Getter
public class Stems {
    int position;
    String name;

    public Stems(int position, String name) {
        this.position = position % 10;
        this.name = name;
    }

    public static Stems getEmpty() {
        return new Stems(-1, "");
    }

    public boolean isEmpty() {
        return position == -1 || name.equals("");
    }

    public Stems getCompatibleStems() {
        int new_position = (position + 5) % 10;
        return new Stems(new_position, TimeInfo.HEAVENLY_STEMS.get(new_position));
    }

    public Stems getIncompatibleStems() {
        int new_position = (position + 4) % 10;
        return new Stems(new_position, TimeInfo.HEAVENLY_STEMS.get(new_position));
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
