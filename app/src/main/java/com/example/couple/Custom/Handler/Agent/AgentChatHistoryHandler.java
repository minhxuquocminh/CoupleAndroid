package com.example.couple.Custom.Handler.Agent;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import com.example.couple.Model.Agent.AgentChatSession;
import com.example.couple.Model.Agent.AgentMessage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class AgentChatHistoryHandler {
    private static final String PREF_NAME = "AGENT_CHAT_HISTORY";
    private static final String KEY_SESSIONS = "SESSIONS";
    private static final String KEY_ACTIVE_SESSION_ID = "ACTIVE_SESSION_ID";
    private static final int MAX_SESSIONS = 30;
    private static final int MAX_MESSAGES_PER_SESSION = 80;

    public static final String NEW_CHAT_TITLE = "Chat mới";

    public static AgentChatSession createDraftSession() {
        long now = System.currentTimeMillis();
        return new AgentChatSession(UUID.randomUUID().toString(), NEW_CHAT_TITLE,
                new ArrayList<>(), now, now);
    }

    public static AgentChatSession createSession(Context context) {
        AgentChatSession session = createDraftSession();
        saveSession(context, session);
        setActiveSessionId(context, session.getId());
        return session;
    }

    public static AgentChatSession getActiveSession(Context context) {
        String activeId = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                .getString(KEY_ACTIVE_SESSION_ID, "");
        return getSession(context, activeId);
    }

    public static AgentChatSession getSession(Context context, String sessionId) {
        if (sessionId == null || sessionId.trim().isEmpty()) return null;
        for (AgentChatSession session : getSessions(context)) {
            if (session.getId().equals(sessionId)) return session;
        }
        return null;
    }

    public static List<AgentChatSession> getSessions(Context context) {
        String data = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                .getString(KEY_SESSIONS, "");
        List<AgentChatSession> sessions = parseSessions(data);
        List<AgentChatSession> visibleSessions = new ArrayList<>();
        for (AgentChatSession session : sessions) {
            if (!session.getMessages().isEmpty()) visibleSessions.add(session);
        }
        sessions = visibleSessions;
        Collections.sort(sessions, (first, second) ->
                Long.compare(second.getUpdatedAt(), first.getUpdatedAt()));
        return sessions;
    }

    public static void saveSession(Context context, AgentChatSession session) {
        if (session == null || session.getMessages().isEmpty()) return;

        session.setMessages(trimMessages(session.getMessages()));
        session.touch();

        List<AgentChatSession> sessions = getSessions(context);
        boolean updated = false;
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).getId().equals(session.getId())) {
                sessions.set(i, session);
                updated = true;
                break;
            }
        }
        if (!updated) sessions.add(session);

        Collections.sort(sessions, (first, second) ->
                Long.compare(second.getUpdatedAt(), first.getUpdatedAt()));
        if (sessions.size() > MAX_SESSIONS) {
            sessions = new ArrayList<>(sessions.subList(0, MAX_SESSIONS));
        }

        context.getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit()
                .putString(KEY_SESSIONS, serializeSessions(sessions))
                .apply();
    }

    public static void setActiveSessionId(Context context, String sessionId) {
        context.getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit()
                .putString(KEY_ACTIVE_SESSION_ID, sessionId == null ? "" : sessionId)
                .apply();
    }

    public static void deleteSession(Context context, String sessionId) {
        if (sessionId == null || sessionId.trim().isEmpty()) return;

        List<AgentChatSession> sessions = getSessions(context);
        List<AgentChatSession> keptSessions = new ArrayList<>();
        for (AgentChatSession session : sessions) {
            if (!session.getId().equals(sessionId)) keptSessions.add(session);
        }

        String activeId = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                .getString(KEY_ACTIVE_SESSION_ID, "");
        context.getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit()
                .putString(KEY_SESSIONS, serializeSessions(keptSessions))
                .putString(KEY_ACTIVE_SESSION_ID, sessionId.equals(activeId) ? "" : activeId)
                .apply();
    }

    public static String createTitle(String message) {
        String safeMessage = message == null ? "" : message.trim().replaceAll("\\s+", " ");
        if (safeMessage.isEmpty()) return NEW_CHAT_TITLE;
        return safeMessage.length() <= 40 ? safeMessage : safeMessage.substring(0, 40) + "...";
    }

    private static List<AgentMessage> trimMessages(List<AgentMessage> messages) {
        if (messages == null || messages.size() <= MAX_MESSAGES_PER_SESSION) return messages;
        return new ArrayList<>(messages.subList(messages.size() - MAX_MESSAGES_PER_SESSION, messages.size()));
    }

    private static List<AgentChatSession> parseSessions(String data) {
        List<AgentChatSession> sessions = new ArrayList<>();
        if (data == null || data.trim().isEmpty()) return sessions;

        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                sessions.add(new AgentChatSession(
                        item.optString("id", ""),
                        item.optString("title", NEW_CHAT_TITLE),
                        parseMessages(item.optJSONArray("messages")),
                        item.optString("activeProfileId", ""),
                        item.optLong("createdAt", System.currentTimeMillis()),
                        item.optLong("updatedAt", System.currentTimeMillis())
                ));
            }
        } catch (Exception ignored) {
        }
        return sessions;
    }

    private static List<AgentMessage> parseMessages(JSONArray jsonArray) {
        List<AgentMessage> messages = new ArrayList<>();
        if (jsonArray == null) return messages;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.optJSONObject(i);
            if (item == null) continue;

            AgentMessage.Role role = "user".equals(item.optString("role", "assistant"))
                    ? AgentMessage.Role.USER : AgentMessage.Role.ASSISTANT;
            messages.add(new AgentMessage(role,
                    item.optString("content", ""),
                    item.optString("modelName", ""),
                    item.optLong("responseTimeMs", 0),
                    parseStringList(item.optJSONArray("imageUris"), item.optString("imageUri", "")),
                    parseStringList(item.optJSONArray("imageMimeTypes"), item.optString("imageMimeType", ""))));
        }
        return messages;
    }

    private static List<String> parseStringList(JSONArray jsonArray, String legacyValue) {
        List<String> values = new ArrayList<>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                String value = jsonArray.optString(i, "");
                if (!value.trim().isEmpty()) values.add(value);
            }
        }
        if (values.isEmpty() && legacyValue != null && !legacyValue.trim().isEmpty()) {
            values.add(legacyValue);
        }
        return values;
    }

    private static String serializeSessions(List<AgentChatSession> sessions) {
        JSONArray jsonArray = new JSONArray();
        try {
            for (AgentChatSession session : sessions) {
                if (session.getMessages().isEmpty()) continue;

                JSONObject item = new JSONObject();
                item.put("id", session.getId());
                item.put("title", session.getTitle());
                item.put("activeProfileId", session.getActiveProfileId());
                item.put("createdAt", session.getCreatedAt());
                item.put("updatedAt", session.getUpdatedAt());
                item.put("messages", serializeMessages(session.getMessages()));
                jsonArray.put(item);
            }
        } catch (Exception ignored) {
        }
        return jsonArray.toString();
    }

    private static JSONArray serializeMessages(List<AgentMessage> messages) throws Exception {
        JSONArray jsonArray = new JSONArray();
        if (messages == null) return jsonArray;

        for (AgentMessage message : messages) {
            JSONObject item = new JSONObject();
            item.put("role", message.isUser() ? "user" : "assistant");
            item.put("content", message.getContent());
            item.put("modelName", message.getModelName());
            item.put("responseTimeMs", message.getResponseTimeMs());
            item.put("imageUris", serializeStringList(message.getImageUris()));
            item.put("imageMimeTypes", serializeStringList(message.getImageMimeTypes()));
            jsonArray.put(item);
        }
        return jsonArray;
    }

    private static JSONArray serializeStringList(List<String> values) {
        JSONArray jsonArray = new JSONArray();
        if (values == null) return jsonArray;

        for (String value : values) {
            jsonArray.put(value == null ? "" : value);
        }
        return jsonArray;
    }
}
