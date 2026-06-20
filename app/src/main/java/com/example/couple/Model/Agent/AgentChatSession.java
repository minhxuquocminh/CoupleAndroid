package com.example.couple.Model.Agent;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class AgentChatSession {
    String id;
    String title;
    List<AgentMessage> messages;
    String activeProfileId;
    long createdAt;
    long updatedAt;

    public AgentChatSession(String id, String title, List<AgentMessage> messages,
                            long createdAt, long updatedAt) {
        this(id, title, messages, "", createdAt, updatedAt);
    }

    public AgentChatSession(String id, String title, List<AgentMessage> messages,
                            String activeProfileId, long createdAt, long updatedAt) {
        this.id = id;
        this.title = title == null || title.trim().isEmpty() ? "Chat mới" : title;
        this.messages = messages == null ? new ArrayList<>() : messages;
        this.activeProfileId = activeProfileId == null ? "" : activeProfileId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setTitle(String title) {
        if (title != null && !title.trim().isEmpty()) {
            this.title = title.trim();
        }
    }

    public void setMessages(List<AgentMessage> messages) {
        this.messages = messages == null ? new ArrayList<>() : messages;
    }

    public void setActiveProfileId(String activeProfileId) {
        this.activeProfileId = activeProfileId == null ? "" : activeProfileId;
    }

    public void touch() {
        updatedAt = System.currentTimeMillis();
    }
}
