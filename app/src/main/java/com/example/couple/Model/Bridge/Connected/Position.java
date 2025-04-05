package com.example.couple.Model.Bridge.Connected;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Position {
    int firstLevel;
    int secondLevel;
    int type;

    private static final double[] PRIZE_NAME = {0, 1, 2.1, 2.2, 3.1, 3.2, 3.3, 3.4, 3.5, 3.6,
            4.1, 4.2, 4.3, 4.4, 5.1, 5.2, 5.3, 5.4, 5.5, 5.6, 6.1, 6.2, 6.3, 7.1, 7.2, 7.3, 7.4};

    public boolean equalsOnlyPosition(Position position) {
        return firstLevel == position.firstLevel && secondLevel == position.secondLevel;
    }

    public String showPrize() {
        return "G" + PRIZE_NAME[firstLevel] + " VT" + (secondLevel + 1);
    }
}
