package com.example.couple.Base.Handler;

import android.os.AsyncTask;

public class AsyncTaskBase<K, T> extends AsyncTask<K, Void, T> {
    private final AsyncTaskCallback callback;

    public AsyncTaskBase(AsyncTaskCallback<K, T> callback) {
        this.callback = callback;
    }

    @SafeVarargs
    @Override
    protected final T doInBackground(K... inputs) {
        return (T) callback.handle(inputs);
    }

}
