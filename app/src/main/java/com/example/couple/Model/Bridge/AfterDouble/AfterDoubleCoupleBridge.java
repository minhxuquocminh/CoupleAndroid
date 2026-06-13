package com.example.couple.Model.Bridge.AfterDouble;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.JackpotHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;

@Getter
public class AfterDoubleCoupleBridge extends Bridge {
    List<AfterDoubleCoupleSupport> supports;
    List<Integer> numbers;
    JackpotHistory jackpotHistory;

    public AfterDoubleCoupleBridge(List<AfterDoubleCoupleSupport> supports, JackpotHistory jackpotHistory) {
        this.supports = supports;
        this.jackpotHistory = jackpotHistory;
        this.numbers = supports.stream()
                .flatMap(support -> Stream.of(
                        support.getPredictedCouple().getInt(),
                        CoupleBase.reverse(support.getPredictedCouple().getInt())
                ))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public String showCompactNumbers() {
        return "";
    }

    @Override
    public String showCompactInfo() {
        return supports.stream()
                .collect(Collectors.groupingBy(
                        AfterDoubleCoupleSupport::getPriority,
                        Collectors.mapping(
                                support -> support.getPredictedCouple().show(),
                                Collectors.joining(" ")
                        )
                ))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("; "));
    }

    @Override
    public String showDetailInfo() {
        String show = showJackpotInfo();
        show += "\n\nChi tiết cầu:";
        show += "\n" + supports.stream()
                .map(AfterDoubleCoupleSupport::show)
                .collect(Collectors.joining("\n"));
        show += "\n\nDàn số:\n" + showNumbers();
        return show.trim();
    }

    @Override
    public BridgeType getType() {
        return BridgeType.AFTER_DOUBLE_COUPLE;
    }

    public static AfterDoubleCoupleBridge getEmpty() {
        return new AfterDoubleCoupleBridge(new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return supports.isEmpty() || numbers.isEmpty();
    }
}
