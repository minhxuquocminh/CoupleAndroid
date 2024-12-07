package com.example.couple.Custom.Handler;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Base.Handler.JsoupBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Model.DateTime.Date.DateBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Api {

    protected static String getDateFromInternet(Context context)
            throws ExecutionException, InterruptedException {
        String link = Const.TIME_URL;
        List<String> elementClasses = new ArrayList<>();
        elementClasses.add("lvn-cld-week");
        elementClasses.add("lvn-cld-day");
        elementClasses.add("lvn-cld-monthyear");
        elementClasses.add("lvn-cld-timebott-main");

        JsoupBase jsoupBase = new JsoupBase(context, link, Const.TIME_OUT, elementClasses);
        jsoupBase.execute();
        return jsoupBase.get();
    }

    protected static String getDateDataFromInternet(Context context, DateBase dateBase)
            throws ExecutionException, InterruptedException {
        String link = Const.TIME_URL + "xem-ngay-tot-xau-ngay-" +
                dateBase.getDay() + "-" + dateBase.getMonth() + "-" + dateBase.getYear();
        List<String> elementClasses = new ArrayList<>();
        elementClasses.add("lvn-xoneday-blocktop");

        JsoupBase jsoupBase = new JsoupBase(context, link, Const.TIME_OUT, elementClasses);
        jsoupBase.execute();
        return jsoupBase.get();
    }

    public static String getJackpotDataFromInternet(Context context, int year)
            throws ExecutionException, InterruptedException {
        String urlAndParams = IOFileBase.readDataFromFile(context, FileName.JACKPOT_URL);
        String[] arr = urlAndParams.split("\n");

        String link = arr[0].trim();
        List<String> elementClasses = new ArrayList<>();
        elementClasses.add(arr[1].trim());
        Map<String, String> postData = new HashMap<>();
        postData.put("year", year + "");

        JsoupBase jsoupBase = new JsoupBase(context, link, Const.TIME_OUT, elementClasses, postData);
        jsoupBase.execute();
        return jsoupBase.get();
    }

    protected static String getLotteryDataFromInternet(Context context, int numberOfDays)
            throws ExecutionException, InterruptedException {
        String urlAndParams = IOFileBase.readDataFromFile(context, FileName.LOTTERY_URL);
        String[] arr = urlAndParams.split("\n");

        String link = arr[0].trim();
        List<String> elementClasses = new ArrayList<>();
        elementClasses.add(arr[1].trim());
        Map<String, String> postData = new HashMap<>();
        postData.put("count", numberOfDays + "");

        JsoupBase jsoupBase = new JsoupBase(context, link, Const.TIME_OUT, elementClasses, postData);
        jsoupBase.execute();
        return jsoupBase.get();
    }

}
