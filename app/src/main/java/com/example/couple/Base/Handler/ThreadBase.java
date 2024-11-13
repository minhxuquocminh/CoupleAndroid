package com.example.couple.Base.Handler;

import com.example.couple.Base.Handler.Interface.CallbackBase;

import lombok.Getter;

// Use: lấy instance.start() để chạy;  instance.join() để get result
public class ThreadBase<P, T> extends Thread {

    private final CallbackBase<P, T> callback;
    private final P param;

    @Getter
    private T result;

    public ThreadBase(CallbackBase<P, T> callback, P param) {
        this.callback = callback;
        this.param = param;
    }

    @Override
    public void run() {
        result = callback.execute(param);
    }

}
