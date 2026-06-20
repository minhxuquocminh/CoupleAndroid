package com.example.couple.Custom.Handler.Agent;

import java.util.List;

public interface AgentModelCallback {
    void onSuccess(List<String> models);

    void onError(String errorMessage);
}
