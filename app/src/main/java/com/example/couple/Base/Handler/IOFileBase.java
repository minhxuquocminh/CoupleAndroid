package com.example.couple.Base.Handler;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOFileBase {

    public static void saveDataToFile(Context context, String fileName, String data, int mode) {
        if (mode == 0) { // Chon 0 neu ban muon ghi lai toan bo file
            mode = Context.MODE_PRIVATE;
        }
        if (mode == 1) { // Chon 1 neu ban muon ghi them du lieu vao file
            mode = Context.MODE_APPEND;
        }
        try {
            FileOutputStream out = context.openFileOutput(fileName, mode);
            out.write(data.getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String readDataFromFile(Context context, String fileName) {
        try {
            FileInputStream in = context.openFileInput(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String s = null;
            while ((s = br.readLine()) != null) {
                sb.append(s).append("\n");
            }
            br.close();
            in.close();
            return sb.toString().trim(); // cut down line and space....
        } catch (IOException e) {
            return "";
        }
    }
}
