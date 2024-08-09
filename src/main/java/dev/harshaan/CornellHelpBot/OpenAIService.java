package dev.harshaan.CornellHelpBot;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class OpenAIService {

    @Value("${OPENAI_API_KEY}")
    private String openaiApiKey;

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    public String generateReply(String prompt) throws IOException {
        OkHttpClient client = new OkHttpClient();

        JSONObject jsonRequest = getJsonObject(prompt);

        RequestBody body = RequestBody.create(
                jsonRequest.toString(),
                MediaType.get("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(OPENAI_API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + openaiApiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "No error body";
                throw new IOException("Unexpected code " + response + ". Error body: " + errorBody);
            }

            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                JSONObject jsonResponse = new JSONObject(responseBody.string());
                JSONArray choices = jsonResponse.getJSONArray("choices");
                return choices.getJSONObject(0).getJSONObject("message").getString("content").trim();
            }
            else {
                return "";
            }
        }
    }

    private static @NotNull JSONObject getJsonObject(String prompt) {
        String context = "You are replying to a prospective Cornell student in a discussion thread focused on admissions and life at Cornell. Provide accurate and helpful information and include links to official Cornell websites where appropriate and possible. Finally, please do not start with a greeting and get right into the answering and keep it short.";
        String finalPrompt = context + " " + prompt;

        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("model", "gpt-4o-mini");
        JSONArray messages = new JSONArray();
        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", finalPrompt);
        messages.put(message);
        jsonRequest.put("messages", messages);
        jsonRequest.put("max_tokens", 250);
        return jsonRequest;
    }
}