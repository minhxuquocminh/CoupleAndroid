package com.example.couple.Custom.Handler.Agent;

import com.example.couple.Model.Agent.AgentMessage;

public interface AgentCallback {
    void onSuccess(AgentMessage message);

    void onError(String errorMessage);
}
