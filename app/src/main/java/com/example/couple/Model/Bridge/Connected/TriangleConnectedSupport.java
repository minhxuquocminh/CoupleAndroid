package com.example.couple.Model.Bridge.Connected;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TriangleConnectedSupport {
    Triangle triangle;
    TrianglePosition trianglePosition;
    List<Triangle> typeList;
    int numberOfRuns;

    public String show() {
        String show = "Các số " + triangle.show(", ") + " - " +
                trianglePosition.getFirstPosition().showPrize() + ", " +
                trianglePosition.getSecondPosition().showPrize() + ", " +
                trianglePosition.getThirdPosition().showPrize() + " TT: ";
        for (int i = 0; i < typeList.size(); i++) {
            show += i != typeList.size() - 1 ? typeList.get(i).show("-") + ", " : typeList.get(i).show("-");
        }
        return show;
    }

    public String showShort() {
        return triangle.show("-") + " (" + typeList.size() + ")";
    }
}
