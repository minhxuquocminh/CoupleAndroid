package com.example.couple.Custom.Old.Display;

public class ShowMatrix {
    public static String showMatrix(int[][] matrix,int m,int n){
        String show="";
        for (int i = 0; i < m; i++) {
            for (int j = 0 ; j < n; j++) {
                show+=matrix[i][j]+" ";
            }
            show+="\n";
        }
        return show;
    }
}
