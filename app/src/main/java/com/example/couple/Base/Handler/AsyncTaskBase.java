package com.example.couple.Base.Handler;

import android.os.AsyncTask;

import com.example.couple.Base.Handler.Interface.AsyncCallback;

public class AsyncTaskBase<K, T> extends AsyncTask<K, Void, T> {
    private final AsyncCallback<K, T> callback;

    public AsyncTaskBase(AsyncCallback<K, T> callback) {
        super();
        this.callback = callback;
    }

    @SafeVarargs
    @Override
    protected final T doInBackground(K... inputs) {
        return callback.handle(inputs);
    }

}
