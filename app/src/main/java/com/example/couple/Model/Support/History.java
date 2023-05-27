package com.example.couple.Model.Support;

import com.example.couple.Base.Handler.DateBase;
import com.example.couple.Custom.Const.Const;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class History {
    DateBase startDate;
    DateBase endDate;
    List<Integer> numbers;

    public String show() {
        String show = " - Từ " + startDate.showFullChars() + " đến " + endDate.showFullChars() + " có các số: \n";
        for (int num : numbers) {
            show += "\t\t\t" + num + "\n";
        }
        return show.trim();
    }

    public int getLastNumber() {
        return numbers.get(numbers.size() - 1);
    }

}
