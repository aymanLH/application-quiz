package com.example.quizapp;

public class Question {

    // ── fields ──────────────────────────────────────────
    private String   text;          // the question sentence
    private String[] options;       // exactly 4 answer choices
    private int      correctIndex;  // 0=A  1=B  2=C  3=D

    // ── constructor ─────────────────────────────────────
    public Question(String text, String[] options, int correctIndex) {
        this.text         = text;
        this.options      = options;
        this.correctIndex = correctIndex;
    }

    // ── getters ──────────────────────────────────────────
    public String   getText()         { return text; }
    public String[] getOptions()      { return options; }
    public int      getCorrectIndex() { return correctIndex; }
}