package com.example.couple.Model.Display;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HeadTail {
    List<BSingle> headList;
    List<BSingle> tailList;

    public String show() {
        String show = "\n - Đầu ";
        for (int i = 0; i < headList.size(); i++) {
            show += headList.get(i).getNumber() + " (" + headList.get(i).getLevel() + " lần)";
            if (i != headList.size() - 1) {
                show += ", ";
            }
        }
        show += "\n - Đuôi ";
        for (int i = 0; i < tailList.size(); i++) {
            show += tailList.get(i).getNumber() + " (" + tailList.get(i).getLevel() + " lần)";
            if (i != tailList.size() - 1) {
                show += ", ";
            }
        }
        return show;
    }
}
