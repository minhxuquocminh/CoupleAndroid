package com.example.couple.Model.Bridge.Couple;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.Bridge.ConnectedBridgeHandler;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Display.Set;
import com.example.couple.Model.Support.ConnectedSupport;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class ConnectedSetBridge extends Bridge {
    List<ConnectedSupport> connectedSupports;
    JackpotHistory jackpotHistory;
    List<Set> sets;
    List<Integer> numbers;

    public ConnectedSetBridge(List<ConnectedSupport> connectedSupports, JackpotHistory jackpotHistory) {
        this.connectedSupports = connectedSupports;
        this.jackpotHistory = jackpotHistory;
        this.sets = ConnectedBridgeHandler.GetConnectedSets(connectedSupports);
        this.numbers = new ArrayList<>();
        for (Set set : sets) {
            numbers.addAll(set.getSetsDetail());
        }
    }

    @Override
    public String showCompactNumbers() {
        String show = "";
        for (Set set : sets) {
            show += set.show() + " ";
        }
        return show.trim();
    }

    @Override
    public String getBridgeName() {
        return Const.CONNECTED_SET_BRIDGE_NAME;
    }

    public static ConnectedSetBridge getEmpty() {
        return new ConnectedSetBridge(new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return connectedSupports.isEmpty() || numbers.isEmpty();
    }
}
