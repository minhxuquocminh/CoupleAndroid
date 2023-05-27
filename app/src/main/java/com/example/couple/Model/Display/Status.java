package com.example.couple.Model.Display;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Status { // để lưu trạng thái cầu bộ 3
    int first;
    int second;
    int third;

    public boolean haveZeroTouch() {
        return first == 0 || second == 0 || third == 0;
    }

    public int getSum() {
        return first + second + third;
    }

    public boolean equalsStatus(Status status) {
        return first == status.getFirst() && second == status.getSecond() && third == status.getThird();
    }

    public String show() {
        return first + "-" + second + "-" + third;
    }
}
