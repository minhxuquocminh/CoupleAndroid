package com.example.couple.Model.Bridge;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.Bridge.ConnectedBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.CycleBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.EstimatedBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.MappingBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.OtherBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.TouchBridgeHandler;
import com.example.couple.Model.Bridge.NumberSet.NumberSetBridge;
import com.example.couple.Model.Bridge.NumberSet.SpecialSet;
import com.example.couple.Model.Handler.Input;
import com.example.couple.Model.Handler.InputType;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BridgeType {

    /**
     * touch bridge
     */
    COMBINE_TOUCH(1001, "Chạm kết hợp"),
    SHADOW_TOUCH(1002, "Cầu chạm bóng"),
    LOTTO_TOUCH(1003, "Cầu chạm lô tô"),
    POSITIVE_SHADOW(1004, "Cầu chạm bóng +"),
    NEGATIVE_SHADOW(1005, "Cầu chạm bóng -"),
    LAST_DAY_SHADOW(1006, "Chạm bóng ngày"),
    LAST_WEEK_SHADOW(1007, "Chạm bóng tuần"),

    /**
     * mapping bridge
     */
    MAPPING(2001, "Cầu ánh xạ"),
    RIGHT_MAPPING(2002, "Cầu ánh xạ P"),
    TRIAD_MAPPING(2003, "Cầu 2 ánh xạ"),

    /**
     * cycle bridge
     */
    COMPATIBLE_CYCLE(3001, "Cầu can chi hợp"),
    INCOMPATIBLE_CYCLE(3002, "Cầu can chi khắc"),
    BRANCH_IN_TWO_DAYS_BRIDGE(3003, "Chi trong 2 ngày"),

    /**
     * connected bridge
     */
    CONNECTED(4001, "Cầu liên thông"),
    CONNECTED_SET(4002, "Cầu liên bộ"),
    PAIR_CONNECTED(4003, "Cầu ghép cặp?"),

    /**
     * special set
     */
    BIG_DOUBLE(5001, "Bộ kép to"),
    SAME_DOUBLE(5002, "Bộ kép bằng"),
    POSITIVE_DOUBLE(5003, "Bộ kép lệch"),

    INPUT_HEAD(6001, InputType.HEAD.name),
    INPUT_TAIL(6002, InputType.TAIL.name),
    INPUT_SUM(6003, InputType.SUM.name),
    INPUT_SET(6004, InputType.SET.name),
    INPUT_BRANCH(6005, InputType.BRANCH.name),
    INPUT_TOUCH(6006, InputType.TOUCH.name),
    INPUT_COMBINE(6007, InputType.COMBINE.name),

    /**
     * others bridge
     */
    ESTIMATED(9001, "Cầu ước lượng"),
    UNAPPEARED_BIG_DOUBLE(9002, "Kép chưa ra"),
    SYNTHETIC(9999, "Cầu tổng hợp");

    public final int value;
    public final String name;

    public Bridge getBridge(List<Jackpot> jackpotList, List<Lottery> lotteryList, List<Input> inputs, int index) {
        Jackpot jackpot = index - 1 >= 0 ? jackpotList.get(index - 1) : Jackpot.getEmpty();
        JackpotHistory jackpotHistory = new JackpotHistory(index, jackpot);
        Input input = this.getInput(inputs);
        switch (this) {
            case COMBINE_TOUCH:
                return TouchBridgeHandler.getCombineTouchBridge(jackpotList, lotteryList, index);
            case CONNECTED:
                return ConnectedBridgeHandler.getConnectedBridge(lotteryList, index, Const.CONNECTED_BRIDGE_FINDING_DAYS, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
            case SYNTHETIC:
                return OtherBridgeHandler.getSyntheticBridge(jackpotList, lotteryList, index);
            case LOTTO_TOUCH:
                return TouchBridgeHandler.getLottoTouchBridge(lotteryList, index);
            case LAST_DAY_SHADOW:
                return TouchBridgeHandler.getLastDayShadowTouchBridge(jackpotList, index);
            case LAST_WEEK_SHADOW:
                return TouchBridgeHandler.getLastWeekShadowTouchBridge(jackpotList, index);
            case MAPPING:
                return MappingBridgeHandler.getMappingBridge(jackpotList, index);
            case CONNECTED_SET:
                return ConnectedBridgeHandler.getConnectedSetBridge(lotteryList, index, Const.CONNECTED_BRIDGE_FINDING_DAYS, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
            case ESTIMATED:
                return EstimatedBridgeHandler.getEstimatedBridge(jackpotList, index);
            case RIGHT_MAPPING:
                return MappingBridgeHandler.getRightMappingBridge(jackpotList, index);
            case COMPATIBLE_CYCLE:
                return CycleBridgeHandler.getCompatibleCycleBridge(jackpotList, index);
            case INCOMPATIBLE_CYCLE:
                return CycleBridgeHandler.getIncompatibleCycleBridge(jackpotList, index);
            case UNAPPEARED_BIG_DOUBLE:
                return OtherBridgeHandler.getUnappearedBigDoubleBridge(jackpotList, index);
            case TRIAD_MAPPING:
                return MappingBridgeHandler.getAnyMappingBridge(jackpotList, index);
            case BRANCH_IN_TWO_DAYS_BRIDGE:
                return CycleBridgeHandler.getBranchInTwoDaysBridge(jackpotList, index);
            case BIG_DOUBLE:
                return new NumberSetBridge(this, SpecialSet.BIG_DOUBLE.values, jackpotHistory);
            case SAME_DOUBLE:
                return new NumberSetBridge(this, SpecialSet.DOUBLE.values, jackpotHistory);
            case POSITIVE_DOUBLE:
                return new NumberSetBridge(this, SpecialSet.POSITIVE_DOUBLE.values, jackpotHistory);
            case INPUT_HEAD:
            case INPUT_TAIL:
            case INPUT_TOUCH:
            case INPUT_SUM:
            case INPUT_SET:
            case INPUT_BRANCH:
            case INPUT_COMBINE:
                if (input == null) return null;
                return new NumberSetBridge(this, input.getNumbers(), jackpotHistory);
            default:
                return null;
        }
    }

    public Input getInput(List<Input> inputs) {
        Map<InputType, Input> inputMap = inputs.stream().collect(Collectors.toMap(Input::getInputType, Function.identity()));
        switch (this) {
            case INPUT_HEAD:
                return inputMap.get(InputType.HEAD);
            case INPUT_TAIL:
                return inputMap.get(InputType.TAIL);
            case INPUT_TOUCH:
                return inputMap.get(InputType.TOUCH);
            case INPUT_SUM:
                return inputMap.get(InputType.SUM);
            case INPUT_SET:
                return inputMap.get(InputType.SET);
            case INPUT_BRANCH:
                return inputMap.get(InputType.BRANCH);
            case INPUT_COMBINE:
                return inputMap.get(InputType.COMBINE);
            default:
                return null;
        }
    }

}
