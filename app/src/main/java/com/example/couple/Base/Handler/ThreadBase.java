package com.example.couple.Base.Handler;

import android.os.Handler;
import android.os.Looper;

public class ThreadBase <T> extends Thread{

    private ThreadCallback<T> callback;
    private T param;

    public ThreadBase(ThreadCallback<T> callback, T param) {
        this.callback = callback;
        this.param = param;
    }

    @Override
    public void run() {
        callback.run(param);
    }

}
