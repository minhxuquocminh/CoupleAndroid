package com.example.couple.Custom.Handler.Sync;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SyncState {
    NOT_YET(0, "chưa đồng bộ"),
    OK(1, "đã đồng bộ"),
    NETWORK_ERROR(2, "lỗi mạng");

    public final int value;
    public final String name;
}
