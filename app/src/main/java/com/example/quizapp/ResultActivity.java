package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // ── Read extras ───────────────────────────────────────
        int    score      = getIntent().getIntExtra("score", 0);
        int    total      = getIntent().getIntExtra("total", 0);
        String name       = getIntent().getStringExtra("player_name");
        final  String playerName = (name != null && !name.trim().isEmpty()) ? name : "Player";

        // ── Bind views ────────────────────────────────────────
        TextView       tvMessage     = findViewById(R.id.tv_message);
        TextView       tvScore       = findViewById(R.id.tv_score);
        MaterialButton btnPlayAgain  = findViewById(R.id.btn_play_again);
        MaterialButton btnLeaderboard= findViewById(R.id.btn_leaderboard);
        TextView       btnClose      = findViewById(R.id.btn_close);

        // ── Score display: "X pts" ────────────────────────────
        int pct = (total > 0) ? Math.round(score * 100f / total) : 0;
        tvScore.setText(score + " / " + total);

        // ── Message title based on percentage ─────────────────
        if (pct >= 80) {
            tvMessage.setText("Excellent!");
        } else if (pct >= 50) {
            tvMessage.setText("Nice Work");
        } else {
            tvMessage.setText("Keep Trying!");
        }

        // ── Animate score pop-in ──────────────────────────────
        ScaleAnimation anim = new ScaleAnimation(
                0.4f, 1f, 0.4f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(500);
        anim.setInterpolator(new OvershootInterpolator(1.5f));
        tvScore.startAnimation(anim);

        // ── Save to SQLite ────────────────────────────────────
        new DBHandler(this).insertScore(new Score(playerName, score, total));

        // ── Close button ──────────────────────────────────────
        btnClose.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        });

        // ── Play Again ────────────────────────────────────────
        btnPlayAgain.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        });

        // ── Leaderboard ───────────────────────────────────────
        btnLeaderboard.setOnClickListener(v -> {
            Intent intent = new Intent(this, LeaderboardActivity.class);
            intent.putExtra("player_name", playerName);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }
}