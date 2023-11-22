package com.example.couple.Custom.Handler.Bridge;

import com.example.couple.Model.Bridge.Couple.ShadowExchangeBridge;
import com.example.couple.Model.Bridge.Couple.ShadowMappingBridge;
import com.example.couple.Model.Display.BSingle;
import com.example.couple.Model.Display.SpecialSetHistory;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Support.JackpotHistory;
import com.example.couple.Model.Support.ShadowSingle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OtherBridgeHandler {

    public static ShadowExchangeBridge GetShadowExchangeBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        if (jackpotList.size() < dayNumberBefore + 2)
            return ShadowExchangeBridge.getEmpty();
        Couple couple = jackpotList.get(dayNumberBefore + 1).getCouple();
        Jackpot jackpot = dayNumberBefore == 0 ? Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        return new ShadowExchangeBridge(couple, new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static SpecialSetHistory GetSpecialSetHistory(List<Jackpot> jackpotList,
                                                         String specialSetName, List<Integer> numbers) {
        if (jackpotList.isEmpty()) return new SpecialSetHistory();
        List<Integer> beatList = new ArrayList<>();
        int count = 0;
        for (Jackpot jackpot : jackpotList) {
            count++;
            if (numbers.contains(jackpot.getCoupleInt())) {
                beatList.add(count);
                count = 0;
            }
        }
        Collections.reverse(beatList);
        return new SpecialSetHistory(specialSetName, numbers, beatList);
    }

    public static ShadowMappingBridge GetShadowMappingBridge(List<Jackpot> reverseJackpotList, int dayNumberBefore) {
        if (reverseJackpotList.size() < dayNumberBefore + 2)
            return ShadowMappingBridge.getEmpty();
        ShadowSingle first = reverseJackpotList.get(dayNumberBefore + 1).getCouple().getShadowSingle();
        ShadowSingle second = reverseJackpotList.get(dayNumberBefore).getCouple().getShadowSingle();
        Jackpot jackpot = dayNumberBefore == 0 ? Jackpot.getEmpty() : reverseJackpotList.get(dayNumberBefore - 1);
        return new ShadowMappingBridge(first, second, new JackpotHistory(dayNumberBefore, jackpot));
    }

    // cầu này để tìm càng thứ 3 dựa trên lịch sử càng giống càng chạy gần đây
    public static List<BSingle> GetTouchsByThirdClawBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        int sizeTest = jackpotList.size() - dayNumberBefore < 5 ? jackpotList.size() - dayNumberBefore : 5;

        List<Integer> nearestThirdClaw = new ArrayList<>();
        for (int i = 0; i < sizeTest; i++) {
            nearestThirdClaw.add(jackpotList.get(dayNumberBefore + i).getThirdClaw());
        }

        int size = jackpotList.size() - sizeTest - dayNumberBefore;

        List<BSingle> BSingleList = new ArrayList<>();
        for (int i = 1; i < size; i++) {
            int count = 0;
            for (int j = 0; j < sizeTest; j++) {
                if (nearestThirdClaw.get(j) == jackpotList.get(dayNumberBefore + i + j).getThirdClaw()) {
                    count++;
                }
            }
            if (count == sizeTest) {
                BSingleList.add(new BSingle(jackpotList.get(dayNumberBefore + i - 1).getThirdClaw(), count));
            }
            if (i == size - 1) {
                sizeTest--;
                if (sizeTest == 1) {
                    break;
                }
                i = 1;
            }
        }

        for (int i = 0; i < BSingleList.size(); i++) {
            for (int j = i + 1; j < BSingleList.size(); j++) {
                if (BSingleList.get(j).getNumber() == BSingleList.get(i).getNumber()) {
                    BSingleList.remove(j);
                }
            }
        }
        return BSingleList;
    }

}
