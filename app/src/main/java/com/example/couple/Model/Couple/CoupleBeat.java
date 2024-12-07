package com.example.couple.Model.Couple;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Model.Origin.Couple;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CoupleBeat {
    Couple couple;
    int beat;

    public String show() {
        return couple.show() + " (" + CoupleBase.showCouple(beat) + ")";
    }
}
