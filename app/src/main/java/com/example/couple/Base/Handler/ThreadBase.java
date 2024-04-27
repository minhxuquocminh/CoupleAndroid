package com.example.couple.Base.Handler;

import lombok.Getter;

public class ThreadBase<T> extends Thread {

    private final ThreadCallback<T> callback;
    private final T param;
    @Getter
    private Object result; // dùng threadBase.join() trước khi get result...

    public ThreadBase(ThreadCallback<T> callback, T param) {
        this.callback = callback;
        this.param = param;
    }

    @Override
    public void run() {
        result = callback.run(param);
    }

}
