package com.example.couple.Model.Support;

import com.example.couple.Custom.Handler.LotteryHandler;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ClawSupport {
    int claw;
    Position position;
    List<Integer> beatList;
    JackpotHistory jackpotHistory;

    public int getConnectedBeat() {
        int count = 0;
        for (int i = 0; i < beatList.size(); i++) {
            if (beatList.get(i) == 1) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    public String showStatus() {
        String show = "Càng " + claw + " (G" + LotteryHandler.swapPrizeName[position.getFirstLevel()] + " - VT" +
                (position.getSecondLevel() + 1) + " - N: ";
        for (int i = 0; i < beatList.size(); i++) {
            show += beatList.get(i) + "";
            if (i != beatList.size() - 1) {
                show += ", ";
            }
        }
        show += ")";
        return show;
    }

    List<Integer> getStatusList(List<Integer> numbers) {
        List<Integer> rs = new ArrayList<>();
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i) == 1) {
                rs.add(1);
            } else {
                for (int j = 1; j < numbers.get(i); j++) {
                    rs.add(0);
                }
                rs.add(1);
            }
        }
        return rs;
    }

    public String showShort() {
        return claw + " (" + getConnectedBeat() + ")";
    }

}
