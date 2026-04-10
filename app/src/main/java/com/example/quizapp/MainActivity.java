package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etName;
    private CardView          btnOffline;
    private CardView          btnAi;
    private LinearLayout      btnLeaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ── Find widgets ──────────────────────────────────────
        etName         = findViewById(R.id.et_name);
        btnOffline     = findViewById(R.id.btn_offline);
        btnAi          = findViewById(R.id.btn_ai);
        btnLeaderboard = findViewById(R.id.btn_leaderboard);

        // ── Play Offline ──────────────────────────────────────
        btnOffline.setOnClickListener(v -> {
            String name = getName();
            if (name == null) return;

            Intent intent = new Intent(this, QuizActivity.class);
            intent.putExtra(QuizActivity.EXTRA_PLAYER, name);
            intent.putExtra(QuizActivity.EXTRA_MODE,   QuizActivity.MODE_OFFLINE);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // ── Play with AI ──────────────────────────────────────
        btnAi.setOnClickListener(v -> {
            String name = getName();
            if (name == null) return;

            Intent intent = new Intent(this, AISetupActivity.class);
            intent.putExtra("player_name", name);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // ── Leaderboard ───────────────────────────────────────
        btnLeaderboard.setOnClickListener(v -> {
            startActivity(new Intent(this, LeaderboardActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    // ── Returns trimmed name or shows error and returns null ──
    private String getName() {
        String name = etName.getText() != null
                ? etName.getText().toString().trim()
                : "";
        if (name.isEmpty()) {
            etName.setError("Please enter your name");
            etName.requestFocus();
            return null;
        }
        return name;
    }

    // ── Clear name field when returning to home ───────────────
    @Override
    protected void onResume() {
        super.onResume();
        etName.setText("");
    }
}