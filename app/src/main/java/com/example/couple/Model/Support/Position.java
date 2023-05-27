package com.example.couple.Model.Support;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Position {
    int firstLevel;
    int secondLevel;
    int type;

    public boolean equalsOnlyPosition(Position position) {
        return firstLevel == position.firstLevel && secondLevel == position.secondLevel;
    }
}
