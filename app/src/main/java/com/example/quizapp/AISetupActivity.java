package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AISetupActivity extends AppCompatActivity {

    private int    selectedCount = 10;
    private String playerName;

    private TextView chip5, chip10, chip15, chip20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_setup);

        playerName = getIntent().getStringExtra("player_name");
        if (playerName == null || playerName.trim().isEmpty()) playerName = "Player";

        // ── Back button ───────────────────────────────────────────────────────
        TextView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // ── Chips ─────────────────────────────────────────────────────────────
        chip5  = findViewById(R.id.chip_5);
        chip10 = findViewById(R.id.chip_10);
        chip15 = findViewById(R.id.chip_15);
        chip20 = findViewById(R.id.chip_20);

        updateChips(10);

        chip5.setOnClickListener(v  -> updateChips(5));
        chip10.setOnClickListener(v -> updateChips(10));
        chip15.setOnClickListener(v -> updateChips(15));
        chip20.setOnClickListener(v -> updateChips(20));

        // ── Generate button ───────────────────────────────────────────────────
        TextInputEditText etTopic    = findViewById(R.id.et_topic);
        MaterialButton    btnGenerate = findViewById(R.id.btn_generate);

        btnGenerate.setOnClickListener(v -> {
            String topic = etTopic.getText() != null
                    ? etTopic.getText().toString().trim()
                    : "";

            if (topic.isEmpty()) {
                etTopic.setError("Please enter a topic");
                etTopic.requestFocus();
                return;
            }

            Intent intent = new Intent(this, QuizActivity.class);
            intent.putExtra(QuizActivity.EXTRA_PLAYER, playerName);
            intent.putExtra(QuizActivity.EXTRA_MODE,   QuizActivity.MODE_AI); // int, not string
            intent.putExtra("topic",          topic);
            intent.putExtra("question_count", selectedCount);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    private void updateChips(int count) {
        selectedCount = count;
        setChipState(chip5,  count == 5);
        setChipState(chip10, count == 10);
        setChipState(chip15, count == 15);
        setChipState(chip20, count == 20);
    }

    private void setChipState(TextView chip, boolean selected) {
        chip.setBackgroundResource(selected
                ? R.drawable.chip_selected
                : R.drawable.chip_unselected);
        chip.setTextColor(selected
                ? android.graphics.Color.WHITE
                : android.graphics.Color.parseColor("#3F51B5"));
    }
}