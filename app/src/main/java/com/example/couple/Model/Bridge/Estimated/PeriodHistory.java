package com.example.couple.Model.Bridge.Estimated;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Model.DateTime.Date.DateBase;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class PeriodHistory {
    DateBase startDate;
    DateBase endDate;
    List<Integer> numbers;

    public String show() {
        String show = " - Từ " + startDate.showFullChars() + " đến " + endDate.showFullChars() + " có các số: \n";
        for (int num : numbers) {
            show += "\t\t\t" + CoupleBase.showCouple(num) + "\n";
        }
        return show.trim();
    }

    public int getLastNumber() {
        return numbers.get(numbers.size() - 1);
    }

}
