package com.example.couple.Model.Agent;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AgentSettings {
    String id;
    String name;
    String provider;
    String baseUrl;
    String model;
    String apiKey;

    public AgentSettings(String provider, String baseUrl, String model, String apiKey) {
        this(provider + " - " + model, provider + " - " + model, provider, baseUrl, model, apiKey);
    }

    public String showName() {
        String providerName = provider == null ? "" : provider.trim();
        String modelName = model == null ? "" : model.trim();
        if (!providerName.isEmpty() && !modelName.isEmpty()) return providerName + " - " + modelName;
        if (name != null && !name.trim().isEmpty()) return name;
        return providerName + modelName;
    }

    public boolean hasApiKey() {
        return apiKey != null && !apiKey.trim().isEmpty();
    }
}
