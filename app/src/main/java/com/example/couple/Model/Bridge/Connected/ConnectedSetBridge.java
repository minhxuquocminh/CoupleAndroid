package com.example.couple.Model.Bridge.Connected;

import com.example.couple.Base.Handler.GenericBase;
import com.example.couple.Custom.Handler.Bridge.ConnectedBridgeHandler;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.NumberSet.NumberSet;
import com.example.couple.Model.Bridge.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class ConnectedSetBridge extends Bridge {
    List<ConnectedSupport> connectedSupports;
    JackpotHistory jackpotHistory;
    List<NumberSet> numberSets;
    List<Integer> numbers;

    public ConnectedSetBridge(List<ConnectedSupport> connectedSupports, JackpotHistory jackpotHistory) {
        this.connectedSupports = connectedSupports;
        this.jackpotHistory = jackpotHistory;
        this.numberSets = ConnectedBridgeHandler.getConnectedSets(connectedSupports);
        this.numbers = new ArrayList<>();
        for (NumberSet numberSet : numberSets) {
            numbers.addAll(numberSet.getSetsDetail());
        }
    }

    @Override
    public String showCompactNumbers() {
        return GenericBase.getDelimiterString(numberSets, " ");
    }

    @Override
    public String getBridgeName() {
        return BridgeType.CONNECTED_SET.name;
    }

    public static ConnectedSetBridge getEmpty() {
        return new ConnectedSetBridge(new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return connectedSupports.isEmpty() || numbers.isEmpty();
    }
}
