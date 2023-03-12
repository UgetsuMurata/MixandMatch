package com.example.mixandmatch;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    /**
     * DATABASE
     * user
     * userID PK, username
     *
     * time_based_leaderboard
     * rowID PK, userID FK, score_easy, score_moderate, score_difficult, score_extreme
     *
     * score_based_leaderboard
     * rowID PK, userID FK, score_easy, score_moderate, score_difficult, score_extreme
     * */
    public static final String DATABASE_NAME = "MMDB.db";

    public DBHandler(Context context){
        super(context, "MMDB.db", null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS user (username TEXT NOT NULL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS time_based_leaderboard (userID INTEGER, score_easy INTEGER, score_moderate INTEGER, score_difficult INTEGER, score_extreme INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS score_based_leaderboard (userID INTEGER, score_easy INTEGER, score_moderate INTEGER, score_difficult INTEGER, score_extreme INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS time_based_leaderboard");
        db.execSQL("DROP TABLE IF EXISTS score_based_leaderboard");
    }
}
