package com.example.couple.Base.Handler;

public interface AsyncTaskCallback<K, T> {
    T handler(K... inputs);
}
