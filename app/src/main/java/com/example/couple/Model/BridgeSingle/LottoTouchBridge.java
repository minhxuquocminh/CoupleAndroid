package com.example.couple.Model.BridgeSingle;

import com.example.couple.Custom.Handler.CoupleHandler;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.BridgeCouple.CombineInterface;
import com.example.couple.Model.Support.Heads;
import com.example.couple.Model.Support.JackpotHistory;
import com.example.couple.Model.Support.Lotto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class LottoTouchBridge implements CombineInterface {
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

    public boolean isWin() {
        return CoupleHandler.isTouch(jackpotHistory, touchs);
    }

    public String showNumbers() {
        return CoupleHandler.showCoupleNumbers(numbers);
    }

    public String showTouchs() {
        return CoupleHandler.showTouchs(touchs);
    }

    public String showBridge() {
        String show = "";
        String win = jackpotHistory.isEmpty() ? "" : (isWin() ? "trúng" : "trượt");
        show += " * " + jackpotHistory.show() + " - " + win + "\n";
        show += "    - " + Const.LOTTO_TOUCH_BRIDGE_NAME + ": " + showTouchs() + ".";
        return show;
    }

    public String showCompactBridge() {
        String show = "";
        String win = jackpotHistory.isEmpty() ? "" : (isWin() ? " (trúng)" : " (trượt)");
        show += "    - " + Const.LOTTO_TOUCH_BRIDGE_NAME + win + ": " + showTouchs() + ".";
        return show;
    }

    public static LottoTouchBridge getEmpty() {
        return new LottoTouchBridge(new ArrayList<>(), new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return headLottos.isEmpty() || tailLottos.isEmpty();
    }

}
