package com.example.couple.Model.Bridge.Connected;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Model.Bridge.JackpotHistory;
import com.example.couple.Model.Bridge.Position;

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
        return "Càng " + claw + " (" + LotteryHandler.showPrize(position) + " - N: " +
                NumberBase.showNumbers(beatList, ", ") + ")";
    }

    public String showShort() {
        return claw + " (" + getConnectedBeat() + ")";
    }

}
