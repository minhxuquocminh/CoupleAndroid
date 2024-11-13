package com.example.couple.Custom.Handler.Sync;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SyncState {
    NOT_YET(0, "-", "Chưa có kết quả"),
    DONE(1, "o", "Đã có kết quả"),
    NETWORK_ERROR(2, "x", "Lỗi mạng");

    public final int value;
    public final String sign;
    public final String name;
}
