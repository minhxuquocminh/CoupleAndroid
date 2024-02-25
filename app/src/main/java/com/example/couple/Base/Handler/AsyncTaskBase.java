package com.example.couple.Base.Handler;

import android.os.AsyncTask;

public class AsyncTaskBase<K, T> extends AsyncTask<K, Void, T> {
    private AsyncTaskCallback callback;

    public AsyncTaskBase(AsyncTaskCallback callback) {
        this.callback = callback;
    }

    @Override
    protected T doInBackground(K... inputs) {
        return (T) callback.handler(inputs);
    }

}
