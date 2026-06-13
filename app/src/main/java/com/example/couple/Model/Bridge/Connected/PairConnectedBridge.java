package com.example.couple.Model.Bridge.Connected;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.JackpotHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;

@Getter
public class PairConnectedBridge extends Bridge {
    String bridgeName;
    List<PairConnectedSupport> pairConnectedSupports;
    JackpotHistory jackpotHistory;
    List<Integer> numbers;

    public PairConnectedBridge(String bridgeName,
                               List<PairConnectedSupport> pairConnectedSupports,
                               JackpotHistory jackpotHistory) {
        this.bridgeName = bridgeName;
        this.pairConnectedSupports = pairConnectedSupports;
        this.jackpotHistory = jackpotHistory;
        this.numbers = pairConnectedSupports.stream()
                .flatMap(support -> Stream.of(
                        support.getPair().getFirst() * 10 + support.getPair().getSecond(),
                        support.getPair().getSecond() * 10 + support.getPair().getFirst()
                ))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public String showCompactNumbers() {
        return numbers.stream()
                .map(CoupleBase::showCouple)
                .collect(Collectors.joining(" "));
    }

    @Override
    public String showCompactInfo() {
        return pairConnectedSupports.stream()
                .map(PairConnectedSupport::showShort)
                .collect(Collectors.joining(" "));
    }

    @Override
    public String showDetailInfo() {
        String show = showJackpotInfo();
        show += "\n\nChi tiết cầu:";
        show += "\n" + pairConnectedSupports.stream()
                .map(PairConnectedSupport::show)
                .collect(Collectors.joining("\n"));
        show += "\n\nDàn số:\n" + showNumbers();
        return show.trim();
    }

    @Override
    public BridgeType getType() {
        return BridgeType.PAIR_CONNECTED;
    }

    public static PairConnectedBridge getEmpty() {
        return new PairConnectedBridge("", new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return pairConnectedSupports.isEmpty() || numbers.isEmpty();
    }
}
