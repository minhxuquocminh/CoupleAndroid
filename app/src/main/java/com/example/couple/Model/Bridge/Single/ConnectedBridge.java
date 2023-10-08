package com.example.couple.Model.Bridge.Single;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Base.Handler.SingleBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Support.ConnectedSupport;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class ConnectedBridge extends Bridge {
    List<ConnectedSupport> connectedSupports;
    JackpotHistory jackpotHistory;
    List<Integer> touchs;
    List<Integer> numbers;

    public ConnectedBridge(List<ConnectedSupport> connectedSupports, JackpotHistory jackpotHistory) {
        this.connectedSupports = connectedSupports;
        this.jackpotHistory = jackpotHistory;
        this.touchs = new ArrayList<>();
        if (!connectedSupports.isEmpty()) {
            int smallShadow0 = CoupleBase.getSmallShadow(connectedSupports.get(0).getValue());
            touchs.add(smallShadow0);
            touchs.add(SingleBase.getShadow(smallShadow0));
            for (int i = 0; i < connectedSupports.size(); i++) {
                int smallShadow = CoupleBase.getSmallShadow(connectedSupports.get(i).getValue());
                if (smallShadow0 != smallShadow) {
                    touchs.add(smallShadow);
                    touchs.add(SingleBase.getShadow(smallShadow));
                    break;
                }
            }
        }
        this.numbers = NumberArrayHandler.getTouchs(touchs);
    }

    @Override
    public String getBridgeName() {
        return Const.CONNECTED_BRIDGE_NAME;
    }

    public static ConnectedBridge getEmpty() {
        return new ConnectedBridge(new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return connectedSupports.isEmpty() || numbers.isEmpty();
    }

}
