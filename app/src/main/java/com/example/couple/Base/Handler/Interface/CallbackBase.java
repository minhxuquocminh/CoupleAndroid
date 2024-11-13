package com.example.couple.Base.Handler.Interface;

public interface CallbackBase<P, T> {
    T execute(P param);
}
