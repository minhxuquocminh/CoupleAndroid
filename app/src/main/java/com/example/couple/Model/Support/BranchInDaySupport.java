package com.example.couple.Model.Support;

import com.example.couple.Model.DateTime.Date.Cycle.Branch;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BranchInDaySupport {
    Branch lastBranchInDay;
    int dayNumberBefore;
}
