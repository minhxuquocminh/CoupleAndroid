package com.example.couple.Custom.Widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class CustomAction {
    public static void changeActivity(Context context, String actionText) {
        if (!(context instanceof Activity)) return;
        Activity activity = (Activity) context;
        if (actionText.contains("quay lại")) {
            activity.onBackPressed();
            return;
        }

        for (TargetClass targetClass : TargetClass.values()) {
            if (!activity.getClass().equals(targetClass.targetClass) && actionText.contains(targetClass.key)) {
                Intent intent = new Intent(context, targetClass.targetClass);
                context.startActivity(intent);
            }
        }
    }
}
