package com.example.couple.Base.Handler;

import android.os.Handler;
import android.os.Looper;

import com.example.couple.Base.Handler.Interface.CallbackNoParam;

public class MainThreadBase {
    CallbackNoParam callback;
    boolean isMainThread; // nếu đã là main thread thì ko cần chuyển về chính nó nữa

    public MainThreadBase(CallbackNoParam callback, boolean isMainThread) {
        this.callback = callback;
        this.isMainThread = isMainThread;
    }

    public void post() {
        if (isMainThread) {
            callback.execute();
        } else {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> {
                callback.execute();
            });
        }
    }

}
