package com.example.couple.Custom.Handler.Agent;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import com.example.couple.Model.Agent.AiPrediction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class AiPredictionHistoryHandler {
    private static final String PREF_NAME = "AI_PREDICTION_HISTORY";
    private static final String KEY_PREDICTIONS = "PREDICTIONS";
    private static final int MAX_PREDICTIONS = 120;

    public static void savePrediction(Context context, String predictionDate, String type,
                                      String modelName, String content, String localStats) {
        savePrediction(context, predictionDate, "", type, modelName, content, localStats);
    }

    public static void savePrediction(Context context, String predictionDate, String sourceLastDate, String type,
                                      String modelName, String content, String localStats) {
        if (content == null || content.trim().isEmpty()) return;

        long now = System.currentTimeMillis();
        AiPrediction prediction = new AiPrediction(UUID.randomUUID().toString(),
                predictionDate == null ? "" : predictionDate,
                sourceLastDate == null ? "" : sourceLastDate,
                type == null ? "" : type,
                modelName == null ? "" : modelName,
                content,
                localStats == null ? "" : localStats,
                now);

        List<AiPrediction> predictions = getPredictions(context);
        predictions.add(0, prediction);
        if (predictions.size() > MAX_PREDICTIONS) {
            predictions = new ArrayList<>(predictions.subList(0, MAX_PREDICTIONS));
        }

        context.getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit()
                .putString(KEY_PREDICTIONS, serializePredictions(predictions))
                .apply();
    }

    public static List<AiPrediction> getPredictions(Context context) {
        String data = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                .getString(KEY_PREDICTIONS, "");
        List<AiPrediction> predictions = parsePredictions(data);
        Collections.sort(predictions, (first, second) ->
                Long.compare(second.getCreatedAt(), first.getCreatedAt()));
        return predictions;
    }

    public static void deletePrediction(Context context, String predictionId) {
        if (predictionId == null || predictionId.trim().isEmpty()) return;

        List<AiPrediction> keptPredictions = new ArrayList<>();
        for (AiPrediction prediction : getPredictions(context)) {
            if (!prediction.getId().equals(predictionId)) keptPredictions.add(prediction);
        }

        context.getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit()
                .putString(KEY_PREDICTIONS, serializePredictions(keptPredictions))
                .apply();
    }

    private static List<AiPrediction> parsePredictions(String data) {
        List<AiPrediction> predictions = new ArrayList<>();
        if (data == null || data.trim().isEmpty()) return predictions;

        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                predictions.add(new AiPrediction(
                        item.optString("id", ""),
                        item.optString("predictionDate", ""),
                        item.optString("sourceLastDate", ""),
                        item.optString("type", ""),
                        item.optString("modelName", ""),
                        item.optString("content", ""),
                        item.optString("localStats", ""),
                        item.optLong("createdAt", System.currentTimeMillis())
                ));
            }
        } catch (Exception ignored) {
        }
        return predictions;
    }

    private static String serializePredictions(List<AiPrediction> predictions) {
        JSONArray jsonArray = new JSONArray();
        try {
            for (AiPrediction prediction : predictions) {
                JSONObject item = new JSONObject();
                item.put("id", prediction.getId());
                item.put("predictionDate", prediction.getPredictionDate());
                item.put("sourceLastDate", prediction.getSourceLastDate());
                item.put("type", prediction.getType());
                item.put("modelName", prediction.getModelName());
                item.put("content", prediction.getContent());
                item.put("localStats", prediction.getLocalStats());
                item.put("createdAt", prediction.getCreatedAt());
                jsonArray.put(item);
            }
        } catch (Exception ignored) {
        }
        return jsonArray.toString();
    }
}
