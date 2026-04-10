package com.example.quizapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Data model for one leaderboard entry.
 * Stored in the SQLite "scores" table by DBHandler.
 */
public class Score {

    // -------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------
    private int    id;           // auto-set by SQLite (0 = not yet saved)
    private String playerName;
    private int    score;        // number of correct answers
    private int    total;        // total questions in that game
    private String date;         // ISO-8601 string: "yyyy-MM-dd HH:mm"

    // -------------------------------------------------------------------------
    // Date format used everywhere in the app
    // -------------------------------------------------------------------------
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Use this when creating a new score to save (date = now).
     */
    public Score(String playerName, int score, int total) {
        this.playerName = playerName;
        this.score      = score;
        this.total      = total;
        this.date       = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
                .format(new Date());
    }

    /**
     * Use this when reading a score back from SQLite (all fields known).
     */
    public Score(int id, String playerName, int score, int total, String date) {
        this.id         = id;
        this.playerName = playerName;
        this.score      = score;
        this.total      = total;
        this.date       = date;
    }

    // -------------------------------------------------------------------------
    // Getters & setters
    // -------------------------------------------------------------------------

    public int    getId()         { return id; }
    public void   setId(int id)   { this.id = id; }

    public String getPlayerName()              { return playerName; }
    public void   setPlayerName(String name)   { this.playerName = name; }

    public int    getScore()           { return score; }
    public void   setScore(int score)  { this.score = score; }

    public int    getTotal()           { return total; }
    public void   setTotal(int total)  { this.total = total; }

    public String getDate()            { return date; }
    public void   setDate(String date) { this.date = date; }

    // -------------------------------------------------------------------------
    // Convenience helpers
    // -------------------------------------------------------------------------

    /** Returns percentage rounded to nearest integer, e.g. 70 */
    public int getPercentage() {
        return total > 0 ? Math.round(score * 100f / total) : 0;
    }

    /** Returns a display string like "7 / 10" */
    public String getScoreDisplay() {
        return score + " / " + total;
    }
}