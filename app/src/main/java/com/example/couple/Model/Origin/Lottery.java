package com.example.couple.Model.Origin;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.Support.Lotto;
import com.example.couple.Model.Support.Position;
import com.example.couple.Model.Time.DateBase;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Lottery {
    private final String timeShow;
    private final String characters;
    private final List<String> lottery;
    private final DateBase dateBase;

    public static Lottery getEmpty() {
        return new Lottery("", "", new ArrayList<>(), DateBase.getEmpty());
    }

    public boolean isEmpty() {
        return lottery.isEmpty() || dateBase.isEmpty();
    }

    public int getValueAtPosition(Position position) {
        return Integer.parseInt(lottery.get(position.getFirstLevel()).
                charAt(position.getSecondLevel()) + "");
    }

    public String getJackpotString() {
        return lottery.get(0);
    }

    public Jackpot getJackpot() {
        return new Jackpot(lottery.get(0), dateBase);
    }

    // clawType được đánh dấu theo thứ tự 54321
    public int getClaw(int clawType) {
        return Integer.parseInt(lottery.get(0).charAt(5 - clawType) + "");
    }

    public Couple getJackpotCouple() {
        String jackpots = lottery.get(0);
        int first = Integer.parseInt(jackpots.charAt(3) + "");
        int second = Integer.parseInt(jackpots.charAt(4) + "");
        return new Couple(first, second);
    }

    public List<Couple> getCoupleList() {
        List<Couple> couples = new ArrayList<>();
        for (String numberString : lottery) {
            int length_number = numberString.length();
            int first = Integer.parseInt(numberString.charAt(length_number - 2) + "");
            int second = Integer.parseInt(numberString.charAt(length_number - 1) + "");
            couples.add(new Couple(first, second));
        }
        return couples;
    }

    public List<Lotto> getHeadLotoList() {
        List<Lotto> lottos = new ArrayList<>();
        int[] a = new int[100];
        for (String numberString : lottery) {
            int length_number = numberString.length();
            int first = Integer.parseInt(numberString.charAt(length_number - 2) + "");
            int second = Integer.parseInt(numberString.charAt(length_number - 1) + "");
            a[first * 10 + second]++;
        }
        for (int i = 0; i < 10; i++) {
            List<Integer> headList = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < a[i * 10 + j]; k++) {
                    headList.add(i * 10 + j);
                }
            }
            lottos.add(new Lotto(Const.HEAD, i, headList));
        }
        return lottos;
    }

    public List<Lotto> getTailLotoList() {
        List<Lotto> lottos = new ArrayList<>();
        int[] a = new int[100];
        for (String numberString : lottery) {
            int length_number = numberString.length();
            int first = Integer.parseInt(numberString.charAt(length_number - 2) + "");
            int second = Integer.parseInt(numberString.charAt(length_number - 1) + "");
            a[first * 10 + second]++;
        }
        for (int j = 0; j < 10; j++) {
            List<Integer> tailList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                for (int k = 0; k < a[i * 10 + j]; k++) {
                    tailList.add(i * 10 + j);
                }
            }
            lottos.add(new Lotto(Const.TAIL, j, tailList));
        }
        return lottos;
    }
}
