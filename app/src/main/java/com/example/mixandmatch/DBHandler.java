package com.example.mixandmatch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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
        db.execSQL("CREATE TABLE IF NOT EXISTS user (username TEXT UNIQUE NOT NULL, logged_in INT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS time_based_leaderboard (username TEXT, score_easy INT, score_moderate INT, score_difficult INT, score_extreme INT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS score_based_leaderboard (username TEXT, score_easy INT, score_moderate INT, score_difficult INT, score_extreme INT)");
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

    //ABSTRACT CLASS FOR TIME AND SCORE
    abstract static class SCORES{
        public abstract void insertScore(String username, String difficulty, int score);
        public abstract ArrayList<String> getTop10(String difficulty);
    }
    //TIME DATABASE FUNCTIONS
    class timeLB extends SCORES{
        @Override
        public void insertScore(String username, String difficulty, int score) {
            /*
              inserts time-based leaderboard
              difficulty
              @param difficulty:
             *              - easy
             *              - moderate
             *              - difficult
             *              - extreme
             */

            //check if username already in leaderboard
            SQLiteDatabase db = DBHandler.this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT username FROM "+TIME+"WHERE username="+username+";", null);
            if ((cursor != null) && (cursor.getCount() > 0)){
                //if in leaderboard, update score
                db.rawQuery("UPDATE "+TIME+" SET score_"+difficulty+"="+score+" WHERE username="+username+";", null);
            }else{
                //if not in leaderboard, insert score
                db.rawQuery("INSERT INTO "+TIME+"(username, score_"+difficulty+") VALUES("+username+", "+score+");", null);
            }
        }
        @Override
        public ArrayList<String> getTop10(String difficulty) {
            /*
              gets top 10 of time-based leaderboard
              difficulty
              values:
              - easy
              - moderate
              - difficult
              - extreme
              */
            //retrieve difficulty data and username
            SQLiteDatabase db = DBHandler.this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT username, score_"+difficulty+" FROM "+TIME+"WHERE difficulty=score_"+difficulty+" SORT BY score_"+difficulty+" LIMIT 10;", null);
            //return only 10 data
            ArrayList<String> arrayList = new ArrayList<>();
            int columnIndex = cursor.getColumnIndex("username");
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                arrayList.add(cursor.getString(columnIndex));
            }
            return arrayList;
        }
    }
    //SCORE DATABASE FUNCTIONS
    class scoreLB extends SCORES{
        @Override
        public void insertScore(String username, String difficulty, int score) {
            /*
              inserts score-based leaderboard
              difficulty
              @param difficulty:
             *              - easy
             *              - moderate
             *              - difficult
             *              - extreme
             */

            //check if username already in leaderboard
            SQLiteDatabase db = DBHandler.this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT username FROM "+SCORE+"WHERE username="+username+";", null);
            if ((cursor != null) && (cursor.getCount() > 0)){
                //if in leaderboard, update score
                db.rawQuery("UPDATE "+SCORE+" SET score_"+difficulty+"="+score+" WHERE username="+username+";", null);
            }else{
                //if not in leaderboard, insert score
                db.rawQuery("INSERT INTO "+SCORE+"(username, score_"+difficulty+") VALUES("+username+", "+score+");", null);
            }
        }
        @Override
        public ArrayList<String> getTop10(String difficulty) {
            /*
              gets top 10 of score-based leaderboard
              difficulty
              values:
              - easy
              - moderate
              - difficult
              - extreme
              */
            //retrieve difficulty data and username
            SQLiteDatabase db = DBHandler.this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT username, score_"+difficulty+" FROM "+SCORE+"WHERE difficulty=score_"+difficulty+" SORT BY score_"+difficulty+" LIMIT 10;", null);
            //return only 10 data
            ArrayList<String> arrayList = new ArrayList<>();
            int columnIndex = cursor.getColumnIndex("username");
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                arrayList.add(cursor.getString(columnIndex));
            }
            return arrayList;
        }
    }

}