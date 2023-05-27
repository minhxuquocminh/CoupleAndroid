package com.example.couple.Custom.Old.Display;

import com.example.couple.Model.Origin.Couple;

import java.util.List;

public class ShowJackpot {

    public static String showMatrix(int matrix[][], int m, int n) {
        String show = "";
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int result = matrix[i][j];
                if (result < 0) {
                    show += "	";
                } else if (result < 10) {
                    show += "0" + result + "	";
                } else {
                    show += result + "	";
                }
            }
            show += "\n";
        }
        return show.trim();
    }

    public static String showReverseCouplesHorizontally(List<Couple> couples) {
        String show = "";
        int count = -1;
        for (int i = couples.size() - 1; i >= 0; i--) {
            count++;
            if (count % 7 == 0) {
                show += "\n";
            }
            show += couples.get(i).toString() + " ";

        }
        return show.trim();
    }
}
