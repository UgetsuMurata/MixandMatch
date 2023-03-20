package com.example.mixandmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class game extends AppCompatActivity {
    DBHandler DB = new DBHandler(this);
    public String username;
    public String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        difficulty = intent.getStringExtra(MainActivity.DIFFICULTY);;
        username = intent.getStringExtra(MainActivity.USERNAME);
    }

    //randomize cards
    //3x2 4x2 5x2 6x2
    private List<Integer> generateCardPlacement(){
        Integer[] cards = {1, 1, 2, 2};
        if (Objects.equals(difficulty, "easy")){
            //3x2
            cards = new Integer[]{1, 1, 2, 2, 3, 3};
        } else if (Objects.equals(difficulty, "moderate")) {
            //4x2
            cards = new Integer[]{1, 1, 2, 2, 3, 3, 4, 4};
        } else if (Objects.equals(difficulty, "difficult")) {
            //4x2
            cards = new Integer[]{1, 1, 2, 2, 3, 3, 4, 4, 5, 5};
        } else {
            cards = new Integer[]{1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6};
        }
        List<Integer> intList = Arrays.asList(cards);
        Collections.shuffle(intList);
        intList.toArray(cards);
        return intList;
    }

    private void storeTimeScore(Integer time_taken){
        //DB.timeLB.insertScore(username, difficulty, time_taken);
    }

    private void addScore(Integer points){
        // factors:
        // how many times flipped for cards as a
        // took how long since first flipped as b
        // (b/10)*a = score added

    }


}