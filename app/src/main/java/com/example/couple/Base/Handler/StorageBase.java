package com.example.couple.Base.Handler;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

public class StorageBase {

    private static final String FLAG = "FLAG";

    public static void setFlag(Context context, String flagName, boolean flagValue) {
        context.getSharedPreferences(FLAG, MODE_PRIVATE).edit().putBoolean(flagName, flagValue).apply();
    }

    public static boolean getFlag(Context context, String flagName) {
        return context.getSharedPreferences(FLAG, MODE_PRIVATE).getBoolean(flagName, false);
    }
}
