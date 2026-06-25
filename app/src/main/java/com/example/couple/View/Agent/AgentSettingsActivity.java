package com.example.couple.View.Agent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.couple.Base.View.ActivityBase;
import com.example.couple.Base.View.SpinnerBase;
import com.example.couple.Custom.Handler.Agent.AgentModelCallback;
import com.example.couple.Custom.Handler.Agent.AgentModelFetcher;
import com.example.couple.Custom.Handler.Agent.AgentSettingsHandler;
import com.example.couple.Model.Agent.AgentSettings;
import com.example.couple.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class AgentSettingsActivity extends ActivityBase {
    public static final String EXTRA_PROFILE_ID = "EXTRA_PROFILE_ID";
    public static final String EXTRA_NEW_PROFILE = "EXTRA_NEW_PROFILE";
    private static final String PROVIDER_CUSTOM = "Custom";
    private static final String[] PROVIDERS = new String[]{
            AgentSettingsHandler.DEFAULT_PROVIDER,
            AgentSettingsHandler.GEMINI_PROVIDER,
            AgentSettingsHandler.GROK_PROVIDER,
            AgentSettingsHandler.OPEN_ROUTER_PROVIDER,
            PROVIDER_CUSTOM
    };

    EditText edtProfileName;
    Spinner spnProvider;
    EditText edtProvider;
    EditText edtBaseUrl;
    EditText edtModel;
    EditText edtApiKey;
    Button btnLoadModels;
    Button btnAdd;
    String editingProfileId = "";
    boolean bindingProfile = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_settings);

        edtProfileName = findViewById(R.id.edtProfileName);
        spnProvider = findViewById(R.id.spnProvider);
        edtProvider = findViewById(R.id.edtProvider);
        edtBaseUrl = findViewById(R.id.edtBaseUrl);
        edtModel = findViewById(R.id.edtModel);
        edtApiKey = findViewById(R.id.edtApiKey);
        btnLoadModels = findViewById(R.id.btnLoadModels);
        btnAdd = findViewById(R.id.btnAdd);

        bindProviderSpinner();
        bindData();
        bindEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AgentSettings editingSettings = AgentSettingsHandler.getProfile(this, editingProfileId);
        if (editingSettings != null) {
            bindProfile(editingSettings);
            return;
        }
        if (editingProfileId.trim().isEmpty()) bindProfile(AgentSettingsHandler.getSettings(this));
    }

    private void bindData() {
        if (getIntent().getBooleanExtra(EXTRA_NEW_PROFILE, false)) {
            bindProfile(new AgentSettings(
                    "",
                    AgentSettingsHandler.DEFAULT_PROVIDER + " - " + AgentSettingsHandler.DEFAULT_MODEL,
                    AgentSettingsHandler.DEFAULT_PROVIDER,
                    AgentSettingsHandler.DEFAULT_BASE_URL,
                    AgentSettingsHandler.DEFAULT_MODEL,
                    ""
            ));
            return;
        }
        String profileId = getIntent().getStringExtra(EXTRA_PROFILE_ID);
        AgentSettings profile = AgentSettingsHandler.getProfile(this, profileId);
        bindProfile(profile == null ? AgentSettingsHandler.getSettings(this) : profile);
    }

    private void bindProviderSpinner() {
        SpinnerBase.bindFilterSpinner(this, spnProvider, PROVIDERS);
        spnProvider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (bindingProfile) return;
                applyProviderSelection(PROVIDERS[position], false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void bindEvents() {
        btnLoadModels.setOnClickListener(view -> loadModels());

        btnAdd.setOnClickListener(view -> {
            AgentSettings settings = getEditingSettings();
            if (!validateSettings(settings)) return;
            if (AgentSettingsHandler.hasProfileWithModel(this, settings)) {
                Toast.makeText(this, "Model này đã có trong danh sách.", Toast.LENGTH_SHORT).show();
                return;
            }
            AgentSettingsHandler.saveSettings(this, settings);
            bindProfile(AgentSettingsHandler.getSettings(this));
            Toast.makeText(this, "Đã thêm model.", Toast.LENGTH_SHORT).show();
        });
    }

    private boolean validateSettings(AgentSettings settings) {
        clearFieldErrors();

        if (isEmpty(settings.getProvider())) {
            edtProvider.setVisibility(View.VISIBLE);
            edtProvider.setError("Nhap provider.");
            edtProvider.requestFocus();
            Toast.makeText(this, "Vui long nhap provider.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (isEmpty(settings.getBaseUrl())) {
            edtBaseUrl.setError("Nhap Base URL.");
            edtBaseUrl.requestFocus();
            Toast.makeText(this, "Vui long nhap Base URL.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidUrl(settings.getBaseUrl())) {
            edtBaseUrl.setError("Base URL khong hop le.");
            edtBaseUrl.requestFocus();
            Toast.makeText(this, "Base URL khong hop le.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (isEmpty(settings.getModel())) {
            edtModel.setError("Nhap model.");
            edtModel.requestFocus();
            Toast.makeText(this, "Vui long nhap model.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!settings.hasApiKey()) {
            edtApiKey.setError("Nhap API key.");
            edtApiKey.requestFocus();
            Toast.makeText(this, "Vui long nhap API key.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void clearFieldErrors() {
        edtProvider.setError(null);
        edtBaseUrl.setError(null);
        edtModel.setError(null);
        edtApiKey.setError(null);
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isValidUrl(String value) {
        try {
            URL url = new URL(value);
            String protocol = url.getProtocol();
            return ("http".equalsIgnoreCase(protocol) || "https".equalsIgnoreCase(protocol))
                    && url.getHost() != null
                    && !url.getHost().trim().isEmpty();
        } catch (MalformedURLException e) {
            return false;
        }
    }

    private void fillPreset(String provider, String baseUrl, String model) {
        editingProfileId = "";
        edtProfileName.setText(provider + " - " + model);
        edtProvider.setText(provider);
        edtBaseUrl.setText(baseUrl);
        edtModel.setText(model);
    }

    private void applyProviderSelection(String provider, boolean keepValues) {
        boolean customProvider = PROVIDER_CUSTOM.equals(provider);
        edtProvider.setVisibility(customProvider ? View.VISIBLE : View.GONE);
        edtBaseUrl.setEnabled(customProvider || AgentSettingsHandler.OPEN_ROUTER_PROVIDER.equals(provider));

        if (keepValues) return;
        if (customProvider) {
            edtApiKey.setText("");
            return;
        }

        String model = getDefaultModel(provider);
        fillPreset(provider, getDefaultBaseUrl(provider), model);
        bindProviderApiKey(provider);
    }

    private void bindProviderApiKey(String provider) {
        for (AgentSettings profile : AgentSettingsHandler.getProfiles(this)) {
            if (profile.getProvider() == null || profile.getApiKey() == null) continue;
            if (profile.getApiKey().trim().isEmpty()) continue;
            if (profile.getProvider().trim().equalsIgnoreCase(provider.trim())) {
                edtApiKey.setText(profile.getApiKey());
                return;
            }
        }
        edtApiKey.setText("");
    }

    private String getDefaultBaseUrl(String provider) {
        if (AgentSettingsHandler.GEMINI_PROVIDER.equals(provider)) {
            return AgentSettingsHandler.GEMINI_BASE_URL;
        }
        if (AgentSettingsHandler.GROK_PROVIDER.equals(provider)) {
            return AgentSettingsHandler.GROK_BASE_URL;
        }
        if (AgentSettingsHandler.OPEN_ROUTER_PROVIDER.equals(provider)) {
            return AgentSettingsHandler.OPEN_ROUTER_BASE_URL;
        }
        return AgentSettingsHandler.DEFAULT_BASE_URL;
    }

    private String getDefaultModel(String provider) {
        if (AgentSettingsHandler.GEMINI_PROVIDER.equals(provider)) {
            return AgentSettingsHandler.GEMINI_MODEL;
        }
        if (AgentSettingsHandler.GROK_PROVIDER.equals(provider)) {
            return AgentSettingsHandler.GROK_MODEL;
        }
        if (AgentSettingsHandler.OPEN_ROUTER_PROVIDER.equals(provider)) {
            return "";
        }
        return AgentSettingsHandler.DEFAULT_MODEL;
    }

    private void loadModels() {
        AgentSettings settings = getEditingSettings();
        if (!settings.hasApiKey()) {
            Toast.makeText(this, "Nhập API key trước khi tải model.", Toast.LENGTH_SHORT).show();
            return;
        }

        setLoadingModels(true);
        AgentModelFetcher.fetch(settings, new AgentModelCallback() {
            @Override
            public void onSuccess(List<String> models) {
                setLoadingModels(false);
                showModelPicker(models);
            }

            @Override
            public void onError(String errorMessage) {
                setLoadingModels(false);
                Toast.makeText(AgentSettingsActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showModelPicker(List<String> models) {
        if (models == null || models.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy model phù hợp.", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] items = models.toArray(new String[0]);
        new AlertDialog.Builder(this)
                .setTitle("Chọn model")
                .setItems(items, (dialog, which) -> {
                    edtModel.setText(items[which]);
                    edtProfileName.setText(edtProvider.getText().toString().trim() + " - " + items[which]);
                })
                .show();
    }

    private void showProfilePicker() {
        List<AgentSettings> profiles = AgentSettingsHandler.getProfiles(this);
        if (profiles.isEmpty()) {
            Toast.makeText(this, "Chưa có cấu hình model nào.", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] items = new String[profiles.size()];
        for (int i = 0; i < profiles.size(); i++) {
            items[i] = profiles.get(i).showName();
        }

        new AlertDialog.Builder(this)
                .setTitle("Chọn cấu hình")
                .setItems(items, (dialog, which) -> bindProfile(profiles.get(which)))
                .show();
    }

    private AgentSettings getEditingSettings() {
        String selectedProvider = spnProvider.getSelectedItem() == null
                ? AgentSettingsHandler.DEFAULT_PROVIDER
                : spnProvider.getSelectedItem().toString();
        String provider = PROVIDER_CUSTOM.equals(selectedProvider)
                ? edtProvider.getText().toString().trim()
                : selectedProvider;
        String model = edtModel.getText().toString().trim();
        String name = edtProfileName.getText().toString().trim();
        if (name.isEmpty()) name = provider + " - " + model;

        return new AgentSettings(
                editingProfileId,
                name,
                provider,
                edtBaseUrl.getText().toString().trim(),
                model,
                edtApiKey.getText().toString().trim()
        );
    }

    private void bindProfile(AgentSettings settings) {
        bindingProfile = true;
        editingProfileId = settings.getId();
        edtProfileName.setText(settings.showName());
        edtProvider.setText(settings.getProvider());
        edtBaseUrl.setText(settings.getBaseUrl());
        edtModel.setText(settings.getModel());
        edtApiKey.setText(settings.getApiKey());
        int providerIndex = getProviderIndex(settings.getProvider());
        spnProvider.setSelection(providerIndex);
        applyProviderSelection(PROVIDERS[providerIndex], true);
        bindingProfile = false;
    }

    private int getProviderIndex(String provider) {
        for (int i = 0; i < PROVIDERS.length; i++) {
            if (PROVIDERS[i].equals(provider)) return i;
        }
        return PROVIDERS.length - 1;
    }

    private void setLoadingModels(boolean loading) {
        btnLoadModels.setEnabled(!loading);
        btnLoadModels.setText(loading ? "Đang tải..." : "Tải danh sách model");
    }

    @Override
    public Context getContext() {
        return this;
    }
}
