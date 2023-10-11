package com.example.couple.Custom.Old.HandleData;

import com.example.couple.Model.Time.DateBase;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Origin.Jackpot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JackpotFileHD {
    public static String[] readFile(String url) {
        // String url = "F:\\+ LT Android\\nghien cuu\\Dac Biet txt\\";

        String[] data = new String[2];
        data[0] = "";
        data[1] = "";
        int lineNumber = 0;
        File file = new File(url);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();
            lineNumber++;
            while (line != null) {
                data[1] += line + "\n";
                line = br.readLine();
                lineNumber++;
            }

            br.close();
            fr.close();

            data[0] = lineNumber + "";

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static String[][] GetCoupleMatrix(String data, int m, int n) {
        String[][] matrix = new String[m][n];

        String[] lines = data.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String[] numbers = lines[i].split("\t");
            for (int j = 1; j < numbers.length; j++) {
                try {
                    String currentNumber = numbers[j];
                    Integer.parseInt(currentNumber);
                    matrix[i][j - 1] = currentNumber.charAt(currentNumber.length() - 2) + ""
                            + currentNumber.charAt(currentNumber.length() - 1);
                } catch (Exception e) {
                    matrix[i][j - 1] = "";
                }
            }
        }
        return matrix;
    }

    public static String[][] GetJackpotMatrix(String data, int m, int n) {
        String[][] matrix = new String[m][n];

        String[] lines = data.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String[] numbers = lines[i].split("\t");
            for (int j = 1; j < numbers.length; j++) {
                try {
                    Integer.parseInt(numbers[j]);
                    matrix[i][j - 1] = numbers[j];
                } catch (Exception e) {
                    matrix[i][j - 1] = "";
                }
            }
        }
        return matrix;
    }

    public static List<Couple> getCoupleListVertically(String[][] matrix, int m, int n, int startYear) {
        List<Couple> coupleList = new ArrayList<>();
        int t = m / 31;
        for (int j = n - 1; j >= 0; j--) {
            for (int i = 31 * t - 1; i >= 31 * (t - 1); i--) {
                if (!matrix[i][j].equals("")) {
                    int first = Integer.parseInt(matrix[i][j].charAt(0) + "");
                    int second = Integer.parseInt(matrix[i][j].charAt(1) + "");
                    DateBase dateBase = new DateBase(i + 1, j + 1, t + startYear - 1);
                    coupleList.add(new Couple(first, second, dateBase));
                }
            }
            if (j == 0) {
                t--;
                if (t == 0)
                    break;
                else {
                    j = n - 1;
                }

            }
        }
        return coupleList;
    }

    public static List<Jackpot> GetJackpotList(String[][] matrix, int m, int n, int startYear) {
        List<Jackpot> listJackpots = new ArrayList<>();
        int t = m / 31;
        for (int j = n - 1; j >= 0; j--) {
            for (int i = 31 * t - 1; i >= 31 * (t - 1); i--) {
                if (!matrix[i][j].equals("")) {
                    DateBase dateBase = new DateBase(i + 1, j + 1, t + startYear - 1);
                    listJackpots.add(new Jackpot(matrix[i][j], dateBase));
                }
            }
            if (j == 0) {
                t--;
                if (t == 0)
                    break;
                else {
                    j = n - 1;
                }
            }
        }
        return listJackpots;
    }

    public static boolean writeToFile(String[] data, String url, boolean append) {
        File file = new File(url);
        try {
            FileWriter fw = new FileWriter(file, append);
            BufferedWriter bw = new BufferedWriter(fw);
            String[] lines = data[1].split("\n");
            for (int i = 0; i < lines.length; i++) {
                bw.write(lines[i]);
                bw.newLine();
            }
            bw.close();
            fw.close();
            return true;

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }
}
