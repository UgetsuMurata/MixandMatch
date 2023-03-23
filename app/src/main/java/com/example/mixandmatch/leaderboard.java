package com.example.mixandmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class leaderboard extends AppCompatActivity {
    DBHandler DB;
    public TextView Top10Usernames, Top10Scores, ResultsUsername, ResultsScore;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        DB = new DBHandler(this);
        Intent intent = getIntent();
        String SCORE = intent.getStringExtra("SCORE");
        String USERNAME = intent.getStringExtra("USERNAME");
        Top10Usernames = findViewById(R.id.top10_usernames);
        Top10Scores = findViewById(R.id.top10_scores);
        ResultsUsername = findViewById(R.id.gameResult_username);
        ResultsScore = findViewById(R.id.gameResult_score);


        ArrayList<ArrayList<String>> top10;
        String usernames = "";
        String scores = "";

        if (intent.getStringExtra("DATABASE").equals("TIME")) {
            DBHandler.timeLB db_scores = DB.new timeLB();
            top10 = db_scores.getTop10(intent.getStringExtra("DIFFICULTY").toLowerCase());
        }
        else {
            DBHandler.scoreLB db_scores = DB.new scoreLB();
            top10 = db_scores.getTop10(intent.getStringExtra("DIFFICULTY").toLowerCase());
        }

        for (int i = 0; i < top10.size(); i++){
            usernames = (i+1) + "." + usernames + top10.get(i).get(0) + "\n";
            scores = scores + top10.get(i).get(0) + "\n";
        }

        ResultsUsername.setText(SCORE);
        ResultsScore.setText(USERNAME);
        if (usernames.equals("")){
            findViewById(R.id.noRecords).setVisibility(View.VISIBLE);
        }
        Top10Usernames.setText(usernames);
        Top10Scores.setText(scores);
    }

    @Override
    public void onBackPressed() {homeIntent();}
    public void backToMenu(View view) {homeIntent();}
    private void homeIntent() {startActivity(new Intent(leaderboard.this, MainActivity.class));}
}