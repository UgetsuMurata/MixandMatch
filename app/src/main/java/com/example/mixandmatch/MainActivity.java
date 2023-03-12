package com.example.mixandmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void click_settings(View view) {
        Intent intent = new Intent(this, settings.class);
        startActivity(intent);
    }

    public void click_info(View view) {
    }

    public void click_leaderboard(View view) {
    }
}