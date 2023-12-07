package com.example.couple.Model.Support;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PairPosition {
    Position firstPosition;
    Position secondPosition;

    public boolean equalsPair(PairPosition pairPosition) {
        return (firstPosition.equalsOnlyPosition(pairPosition.getFirstPosition()) &&
                secondPosition.equalsOnlyPosition(pairPosition.getSecondPosition())) ||
                (firstPosition.equalsOnlyPosition(pairPosition.getSecondPosition()) &&
                        secondPosition.equalsOnlyPosition(pairPosition.getFirstPosition()));
    }

    public String showType() {
        return firstPosition.getType() + "-" + secondPosition.getType();
    }
}
