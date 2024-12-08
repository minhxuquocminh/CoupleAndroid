package com.example.couple.Model.Bridge.Connected;

import com.example.couple.Custom.Handler.LotteryHandler;

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
                LotteryHandler.showPrize(trianglePosition.getFirstPosition()) + ", " +
                LotteryHandler.showPrize(trianglePosition.getSecondPosition()) + ", " +
                LotteryHandler.showPrize(trianglePosition.getThirdPosition()) + " TT: ";
        for (int i = 0; i < typeList.size(); i++) {
            show += i != typeList.size() - 1 ? typeList.get(i).show("-") + ", " : typeList.get(i).show("-");
        }
        return show;
    }

    public String showShort() {
        return triangle.show("-") + " (" + typeList.size() + ")";
    }
}
