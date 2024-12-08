package com.example.couple.Model.Bridge.Connected;

import com.example.couple.Model.Bridge.Position;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SupportTriad {
    int value;
    Position position;
    List<Integer> statusList;
}
