package com.example.couple.Model.Display;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CompactSingles {
    List<Integer> compactList; // là các single number
    List<Integer> duplicateList; // là các single number

    public String show() {
        String show = "";
        for (int i = 0; i < compactList.size(); i++) {
            show += compactList.get(i);
            if (i != compactList.size() - 1) {
                show += ", ";
            }
        }
        if (!duplicateList.isEmpty()) show += "; lặp lại: ";
        for (int i = 0; i < duplicateList.size(); i++) {
            show += duplicateList.get(i);
            if (i != duplicateList.size() - 1) {
                show += ", ";
            }
        }
        return show;
    }
}
