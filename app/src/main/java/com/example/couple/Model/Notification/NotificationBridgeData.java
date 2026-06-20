package com.example.couple.Model.Notification;

import com.example.couple.Base.Handler.NumberBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class NotificationBridgeData {
    String bridgeName;
    String subBridgeName;
    String info;
    List<Integer> numbers;
    @Setter
    boolean winNotified;

    public NotificationBridgeData(String bridgeName, String subBridgeName, String info, List<Integer> numbers) {
        this(bridgeName, subBridgeName, info, numbers, false);
    }

    public boolean isWin(int number) {
        return numbers.contains(number);
    }

    public String showName() {
        return subBridgeName == null || subBridgeName.isEmpty()
                ? bridgeName
                : bridgeName + " " + subBridgeName;
    }

    public String showNotificationLine() {
        return info == null || info.isEmpty() ? showName() : info;
    }

    public String showNumbers() {
        List<Integer> sortedNumbers = new ArrayList<>(numbers);
        Collections.sort(sortedNumbers);
        return NumberBase.showNumbers(sortedNumbers, 2, ", ");
    }
}
