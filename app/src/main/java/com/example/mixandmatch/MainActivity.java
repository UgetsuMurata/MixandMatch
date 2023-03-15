package com.example.mixandmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    DBHandler myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DBHandler(this);

    }

    public void click_info(View view) {
        Intent intent = new Intent(this, information.class);
        startActivity(intent);
    }

    public void click_leaderboard(View view) {
        Intent intent = new Intent(this, settings.class);
        startActivity(intent);
    }

    public void click_delete(View view) {
    }
}