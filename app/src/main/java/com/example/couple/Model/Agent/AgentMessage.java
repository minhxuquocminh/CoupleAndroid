package com.example.couple.Model.Agent;

import com.example.couple.Model.DateTime.Time.TimeBase;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class AgentMessage {
    public enum Role {
        USER,
        ASSISTANT
    }

    Role role;
    String content;
    String modelName;
    long responseTimeMs;
    List<String> imageUris;
    List<String> imageMimeTypes;
    TimeBase createdTime;

    public AgentMessage(Role role, String content) {
        this(role, content, "");
    }

    public AgentMessage(Role role, String content, String modelName) {
        this(role, content, modelName, new ArrayList<>(), new ArrayList<>());
    }

    public AgentMessage(Role role, String content, String modelName, long responseTimeMs) {
        this(role, content, modelName, responseTimeMs, new ArrayList<>(), new ArrayList<>());
    }

    public AgentMessage(Role role, String content, String modelName, String imageUri, String imageMimeType) {
        this(role, content, modelName, singleItemList(imageUri), singleItemList(imageMimeType));
    }

    public AgentMessage(Role role, String content, String modelName,
                        List<String> imageUris, List<String> imageMimeTypes) {
        this(role, content, modelName, 0, imageUris, imageMimeTypes);
    }

    public AgentMessage(Role role, String content, String modelName, long responseTimeMs,
                        List<String> imageUris, List<String> imageMimeTypes) {
        this.role = role;
        this.content = content == null ? "" : content;
        this.modelName = modelName == null ? "" : modelName;
        this.responseTimeMs = Math.max(0, responseTimeMs);
        this.imageUris = imageUris == null ? new ArrayList<>() : imageUris;
        this.imageMimeTypes = imageMimeTypes == null ? new ArrayList<>() : imageMimeTypes;
        this.createdTime = TimeBase.current();
    }

    public boolean isUser() {
        return role == Role.USER;
    }

    public boolean hasImage() {
        return imageUris != null && !imageUris.isEmpty();
    }

    public String getImageMimeType(int index) {
        if (imageMimeTypes == null || index < 0 || index >= imageMimeTypes.size()) return "";
        return imageMimeTypes.get(index) == null ? "" : imageMimeTypes.get(index);
    }

    private static List<String> singleItemList(String value) {
        List<String> values = new ArrayList<>();
        if (value != null && !value.trim().isEmpty()) values.add(value);
        return values;
    }
}
