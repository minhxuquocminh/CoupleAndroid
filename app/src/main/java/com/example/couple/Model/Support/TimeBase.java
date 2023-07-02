package com.example.couple.Model.Support;

import com.example.couple.Base.Handler.DateBase;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TimeBase {
    DateBase dateBase;
    DateLunar dateLunar;
    DateCycle dateCycle;

    public static TimeBase getEmpty() {
        return new TimeBase(DateBase.getEmpty(), DateLunar.getEmpty(), DateCycle.getEmpty());
    }

    public boolean isEmpty() {
        return dateBase.isEmpty() || dateLunar.isEmpty() || dateCycle.isEmpty();
    }
}
