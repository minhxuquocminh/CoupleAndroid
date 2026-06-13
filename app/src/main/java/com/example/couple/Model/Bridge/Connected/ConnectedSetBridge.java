package com.example.couple.Model.Bridge.Connected;

import com.example.couple.Base.Handler.GenericBase;
import com.example.couple.Custom.Handler.Bridge.ConnectedBridgeHandler;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.JackpotHistory;
import com.example.couple.Model.Bridge.NumberSet.SetBase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class ConnectedSetBridge extends Bridge {
    List<ConnectedSupport> connectedSupports;
    JackpotHistory jackpotHistory;
    List<SetBase> setBases;
    List<Integer> numbers;

    public ConnectedSetBridge(List<ConnectedSupport> connectedSupports, JackpotHistory jackpotHistory) {
        this.connectedSupports = connectedSupports;
        this.jackpotHistory = jackpotHistory;
        this.setBases = ConnectedBridgeHandler.getConnectedSets(connectedSupports);
        this.numbers = new ArrayList<>();
        for (SetBase setBase : setBases) {
            numbers.addAll(setBase.getSetsDetail());
        }
    }

    @Override
    public String showCompactNumbers() {
        return GenericBase.getDelimiterString(setBases, " ");
    }

    @Override
    public String showCompactInfo() {
        return GenericBase.getDelimiterString(setBases, " ");
    }

    @Override
    public String showDetailInfo() {
        String show = showJackpotInfo();
        show += "\n\nCầu liên thông:";
        show += "\n" + connectedSupports.stream()
                .map(ConnectedSupport::show)
                .collect(Collectors.joining("\n"));
        show += "\n\nLiên bộ:\n" + GenericBase.getDelimiterString(setBases, " ");
        show += "\n\nDàn số:\n" + showNumbers();
        return show.trim();
    }

    @Override
    public BridgeType getType() {
        return BridgeType.CONNECTED_SET;
    }

    public static ConnectedSetBridge getEmpty() {
        return new ConnectedSetBridge(new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return connectedSupports.isEmpty() || numbers.isEmpty();
    }
}
