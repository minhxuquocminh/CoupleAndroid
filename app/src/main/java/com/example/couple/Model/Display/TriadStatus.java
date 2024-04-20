package com.example.couple.Model.Display;

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

    public String show() {
        return first + "-" + second + "-" + third;
    }
}
