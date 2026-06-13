package com.example.couple.Model.Bridge.Touch;

import com.example.couple.Base.Handler.SingleBase;
import com.example.couple.Custom.Handler.Bridge.ConnectedBridgeHandler;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.Connected.ConnectedSupport;
import com.example.couple.Model.Bridge.JackpotHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class ConnectedBridge extends TouchBridge {
    List<ConnectedSupport> connectedSupports;
    JackpotHistory jackpotHistory;
    List<Integer> touches;
    List<Integer> numbers;

    public ConnectedBridge(List<ConnectedSupport> connectedSupports, JackpotHistory jackpotHistory) {
        this.connectedSupports = connectedSupports;
        this.jackpotHistory = jackpotHistory;
        this.touches = ConnectedBridgeHandler.getConnectedTouchs(connectedSupports);
        this.numbers = NumberArrayHandler.getTouchs(touches);
    }

    @Override
    public String showCompactNumbers() {
        return SingleBase.showTouches(touches);
    }

    @Override
    public String showDetailInfo() {
        String show = showJackpotInfo();
        show += "\n\nCầu liên thông:";
        show += "\n" + connectedSupports.stream()
                .map(ConnectedSupport::show)
                .collect(Collectors.joining("\n"));
        show += "\n\nChạm:\n" + SingleBase.showTouches(touches, ", ");
        show += "\n\nDàn số:\n" + showNumbers();
        return show.trim();
    }

    @Override
    public BridgeType getType() {
        return BridgeType.CONNECTED;
    }

    public static ConnectedBridge getEmpty() {
        return new ConnectedBridge(new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return connectedSupports.isEmpty() || numbers.isEmpty();
    }

}
