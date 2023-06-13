package com.example.couple.Custom.Handler;

import android.content.Context;

import com.example.couple.Base.Handler.DateBase;
import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.Origin.Lottery;

import java.util.ArrayList;
import java.util.List;

public class LotteryHandler {
    public static final double[] swapPrizeName = {0, 1, 2.1, 2.2, 3.1, 3.2, 3.3, 3.4, 3.5, 3.6,
            4.1, 4.2, 4.3, 4.4, 5.1, 5.2, 5.3, 5.4, 5.5, 5.6, 6.1, 6.2, 6.3, 7.1, 7.2, 7.3, 7.4};

    public static List<Lottery> getLotteryListFromFile(Context context, int numberOfDays) {
        String data = IOFileBase.readDataFromFile(context, Const.LOTTERY_FILE_NAME);
        if (data.equals("")) return new ArrayList<>();
        List<Lottery> lotteries = new ArrayList<>();
        String elements[] = data.split("---");
        int length_lotteries = (numberOfDays < elements.length) ? numberOfDays : elements.length;
        for (int i = 0; i < length_lotteries; i++) {
            String part[] = elements[i].split("Ký tự");
            String timeShow = part[0].trim();
            String times[] = timeShow.split(" ");
            String day[] = times[times.length - 1].trim().split("-");
            DateBase dateBase = new DateBase(Integer.parseInt(day[0]),
                    Integer.parseInt(day[1]), Integer.parseInt(day[2]));

            String afterTimeShow[] = part[1].trim().split("Đặc biệt");
            String characters = afterTimeShow[0].trim();
            String numbers[] = afterTimeShow[1].trim().split(" ");
            List<String> lotteryString = new ArrayList<>();
            for (String number : numbers) {
                try {
                    Integer.parseInt(number);
                    lotteryString.add(number);
                } catch (Exception e) {
                }
            }
            Lottery lottery = new Lottery(timeShow, characters, lotteryString, dateBase);
            lotteries.add(lottery);
        }
        return lotteries;
    }


}
