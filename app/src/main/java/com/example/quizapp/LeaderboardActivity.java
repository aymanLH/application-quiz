package com.example.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // ── Back button ───────────────────────────────────────
        TextView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // ── Play Again ────────────────────────────────────────
        MaterialButton btnPlayAgain = findViewById(R.id.btn_play_again);
        btnPlayAgain.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        });

        // ── Load scores ───────────────────────────────────────
        LinearLayout container = findViewById(R.id.ll_scores_container);
        List<Score> scores = new DBHandler(this).getAllScores();

        if (scores.isEmpty()) {
            TextView empty = new TextView(this);
            empty.setText("No scores yet. Play a game!");
            empty.setTextColor(Color.parseColor("#9E9E9E"));
            empty.setTextSize(14f);
            empty.setGravity(Gravity.CENTER);
            empty.setPadding(0, 32, 0, 32);
            container.addView(empty);
        } else {
            for (int i = 0; i < scores.size(); i++) {
                container.addView(buildRow(i + 1, scores.get(i)));
            }
        }
    }

    private View buildRow(int rank, Score score) {
        boolean isFirst = (rank == 1);

        // ── Row wrapper — warm beige for #1, transparent for rest ──
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        int pad = dpToPx(14);
        row.setPadding(pad, pad, pad, pad);
        if (isFirst) {
            android.graphics.drawable.GradientDrawable bg =
                    new android.graphics.drawable.GradientDrawable();
            bg.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
            bg.setColor(Color.parseColor("#FFF3E8"));
            bg.setCornerRadius(dpToPx(14));
            row.setBackground(bg);
            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            rowParams.setMargins(0, 0, 0, dpToPx(10));
            row.setLayoutParams(rowParams);
        } else {
            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            rowParams.setMargins(0, dpToPx(4), 0, dpToPx(4));
            row.setLayoutParams(rowParams);
        }

        // ── Rank circle ───────────────────────────────────────
        TextView tvRank = new TextView(this);
        tvRank.setText(String.valueOf(rank));
        tvRank.setTextColor(Color.WHITE);
        tvRank.setTextSize(15f);
        tvRank.setTypeface(null, Typeface.BOLD);
        tvRank.setGravity(Gravity.CENTER);

        android.graphics.drawable.GradientDrawable circle =
                new android.graphics.drawable.GradientDrawable();
        circle.setShape(android.graphics.drawable.GradientDrawable.OVAL);
        // Orange for #1, light grey for others
        circle.setColor(Color.parseColor(isFirst ? "#FF6B35" : "#E8E8F0"));

        int size = dpToPx(48);
        tvRank.setBackground(circle);
        tvRank.setTextColor(isFirst ? Color.WHITE : Color.parseColor("#888888"));
        LinearLayout.LayoutParams circleParams = new LinearLayout.LayoutParams(size, size);
        circleParams.setMarginEnd(dpToPx(16));
        tvRank.setLayoutParams(circleParams);
        row.addView(tvRank);

        // ── Name + date column ────────────────────────────────
        LinearLayout nameCol = new LinearLayout(this);
        nameCol.setOrientation(LinearLayout.VERTICAL);
        nameCol.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        TextView tvName = new TextView(this);
        tvName.setText(score.getPlayerName());
        tvName.setTextColor(Color.parseColor("#1A1A2E"));
        tvName.setTextSize(16f);
        tvName.setTypeface(null, Typeface.BOLD);
        nameCol.addView(tvName);

        TextView tvDate = new TextView(this);
        tvDate.setText(formatDate(score.getDate()));
        tvDate.setTextColor(Color.parseColor("#AAAAAA"));
        tvDate.setTextSize(12f);
        nameCol.addView(tvDate);

        row.addView(nameCol);

        // ── Score ─────────────────────────────────────────────
        TextView tvScore = new TextView(this);
        tvScore.setText(score.getScoreDisplay());
        tvScore.setTextSize(17f);
        tvScore.setTypeface(null, Typeface.BOLD);
        // Orange for #1, dark grey for rest
        tvScore.setTextColor(Color.parseColor(isFirst ? "#FF6B35" : "#333333"));
        tvScore.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        row.addView(tvScore);

        return row;
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }

    private String formatDate(String raw) {
        try {
            java.text.SimpleDateFormat inFmt =
                    new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
            java.text.SimpleDateFormat outFmt =
                    new java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault());
            return outFmt.format(inFmt.parse(raw));
        } catch (Exception e) {
            return raw;
        }
    }
}