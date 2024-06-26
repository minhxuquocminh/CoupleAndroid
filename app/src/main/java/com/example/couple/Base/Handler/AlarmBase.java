package com.example.couple.Base.Handler;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.couple.Model.DateTime.Time.TimeBase;

import java.util.Calendar;

public class AlarmBase {
    @SuppressLint({"NewApi", "ScheduleExactAlarm"})
    public static void registerAlarmEveryDay(Context context, Class<?> receiverClass, int REQUEST_CODE,
                                             TimeBase timeBase) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, receiverClass);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent,
                PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);

        Calendar calendar = timeBase.toCalendar();
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public static void registerAlarmOneTime(Context context, Class<?> receiverClass, int REQUEST_CODE,
                                            TimeBase timeBase) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, receiverClass);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_MUTABLE);

        Calendar calendar = timeBase.toCalendar();
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

}
