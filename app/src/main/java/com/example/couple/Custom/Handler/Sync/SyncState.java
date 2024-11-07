package com.example.couple.Custom.Handler.Sync;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SyncState {
    NOT_YET(0, "o", "Chưa cập nhật"),
    DONE(1, "-", "Đã cập nhật"),
    NETWORK_ERROR(2, "x", "Lỗi mạng");

    public final int value;
    public final String sign;
    public final String name;
}
