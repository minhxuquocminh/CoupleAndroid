package com.example.couple.Model.Display;

import com.example.couple.Model.Time.DateBase;
import com.example.couple.Model.Support.NumberArray;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Prediction implements Serializable {
    String id;
    String name;
    String time;
    String winningTime;
    int runs;
    int lost;
    String triads;
    NumberArray numberArray;
    String info;
    DateBase lastUpdate;
    int type; // 1: week, 2: month

    public String toTimeName() {
        return name + " (" + time + ")";
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
//        result.put("id", id); // khong duoc put vi se lam thay doi id tren firebase
        result.put("name", name);
        result.put("time", time);
        result.put("winningTime", winningTime);
        result.put("runs", runs);
        result.put("lost", lost);
        result.put("triads", triads);
        result.put("numberArray", numberArray);
        result.put("lastUpdate", lastUpdate);
        result.put("info", info);
        result.put("type", type);
        return result;
    }

}
