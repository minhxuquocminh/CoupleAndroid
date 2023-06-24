package com.example.couple.Model.Display;

import com.example.couple.Custom.Handler.CoupleHandler;
import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.NumberArrayHandler;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SpecialNumbersHistory {
    String type;
    int value;
    int appearanceTimes;
    int dayNumberBefore;
    List<Integer> beatList;
    List<Integer> numbers;

    public SpecialNumbersHistory(String type, int value, List<Integer> beatList) {
        this.type = type;
        this.value = value;
        this.appearanceTimes = beatList.size();
        this.dayNumberBefore = beatList.isEmpty() ?
                Const.MAX_DAY_NUMBER_BEFORE : beatList.get(beatList.size() - 1);
        this.beatList = beatList;
        switch (type) {
            case Const.HEAD:
                this.numbers = NumberArrayHandler.getHeads(value);
                break;
            case Const.TAIL:
                this.numbers = NumberArrayHandler.getTails(value);
                break;
            case Const.SUM:
                this.numbers = NumberArrayHandler.getSums(value);
                break;
            case Const.SET:
                Set set = new Set(value);
                this.numbers = set.getSetsDetail();
                break;
            case Const.DOUBLE:
                this.numbers = Const.DOUBLE_SET;
                this.value = -1;
                break;
            case Const.DEVIATED_DOUBLE:
                this.numbers = Const.DEVIATED_DOUBLE_SET;
                this.value = -1;
                break;
            case Const.NEAR_DOUBLE:
                this.numbers = Const.NEAR_DOUBLE_SET;
                this.value = -1;
                break;
            default:
                break;
        }
    }

    public String showNumberType() {
        return type + " " + (value == -1 ? "" :
                (type.equals(Const.SET) ? CoupleHandler.showCouple(value) : value + ""));
    }

    public String showHistory() {
        return "  - " + showNumberType() + ": " + NumberBase.showNumbers(beatList, ", ") + ".";
    }

    public String showNumbers() {
        return CoupleHandler.showCoupleNumbers(numbers);
    }

}
