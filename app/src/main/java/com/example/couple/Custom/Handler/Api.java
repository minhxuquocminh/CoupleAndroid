package com.example.couple.Custom.Handler;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Base.Handler.JsoupBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Model.Time.DateBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Api {

    public static String GetTimeDataFromInternet(Context context)
            throws ExecutionException, InterruptedException {
        String link = Const.TIME_URL;
        List<String> listClassName = new ArrayList<>();
        listClassName.add("lvn-cld-week");
        listClassName.add("lvn-cld-day");
        listClassName.add("lvn-cld-monthyear");
        listClassName.add("lvn-cld-timebott-main");

        JsoupBase jsoupBase = new JsoupBase(context, link, Const.TIME_OUT, listClassName);
        jsoupBase.execute();
        return jsoupBase.get();
    }

    public static String GetSexagenaryCycleByDay(Context context, DateBase dateBase)
            throws ExecutionException, InterruptedException {
        String link = Const.TIME_URL + "xem-ngay-tot-xau-ngay-" +
                dateBase.getDay() + "-" + dateBase.getMonth() + "-" + dateBase.getYear();
        List<String> listClassName = new ArrayList<>();
        listClassName.add("lvn-xoneday-blocktop");

        JsoupBase jsoupBase = new JsoupBase(context, link, Const.TIME_OUT, listClassName);
        jsoupBase.execute();
        return jsoupBase.get();
    }

    public static String GetJackpotDataFromInternet(Context context, int year)
            throws ExecutionException, InterruptedException {
        String urlAndParams = IOFileBase.readDataFromFile(context, FileName.JACKPOT_URL);
        String[] arr = urlAndParams.split("\n");

        String link = arr[0].trim();
        List<String> listClassName = new ArrayList<>();
        listClassName.add(arr[1].trim());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("year", year + "");

        JsoupBase jsoupBase = new JsoupBase(context, link, Const.TIME_OUT, listClassName, hashMap);
        jsoupBase.execute();

        return jsoupBase.get();
    }

    public static String GetLotteryDataFromInternet(Context context, int numberOfDays)
            throws ExecutionException, InterruptedException {
        String urlAndParams = IOFileBase.readDataFromFile(context, FileName.LOTTERY_URL);
        String[] arr = urlAndParams.split("\n");

        String link = arr[0].trim();
        List<String> listClassName = new ArrayList<>();
        listClassName.add(arr[1].trim());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("count", numberOfDays + "");

        JsoupBase jsoupBase = new JsoupBase(context, link, Const.TIME_OUT, listClassName, hashMap);
        jsoupBase.execute();

        return jsoupBase.get();
    }

}
