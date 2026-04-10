package com.example.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLite database handler for QuizApp.
 *
 * Database name : quizapp.db
 * Version       : 1
 * Table         : scores
 *
 *   Column       Type     Notes
 *   -----------  -------  ------------------------------------
 *   id           INTEGER  PRIMARY KEY AUTOINCREMENT
 *   player_name  TEXT     NOT NULL
 *   score        INTEGER  correct answers
 *   total        INTEGER  total questions
 *   date         TEXT     "yyyy-MM-dd HH:mm"
 */
public class DBHandler extends SQLiteOpenHelper {

    // -------------------------------------------------------------------------
    // Constants
    // -------------------------------------------------------------------------

    private static final String DB_NAME    = "quizapp.db";
    private static final int    DB_VERSION = 1;

    // Table
    public static final String TABLE_SCORES       = "scores";

    // Columns
    public static final String COL_ID          = "id";
    public static final String COL_PLAYER_NAME = "player_name";
    public static final String COL_SCORE       = "score";
    public static final String COL_TOTAL       = "total";
    public static final String COL_DATE        = "date";

    // -------------------------------------------------------------------------
    // CREATE / DROP statements
    // -------------------------------------------------------------------------

    private static final String SQL_CREATE_SCORES =
            "CREATE TABLE " + TABLE_SCORES + " ("
                    + COL_ID          + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_PLAYER_NAME + " TEXT NOT NULL, "
                    + COL_SCORE       + " INTEGER NOT NULL, "
                    + COL_TOTAL       + " INTEGER NOT NULL, "
                    + COL_DATE        + " TEXT NOT NULL"
                    + ")";

    private static final String SQL_DROP_SCORES =
            "DROP TABLE IF EXISTS " + TABLE_SCORES;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // -------------------------------------------------------------------------
    // SQLiteOpenHelper callbacks
    // -------------------------------------------------------------------------

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SCORES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Simple strategy: drop and recreate.
        // Increment DB_VERSION if you change the schema in future steps.
        db.execSQL(SQL_DROP_SCORES);
        onCreate(db);
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    /**
     * Insert a new score entry.
     *
     * @param score  Score object (id field is ignored — SQLite assigns it)
     * @return       The row id assigned by SQLite, or -1 if insert failed
     */
    public long insertScore(Score score) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_PLAYER_NAME, score.getPlayerName());
        values.put(COL_SCORE,       score.getScore());
        values.put(COL_TOTAL,       score.getTotal());
        values.put(COL_DATE,        score.getDate());

        long newRowId = db.insert(TABLE_SCORES, null, values);
        db.close();
        return newRowId;
    }

    /**
     * Retrieve all scores, sorted by score descending (highest first).
     * Ties are broken by date descending (most recent first).
     *
     * @return  List of Score objects (may be empty, never null)
     */
    public List<Score> getAllScores() {
        List<Score> scores = new ArrayList<>();
        SQLiteDatabase db  = getReadableDatabase();

        String orderBy = COL_SCORE + " DESC, " + COL_DATE + " DESC";
        Cursor cursor  = db.query(
                TABLE_SCORES,
                null,       // all columns
                null,       // no WHERE
                null,       // no WHERE args
                null,       // no GROUP BY
                null,       // no HAVING
                orderBy
        );

        if (cursor != null && cursor.moveToFirst()) {
            int idxId   = cursor.getColumnIndexOrThrow(COL_ID);
            int idxName = cursor.getColumnIndexOrThrow(COL_PLAYER_NAME);
            int idxScore= cursor.getColumnIndexOrThrow(COL_SCORE);
            int idxTotal= cursor.getColumnIndexOrThrow(COL_TOTAL);
            int idxDate = cursor.getColumnIndexOrThrow(COL_DATE);

            do {
                Score s = new Score(
                        cursor.getInt(idxId),
                        cursor.getString(idxName),
                        cursor.getInt(idxScore),
                        cursor.getInt(idxTotal),
                        cursor.getString(idxDate)
                );
                scores.add(s);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return scores;
    }

    /**
     * Delete every row in the scores table.
     * Called by the "Clear All" button in LeaderboardActivity (Step 7).
     */
    public void deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_SCORES, null, null);
        db.close();
    }

    /**
     * Returns the total number of saved scores.
     * Useful for showing a count badge in the leaderboard header.
     */
    public int getScoreCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_SCORES, null);
        int count = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return count;
    }
}