package com.example.couple.Model.Bridge.Connected;

import androidx.annotation.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TriadStatus { // để lưu trạng thái cầu bộ 3
    int first;
    int second;
    int third;

    public boolean haveZeroTouch() {
        return first == 0 || second == 0 || third == 0;
    }

    @NonNull
    @Override
    public String toString() {
        return first + "-" + second + "-" + third;
    }
}
