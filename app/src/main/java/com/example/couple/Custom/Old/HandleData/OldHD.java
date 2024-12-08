package com.example.couple.Custom.Old.HandleData;


import com.example.couple.Model.Statistics.BCouple;

import java.util.ArrayList;

public class OldHD {

    public static ArrayList<BCouple> getBalanceCouplesV0(BCouple digit2D1, BCouple digit2D2) {
        ArrayList<BCouple> BCouples = new ArrayList<>();

        int num1_day1 = digit2D1.getFirst();
        int num2_day1 = digit2D1.getSecond();
        int num1_day2 = digit2D2.getFirst();
        int num2_day2 = digit2D2.getSecond();
        // different
        if (num1_day1 - num1_day2 >= 0) {
            int second = num2_day1 + num2_day2;
            if (second > 9) {
                second = (20 - second) % 10;
            }
            BCouples.add(new BCouple(num1_day1 - num1_day2, second));
        }
        if (num1_day1 - num2_day2 >= 0) {
            int second = num2_day1 + num1_day2;
            if (second > 9) {
                second = (20 - second) % 10;
            }
            BCouples.add(new BCouple(num1_day1 - num2_day2, second));
        }
        // sum
        if (num1_day1 + num1_day2 < 10) {
            int second = num2_day1 - num2_day2;
            if (second < 0) {
                second = -1;
            }
            BCouples.add(new BCouple(num1_day1 + num1_day2, second));
        }
        if (num1_day1 + num2_day2 < 10) {
            int second = num2_day1 - num1_day2;
            if (second < 0) {
                second = -1;
            }
            BCouples.add(new BCouple(num1_day1 + num2_day2, second));
        }

        if (num1_day1 + num1_day2 >= 10) {
            int second = num2_day1 - num2_day2;
            if (second < 0) {
                second = -1;
            }
            BCouples.add(new BCouple((20 - num1_day1 - num1_day2) % 10, second));
        }
        if (num1_day1 + num2_day2 >= 10) {
            int second = num2_day1 - num1_day2;
            if (second < 0) {
                second = -1;
            }
            BCouples.add(new BCouple((20 - num1_day1 - num2_day2) % 10, second));
        }

        return BCouples;
    }
}
