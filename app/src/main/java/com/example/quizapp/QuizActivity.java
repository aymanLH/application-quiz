package com.example.quizapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    // ── Intent keys ───────────────────────────────────────────
    public static final String EXTRA_MODE   = "mode";
    public static final String EXTRA_PLAYER = "player_name";
    public static final int    MODE_OFFLINE = 0;
    public static final int    MODE_AI      = 1;

    // ── Colors ────────────────────────────────────────────────
    private static final int COLOR_DEFAULT = Color.parseColor("#FFFFFF");
    private static final int COLOR_CORRECT = Color.parseColor("#4CAF50");
    private static final int COLOR_WRONG   = Color.parseColor("#F44336");
    private static final int COLOR_PRIMARY = Color.parseColor("#3F51B5");
    private static final int COLOR_WHITE   = Color.parseColor("#FFFFFF");

    // ── UI widgets ────────────────────────────────────────────
    private TextView      tvPlayerName, tvCounter, tvQuestion;
    private ProgressBar   progressBar;
    private MaterialButton btnNext;
    private ImageButton   btnBack;

    private CardView  cardA, cardB, cardC, cardD;
    private TextView  labelA, labelB, labelC, labelD;
    private TextView  tvA, tvB, tvC, tvD;

    // ── Game state ────────────────────────────────────────────
    private ArrayList<Question> questions;
    private int     currentIndex   = 0;
    private int     score          = 0;
    private int     totalQuestions;
    private boolean answered       = false;
    private String  playerName;

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // ── 1. Read extras ────────────────────────────────────
        playerName  = getIntent().getStringExtra(EXTRA_PLAYER);
        int    mode = getIntent().getIntExtra(EXTRA_MODE, MODE_OFFLINE);
        String topic        = getIntent().getStringExtra("topic");
        int    questionCount = getIntent().getIntExtra("question_count", 10);

        // ── 2. Bind widgets ───────────────────────────────────
        bindViews();
        tvPlayerName.setText(playerName);

        // ── 3. Load questions ─────────────────────────────────
        if (mode == MODE_AI && topic != null && !topic.isEmpty()) {
            loadAiQuestions(topic, questionCount);
        } else {
            questions      = QuestionBank.getAllQuestions();
            totalQuestions = questions.size();
            setupClickListeners();
            showQuestion();
        }
    }

    // ════════════════════════════════════════════════════════
    // AI mode: show loading dialog, call API, then start quiz
    // ════════════════════════════════════════════════════════
    private void loadAiQuestions(String topic, int count) {
        // Show loading spinner
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Generating questions about \"" + topic + "\"...");
        dialog.setCancelable(false);
        dialog.show();

        ApiService.generateQuestions(topic, count, new ApiService.Callback() {
            @Override
            public void onSuccess(ArrayList<Question> result) {
                dialog.dismiss();
                questions      = result;
                totalQuestions = questions.size();
                setupClickListeners();
                showQuestion();
            }

            @Override
            public void onError(String message) {
                dialog.dismiss();
                Toast.makeText(QuizActivity.this,
                        "Failed to generate questions: " + message,
                        Toast.LENGTH_LONG).show();
                // Fall back to offline questions so the user isn't stuck
                questions      = QuestionBank.getAllQuestions();
                totalQuestions = questions.size();
                setupClickListeners();
                showQuestion();
            }
        });
    }

    // ════════════════════════════════════════════════════════
    // Bind all views by ID
    // ════════════════════════════════════════════════════════
    private void bindViews() {
        tvPlayerName = findViewById(R.id.tv_player_name);
        tvCounter    = findViewById(R.id.tv_question_counter);
        tvQuestion   = findViewById(R.id.tv_question);
        progressBar  = findViewById(R.id.progress_bar);
        btnNext      = findViewById(R.id.btn_next);
        btnBack      = findViewById(R.id.btn_back);

        cardA  = findViewById(R.id.card_a);
        cardB  = findViewById(R.id.card_b);
        cardC  = findViewById(R.id.card_c);
        cardD  = findViewById(R.id.card_d);

        labelA = findViewById(R.id.label_a);
        labelB = findViewById(R.id.label_b);
        labelC = findViewById(R.id.label_c);
        labelD = findViewById(R.id.label_d);

        tvA    = findViewById(R.id.tv_option_a);
        tvB    = findViewById(R.id.tv_option_b);
        tvC    = findViewById(R.id.tv_option_c);
        tvD    = findViewById(R.id.tv_option_d);
    }

    // ════════════════════════════════════════════════════════
    // Set up click listeners (called after questions are ready)
    // ════════════════════════════════════════════════════════
    private void setupClickListeners() {
        cardA.setOnClickListener(v -> checkAnswer(0));
        cardB.setOnClickListener(v -> checkAnswer(1));
        cardC.setOnClickListener(v -> checkAnswer(2));
        cardD.setOnClickListener(v -> checkAnswer(3));

        // SKIP — jumps to next question immediately if not yet answered.
        // If already answered, auto-advance is already running so do nothing.
        btnNext.setOnClickListener(v -> {
            if (!answered) {
                goToNext();
            }
        });

        btnBack.setOnClickListener(v -> finish());
    }

    // ════════════════════════════════════════════════════════
    // showQuestion
    // ════════════════════════════════════════════════════════
    private void showQuestion() {
        answered = false;
        resetCardColors();

        Question q = questions.get(currentIndex);

        tvCounter.setText("Q " + (currentIndex + 1) + "/" + totalQuestions);
        progressBar.setProgress(
                (int) (((currentIndex + 1) / (float) totalQuestions) * 100));
        tvQuestion.setText(q.getText());
        tvA.setText(q.getOptions()[0]);
        tvB.setText(q.getOptions()[1]);
        tvC.setText(q.getOptions()[2]);
        tvD.setText(q.getOptions()[3]);
    }

    // ════════════════════════════════════════════════════════
    // checkAnswer
    // ════════════════════════════════════════════════════════
    private void checkAnswer(int selectedIndex) {
        if (answered) return;
        answered = true;

        int correctIndex = questions.get(currentIndex).getCorrectIndex();

        highlightCard(correctIndex, COLOR_CORRECT, COLOR_WHITE);

        if (selectedIndex != correctIndex) {
            highlightCard(selectedIndex, COLOR_WRONG, COLOR_WHITE);
        } else {
            score++;
        }

        handler.postDelayed(this::goToNext, 1200);
    }

    // ════════════════════════════════════════════════════════
    // highlightCard
    // ════════════════════════════════════════════════════════
    private void highlightCard(int index, int cardColor, int textColor) {
        CardView card;
        TextView label;

        switch (index) {
            case 0: card = cardA; label = labelA; break;
            case 1: card = cardB; label = labelB; break;
            case 2: card = cardC; label = labelC; break;
            default: card = cardD; label = labelD; break;
        }

        card.setCardBackgroundColor(cardColor);
        label.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(
                        cardColor == COLOR_CORRECT ? COLOR_WHITE :
                                cardColor == COLOR_WRONG ? COLOR_WHITE : COLOR_PRIMARY
                )
        );
        label.setTextColor(
                cardColor == COLOR_DEFAULT ? COLOR_WHITE : cardColor
        );
    }

    // ════════════════════════════════════════════════════════
    // resetCardColors
    // ════════════════════════════════════════════════════════
    private void resetCardColors() {
        int[] indices = {0, 1, 2, 3};
        for (int i : indices) highlightCard(i, COLOR_DEFAULT, COLOR_WHITE);

        for (TextView label : new TextView[]{labelA, labelB, labelC, labelD}) {
            label.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(COLOR_PRIMARY));
            label.setTextColor(COLOR_WHITE);
        }
        for (TextView tv : new TextView[]{tvA, tvB, tvC, tvD}) {
            tv.setTextColor(Color.parseColor("#333333"));
        }
    }

    // ════════════════════════════════════════════════════════
    // goToNext
    // ════════════════════════════════════════════════════════
    private void goToNext() {
        handler.removeCallbacksAndMessages(null);
        currentIndex++;

        if (currentIndex < totalQuestions) {
            showQuestion();
        } else {
            finishQuiz();
        }
    }

    // ════════════════════════════════════════════════════════
    // finishQuiz
    // ════════════════════════════════════════════════════════
    private void finishQuiz() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("score",       score);
        intent.putExtra("total",       totalQuestions);
        intent.putExtra("player_name", playerName);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}