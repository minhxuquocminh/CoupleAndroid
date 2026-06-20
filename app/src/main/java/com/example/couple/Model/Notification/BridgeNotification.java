package com.example.couple.Model.Notification;

import com.example.couple.Model.DateTime.Date.DateBase;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BridgeNotification {
    DateBase dateBase;
    List<NotificationBridgeData> bridgeDataList;

    public boolean isEmpty() {
        return dateBase.isEmpty() || bridgeDataList.isEmpty();
    }
}
