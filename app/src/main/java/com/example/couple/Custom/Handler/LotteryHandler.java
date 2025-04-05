package com.example.couple.Custom.Handler;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Enum.Split;
import com.example.couple.Model.DateTime.Date.DateBase;
import com.example.couple.Model.Origin.Lottery;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LotteryHandler {


    public static boolean updateLottery(Context context, int numberOfDays) {
        try {
            String lotteryData = Api.getLotteryDataFromInternet(context, numberOfDays);
            if (lotteryData.isEmpty()) {
                return false;
            }
            IOFileBase.saveDataToFile(context, FileName.LOTTERY, lotteryData, 0);
            List<Lottery> lotteries = getLotteryListFromFile(context, 1);
            saveLastDate(context, lotteries);
            return true;
        } catch (ExecutionException | InterruptedException e) {
            return false;
        }
    }

    public static List<Lottery> getLotteryListFromFile(Context context, int numberOfDays) {
        String data = IOFileBase.readDataFromFile(context, FileName.LOTTERY);
        if (data.isEmpty()) return new ArrayList<>();
        List<Lottery> lotteries = new ArrayList<>();
        String[] elements = data.split(Split.FIRST_ROUND.value);
        int length_lotteries = Math.min(numberOfDays, elements.length);
        for (int i = 0; i < length_lotteries; i++) {
            String[] part = elements[i].split("Ký tự");
            String timeShow = part[0].trim();
            String[] times = timeShow.split(" ");
            String[] day = times[times.length - 1].trim().split("-");
            DateBase dateBase = new DateBase(Integer.parseInt(day[0]),
                    Integer.parseInt(day[1]), Integer.parseInt(day[2]));

            String[] afterTimeShow = part[1].trim().split("Đặc biệt");
            String characters = afterTimeShow[0].trim();
            String[] numbers = afterTimeShow[1].trim().split(" ");
            List<String> lotteryString = new ArrayList<>();
            int count = 0;
            for (String number : numbers) {
                try {
                    Integer.parseInt(number);
                    lotteryString.add(number);
                    count++;
                } catch (Exception ignored) {
                }
            }
            if (count != Const.NUMBER_OF_PRIZES) continue;
            Lottery lottery = new Lottery(timeShow, characters, lotteryString, dateBase);
            lotteries.add(lottery);
        }
        return lotteries;
    }

    private static void saveLastDate(Context context, List<Lottery> lotteries) {
        if (lotteries.isEmpty()) return;
        String lastDate = lotteries.get(0).getDateBase().toString("-");
        IOFileBase.saveDataToFile(context, FileName.LOTTERY_LAST_DATE, lastDate, Context.MODE_PRIVATE);
    }

    public static DateBase getLastDate(Context context) {
        String lastDate = IOFileBase.readDataFromFile(context, FileName.LOTTERY_LAST_DATE);
        return DateBase.fromString(lastDate, "-");
    }

}
