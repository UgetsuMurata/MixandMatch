package com.example.mixandmatch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    public static final String USER = "user";
    public static final String TIME = "time_based_leaderboard";
    public static final String SCORE = "score_based_leaderboard";

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS user (username TEXT UNIQUE NOT NULL, logged_in TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS time_based_leaderboard (username TEXT, score_easy TEXT, score_moderate TEXT, score_difficult TEXT, score_extreme TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS score_based_leaderboard (username TEXT, score_easy TEXT, score_moderate TEXT, score_difficult TEXT, score_extreme TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS time_based_leaderboard");
        db.execSQL("DROP TABLE IF EXISTS score_based_leaderboard");
    }

    //USER DATABASE FUNCTIONS
    public boolean insertUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        long result = db.insert(USER, null, contentValues);
        if (result == -1)return false;else {
            changeUser(username);
            return true;
        }
    }
    public String getLoggedIn(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT username FROM "+USER+" WHERE logged_in=1;", null);
        String results = cursor.getString(0);
        cursor.close();
        return results;
    }
    public void changeUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("UPDATE "+USER+" SET logged_in=0 WHERE logged_in=1;", null);
        cursor.close();
        cursor = db.rawQuery("UPDATE "+USER+" SET logged_in=1 WHERE username="+username+";", null);
        cursor.close();
    }
    public void deleteUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("DELETE FROM "+USER+" WHERE username="+username+";", null);
        cursor.close();
        cursor = db.rawQuery("DELETE FROM "+TIME+" WHERE username="+username+";", null);
        cursor.close();
        cursor = db.rawQuery("DELETE FROM "+SCORE+" WHERE username="+username+";", null);
        cursor.close();
    }



}