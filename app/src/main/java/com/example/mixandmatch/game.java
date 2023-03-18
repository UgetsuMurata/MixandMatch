package com.example.mixandmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    //randomize cards
    //3x2 4x2 5x2 6x2
    private static List<Integer> generateCardPlacement(String difficulty){
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



}