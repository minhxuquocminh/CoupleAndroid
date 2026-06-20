package com.example.couple.Custom.Handler.Agent;

import android.content.Context;

import com.example.couple.Model.Agent.AgentMessage;
import com.example.couple.Model.Agent.AgentSettings;

import java.util.List;

public interface AgentClient {
    AgentRequest send(Context context, AgentSettings settings, List<AgentMessage> history, AgentMessage userMessage,
                      AgentCallback callback);
}
