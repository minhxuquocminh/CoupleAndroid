package com.example.couple.View.Agent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Base.View.ActivityBase;
import com.example.couple.Custom.Handler.Agent.AgentCallback;
import com.example.couple.Custom.Handler.Agent.AgentClient;
import com.example.couple.Custom.Handler.Agent.AgentRequest;
import com.example.couple.Custom.Handler.Agent.AgentSettingsHandler;
import com.example.couple.Custom.Handler.Agent.AiPredictionHistoryHandler;
import com.example.couple.Custom.Handler.Agent.RemoteAgentClient;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Model.Agent.AgentMessage;
import com.example.couple.Model.Agent.AgentSettings;
import com.example.couple.Model.DateTime.Date.DateBase;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AiJackpotStatisticsActivity extends ActivityBase {
    private static final int REQUEST_MANAGE_MODEL = 2501;
    private static final int DAYS_TO_ANALYZE = 365;
    private static final int RAW_DAYS_TO_SEND = 180;
    private static final int MODEL_LABEL_MAX_LENGTH = 22;
    private static final int MODE_BRIDGE = 1;
    private static final int MODE_STATS = 2;

    TextView tvLocalStats;
    TextView tvBridgeAnswer;
    TextView tvStatsAnswer;
    Button btnAskBridge;
    Button btnAskStats;
    Button btnStop;
    Button btnSelectModel;
    Button btnPredictionHistory;

    AgentRequest activeRequest;
    boolean isWaitingResponse = false;
    int activeRequestId = 0;
    int activeMode = 0;
    JackpotStats currentStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_jackpot_statistics);

        tvLocalStats = findViewById(R.id.tvLocalStats);
        tvBridgeAnswer = findViewById(R.id.tvBridgeAnswer);
        tvStatsAnswer = findViewById(R.id.tvStatsAnswer);
        btnAskBridge = findViewById(R.id.btnAskBridge);
        btnAskStats = findViewById(R.id.btnAskStats);
        btnStop = findViewById(R.id.btnStop);
        btnSelectModel = findViewById(R.id.btnSelectModel);
        btnPredictionHistory = findViewById(R.id.btnPredictionHistory);

        bindEvents();
        refreshModelLabel();
        reloadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshModelLabel();
    }

    private void bindEvents() {
        btnAskBridge.setOnClickListener(view -> askAi(MODE_BRIDGE));
        btnAskStats.setOnClickListener(view -> askAi(MODE_STATS));
        btnStop.setOnClickListener(view -> stopRequest());
        btnSelectModel.setOnClickListener(view -> showModelPicker());
        btnPredictionHistory.setOnClickListener(view ->
                startActivity(new Intent(this, AiPredictionHistoryActivity.class)));
    }

    private void reloadData() {
        loadStats();
        tvBridgeAnswer.setText("Bấm “Soi cầu ĐB” để AI phân tích cầu từ dữ liệu đã lưu.");
        tvStatsAnswer.setText("Bấm “Thống kê ĐB” để AI diễn giải các thống kê bên dưới.");
    }

    private void loadStats() {
        List<Jackpot> jackpots = JackpotHandler.getJackpotListByDays(this, DAYS_TO_ANALYZE);
        currentStats = buildStats(jackpots);
        tvLocalStats.setText(currentStats.localSummary);
    }

    private void askAi(int mode) {
        if (isWaitingResponse) return;
        loadStats();
        if (currentStats.jackpots.isEmpty()) {
            Toast.makeText(this, "Chưa có dữ liệu ĐB để phân tích.", Toast.LENGTH_SHORT).show();
            return;
        }

        AgentSettings settings = AgentSettingsHandler.getSettings(this);
        if (!settings.hasApiKey()) {
            Toast.makeText(this, "Bạn cần thêm API key/model trước khi hỏi AI.", Toast.LENGTH_LONG).show();
            openModelManage();
            return;
        }

        int requestId = ++activeRequestId;
        long startedAt = System.currentTimeMillis();
        activeMode = mode;
        setWaitingResponse(true);
        setThinkingText(mode);

        AgentClient client = new RemoteAgentClient();
        AgentMessage userMessage = new AgentMessage(AgentMessage.Role.USER,
                mode == MODE_BRIDGE ? buildBridgePrompt(currentStats) : buildStatsPrompt(currentStats));
        List<AgentMessage> history = new ArrayList<>();
        history.add(userMessage);

        activeRequest = client.send(this, settings, history, userMessage, new AgentCallback() {
            @Override
            public void onSuccess(AgentMessage message) {
                if (requestId != activeRequestId || !isWaitingResponse) return;
                activeRequest = null;
                String elapsed = formatElapsed(System.currentTimeMillis() - startedAt);
                String answer = message.getContent() + "\n\nModel: " + settings.showName() + " - " + elapsed;
                setAnswer(mode, answer);
                savePrediction(mode, settings.showName(), answer);
                setWaitingResponse(false);
            }

            @Override
            public void onError(String errorMessage) {
                if (requestId != activeRequestId || !isWaitingResponse) return;
                activeRequest = null;
                setAnswer(mode, errorMessage == null || errorMessage.trim().isEmpty()
                        ? "AI chưa trả lời được lúc này." : errorMessage);
                setWaitingResponse(false);
            }
        });
    }

    private void setThinkingText(int mode) {
        setAnswer(mode, "AI đang suy nghĩ, chờ chút nhé...");
    }

    private void setAnswer(int mode, String answer) {
        if (mode == MODE_BRIDGE) {
            tvBridgeAnswer.setText(answer);
        } else {
            tvStatsAnswer.setText(answer);
        }
    }

    private void savePrediction(int mode, String modelName, String answer) {
        AiPredictionHistoryHandler.savePrediction(this,
                currentStats == null ? DateBase.today().showFullChars() : currentStats.predictionDate,
                currentStats == null ? "" : currentStats.sourceLastDate,
                mode == MODE_BRIDGE ? "Soi cầu ĐB" : "Thống kê ĐB",
                modelName,
                answer,
                currentStats == null ? "" : currentStats.localSummary);
    }

    private JackpotStats buildStats(List<Jackpot> jackpots) {
        JackpotStats stats = new JackpotStats();
        stats.jackpots = jackpots == null ? new ArrayList<>() : jackpots;
        int[] frequencies = new int[100];
        int[] lastSeen = new int[100];
        for (int i = 0; i < lastSeen.length; i++) {
            lastSeen[i] = -1;
        }

        Map<String, Integer> pairCounts = new HashMap<>();
        int recentLimit = Math.min(30, stats.jackpots.size());
        int[] recentFrequencies = new int[100];

        for (int i = 0; i < stats.jackpots.size(); i++) {
            Jackpot jackpot = stats.jackpots.get(i);
            if (jackpot.isEmptyOrInvalid()) continue;

            int couple = jackpot.getCoupleInt();
            frequencies[couple]++;
            if (i < recentLimit) recentFrequencies[couple]++;
            if (lastSeen[couple] < 0) lastSeen[couple] = i;

            if (i < stats.jackpots.size() - 1) {
                Jackpot olderJackpot = stats.jackpots.get(i + 1);
                if (!olderJackpot.isEmptyOrInvalid()) {
                    String key = formatCouple(olderJackpot.getCoupleInt()) + " -> " + formatCouple(couple);
                    Integer count = pairCounts.get(key);
                    pairCounts.put(key, count == null ? 1 : count + 1);
                }
            }
        }

        bindPredictionDates(stats);
        stats.mostAppeared = topNumbers(frequencies, 8);
        stats.longMissing = topMissing(lastSeen, 8, stats.jackpots.size());
        stats.relatedPairs = topPairs(pairCounts, 8);
        stats.recentTrend = topTrend(frequencies, recentFrequencies, recentLimit, stats.jackpots.size(), 8);

        StringBuilder builder = new StringBuilder();
        builder.append("Soi cho ngày: ").append(stats.predictionDate).append("\n");
        builder.append("Dữ liệu đến ngày: ").append(stats.sourceLastDate).append("\n");
        builder.append("Dữ liệu nền: ").append(stats.jackpots.size()).append(" ngày gần nhất.\n\n");
        builder.append("Số xuất hiện nhiều:\n").append(joinRows(stats.mostAppeared)).append("\n\n");
        builder.append("Số lâu chưa về:\n").append(joinRows(stats.longMissing)).append("\n\n");
        builder.append("Cặp hay đi cùng:\n").append(joinRows(stats.relatedPairs)).append("\n\n");
        builder.append("Xu hướng 30 ngày gần đây:\n").append(joinRows(stats.recentTrend));
        stats.localSummary = builder.toString();
        return stats;
    }

    private void bindPredictionDates(JackpotStats stats) {
        stats.sourceLastDate = DateBase.today().showFullChars();
        stats.predictionDate = DateBase.today().addDays(1).showFullChars();
        for (Jackpot jackpot : stats.jackpots) {
            if (jackpot.isEmptyOrInvalid()) continue;
            stats.sourceLastDate = jackpot.getDateBase().showFullChars();
            stats.predictionDate = jackpot.getDateBase().addDays(1).showFullChars();
            return;
        }
    }

    private List<RankRow> topNumbers(int[] values, int limit) {
        List<RankRow> rows = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            rows.add(new RankRow(formatCouple(i), values[i]));
        }
        Collections.sort(rows, (first, second) -> second.value - first.value);
        return rows.subList(0, Math.min(limit, rows.size()));
    }

    private List<RankRow> topMissing(int[] lastSeen, int limit, int jackpotSize) {
        List<RankRow> rows = new ArrayList<>();
        for (int i = 0; i < lastSeen.length; i++) {
            int days = lastSeen[i] < 0 ? jackpotSize : lastSeen[i];
            rows.add(new RankRow(formatCouple(i), days));
        }
        Collections.sort(rows, (first, second) -> second.value - first.value);
        return rows.subList(0, Math.min(limit, rows.size()));
    }

    private List<RankRow> topPairs(Map<String, Integer> pairCounts, int limit) {
        List<RankRow> rows = new ArrayList<>();
        for (String key : pairCounts.keySet()) {
            rows.add(new RankRow(key, pairCounts.get(key)));
        }
        Collections.sort(rows, (first, second) -> second.value - first.value);
        return rows.subList(0, Math.min(limit, rows.size()));
    }

    private List<RankRow> topTrend(int[] allFrequencies, int[] recentFrequencies,
                                   int recentLimit, int totalSize, int limit) {
        List<RankRow> rows = new ArrayList<>();
        if (recentLimit == 0 || totalSize == 0) return rows;
        for (int i = 0; i < allFrequencies.length; i++) {
            double recentRate = recentFrequencies[i] * 100.0 / recentLimit;
            double allRate = allFrequencies[i] * 100.0 / totalSize;
            int score = (int) Math.round((recentRate - allRate) * 100);
            rows.add(new RankRow(formatCouple(i), score));
        }
        Collections.sort(rows, (first, second) -> second.value - first.value);
        return rows.subList(0, Math.min(limit, rows.size()));
    }

    private String joinRows(List<RankRow> rows) {
        if (rows == null || rows.isEmpty()) return "Chưa đủ dữ liệu.";
        StringBuilder builder = new StringBuilder();
        for (RankRow row : rows) {
            builder.append("- ").append(row.label).append(": ").append(row.value).append("\n");
        }
        return builder.toString().trim();
    }

    private String buildBridgePrompt(JackpotStats stats) {
        StringBuilder builder = new StringBuilder();
        builder.append("Bạn là trợ lý soi cầu giải đặc biệt miền Bắc trong app CoupleAndroid.\n");
        builder.append("Hãy soi cầu cho ngày ").append(stats.predictionDate)
                .append(", chỉ dùng dữ liệu đến hết ngày ").append(stats.sourceLastDate).append(".\n");
        builder.append("Dựa trên dữ liệu hai số cuối giải ĐB gần đây, hãy soi cầu theo hướng tham khảo thống kê.\n");
        builder.append("Không khẳng định chắc chắn, không dùng giọng cam kết trúng.\n");
        builder.append("Trả lời ngắn theo các mục: nhận xét cầu, điểm đáng chú ý, bộ số đề xuất tối đa 10 số, cảnh báo tham khảo.\n\n");
        builder.append("Thống kê nền:\n").append(stats.localSummary).append("\n\n");
        appendRawData(builder, stats);
        return builder.toString();
    }

    private String buildStatsPrompt(JackpotStats stats) {
        StringBuilder builder = new StringBuilder();
        builder.append("Hãy phân tích thống kê giải đặc biệt miền Bắc bằng tiếng Việt.\n");
        builder.append("Phần phân tích này dùng để tham khảo cho ngày ").append(stats.predictionDate)
                .append(", chỉ dùng dữ liệu đến hết ngày ").append(stats.sourceLastDate).append(".\n");
        builder.append("Chỉ coi đây là tham khảo xác suất, không khẳng định chắc chắn.\n");
        builder.append("Trả lời theo 4 mục: số xuất hiện nhiều, số lâu chưa về, cặp hay đi cùng, xu hướng thống kê.\n");
        builder.append("Cuối cùng đề xuất tối đa 10 số hai chữ số và nêu lý do ngắn.\n\n");
        builder.append("Thống kê đã tính sẵn:\n").append(stats.localSummary).append("\n\n");
        appendRawData(builder, stats);
        return builder.toString();
    }

    private void appendRawData(StringBuilder builder, JackpotStats stats) {
        builder.append("Dữ liệu gần nhất, định dạng ngày: giải ĐB -> hai số cuối:\n");
        int limit = Math.min(RAW_DAYS_TO_SEND, stats.jackpots.size());
        for (int i = 0; i < limit; i++) {
            Jackpot jackpot = stats.jackpots.get(i);
            if (jackpot.isEmptyOrInvalid()) continue;
            builder.append(jackpot.getDateBase().toString("-"))
                    .append(": ")
                    .append(jackpot.getJackpot())
                    .append(" -> ")
                    .append(formatCouple(jackpot.getCoupleInt()))
                    .append("\n");
        }
    }

    private String formatCouple(int number) {
        return NumberBase.showNumberString(number, 2);
    }

    private void showModelPicker() {
        List<AgentSettings> profiles = AgentSettingsHandler.getProfiles(this);
        if (profiles.isEmpty()) {
            Toast.makeText(this, "Bạn chưa lưu model nào. Hãy thêm API key và model trước.", Toast.LENGTH_LONG).show();
            openModelManage();
            return;
        }

        List<AgentSettings> sortedProfiles = groupedProfiles(profiles);
        ListView listView = new ListView(this);
        listView.setAdapter(new ModelPickerAdapter(sortedProfiles));

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Chọn model")
                .setView(listView)
                .setPositiveButton("Quản lý model", (dialogInterface, which) -> openModelManage())
                .create();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            AgentSettings profile = sortedProfiles.get(position);
            AgentSettingsHandler.setActiveProfileId(this, profile.getId());
            Toast.makeText(this, "Đã chọn model: " + profile.showName(), Toast.LENGTH_SHORT).show();
            refreshModelLabel();
            reloadData();
            dialog.dismiss();
        });
        dialog.show();
    }

    private List<AgentSettings> groupedProfiles(List<AgentSettings> profiles) {
        List<AgentSettings> sortedProfiles = new ArrayList<>(profiles);
        Collections.sort(sortedProfiles, new Comparator<AgentSettings>() {
            @Override
            public int compare(AgentSettings first, AgentSettings second) {
                int groupCompare = getModelGroupOrder(first) - getModelGroupOrder(second);
                if (groupCompare != 0) return groupCompare;
                return first.showName().compareToIgnoreCase(second.showName());
            }
        });
        return sortedProfiles;
    }

    private void openModelManage() {
        Intent intent = new Intent(this, AgentModelManageActivity.class);
        intent.putExtra(AgentModelManageActivity.EXTRA_ACTIVE_PROFILE_ID,
                AgentSettingsHandler.getSettings(this).getId());
        startActivityForResult(intent, REQUEST_MANAGE_MODEL);
    }

    private void refreshModelLabel() {
        AgentSettings settings = AgentSettingsHandler.getSettings(this);
        btnSelectModel.setText(shortModelName(settings));
    }

    private String shortModelName(AgentSettings settings) {
        if (!settings.hasApiKey()) return "Thêm model";
        String provider = settings.getProvider() == null ? "" : settings.getProvider().trim();
        String model = settings.getModel() == null ? "" : settings.getModel().trim();
        String label = model.isEmpty() ? provider : provider.isEmpty() ? model : provider + " " + model;
        if (label.length() <= MODEL_LABEL_MAX_LENGTH) return label;
        return label.substring(0, MODEL_LABEL_MAX_LENGTH - 3) + "...";
    }

    private void stopRequest() {
        activeRequestId++;
        if (activeRequest != null) {
            activeRequest.cancel();
            activeRequest = null;
        }
        setAnswer(activeMode == 0 ? MODE_STATS : activeMode, "Đã dừng lượt phân tích.");
        setWaitingResponse(false);
    }

    private void setWaitingResponse(boolean waitingResponse) {
        isWaitingResponse = waitingResponse;
        btnAskBridge.setEnabled(!waitingResponse);
        btnAskStats.setEnabled(!waitingResponse);
        btnStop.setEnabled(waitingResponse);
        btnSelectModel.setEnabled(!waitingResponse);
        if (!waitingResponse) activeMode = 0;
    }

    private String formatElapsed(long elapsedMs) {
        if (elapsedMs < 1000) return elapsedMs + "ms";
        return String.format(Locale.US, "%.1fs", elapsedMs / 1000.0);
    }

    private String getModelGroup(AgentSettings profile) {
        String text = ((profile.getProvider() == null ? "" : profile.getProvider()) + " "
                + (profile.getModel() == null ? "" : profile.getModel())).toLowerCase();
        if (text.contains("gemini")) return "Gemini";
        if (text.contains("gpt") || text.contains("openai")) return "GPT / OpenAI";
        if (text.contains("grok") || text.contains("x.ai")) return "Grok";
        if (text.contains("openrouter")) return "OpenRouter";
        return "Khác";
    }

    private int getModelGroupOrder(AgentSettings profile) {
        String group = getModelGroup(profile);
        if ("Gemini".equals(group)) return 0;
        if ("GPT / OpenAI".equals(group)) return 1;
        if ("Grok".equals(group)) return 2;
        if ("OpenRouter".equals(group)) return 3;
        return 4;
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    public Context getContext() {
        return this;
    }

    private static class JackpotStats {
        List<Jackpot> jackpots = new ArrayList<>();
        List<RankRow> mostAppeared = new ArrayList<>();
        List<RankRow> longMissing = new ArrayList<>();
        List<RankRow> relatedPairs = new ArrayList<>();
        List<RankRow> recentTrend = new ArrayList<>();
        String localSummary = "";
        String predictionDate = "";
        String sourceLastDate = "";
    }

    private static class RankRow {
        String label;
        int value;

        RankRow(String label, int value) {
            this.label = label;
            this.value = value;
        }
    }

    private class ModelPickerAdapter extends BaseAdapter {
        List<AgentSettings> profiles;

        ModelPickerAdapter(List<AgentSettings> profiles) {
            this.profiles = profiles;
        }

        @Override
        public int getCount() {
            return profiles.size();
        }

        @Override
        public Object getItem(int position) {
            return profiles.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AgentSettings profile = profiles.get(position);
            TextView textView = convertView instanceof TextView
                    ? (TextView) convertView
                    : new TextView(AiJackpotStatisticsActivity.this);
            boolean active = profile.getId().equals(AgentSettingsHandler.getSettings(AiJackpotStatisticsActivity.this).getId());
            textView.setText(profile.showName());
            textView.setSingleLine(true);
            textView.setPadding(dp(20), dp(11), dp(20), dp(11));
            textView.setTextSize(16);
            textView.setTypeface(null, active ? android.graphics.Typeface.BOLD : android.graphics.Typeface.NORMAL);
            textView.setTextColor(getResources().getColor(active ? R.color.colorPrimary : R.color.colorText));
            return textView;
        }
    }
}
