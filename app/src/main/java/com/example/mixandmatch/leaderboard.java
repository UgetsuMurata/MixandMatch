package com.example.mixandmatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class leaderboard extends AppCompatActivity {
    DBHandler DB;
    public TextView Top10Usernames, Top10Scores, ResultsUsername, ResultsScore;

    String share_results;

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
        String difficulty = intent.getStringExtra("DIFFICULTY");
        String mode = intent.getStringExtra("DATABASE");
        if (mode.equals("TIME")) {
            DBHandler.timeLB db_scores = DB.new timeLB();
            top10 = db_scores.getTop10(difficulty.toLowerCase());
        } else {
            DBHandler.scoreLB db_scores = DB.new scoreLB();
            top10 = db_scores.getTop10(difficulty.toLowerCase());
            if (intent.getBooleanExtra("TIMER_DONE", false))
                Toast.makeText(getApplicationContext(), "Time's up!", Toast.LENGTH_SHORT).show();
        }

        for (int i = 0; i < top10.size(); i++){
            usernames = usernames + (i+1) + "." + top10.get(i).get(0) + "\n";
            if (mode.equals("SCORE")) scores = scores + top10.get(i).get(1) + "\n";
            else scores = scores + ConvertSeconds(top10.get(i).get(1)) + "\n";
        }

        if (mode.equals("SCORE")) ResultsScore.setText(SCORE);
        else ResultsScore.setText(ConvertSeconds(SCORE));
        ResultsUsername.setText(USERNAME);
        if (usernames.equals("")){
            findViewById(R.id.noRecords).setVisibility(View.VISIBLE);
        }
        Top10Usernames.setText(usernames);
        Top10Scores.setText(scores);

        if (mode.equals("SCORE")) share_results = "I achieved a score of "+ SCORE +" at "+difficulty.toLowerCase()+" difficulty in the game Mix And Match!";
        else share_results = "I achieved a time of "+ ConvertSeconds(SCORE) +" at "+difficulty.toLowerCase()+" difficulty in the game Mix And Match!";
    }

    private String ConvertSeconds(String secondString){
        int seconds = Integer.parseInt(secondString);
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, secs);
    }

    @Override
    public void onBackPressed() {homeIntent();}
    public void backToMenu(View view) {homeIntent();}
    private void homeIntent() {startActivity(new Intent(leaderboard.this, MainActivity.class));}

    public void shareResults(View view) {

        String txt = share_results.toString();
        String mimeType = "text/plain";

        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("Share your Results")
                .setText(txt)
                .startChooser();
    }
}