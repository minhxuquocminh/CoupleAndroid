package com.example.couple.Custom.Handler.Agent;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import com.example.couple.Model.Agent.AgentSettings;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AgentSettingsHandler {
    private static final String PREF_NAME = "AGENT_SETTINGS";
    private static final String KEY_PROVIDER = "PROVIDER";
    private static final String KEY_BASE_URL = "BASE_URL";
    private static final String KEY_MODEL = "MODEL";
    private static final String KEY_API_KEY = "API_KEY";
    private static final String KEY_PROFILES = "PROFILES";
    private static final String KEY_ACTIVE_PROFILE_ID = "ACTIVE_PROFILE_ID";

    public static final String DEFAULT_PROVIDER = "OpenAI";
    public static final String DEFAULT_BASE_URL = "https://api.openai.com/v1";
    public static final String DEFAULT_MODEL = "gpt-4.1-mini";
    public static final String GEMINI_PROVIDER = "Gemini";
    public static final String GEMINI_BASE_URL = "https://generativelanguage.googleapis.com";
    public static final String GEMINI_MODEL = "gemini-1.5-flash";
    public static final String GROK_PROVIDER = "Grok";
    public static final String GROK_BASE_URL = "https://api.x.ai/v1";
    public static final String GROK_MODEL = "grok-beta";
    public static final String OPEN_ROUTER_PROVIDER = "OpenRouter";
    public static final String OPEN_ROUTER_BASE_URL = "https://openrouter.ai/api/v1";

    public static AgentSettings getSettings(Context context) {
        List<AgentSettings> profiles = getProfiles(context);
        String activeId = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                .getString(KEY_ACTIVE_PROFILE_ID, "");
        for (AgentSettings profile : profiles) {
            if (profile.getId().equals(activeId)) return profile;
        }
        if (!profiles.isEmpty()) return profiles.get(0);

        return getLegacySettings(context);
    }

    public static AgentSettings getSettings(Context context, String profileId) {
        AgentSettings profile = getProfile(context, profileId);
        return profile == null ? getSettings(context) : profile;
    }

    public static AgentSettings getLegacySettings(Context context) {
        return new AgentSettings(
                "legacy",
                DEFAULT_PROVIDER + " - " + context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                        .getString(KEY_MODEL, DEFAULT_MODEL),
                context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                        .getString(KEY_PROVIDER, DEFAULT_PROVIDER),
                context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                        .getString(KEY_BASE_URL, DEFAULT_BASE_URL),
                context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                        .getString(KEY_MODEL, DEFAULT_MODEL),
                context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                        .getString(KEY_API_KEY, "")
        );
    }

    public static void saveSettings(Context context, AgentSettings settings) {
        AgentSettings savedSettings = saveProfile(context, settings);
        setActiveProfileId(context, savedSettings.getId());
        context.getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit()
                .putString(KEY_PROVIDER, savedSettings.getProvider())
                .putString(KEY_BASE_URL, savedSettings.getBaseUrl())
                .putString(KEY_MODEL, savedSettings.getModel())
                .putString(KEY_API_KEY, savedSettings.getApiKey())
                .apply();
    }

    public static List<AgentSettings> getProfiles(Context context) {
        String data = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                .getString(KEY_PROFILES, "");
        List<AgentSettings> profiles = parseProfiles(data);
        if (!profiles.isEmpty()) return profiles;

        AgentSettings legacySettings = getLegacySettings(context);
        if (legacySettings.hasApiKey()) profiles.add(legacySettings);
        return profiles;
    }

    public static AgentSettings saveProfile(Context context, AgentSettings settings) {
        List<AgentSettings> profiles = getProfiles(context);
        AgentSettings safeSettings = ensureId(settings);
        boolean updated = false;

        for (int i = 0; i < profiles.size(); i++) {
            if (profiles.get(i).getId().equals(safeSettings.getId())) {
                profiles.set(i, safeSettings);
                updated = true;
                break;
            }
        }
        if (!updated) profiles.add(safeSettings);

        context.getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit()
                .putString(KEY_PROFILES, serializeProfiles(profiles))
                .apply();
        return safeSettings;
    }

    public static AgentSettings getProfile(Context context, String profileId) {
        if (profileId == null || profileId.trim().isEmpty()) return null;

        for (AgentSettings profile : getProfiles(context)) {
            if (profile.getId().equals(profileId)) return profile;
        }
        return null;
    }

    public static boolean deleteProfile(Context context, String profileId) {
        if (profileId == null || profileId.trim().isEmpty()) return false;

        List<AgentSettings> profiles = getProfiles(context);
        boolean deleted = false;
        for (int i = profiles.size() - 1; i >= 0; i--) {
            if (profiles.get(i).getId().equals(profileId)) {
                profiles.remove(i);
                deleted = true;
            }
        }
        if (!deleted) return false;

        String activeId = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                .getString(KEY_ACTIVE_PROFILE_ID, "");
        String newActiveId = activeId;
        if (profileId.equals(activeId) || newActiveId.trim().isEmpty()) {
            newActiveId = profiles.isEmpty() ? "" : profiles.get(0).getId();
        }

        android.content.SharedPreferences.Editor editor =
                context.getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit()
                        .putString(KEY_PROFILES, serializeProfiles(profiles))
                        .putString(KEY_ACTIVE_PROFILE_ID, newActiveId);

        if (profiles.isEmpty()) {
            editor.remove(KEY_PROVIDER)
                    .remove(KEY_BASE_URL)
                    .remove(KEY_MODEL)
                    .remove(KEY_API_KEY);
        } else if (!newActiveId.equals(activeId)) {
            AgentSettings activeProfile = profiles.get(0);
            for (AgentSettings profile : profiles) {
                if (profile.getId().equals(newActiveId)) {
                    activeProfile = profile;
                    break;
                }
            }
            editor.putString(KEY_PROVIDER, activeProfile.getProvider())
                    .putString(KEY_BASE_URL, activeProfile.getBaseUrl())
                    .putString(KEY_MODEL, activeProfile.getModel())
                    .putString(KEY_API_KEY, activeProfile.getApiKey());
        }

        editor.apply();
        return true;
    }

    public static boolean hasProfileWithModel(Context context, AgentSettings settings) {
        for (AgentSettings profile : getProfiles(context)) {
            if (!sameText(profile.getId(), settings.getId())
                    && sameText(profile.getProvider(), settings.getProvider())
                    && sameText(profile.getBaseUrl(), settings.getBaseUrl())
                    && sameText(profile.getModel(), settings.getModel())) {
                return true;
            }
        }
        return false;
    }

    public static void setActiveProfileId(Context context, String profileId) {
        context.getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit()
                .putString(KEY_ACTIVE_PROFILE_ID, profileId == null ? "" : profileId)
                .apply();
    }

    private static AgentSettings ensureId(AgentSettings settings) {
        String id = settings.getId();
        if (id == null || id.trim().isEmpty() || "legacy".equals(id)) {
            id = UUID.randomUUID().toString();
        }
        String name = settings.getName();
        if (name == null || name.trim().isEmpty()) {
            name = settings.getProvider() + " - " + settings.getModel();
        }
        return new AgentSettings(id, name, settings.getProvider(), settings.getBaseUrl(),
                settings.getModel(), settings.getApiKey());
    }

    private static boolean sameText(String first, String second) {
        String safeFirst = first == null ? "" : first.trim();
        String safeSecond = second == null ? "" : second.trim();
        return safeFirst.equalsIgnoreCase(safeSecond);
    }

    private static List<AgentSettings> parseProfiles(String data) {
        List<AgentSettings> profiles = new ArrayList<>();
        if (data == null || data.trim().isEmpty()) return profiles;

        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                profiles.add(new AgentSettings(
                        item.optString("id", ""),
                        item.optString("name", ""),
                        item.optString("provider", DEFAULT_PROVIDER),
                        item.optString("baseUrl", DEFAULT_BASE_URL),
                        item.optString("model", DEFAULT_MODEL),
                        item.optString("apiKey", "")
                ));
            }
        } catch (Exception ignored) {
        }
        return profiles;
    }

    private static String serializeProfiles(List<AgentSettings> profiles) {
        JSONArray jsonArray = new JSONArray();
        try {
            for (AgentSettings profile : profiles) {
                JSONObject item = new JSONObject();
                item.put("id", profile.getId());
                item.put("name", profile.getName());
                item.put("provider", profile.getProvider());
                item.put("baseUrl", profile.getBaseUrl());
                item.put("model", profile.getModel());
                item.put("apiKey", profile.getApiKey());
                jsonArray.put(item);
            }
        } catch (Exception ignored) {
        }
        return jsonArray.toString();
    }
}
