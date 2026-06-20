package com.example.couple.Custom.Handler.Agent;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.couple.Model.Agent.AgentMessage;
import com.example.couple.Model.Agent.AgentSettings;

import java.util.List;

public class MockAgentClient implements AgentClient {
    private static final long REPLY_DELAY_MS = 350;

    @Override
    public AgentRequest send(Context context, AgentSettings settings, List<AgentMessage> history, AgentMessage userMessage,
                             AgentCallback callback) {
        Handler handler = new Handler(Looper.getMainLooper());
        MockAgentRequest request = new MockAgentRequest(handler);
        Runnable replyTask = () -> {
            if (request.isCancelled()) return;
            callback.onSuccess(new AgentMessage(AgentMessage.Role.ASSISTANT,
                    createReply(settings, userMessage)));
        };
        request.setReplyTask(replyTask);
        handler.postDelayed(replyTask, REPLY_DELAY_MS);
        return request;
    }

    private String createReply(AgentSettings settings, AgentMessage userMessage) {
        String content = userMessage.getContent().toLowerCase();
        if (userMessage.hasImage()) {
            return "Mình đã nhận ảnh" + (userMessage.getContent().isEmpty()
                    ? "." : " kèm nội dung: " + userMessage.getContent());
        }
        if (content.contains("model") || content.contains("api key") || content.contains("key")) {
            return "Agent đang dùng cấu hình: " + settings.getProvider()
                    + " / " + settings.getModel()
                    + ". API key: " + (settings.hasApiKey() ? "đã nhập" : "chưa nhập")
                    + ".";
        }
        if (content.contains("cầu") || content.contains("cau")) {
            return "Mình có thể hỗ trợ xem cầu mới, cầu gan, cầu liên thông hoặc giải thích các màn cầu. Bước sau mình sẽ được nối với dữ liệu thật của app.";
        }
        if (content.contains("thông báo") || content.contains("thong bao")) {
            return "Mình có thể giúp kiểm tra cài đặt thông báo, giờ báo và lịch sử thông báo. Hiện tại đây là phản hồi mock.";
        }
        if (content.contains("thống kê") || content.contains("thong ke")) {
            return "Mình có thể dẫn bạn tới các màn thống kê ĐB theo năm, nhiều năm hoặc ĐB hôm sau.";
        }
        return "Mình đã nhận: \"" + userMessage.getContent()
                + "\".\nPhần agent thật sẽ được nối vào AgentClient ở bước tiếp theo.";
    }

    private static class MockAgentRequest implements AgentRequest {
        private final Handler handler;
        private Runnable replyTask;
        private boolean cancelled = false;

        MockAgentRequest(Handler handler) {
            this.handler = handler;
        }

        void setReplyTask(Runnable replyTask) {
            this.replyTask = replyTask;
        }

        @Override
        public void cancel() {
            cancelled = true;
            if (replyTask != null) handler.removeCallbacks(replyTask);
        }

        @Override
        public boolean isCancelled() {
            return cancelled;
        }
    }
}
