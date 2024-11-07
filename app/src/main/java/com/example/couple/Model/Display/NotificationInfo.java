package com.example.couple.Model.Display;

import com.example.couple.Model.DateTime.DateTimeBase;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NotificationInfo {
    String title;
    String content;
    DateTimeBase createdDateTime;

    public static NotificationInfo getEmpty() {
        return new NotificationInfo("", "", DateTimeBase.getEmpty());
    }

    public boolean isEmpty() {
        return createdDateTime.isEmpty() || title.isEmpty() || content.isEmpty();
    }
}
