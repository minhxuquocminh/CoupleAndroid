package com.example.couple.Base.Handler.Interface;

public interface AsyncCallback<K, T> {
    T handle(K... inputs);
}
