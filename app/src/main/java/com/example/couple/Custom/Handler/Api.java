package com.example.couple.Custom.Handler;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Base.Handler.JsoupBase;
import com.example.couple.Custom.Const.Const;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Api {
    public static String GetTimeDataFromInternet()
            throws ExecutionException, InterruptedException {
        String link = Const.TIME_URL;
        List<String> listClassName = new ArrayList<>();
        listClassName.add("lvn-cld-week");
        listClassName.add("lvn-cld-day");
        listClassName.add("lvn-cld-monthyear");
        listClassName.add("lvn-cld-timebott-main");

        JsoupBase jsoupBase = new JsoupBase(link, listClassName);
        jsoupBase.execute();
        return jsoupBase.get();
    }

    public static String GetSexagenaryCycleByDay(int day, int month, int year)
            throws ExecutionException, InterruptedException {
        String link = Const.TIME_URL + "xem-ngay-tot-xau-ngay-" + day + "-" + month + "-" + year;
        List<String> listClassName = new ArrayList<>();
        listClassName.add("lvn-xoneday-blocktop");

        JsoupBase jsoupBase = new JsoupBase(link, listClassName);
        jsoupBase.execute();
        return jsoupBase.get();
    }

    public static String GetJackpotDataFromInternet(Context context, int year)
            throws ExecutionException, InterruptedException {
        String urlAndParams = IOFileBase.readDataFromFile(context, Const.JACKPOT_URL_FILE_NAME);
        String arr[] = urlAndParams.split("\n");

        String link = arr[0].trim();
        List<String> listClassName = new ArrayList<>();
        listClassName.add(arr[1].trim());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("year", year + "");

        JsoupBase jsoupBase = new JsoupBase(link, listClassName, hashMap);
        jsoupBase.execute();

        return jsoupBase.get();
    }

    public static String GetLotteryDataFromInternet(Context context, int numberOfDays)
            throws ExecutionException, InterruptedException {
        String urlAndParams = IOFileBase.readDataFromFile(context, Const.LOTTERY_URL_FILE_NAME);
        String arr[] = urlAndParams.split("\n");

        String link = arr[0].trim();
        List<String> listClassName = new ArrayList<>();
        listClassName.add(arr[1].trim());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("count", numberOfDays + "");

        JsoupBase jsoupBase = new JsoupBase(link, listClassName, hashMap);
        jsoupBase.execute();

        return jsoupBase.get();
    }

}
