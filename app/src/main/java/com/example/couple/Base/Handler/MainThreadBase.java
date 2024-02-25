package com.example.couple.Base.Handler;

import android.os.Handler;
import android.os.Looper;

public class MainThreadBase {
    MainThreadCallback callback;
    boolean isMainThread; // nếu đã là main thread thì ko cần chuyển về chính nó nữa

    public MainThreadBase(MainThreadCallback callback, boolean isMainThread) {
        this.callback = callback;
        this.isMainThread = isMainThread;
    }

    public void post() {
        if (isMainThread) {
            callback.postMainThread();
        } else {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> {
                callback.postMainThread();
            });
        }
    }

}
