package com.example.couple.Model.Bridge.Connected;

import com.example.couple.Model.DateTime.Date.DateBase;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DayPositions {
    DateBase dateBase;
    List<Position> positions;
}
