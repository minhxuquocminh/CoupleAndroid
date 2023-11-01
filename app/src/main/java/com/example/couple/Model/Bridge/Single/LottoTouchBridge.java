package com.example.couple.Model.Bridge.Single;

import com.example.couple.Base.Handler.SingleBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Support.Heads;
import com.example.couple.Model.Support.JackpotHistory;
import com.example.couple.Model.Support.Lotto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class LottoTouchBridge extends Bridge {
    List<Lotto> headLottos;
    List<Lotto> tailLottos;
    List<Integer> touchs;
    JackpotHistory jackpotHistory;
    List<Integer> numbers;

    // headLottos, tailLottos là list lotto của hôm trước đó.

    public LottoTouchBridge(List<Lotto> headLottos, List<Lotto> tailLottos, JackpotHistory jackpotHistory) {
        this.headLottos = headLottos;
        this.tailLottos = tailLottos;
        List<Heads> headsList = new ArrayList<>();
        int max_lottoNumber = 0;
        for (int i = 0; i < 8; i++) {
            List<Lotto> headList = new ArrayList<>();
            int countHead = 0;
            for (Lotto lotto : headLottos) {
                if (lotto.getCoupleNumber() == i) {
                    headList.add(lotto);
                    countHead++;
                    if (max_lottoNumber < countHead) max_lottoNumber = countHead;
                }
            }
            if (!headList.isEmpty()) {
                headsList.add(new Heads(i, headList));
            }
        }
        this.touchs = new ArrayList<>();
        if (max_lottoNumber != 0) {
            for (Heads heads : headsList) {
                if (heads.getHeadNumber() == max_lottoNumber) {
                    this.touchs.addAll(heads.getHeadListInt());
                }
            }
        }
        this.jackpotHistory = jackpotHistory;
        this.numbers = NumberArrayHandler.getTouchs(touchs);
    }

    @Override
    public String showCompactNumbers() {
        return SingleBase.showTouchs(touchs);
    }

    public String getBridgeName() {
        return Const.LOTTO_TOUCH_BRIDGE_NAME;
    }

    public static LottoTouchBridge getEmpty() {
        return new LottoTouchBridge(new ArrayList<>(), new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return headLottos.isEmpty() || tailLottos.isEmpty();
    }

}
