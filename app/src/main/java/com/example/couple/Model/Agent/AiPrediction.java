package com.example.couple.Model.Agent;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AiPrediction {
    String id;
    String predictionDate;
    String sourceLastDate;
    String type;
    String modelName;
    String content;
    String localStats;
    long createdAt;

    public String showTitle() {
        return type + " - " + predictionDate;
    }

    public String showDataRange() {
        if (sourceLastDate == null || sourceLastDate.trim().isEmpty()) return "";
        return "Dữ liệu đến ngày " + sourceLastDate;
    }
}
