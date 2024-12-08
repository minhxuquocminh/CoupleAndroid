package com.example.couple.Model.Bridge.Connected;

import com.example.couple.Model.Bridge.Position;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TrianglePosition {
    Position firstPosition;
    Position secondPosition;
    Position thirdPosition;

    public boolean equalsTriangle(TrianglePosition trianglePosition) {
        Position first = trianglePosition.getFirstPosition();
        Position second = trianglePosition.getSecondPosition();
        Position third = trianglePosition.getThirdPosition();
        return (firstPosition.equalsOnlyPosition(first) &&
                secondPosition.equalsOnlyPosition(second) &&
                thirdPosition.equalsOnlyPosition(third)) ||
                (firstPosition.equalsOnlyPosition(second) &&
                        secondPosition.equalsOnlyPosition(third) &&
                        thirdPosition.equalsOnlyPosition(first)) ||
                (firstPosition.equalsOnlyPosition(second) &&
                        secondPosition.equalsOnlyPosition(first) &&
                        thirdPosition.equalsOnlyPosition(third)) ||
                (firstPosition.equalsOnlyPosition(third) &&
                        secondPosition.equalsOnlyPosition(first) &&
                        thirdPosition.equalsOnlyPosition(second)) ||
                (firstPosition.equalsOnlyPosition(third) &&
                        secondPosition.equalsOnlyPosition(second) &&
                        thirdPosition.equalsOnlyPosition(first));
    }

    public String showType() {
        return firstPosition.getType() + "-" + secondPosition.getType() + "-" + thirdPosition.getType();
    }
}
