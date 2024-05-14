package com.example.couple.Base.Handler;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import com.example.couple.Custom.Const.Const;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutionException;

public class InternetBase {

    /**
     * require:
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
     */
    public static boolean checkNetworkStatus(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null &&
                    activeNetworkInfo.isConnected() && activeNetworkInfo.isAvailable();
        }
        return connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork()) != null;
    }

    public static boolean pingWebsite(String url, int timeout) {
        AsyncTaskBase<String, Boolean> task = new AsyncTaskBase<>(inputs -> {
            try {
                InetAddress address = InetAddress.getByName(url);
                return address.isReachable(timeout);
            } catch (IOException e) {
                return false;
            }
        });

        try {
            task.execute();
            return task.get();
        } catch (ExecutionException | InterruptedException e) {
            return false;
        }
    }

    public static boolean isInternetAvailable(Context context) {
        return checkNetworkStatus(context) && pingWebsite(Const.GOOGLE_URL, Const.TIME_OUT);
    }

}
