package com.example.quizapp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Calls the OpenRouter API (Llama 3.3 70B free) to generate quiz questions.
 *
 * Usage:
 *   ApiService.generateQuestions(topic, count, new ApiService.Callback() {
 *       public void onSuccess(ArrayList<Question> questions) { ... }
 *       public void onError(String message)                  { ... }
 *   });
 *
 * The callback is always delivered on the MAIN thread.
 */
public class ApiService {

    // ── OpenRouter endpoint + model ───────────────────────────────────────────
    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";
    private static final String MODEL   = "google/gemma-3-4b-it:free";
    private static final String API_KEY = "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";

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    // ── Callback interface ────────────────────────────────────────────────────
    public interface Callback {
        void onSuccess(ArrayList<Question> questions);
        void onError(String message);
    }

    // ── Public entry point ────────────────────────────────────────────────────

    /**
     * Generates quiz questions on a background thread and delivers
     * the result via callback on the main thread.
     *
     * @param topic    Topic entered by the user e.g. "History of Morocco"
     * @param count    Number of questions: 5, 10, 15, or 20
     * @param callback onSuccess / onError
     */
    public static void generateQuestions(String topic, int count, Callback callback) {
        // Run network call on a background thread
        new Thread(() -> {
            try {
                ArrayList<Question> questions = callApi(topic, count);
                // Deliver success on main thread
                new android.os.Handler(android.os.Looper.getMainLooper())
                        .post(() -> callback.onSuccess(questions));
            } catch (Exception e) {
                String msg = e.getMessage() != null ? e.getMessage() : "Unknown error";
                new android.os.Handler(android.os.Looper.getMainLooper())
                        .post(() -> callback.onError(msg));
            }
        }).start();
    }

    // ── Private: build request, call API, parse response ─────────────────────

    private static ArrayList<Question> callApi(String topic, int count) throws Exception {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        // ── Build the prompt ──────────────────────────────────────────────────
        String prompt = buildPrompt(topic, count);

        // ── Build JSON request body (OpenAI-compatible format) ────────────────
        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", prompt);

        JSONArray messages = new JSONArray();
        messages.put(message);

        JSONObject body = new JSONObject();
        body.put("model", MODEL);
        body.put("messages", messages);
        body.put("temperature", 0.7);
        body.put("max_tokens", 2000);

        // ── Build HTTP request ────────────────────────────────────────────────
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("HTTP-Referer", "https://quizapp.example.com")
                .addHeader("X-Title", "QuizApp")
                .post(RequestBody.create(body.toString(), JSON))
                .build();

        // ── Execute ───────────────────────────────────────────────────────────
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new Exception("API error: " + response.code() + " " + response.message());
            }

            String responseBody = response.body().string();
            return parseResponse(responseBody);
        }
    }

    // ── Prompt ────────────────────────────────────────────────────────────────

    private static String buildPrompt(String topic, int count) {
        return "Generate exactly " + count + " multiple choice quiz questions about: " + topic + ".\n\n"
                + "Return ONLY a valid JSON array. No explanation, no markdown, no code blocks.\n"
                + "Each object must have exactly these fields:\n"
                + "  \"question\": string\n"
                + "  \"options\": array of exactly 4 strings\n"
                + "  \"answer\": integer 0-3 (index of the correct option)\n\n"
                + "Example format:\n"
                + "[\n"
                + "  {\n"
                + "    \"question\": \"What is the capital of France?\",\n"
                + "    \"options\": [\"Berlin\", \"Madrid\", \"Paris\", \"Rome\"],\n"
                + "    \"answer\": 2\n"
                + "  }\n"
                + "]\n\n"
                + "Return only the JSON array, nothing else.";
    }

    // ── Parse JSON response into ArrayList<Question> ──────────────────────────

    private static ArrayList<Question> parseResponse(String responseBody) throws Exception {
        ArrayList<Question> questions = new ArrayList<>();

        // Extract the content string from OpenAI-compatible response
        JSONObject root    = new JSONObject(responseBody);
        JSONArray  choices = root.getJSONArray("choices");
        String     content = choices.getJSONObject(0)
                .getJSONObject("message")
                .getString("content")
                .trim();

        // Strip markdown code blocks if model wraps response anyway
        if (content.startsWith("```")) {
            content = content.replaceAll("```json", "").replaceAll("```", "").trim();
        }

        // Parse the JSON array of questions
        JSONArray arr = new JSONArray(content);

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);

            String questionText = obj.getString("question");

            JSONArray optionsArr = obj.getJSONArray("options");
            String[] options = new String[4];
            for (int j = 0; j < 4; j++) {
                options[j] = optionsArr.getString(j);
            }

            int correctIndex = obj.getInt("answer");

            questions.add(new Question(questionText, options, correctIndex));
        }

        if (questions.isEmpty()) {
            throw new Exception("No questions received from API");
        }

        return questions;
    }
}